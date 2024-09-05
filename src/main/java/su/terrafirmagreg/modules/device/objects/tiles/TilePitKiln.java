package su.terrafirmagreg.modules.device.objects.tiles;

import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.spi.Heat;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.device.ConfigDevice;
import su.terrafirmagreg.modules.device.init.BlocksDevice;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;


import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.te.TEPlacedItem;
import net.dries007.tfc.util.calendar.Calendar;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import static su.terrafirmagreg.data.Properties.FULL;
import static su.terrafirmagreg.data.Properties.LIT;

public class TilePitKiln extends TEPlacedItem implements ITickable {

  public static final int STRAW_NEEDED = 8;
  public static final int WOOD_NEEDED = 8;
  public static final Vec3i[] DIAGONALS = new Vec3i[]{new Vec3i(1, 0, 1), new Vec3i(-1, 0, 1),
      new Vec3i(1, 0, -1), new Vec3i(-1, 0, -1)};
  private final NonNullList<ItemStack> logItems = NonNullList.withSize(WOOD_NEEDED,
      ItemStack.EMPTY);
  private final NonNullList<ItemStack> strawItems = NonNullList.withSize(STRAW_NEEDED,
      ItemStack.EMPTY);
  @Getter
  private long litTick;
  private boolean isLit;

  public static void convertPlacedItemToPitKiln(World world, BlockPos pos, ItemStack strawStack) {

    var tileOld = TileUtils.getTile(world, pos, TEPlacedItem.class);
    if (tileOld != null) {
      return;
    }

    // Remove inventory items
    // This happens here to stop the block dropping its items in onBreakBlock()
    IItemHandler capOld = tileOld.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
        null);
    ItemStack[] inventory = new ItemStack[4];
    if (capOld != null) {
      for (int i = 0; i < 4; i++) {
        inventory[i] = capOld.extractItem(i, 64, false);
      }
    }

    // Replace the block
    world.setBlockState(pos, BlocksDevice.PIT_KILN.getDefaultState());
    // Play placement sound
    world.playSound(null, pos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 0.5f, 1.0f);

    // Copy TE data
    var tileNew = TileUtils.getTile(world, pos, TilePitKiln.class);
    if (tileNew != null) {
      return;
    }

    // Copy inventory
    IItemHandler capNew = tileNew.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
        null);
    if (capNew != null) {
      for (int i = 0; i < 4; i++) {
        if (inventory[i] != null && !inventory[i].isEmpty()) {
          capNew.insertItem(i, inventory[i], false);
        }
      }
    }

    // Copy misc data
    tileNew.isHoldingLargeItem = tileOld.isHoldingLargeItem;
    if (OreDictUtils.contains(strawStack, "blockStraw")) {
      tileNew.addStrawBlock();
    } else {
      tileNew.addStraw(strawStack.splitStack(1));
    }

  }

  @Override
  public void update() {
    if (isLit) {
      if (world.getTotalWorldTime() % 10 == 0) {
        BlockPos above = pos.up();
        if (world.isAirBlock(above)) {
          world.setBlockState(above, Blocks.FIRE.getDefaultState());
        } else {
          IBlockState stateAbove = world.getBlockState(above);
          if (stateAbove.getMaterial() != Material.FIRE) {
            // consume contents, don't cook items, convert to placed item
            emptyFuelContents();
            convertPitKilnToPlacedItem(world, pos);
            return;
          }
        }

        if (!isValid()) {
          // consume contents, don't cook items, convert to placed item
          emptyFuelContents();
          convertPitKilnToPlacedItem(world, pos);
          return;
        }
      }

      // Check if complete
      long remainingTicks =
          ConfigDevice.BLOCKS.PIT_KILN.ticks - (Calendar.PLAYER_TIME.getTicks() - litTick);
      if (remainingTicks <= 0) {
        // Empty ingredients
        emptyFuelContents();

        // If we missed the point where remainingTicks == 0, then we need to transaction wrap this
        if (remainingTicks < 0) {
          Calendar.runTransaction(remainingTicks, 0, this::cookContents);
        } else {
          cookContents();
        }

        world.setBlockToAir(pos.up());
        updateBlock();

        // Since there will be no items in the pit kiln at this point
        TEPlacedItem.convertPitKilnToPlacedItem(world, pos);
      }
    }
  }

  @Override
  public void onBreakBlock(World worldIn, BlockPos pos, IBlockState state) {
    strawItems.forEach(
        i -> InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), i));
    logItems.forEach(
        i -> InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), i));
    super.onBreakBlock(worldIn, pos, state);
  }

  public boolean isLit() {
    return isLit;
  }

  public boolean hasFuel() {
    return !(logItems.stream().anyMatch(ItemStack::isEmpty) || strawItems.stream()
        .anyMatch(ItemStack::isEmpty));
  }

  /**
   * @return true if an action was taken (passed back through onItemRightClick)
   */
  public boolean onRightClick(EntityPlayer player, ItemStack stack, boolean x, boolean z) {
    if (isLit()) {
      return false;
    }

    // Try and extract an item
    if (player.isSneaking()) {
      // This will search through the logItems, then the strawItems
      ItemStack dropStack = logItems.stream()
          .filter(i -> !i.isEmpty())
          .findFirst()
          .orElseGet(() -> strawItems.stream()
              .filter(i -> !i.isEmpty())
              .findFirst()
              .orElse(ItemStack.EMPTY));
      if (!dropStack.isEmpty()) {
        ItemHandlerHelper.giveItemToPlayer(player, dropStack.splitStack(1));
        updateBlock();

        if (getStrawCount() == 0) {
          TEPlacedItem.convertPitKilnToPlacedItem(world, pos);
        }
        return true;
      }
    } else if (!stack.isEmpty()) {
      // Insert an item
      int strawCount = getStrawCount(), logCount = getLogCount();

      // Straw
      if (OreDictUtils.contains(stack, "straw") && strawCount < STRAW_NEEDED) {
        addStraw(stack.splitStack(1));
        world.playSound(null, pos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 0.5f, 1.0f);
        updateBlock();
        return true;
      }

      // Straw via thatch block (special exception)
      if (stack.getItem() == Item.getItemFromBlock(BlocksCore.THATCH)
          && strawCount <= STRAW_NEEDED - 4) {
        stack.shrink(1);
        addStrawBlock();
        world.playSound(null, pos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 0.5f, 1.0f);
        updateBlock();
        return true;
      }
      // Only insert logItems if all strawItems is inserted
      if (strawCount == STRAW_NEEDED) {
        // Logs
        if (OreDictUtils.contains(stack, "logWood") && logCount < WOOD_NEEDED) {
          addLog(stack.splitStack(1));
          world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 0.5f,
              1.0f);
          updateBlock();
          return true;
        }
        // Light
        if (logCount == WOOD_NEEDED && stack.getItem() instanceof ItemFlintAndSteel) {
          // Flint and steel should light immediately
          return tryLight();
        }
      }
    }
    return false;
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    isLit = nbt.getBoolean("isLit");
    litTick = nbt.getLong("litTick");
    ItemStackHelper.loadAllItems(nbt.getCompoundTag("strawItems"), strawItems);
    ItemStackHelper.loadAllItems(nbt.getCompoundTag("logItems"), logItems);
    super.readFromNBT(nbt);
  }

  @Override
  @NotNull
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    NBTUtils.setGenericNBTValue(nbt, "isLit", isLit);
    NBTUtils.setGenericNBTValue(nbt, "litTick", litTick);
    NBTUtils.setGenericNBTValue(nbt, "strawItems",
        ItemStackHelper.saveAllItems(new NBTTagCompound(), strawItems));
    NBTUtils.setGenericNBTValue(nbt, "logItems",
        ItemStackHelper.saveAllItems(new NBTTagCompound(), logItems));
    return super.writeToNBT(nbt);
  }

  @Override
  protected void updateBlock() {
    IBlockState state = world.getBlockState(pos);
    world.setBlockState(pos,
        state.withProperty(FULL, getStrawCount() == STRAW_NEEDED && getLogCount() == WOOD_NEEDED));
    markForBlockUpdate();
  }

  public int getLogCount() {
    return (int) logItems.stream().filter(i -> !i.isEmpty()).count();
  }

  public int getStrawCount() {
    return (int) strawItems.stream().filter(i -> !i.isEmpty()).count();
  }

  public boolean tryLight() {
    if (hasFuel() && isValid() && !isLit()) {
      BlockPos above = pos.up();
      if (Blocks.FIRE.canPlaceBlockAt(world, above)) {
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
          if (!world.isSideSolid(pos.offset(facing), facing.getOpposite())) {
            return false;
          }
        }
        isLit = true;
        litTick = Calendar.PLAYER_TIME.getTicks();
        updateBlock();
        world.setBlockState(pos, world.getBlockState(pos).withProperty(LIT, true));
        world.setBlockState(above, Blocks.FIRE.getDefaultState());
        //Light other adjacent pit kilns
        for (Vec3i diagonal : DIAGONALS) {
          BlockPos pitPos = pos.add(diagonal);
          var tile = TileUtils.getTile(world, pitPos, TilePitKiln.class);
          if (tile != null) {
            tile.tryLight();
          }
        }
        return true;
      }
    }
    return false;
  }

  public void emptyFuelContents() {
    strawItems.clear();
    logItems.clear();
  }

  private void cookContents() {
    for (int i = 0; i < inventory.getSlots(); i++) {
      ItemStack stack = inventory.getStackInSlot(i);
      ItemStack outputStack = ItemStack.EMPTY;
      // First, heat up the item to max temperature, so the recipe can properly check the temperature of the item
      var cap = CapabilityHeat.get(stack);
      if (cap != null) {
        cap.setTemperature(Heat.maxVisibleTemperature());

        // Only Tier I and below can be melted in a pit kiln
        HeatRecipe recipe = HeatRecipe.get(stack, Metal.Tier.TIER_I);
        if (recipe != null) {
          outputStack = recipe.getOutputStack(stack);
        }

        // Heat up the output as well
        var outputHeat = CapabilityHeat.get(outputStack);
        if (outputHeat != null) {
          outputHeat.setTemperature(Heat.maxVisibleTemperature());
        }
      }
      // Reset item in inventory
      inventory.setStackInSlot(i, outputStack);
    }
  }

  private void addStrawBlock() {
    for (int i = 0; i < 4; i++) {
      addStraw(new ItemStack(ItemsCore.STRAW));
    }
  }

  private void addStraw(ItemStack stack) {
    for (int i = 0; i < strawItems.size(); i++) {
      if (strawItems.get(i).isEmpty()) {
        strawItems.set(i, stack);
        return;
      }
    }
  }

  private void addLog(ItemStack stack) {
    for (int i = 0; i < logItems.size(); i++) {
      if (logItems.get(i).isEmpty()) {
        logItems.set(i, stack);
        return;
      }
    }
  }

  private boolean isValid() {
    for (EnumFacing face : EnumFacing.HORIZONTALS) {
      if (!world.getBlockState(pos.offset(face)).isNormalCube()) {
        return false;
      }
    }
    return world.isSideSolid(pos.down(), EnumFacing.UP);
  }
}
