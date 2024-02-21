package su.terrafirmagreg.modules.rock.objects.tiles;

import lombok.Getter;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.items.ItemGem;
import net.dries007.tfc.objects.te.TEBase;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
public class TERockGemDisplay extends TEBase {

	private final NonNullList<ItemStack> items;
	private int grade;
	private int size;

	public TERockGemDisplay() {
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

	public void onBreakBlock() {
		this.items.forEach((i) -> {
			InventoryHelper.spawnItemStack(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), i);
		});
	}

	public void readFromNBT(@NotNull NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.grade = nbt.getInteger("grade");
		this.size = nbt.getInteger("size");
		this.items.clear();
		ItemStackHelper.loadAllItems(nbt.getCompoundTag("items"), this.items);
	}

	@Nonnull
	public NBTTagCompound writeToNBT(@NotNull NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("grade", grade);
		nbt.setInteger("size", size);
		nbt.setTag("items", ItemStackHelper.saveAllItems(new NBTTagCompound(), this.items));
		return nbt;
	}

	public void onDataPacket(@NotNull NetworkManager net, @NotNull SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
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

	public int getMaxStackSize() {
		Gem.Grade currentGrade = Gem.Grade.valueOf(grade);
		return switch (currentGrade) {
			case CHIPPED -> 8;
			case FLAWED -> 6;
			case NORMAL -> 3;
			case FLAWLESS -> 2;
			default -> 1;
		};
	}

}
