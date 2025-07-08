package su.terrafirmagreg.modules.wood.object.entity;

import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.api.data.DataSerializers;
import su.terrafirmagreg.api.data.Tags;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.RegistryUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderContainer;
import su.terrafirmagreg.modules.soil.api.types.ISoilEntry;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.wood.ConfigWood;
import su.terrafirmagreg.modules.wood.client.gui.GuiWoodPlow;
import su.terrafirmagreg.modules.wood.init.ItemsWood;
import su.terrafirmagreg.modules.wood.object.container.ContainerWoodPlowCart;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.objects.blocks.BlockPlacedItemFlat;

import org.jetbrains.annotations.NotNull;

public class EntityWoodPlowCart extends EntityWoodCartInventory
  implements IInventoryChangedListener, IProviderContainer<ContainerWoodPlowCart, GuiWoodPlow> {

  private static final DataParameter<Boolean> PLOWING = EntityDataManager.createKey(EntityWoodPlowCart.class, DataSerializers.BOOLEAN);
  private static final double BLADEOFFSET = 1.7D;
  @SuppressWarnings("rawtypes")
  private static final DataParameter[] TOOLS = {
    EntityDataManager.createKey(EntityWoodPlowCart.class, DataSerializers.ITEM_STACK),
    EntityDataManager.createKey(EntityWoodPlowCart.class, DataSerializers.ITEM_STACK),
    EntityDataManager.createKey(EntityWoodPlowCart.class, DataSerializers.ITEM_STACK)
  };

  public EntityWoodPlowCart(World worldIn) {
    super(worldIn);
    this.setSize(1.5F, 1.4F);
    this.spacing = 2.4D;
    this.initInventory(this.getName(), true, 3);
    this.inventory.addInventoryChangeListener(this);
  }

  @Override
  public boolean canBePulledBy(Entity pullingIn) {
    if (this.isPassenger(pullingIn)) {
      return false;
    }
    for (String entry : ConfigWood.ITEM.PLOW_CART.canPull) {
      if (entry.equals(pullingIn instanceof EntityPlayer ? "minecraft:player" : EntityList.getKey(pullingIn).toString())) {
        return true;
      }
    }
    return false;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(PLOWING, false);
    for (DataParameter tool : TOOLS) {
      this.dataManager.register(tool, ItemStack.EMPTY);
    }
  }

  public void onUpdate() {
    super.onUpdate();
    EntityPlayer player =
      this.pulling != null && this.pulling.getControllingPassenger() instanceof EntityPlayer
      ? (EntityPlayer) this.pulling.getControllingPassenger()
      : (this.pulling instanceof EntityPlayer ? (EntityPlayer) this.pulling : null);
    if (!this.world.isRemote && this.dataManager.get(PLOWING) && player != null) {
      if (this.prevPosX != this.posX || this.prevPosZ != this.posZ) {
        for (int i = 0; i < this.inventory.getSizeInventory(); i++) {
          if (inventory.getStackInSlot(i) != ItemStack.EMPTY) {
            float offset = 38.0F + i * -38.0F;
            double blockPosX = this.posX + MathHelper.sin((this.rotationYaw - offset) * 0.017453292F) * BLADEOFFSET;
            double blockPosZ = this.posZ - MathHelper.cos((this.rotationYaw - offset) * 0.017453292F) * BLADEOFFSET;
            BlockPos blockPos = new BlockPos(blockPosX, this.posY - 0.5D, blockPosZ);
            BlockPos upPos = blockPos.up();
            Material upMaterial = this.world.getBlockState(upPos).getMaterial();
            if (upMaterial == Material.AIR) {
              handleTool(blockPos, i, player);
            } else if (upMaterial == Material.PLANTS || upMaterial == Material.VINE || this.world.getBlockState(upPos).getBlock() instanceof BlockPlacedItemFlat) {

              this.world.destroyBlock(upPos, false);
              handleTool(blockPos, i, player);
            }
          }
        }
      }
    }
  }

  @Override
  public Item getItemCart() {
    var type = getWood();
    if (type != null) {
      return ItemsWood.PLOW_CART.get(type);
    }
    return getItemCart();
  }

  private void handleTool(BlockPos pos, int slot, EntityPlayer player) {
    if (world.isRemote) {
      return;
    }
    var state = this.world.getBlockState(pos);
    var block = state.getBlock();
    var itemstack = this.inventory.getStackInSlot(slot);

    if (block instanceof ISoilEntry soilEntry) {

      if (RegistryUtils.isTag(block, Tags.GRASS, Tags.DIRT, Tags.DRY_GRASS)) {

        if (StackUtils.doesStackMatchTool(itemstack, ToolClasses.HOE)) {
          this.world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
          this.world.setBlockState(pos, BlocksSoil.FARMLAND.get(soilEntry.getType()).getDefaultState());
          this.damageAndUpdateOnBreak(pos, slot, itemstack, player);

        } else if (StackUtils.doesStackMatchTool(itemstack, ToolClasses.SHOVEL)) {

          this.world.playSound(null, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
          this.world.setBlockState(pos, BlocksSoil.GRASS_PATH.get(soilEntry.getType()).getDefaultState());
          this.damageAndUpdateOnBreak(pos, slot, itemstack, player);
        }
      }
    }
  }

  private void damageAndUpdateOnBreak(BlockPos pos, int slot, ItemStack itemstack, EntityPlayer player) {
    itemstack.damageItem(1, player);
    if (itemstack.isEmpty()) {
      this.dataManager.set(TOOLS[slot], ItemStack.EMPTY);
      if (!world.isRemote) {
        world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
      }
    }

  }

  public boolean getPlowing() {
    return this.dataManager.get(PLOWING);
  }

  @Override
  public void onInventoryChanged(@NotNull IInventory invBasic) {
    for (int i = 0; i < TOOLS.length; ++i) {
      if (this.dataManager.get(TOOLS[i]) != invBasic.getStackInSlot(i)) {
        this.dataManager.set(TOOLS[i], this.inventory.getStackInSlot(i));
      }
    }

  }

  @Override
  public boolean processInitialInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
    if (!this.world.isRemote) {
      if (player.isSneaking()) {
        GuiHandler.openGui(world, new BlockPos(this.getEntityId(), 0, 0), player);
      } else {
        this.dataManager.set(PLOWING, !this.dataManager.get(PLOWING));
      }
    }
    return true;
  }

  @SuppressWarnings("unchecked")
  public ItemStack getTool(int i) {
    return (ItemStack) this.dataManager.get(TOOLS[i]);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void readEntityFromNBT(NBTTagCompound nbt) {
    super.readEntityFromNBT(nbt);
    dataManager.set(PLOWING, nbt.getBoolean("Plowing"));
    for (int i = 0; i < TOOLS.length; i++) {
      this.dataManager.set(TOOLS[i], this.inventory.getStackInSlot(i));
    }
  }

  @Override
  protected void writeEntityToNBT(@NotNull NBTTagCompound nbt) {
    super.writeEntityToNBT(nbt);
    NBTUtils.setGenericNBTValue(nbt, "Plowing", dataManager.get(PLOWING));
  }

  @Override
  public ContainerWoodPlowCart getContainer(InventoryPlayer inventoryPlayer, World world,
                                            IBlockState state, BlockPos pos) {
    return new ContainerWoodPlowCart(inventoryPlayer, inventory, this, inventoryPlayer.player);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public GuiWoodPlow getGuiContainer(InventoryPlayer inventoryPlayer, World world,
                                     IBlockState state, BlockPos pos) {
    return new GuiWoodPlow(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer,
      inventory);
  }
}
