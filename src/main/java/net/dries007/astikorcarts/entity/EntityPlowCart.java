package net.dries007.astikorcarts.entity;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.StackUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import net.dries007.astikorcarts.AstikorCarts;
import net.dries007.astikorcarts.config.ModConfig;
import net.dries007.astikorcarts.init.ModItems;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.BlockPlacedItemFlat;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;

public class EntityPlowCart extends AbstractDrawnInventory implements IInventoryChangedListener {

  private static final DataParameter<Boolean> PLOWING = EntityDataManager.createKey(EntityPlowCart.class, DataSerializers.BOOLEAN);
  private static final double BLADEOFFSET = 1.7D;
  @SuppressWarnings("rawtypes")
  private static final DataParameter[] TOOLS = {
    EntityDataManager.createKey(EntityPlowCart.class, DataSerializers.ITEM_STACK),
    EntityDataManager.createKey(EntityPlowCart.class, DataSerializers.ITEM_STACK),
    EntityDataManager.createKey(EntityPlowCart.class, DataSerializers.ITEM_STACK)
  };

  public EntityPlowCart(World worldIn) {
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
    for (String entry : ModConfig.plowCart.canPull) {
      if (entry.equals(pullingIn instanceof EntityPlayer ? "minecraft:player" : EntityList.getKey(pullingIn).toString())) {
        return true;
      }
    }
    return false;
  }

  public Item getCartItem() {
    return ModItems.PLOWCART;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(PLOWING, false);
    for (int i = 0; i < TOOLS.length; i++) {
      this.dataManager.register(TOOLS[i], ItemStack.EMPTY);
    }
  }

  public void onUpdate() {
    super.onUpdate();
    EntityPlayer player =
      this.pulling != null && this.pulling.getControllingPassenger() instanceof EntityPlayer ? (EntityPlayer) this.pulling.getControllingPassenger()
                                                                                             : (this.pulling instanceof EntityPlayer
                                                                                                ? (EntityPlayer) this.pulling : null);
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
            } else if (upMaterial == Material.PLANTS || upMaterial == Material.VINE || this.world.getBlockState(upPos)
              .getBlock() instanceof BlockPlacedItemFlat) {
              this.world.destroyBlock(upPos, false);
              handleTool(blockPos, i, player);
            }
          }
        }
      }
    }
  }

  public boolean getPlowing() {
    return this.dataManager.get(PLOWING);
  }

  @Override
  public void onInventoryChanged(IInventory invBasic) {
    for (int i = 0; i < TOOLS.length; ++i) {
      if (this.dataManager.get(TOOLS[i]) != invBasic.getStackInSlot(i)) {
        this.dataManager.set(TOOLS[i], this.inventory.getStackInSlot(i));
      }
    }

  }

  @Override
  public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    if (!this.world.isRemote) {
      if (player.isSneaking()) {
        player.openGui(AstikorCarts.instance, 1, this.world, this.getEntityId(), 0, 0);
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
  protected void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    dataManager.set(PLOWING, compound.getBoolean("Plowing"));
    for (int i = 0; i < TOOLS.length; i++) {
      this.dataManager.set(TOOLS[i], this.inventory.getStackInSlot(i));
    }
  }

  @Override
  protected void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setBoolean("Plowing", dataManager.get(PLOWING));
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

  private void handleTool(BlockPos pos, int slot, EntityPlayer player) {
    if (world.isRemote) {return;}
    IBlockState state = this.world.getBlockState(pos);
    ItemStack itemStack = this.inventory.getStackInSlot(slot);
    Item item = itemStack.getItem();

    if (state.getBlock() instanceof BlockRockVariant blockRock) {
      if (blockRock.getType() == Rock.Type.GRASS || blockRock.getType() == Rock.Type.DIRT || blockRock.getType() == Rock.Type.DRY_GRASS) {
        if (StackUtils.doesStackMatchTool(itemStack, ToolClasses.HOE)) {
          world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
          world.setBlockState(pos, BlockRockVariant.get(blockRock.getRock(), Rock.Type.FARMLAND).getDefaultState());
          damageAndUpdateOnBreak(pos, slot, itemStack, player);


        } else if (StackUtils.doesStackMatchTool(itemStack, ToolClasses.SHOVEL)) {
          world.playSound(null, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
          this.world.setBlockState(pos, BlockRockVariant.get(blockRock.getRock(), Rock.Type.PATH).getDefaultState());
          this.damageAndUpdateOnBreak(pos, slot, itemStack, player);
        }
      }
    }
  }
}
