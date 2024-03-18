package su.terrafirmagreg.modules.wood.objects.entities;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class EntityWoodCartInventory extends EntityWoodCart {

	public InventoryBasic inventory;
	protected IItemHandler itemHandler;

	public EntityWoodCartInventory(World worldIn) {
		super(worldIn);
	}

	@Override
	public void onDestroyed(DamageSource source, boolean byCreativePlayer) {
		if (this.world.getGameRules().getBoolean("doEntityDrops")) {
			super.onDestroyed(source, byCreativePlayer);
			InventoryHelper.dropInventoryItems(this.world, this, inventory);
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			this.inventory.setInventorySlotContents(nbttagcompound.getByte("Slot") & 255, new ItemStack(nbttagcompound));
		}
	}

	@Override
	protected void writeEntityToNBT(@NotNull NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.inventory.getSizeInventory(); ++i) {
			ItemStack itemstack = this.inventory.getStackInSlot(i);
			if (!itemstack.isEmpty()) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				itemstack.writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		compound.setTag("Items", nbttaglist);
	}

	@Override
	public boolean hasCapability(@NotNull Capability<?> capability, @Nullable net.minecraft.util.EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public <T> T getCapability(@NotNull Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) itemHandler;
		}
		return super.getCapability(capability, facing);
	}

	/**
	 * Initializes an inventory with an IInventory wrapper.
	 *
	 * @param title      the title of the inventory
	 * @param customName wether or not this inventory has a custom name
	 * @param size       the size of the inventory.
	 */
	protected void initInventory(String title, boolean customName, int size) {
		this.inventory = new InventoryBasic(title, customName, size);
		this.itemHandler = new InvWrapper(this.inventory);
	}
}
