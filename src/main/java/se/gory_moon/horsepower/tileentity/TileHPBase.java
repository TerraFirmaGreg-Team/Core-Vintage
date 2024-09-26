package se.gory_moon.horsepower.tileentity;

import su.terrafirmagreg.api.base.tile.BaseTile;
import su.terrafirmagreg.api.util.BlockUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.RangedWrapper;

import se.gory_moon.horsepower.recipes.HPRecipeBase;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.Setter;

public abstract class TileHPBase extends BaseTile {

  private final IItemHandler handlerBottom;
  protected NonNullList<ItemStack> itemStacks = NonNullList.withSize(3, ItemStack.EMPTY);
  @Getter
  protected IHPInventory inventory;
  @Setter
  private EnumFacing forward = null;
  private IItemHandler handlerNull = null;
  private IItemHandler handlerIn = null;

  public TileHPBase(int inventorySize) {
    itemStacks = NonNullList.withSize(inventorySize, ItemStack.EMPTY);

    inventory = new IHPInventory() {

      @Override
      public int getSizeInventory() {
        return itemStacks.size();
      }

      @Override
      public boolean isEmpty() {
        for (ItemStack itemstack : itemStacks) {
          if (!itemstack.isEmpty()) {
            return false;
          }
        }

        return true;
      }

      @Override
      public ItemStack getStackInSlot(int index) {
        if (index >= itemStacks.size()) {
          return ItemStack.EMPTY;
        }
        return itemStacks.get(index);
      }

      @Override
      public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = ItemStackHelper.getAndSplit(itemStacks, index, count);
        if (!stack.isEmpty()) {
          markDirty();
        }
        return stack;
      }

      @Override
      public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = ItemStackHelper.getAndRemove(itemStacks, index);
        return stack;
      }

      @Override
      public void setInventorySlotContents(int index, ItemStack stack) {
        TileHPBase.this.setInventorySlotContents(index, stack);
      }

      @Override
      public int getInventoryStackLimit() {
        return TileHPBase.this.getInventoryStackLimit();
      }

      @Override
      public void markDirty() {
        TileHPBase.this.markDirty();
      }

      @Override
      public boolean isUsableByPlayer(EntityPlayer player) {
        return getWorld().getTileEntity(getPos()) == TileHPBase.this &&
               player.getDistanceSq((double) getPos().getX() + 0.5D, (double) getPos().getY() + 0.5D, (double) getPos().getZ() + 0.5D) <= 64.0D;
      }

      @Override
      public void openInventory(EntityPlayer player) {
      }

      @Override
      public void closeInventory(EntityPlayer player) {
      }

      @Override
      public boolean isItemValidForSlot(int index, ItemStack stack) {
        return TileHPBase.this.isItemValidForSlot(index, stack);
      }

      @Override
      public int getField(int id) {
        return TileHPBase.this.getField(id);
      }

      @Override
      public void setField(int id, int value) {
        TileHPBase.this.setField(id, value);
      }

      @Override
      public int getFieldCount() {
        return TileHPBase.this.getFieldCount();
      }

      @Override
      public void clear() {
        itemStacks.clear();
      }

      @Override
      public void setSlotContent(int index, ItemStack stack) {
        itemStacks.set(index, stack);

        if (index == 0 && stack.getCount() > this.getInventoryStackLimit(stack)) {
          stack.setCount(this.getInventoryStackLimit(stack));
        }
      }

      public int getInventoryStackLimit(ItemStack stack) {
        return TileHPBase.this.getInventoryStackLimit(stack);
      }

      @Override
      public String getName() {
        return TileHPBase.this.getName();
      }

      @Override
      public boolean hasCustomName() {
        return false;
      }

      @Override
      public ITextComponent getDisplayName() {
        return TileHPBase.this.getDisplayName();
      }
    };
    handlerIn = new RangedWrapper(new InvWrapper(inventory), 0, 1);
    handlerBottom = new RangedWrapper(new InvWrapper(inventory), 1, getOutputSlot() + 1);
    handlerNull = new InvWrapper(inventory);
  }

  public static boolean canCombine(ItemStack stack1, ItemStack stack2) {
    return stack1.getItem() == stack2.getItem() && (stack1.getMetadata() == stack2.getMetadata() &&
                                                    (stack1.getCount() <= stack1.getMaxStackSize() && ItemStack.areItemStackTagsEqual(stack1, stack2)));
  }

  public void setInventorySlotContents(int index, ItemStack stack) {
    inventory.setSlotContent(index, stack);
  }

  public abstract int getInventoryStackLimit();

  public abstract boolean isItemValidForSlot(int index, ItemStack stack);

  public int getField(int id) {
    return 0;
  }

  public void setField(int id, int value) {
  }

  public int getFieldCount() {
    return 0;
  }

  public int getInventoryStackLimit(ItemStack stack) {
    return getInventoryStackLimit();
  }

  public abstract String getName();

  public abstract int getOutputSlot();

  public abstract ItemStack getRecipeItemStack();

  public ItemStack removeStackFromSlot(int index) {
    return inventory.removeStackFromSlot(index);
  }

  public boolean canWork() {
    if (getStackInSlot(0).isEmpty()) {
      return false;
    } else {
      HPRecipeBase recipeBase = getRecipe();
      if (recipeBase == null) {
        return false;
      }

      ItemStack input = recipeBase.getInput();
      ItemStack itemstack = recipeBase.getOutput();
      ItemStack secondary = recipeBase.getSecondary();

      if (getStackInSlot(0).getCount() < input.getCount()) {
        return false;
      }
      if (itemstack.isEmpty()) {
        return false;
      }

      ItemStack output = getStackInSlot(1);
      ItemStack outputSecondary = secondary.isEmpty() ? ItemStack.EMPTY : inventory.getStackInSlot(2);
      if (!secondary.isEmpty() && !outputSecondary.isEmpty()) {
        if (!outputSecondary.isItemEqual(secondary)) {
          return false;
        }
        if (outputSecondary.getCount() + secondary.getCount() > secondary.getMaxStackSize()) {
          return false;
        }
      }
      return output.isEmpty() || output.isItemEqual(itemstack) && output.getCount() + itemstack.getCount() <= output.getMaxStackSize();
    }
  }

  public ItemStack getStackInSlot(int index) {
    return inventory.getStackInSlot(index);
  }

  public abstract HPRecipeBase getRecipe();

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);

    itemStacks = NonNullList.withSize(inventory.getSizeInventory(), ItemStack.EMPTY);
    ItemStackHelper.loadAllItems(compound, itemStacks);

    if (canBeRotated()) {
      forward = EnumFacing.byName(compound.getString("forward"));
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    super.writeToNBT(compound);
    ItemStackHelper.saveAllItems(compound, itemStacks);

    if (canBeRotated()) {
      compound.setString("forward", getForward().getName());
    }
    return compound;
  }

  @Override
  public void markDirty() {
    BlockUtils.notifyBlockUpdate(world, pos, 2);
    super.markDirty();
  }


  @Override
  @SideOnly(Side.CLIENT)
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    handleUpdateTag(pkt.getNbtCompound());
  }

  @Override
  public void handleUpdateTag(NBTTagCompound tag) {
    readFromNBT(tag);
    markDirty();
  }

  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing));
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      if (facing == null) {
        return (T) handlerNull;
      } else if (facing == EnumFacing.DOWN) {
        return (T) handlerBottom;
      } else {
        return (T) handlerIn;
      }
    }
    return super.getCapability(capability, facing);
  }


  public boolean canBeRotated() {
    return false;
  }

  public EnumFacing getForward() {
    if (forward == null) {
      return EnumFacing.NORTH;
    }
    return forward;
  }

}
