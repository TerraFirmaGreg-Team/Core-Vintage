package su.terrafirmagreg.modules.device.object.tile;

import lombok.Getter;

import net.dries007.tfc.util.Alloy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.api.base.tile.BaseTileInventory;
import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.modules.device.client.gui.GuiAlloyCalculator;
import su.terrafirmagreg.modules.device.object.container.ContainerAlloyCalculator;

@Getter
public class TileAlloyCalculator extends BaseTileInventory implements IProviderContainer<ContainerAlloyCalculator, GuiAlloyCalculator> {


  private Alloy alloy;

  public TileAlloyCalculator() {
    super(9);
  }

  @Override
  public void setAndUpdateSlots(int slot) {
    Alloy computedAlloy = new Alloy();
    computedAlloy.add(this.inventory.getStackInSlot(slot));
    if (computedAlloy.getAmount() == 0) {
      this.alloy = null;
    } else {
      this.alloy = computedAlloy;
    }
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
