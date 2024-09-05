package su.terrafirmagreg.modules.device.client.render;

import su.terrafirmagreg.modules.device.objects.itemblocks.ItemBlockFridge;
import su.terrafirmagreg.modules.device.objects.tiles.TileFridge;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class TEISRFridge extends TileEntityItemStackRenderer {

  @Override
  public void renderByItem(ItemStack itemStack, float partialTicks) {
    if (itemStack.getItem() instanceof ItemBlockFridge) {
      TileEntityRendererDispatcher.instance.render(new TileFridge(), 0.0D, 0.0D, 0.0D,
          partialTicks);
    }
  }
}
