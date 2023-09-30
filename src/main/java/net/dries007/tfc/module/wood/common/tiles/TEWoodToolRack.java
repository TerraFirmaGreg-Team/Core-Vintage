package net.dries007.tfc.module.wood.common.tiles;

import gregtech.api.items.toolitem.IGTTool;
import net.dries007.tfc.module.core.objects.items.ItemFireStarter;
import net.dries007.tfc.module.core.api.tile.TEBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TEWoodToolRack extends TEBase {
    private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);

    /**
     * @return true if this item can be put on a tool rack, false otherwise
     */
    public static boolean isItemEligible(@Nullable ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        var item = stack.getItem();
        return item instanceof IGTTool || item instanceof ItemFireStarter;
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public void onBreakBlock() {
        items.forEach(i -> InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), i));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        items.clear();
        ItemStackHelper.loadAllItems(nbt.getCompoundTag("items"), items);
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setTag("items", ItemStackHelper.saveAllItems(new NBTTagCompound(), items));
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        markForBlockUpdate();
    }

    public boolean onRightClick(EntityPlayer player, EnumHand hand, int slot) {
        ItemStack slotItem = items.get(slot);
        ItemStack heldItem = player.getHeldItem(hand);
        if (!slotItem.isEmpty()) {
            ItemHandlerHelper.giveItemToPlayer(player, slotItem.splitStack(1));
            items.set(slot, ItemStack.EMPTY);
        } else if (isItemEligible(heldItem)) {
            items.set(slot, player.isCreative() ? heldItem.copy() : heldItem.splitStack(1));
        } else {
            return false;
        }
        markForBlockUpdate();
        return true;
    }
}
