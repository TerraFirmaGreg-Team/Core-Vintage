package net.dries007.tfc.objects.te;

import su.terrafirmagreg.api.base.object.tile.spi.BaseTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.items.ItemGem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TEGemDisplay extends BaseTile {

  private final NonNullList<ItemStack> items;
  private int grade;
  private int size;

  public TEGemDisplay() {
    this.items = NonNullList.withSize(8, ItemStack.EMPTY);
    grade = 0;
    size = 0;
  }

  public boolean isItemEligible(@Nullable ItemStack stack) {
    if (stack != null && !stack.isEmpty()) {
      Item item = stack.getItem();
      if (item instanceof ItemGem) {
        if (size == 0) {
          grade = Gem.Grade.valueOf(stack.getItemDamage()).ordinal();
          return true;
        }
        return grade == stack.getItemDamage();
      }
    }
    return false;
  }

  public NonNullList<ItemStack> getItems() {
    return this.items;
  }

  public void onBreakBlock() {
    this.items.forEach((i) -> {
      InventoryHelper.spawnItemStack(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), i);
    });
  }

  public void readFromNBT(NBTTagCompound nbt) {
    super.readFromNBT(nbt);
    this.grade = nbt.getInteger("grade");
    this.size = nbt.getInteger("size");
    this.items.clear();
    ItemStackHelper.loadAllItems(nbt.getCompoundTag("items"), this.items);
  }

  @Nonnull
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    super.writeToNBT(nbt);
    nbt.setInteger("grade", grade);
    nbt.setInteger("size", size);
    nbt.setTag("items", ItemStackHelper.saveAllItems(new NBTTagCompound(), this.items));
    return nbt;
  }

  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
    super.onDataPacket(net, packet);
    this.markForBlockUpdate();
  }

  public boolean onRightClick(EntityPlayer player, EnumHand hand) {
    ItemStack heldItem = player.getHeldItem(hand);
    if (heldItem.isEmpty() && size > 0) {
      ItemHandlerHelper.giveItemToPlayer(player, items.get(size - 1));
      this.items.set(size - 1, ItemStack.EMPTY);
      size--;
    } else if (isItemEligible(heldItem) && size < getMaxStackSize()) {
      if (player.isCreative()) {
        ItemStack temp = heldItem.copy();
        temp.setCount(1);
        this.items.set(size, temp);
      } else {
        this.items.set(size, heldItem.splitStack(1));
      }
      size++;
    }
    this.markForBlockUpdate();
    return true;
  }

  public int getGrade() {
    return grade;
  }

  public int getSize() {
    return size;
  }

  public int getMaxStackSize() {
    Gem.Grade currentGrade = Gem.Grade.valueOf(grade);
    switch (currentGrade) {
      case CHIPPED:
        return 8;
      case FLAWED:
        return 6;
      case NORMAL:
        return 3;
      case FLAWLESS:
        return 2;
      default:
        return 1;
    }
  }

}
