package su.terrafirmagreg.modules.device.content.tile;

import su.terrafirmagreg.framework.manager.content.base.tile.spi.BaseTileInventory;
import su.terrafirmagreg.framework.manager.content.provider.IProviderContainer;
import su.terrafirmagreg.modules.core.feature.heat.capability.CapabilityHeat;
import su.terrafirmagreg.modules.device.content.container.ContainerAlloyCalculator;
import su.terrafirmagreg.modules.device.content.gui.GuiAlloyCalculator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.util.Alloy;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class TileAlloyCalculator extends BaseTileInventory implements IProviderContainer<ContainerAlloyCalculator, GuiAlloyCalculator> {


  @Nullable
  private Alloy alloy;

  public TileAlloyCalculator() {
    super(9);
  }


  @Override
  public void setAndUpdateSlots(int slot) {
    calculateAlloy();
  }

  public void calculateAlloy() {
    Alloy computedAlloy = new Alloy();
    for (int slot = 0; slot < this.inventory.getSlots(); slot++) {
      computedAlloy.add(this.inventory.getStackInSlot(slot));
    }
    if (computedAlloy.getAmount() == 0) {this.alloy = null;} else {this.alloy = computedAlloy;}
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    if (!CapabilityHeat.has(stack)) {
      return false;
    }
    return CapabilityHeat.get(stack) != null;
  }

  @Override
  public ContainerAlloyCalculator getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new ContainerAlloyCalculator(inventoryPlayer, this);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public GuiAlloyCalculator getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new GuiAlloyCalculator(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this);
  }
}
