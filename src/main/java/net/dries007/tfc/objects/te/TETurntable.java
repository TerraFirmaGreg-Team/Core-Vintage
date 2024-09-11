package net.dries007.tfc.objects.te;

import su.terrafirmagreg.api.base.tile.BaseTileTickableInventory;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.data.Properties;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.recipes.knapping.KnappingTypes;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.client.TFCSounds;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@MethodsReturnNonnullByDefault
public class TETurntable extends BaseTileTickableInventory {

  private static final List<Item> POTTERY = TFCRegistries.KNAPPING.getValuesCollection().stream()
          .filter(recipe -> recipe.getType()
                  .equals(KnappingTypes.CLAY))
          .map(recipe -> recipe.getOutput(ItemStack.EMPTY)
                  .getItem())
          .collect(Collectors.toList());

  private int speed;
  private int progress;
  private int internalProgress;

  public TETurntable() {
    super(1);
    speed = 0;
    progress = 0;
    internalProgress = 0;
  }

  public int getSpeed() {
    return speed;
  }

  public int getInternalProgress() {
    return internalProgress;
  }

  public void rotate() {
    speed++;
    markForSync();
    world.playSound(null, pos, TFCSounds.QUERN_USE, SoundCategory.BLOCKS, 1.0F, 3.0F);
  }

  @Override
  public void update() {
    super.update();
    if (speed > 0 && world.getTotalWorldTime() % 10 == 0) {
      speed--;
    }
    speed = MathHelper.clamp(speed, 0, 20);
    if (speed > 0) {
      internalProgress++;
    }
    int clay = getClayAmount();
    if (clay > 0 && speed > 0) {
      if (world.isRemote && speed > 5) {
        world.spawnParticle(EnumParticleTypes.ITEM_CRACK, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                (world.rand.nextDouble() - world.rand.nextDouble()) / 4.0D, world.rand.nextDouble() / 4.0D,
                (world.rand.nextDouble() - world.rand.nextDouble()) / 4.0D, Item.getIdFromItem(Items.CLAY_BALL));
      }
      if (speed > 15 && hasPottery()) {
        progress++;
        if (progress > 40) {
          progress = 0;
          speed = 15;
          if (!world.isRemote) {
            world.setBlockState(pos, getBlockType().getDefaultState()
                    .withProperty(Properties.CLAY_LEVEL, clay - 1));
            StackUtils.spawnItemStack(world, pos.up(), item());
            markForSync();
          }
        }
      }
    } else {
      progress = 0;
    }
    if (speed == 0 && internalProgress % 360 == 0) {
      internalProgress = 0;
    }
  }

  public int getClayAmount() {
    return world.getBlockState(pos).getValue(Properties.CLAY_LEVEL);
  }

  public boolean hasPottery() {
    return isPottery(item());
  }

  private ItemStack item() {
    return inventory.getStackInSlot(0);
  }

  public static boolean isPottery(ItemStack stack) {
    return POTTERY.contains(stack.getItem());
  }

  @Override
  public int getSlotLimit(int slot) {
    return 1;
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    return POTTERY.contains(stack.getItem());
  }

  @Override
  public void setAndUpdateSlots(int slot) {
    markForBlockUpdate();
    super.setAndUpdateSlots(slot);
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    super.readFromNBT(nbt);
    speed = nbt.getInteger("speed");
    progress = nbt.getInteger("progress");
    internalProgress = nbt.getInteger("internalProgress");
  }

  @Override
  @NotNull
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setInteger("speed", speed);
    nbt.setInteger("progress", progress);
    nbt.setInteger("internalProgress", internalProgress);
    return super.writeToNBT(nbt);
  }

  public void onBreakBlock(World world, BlockPos pos, IBlockState state) {
    StackUtils.spawnItemStack(world, pos, inventory.getStackInSlot(0));
    int clay = state.getValue(Properties.CLAY_LEVEL);
    if (clay > 0) {
      StackUtils.spawnItemStack(world, pos, new ItemStack(Items.CLAY_BALL, 5 * clay));
    }
  }
}
