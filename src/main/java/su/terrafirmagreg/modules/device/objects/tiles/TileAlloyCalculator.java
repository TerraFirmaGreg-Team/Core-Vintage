package su.terrafirmagreg.modules.device.objects.tiles;

import su.terrafirmagreg.api.base.tile.BaseTile;
import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.modules.device.client.gui.GuiAlloyCalculator;
import su.terrafirmagreg.modules.device.objects.containers.ContainerAlloyCalculator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;


import net.dries007.tfc.util.Alloy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

public class TileAlloyCalculator extends BaseTile implements
        IProviderContainer<ContainerAlloyCalculator, GuiAlloyCalculator> {

  public final ItemStackHandler stacks = new ItemStackHandler(9);
  @Getter
  private Alloy alloy;

  public TileAlloyCalculator() {
  }

  public void calculateAlloy() {
    Alloy computedAlloy = new Alloy();
    for (int slot = 0; slot < this.stacks.getSlots(); slot++) {
      computedAlloy.add(this.stacks.getStackInSlot(slot));
    }
    if (computedAlloy.getAmount() == 0) {
      this.alloy = null;
    } else {
      this.alloy = computedAlloy;
    }
  }

  @Override
  public void readFromNBT(@NotNull NBTTagCompound nbt) {
    super.readFromNBT(nbt);
    this.stacks.deserializeNBT(nbt.getCompoundTag("stacks"));
  }

  @Override
  public NBTTagCompound writeToNBT(@NotNull NBTTagCompound nbt) {
    nbt = super.writeToNBT(nbt);
    NBTUtils.setGenericNBTValue(nbt, "stacks", this.stacks.serializeNBT());
    return nbt;
  }

  @Override
  public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(
            capability, facing);
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
  public ContainerAlloyCalculator getContainer(InventoryPlayer inventoryPlayer, World world,
          IBlockState state, BlockPos pos) {
    return new ContainerAlloyCalculator(inventoryPlayer, this);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public GuiAlloyCalculator getGuiContainer(InventoryPlayer inventoryPlayer, World world,
          IBlockState state, BlockPos pos) {
    return new GuiAlloyCalculator(getContainer(inventoryPlayer, world, state, pos));
  }
}
