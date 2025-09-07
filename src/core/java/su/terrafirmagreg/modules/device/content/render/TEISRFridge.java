package su.terrafirmagreg.modules.device.content.render;

import su.terrafirmagreg.modules.device.content.item.ItemBlockFridge;
import su.terrafirmagreg.modules.device.content.tile.TileFridge;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class TEISRFridge extends TileEntityItemStackRenderer {

  @Override
  public void renderByItem(ItemStack itemStack, float partialTicks) {
    if (itemStack.getItem() instanceof ItemBlockFridge) {
      TileEntityRendererDispatcher.instance.render(new TileFridge(), 0.0D, 0.0D, 0.0D, partialTicks);
    }
  }
}
