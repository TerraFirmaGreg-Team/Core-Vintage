package su.terrafirmagreg.modules.device.client.render;

import su.terrafirmagreg.modules.device.object.tile.TileFridge;
import su.terrafirmagreg.modules.device.object.tile.TileWireDrawBench;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.items.itemblocks.ItemBlockFridge;
import net.dries007.tfc.objects.items.itemblocks.ItemBlockWireDrawBench;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TEISRFridge extends TileEntityItemStackRenderer {

  private static final TileWireDrawBench teWireDrawBench = new TileWireDrawBench();
  private static final TileFridge teFridge = new TileFridge();

  @Override
  public void renderByItem(ItemStack itemStack, float partialTicks) {
    if (itemStack.getItem() instanceof ItemBlockWireDrawBench) {
      TileEntityRendererDispatcher.instance.render(teWireDrawBench, 0.0D, 0.0D, 0.0D, partialTicks);
    } else if (itemStack.getItem() instanceof ItemBlockFridge) {
      TileEntityRendererDispatcher.instance.render(teFridge, 0.0D, 0.0D, 0.0D, partialTicks);
    }
  }
}
