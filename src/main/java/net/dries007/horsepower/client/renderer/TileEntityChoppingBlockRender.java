package net.dries007.horsepower.client.renderer;

import su.terrafirmagreg.modules.device.object.tile.TileChopperManual;

import net.minecraft.client.renderer.GlStateManager;

public class TileEntityChoppingBlockRender extends TileEntityHPBaseRenderer<TileChopperManual> {

  @Override
  public void render(TileChopperManual te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);
    if (!te.getStackInSlot(0).isEmpty()) {renderStillItem(te, te.getStackInSlot(0), 0.5F, 0.63F, 0.5F, 2F);}
    GlStateManager.popMatrix();

    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);
    if (!te.getStackInSlot(1).isEmpty()) {renderStillItem(te, te.getStackInSlot(1), 0.5F, 0.63F, 0.5F, 2F);}
    GlStateManager.popMatrix();

    super.render(te, x, y + 1, z, partialTicks, destroyStage, alpha);
  }
}
