package su.terrafirmagreg.modules.device.objects.tiles;

import lombok.Getter;
import net.dries007.tfc.util.Alloy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.tile.IContainerProvider;
import su.terrafirmagreg.api.spi.tile.TEBase;
import su.terrafirmagreg.modules.device.client.gui.GuiAlloyCalculator;
import su.terrafirmagreg.modules.device.objects.container.ContainerAlloyCalculator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TEAlloyCalculator extends TEBase implements IContainerProvider<ContainerAlloyCalculator, GuiAlloyCalculator> {

	public final ItemStackHandler stacks = new ItemStackHandler(9);
	@Getter
	private Alloy alloy;

	public TEAlloyCalculator() {}

	public void calculateAlloy() {
		Alloy computedAlloy = new Alloy();
		for (int slot = 0; slot < this.stacks.getSlots(); slot++) {
			computedAlloy.add(this.stacks.getStackInSlot(slot));
		}
		if (computedAlloy.getAmount() == 0)
			this.alloy = null;
		else
			this.alloy = computedAlloy;
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		compound.setTag("Stacks", this.stacks.serializeNBT());
		return compound;
	}

	@Override
	public void readFromNBT(@NotNull NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.stacks.deserializeNBT(compound.getCompoundTag("Stacks"));
	}

	@Nullable
	@Override
	public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) this.stacks;
		}

		return null;
	}

	@Override
	public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public ContainerAlloyCalculator getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
		return new ContainerAlloyCalculator(inventoryPlayer, this);
	}

	@Override
	public GuiAlloyCalculator getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
		return new GuiAlloyCalculator(getContainer(inventoryPlayer, world, state, pos));
	}
}
