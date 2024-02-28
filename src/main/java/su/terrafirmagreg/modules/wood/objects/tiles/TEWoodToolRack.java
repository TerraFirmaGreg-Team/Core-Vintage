package su.terrafirmagreg.modules.wood.objects.tiles;

import gregtech.api.items.toolitem.IGTTool;
import lombok.Getter;
import net.dries007.tfc.objects.items.ItemFireStarter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.tile.TEBase;
import su.terrafirmagreg.api.util.OreDictUtils;


@Getter
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
		return !item.getToolClasses(stack).isEmpty() ||
				item instanceof IGTTool ||
				item instanceof ItemFireStarter ||
				item instanceof ItemTool ||
				item instanceof ItemBow ||
				item instanceof ItemHoe ||
				item instanceof ItemSword ||
				item instanceof ItemFlintAndSteel ||
				OreDictUtils.contains(stack, "tool");
	}

	public void onBreakBlock() {
		items.forEach(i -> InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), i));
	}

	@Override
	public void readFromNBT(@NotNull NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		items.clear();
		ItemStackHelper.loadAllItems(nbt.getCompoundTag("items"), items);
	}

	@Override
	@NotNull
	public NBTTagCompound writeToNBT(@NotNull NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setTag("items", ItemStackHelper.saveAllItems(new NBTTagCompound(), items));
		return nbt;
	}

	@Override
	public void onDataPacket(@NotNull NetworkManager net, SPacketUpdateTileEntity pkt) {
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
