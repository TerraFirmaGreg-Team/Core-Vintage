package su.terrafirmagreg.modules.device.client.render;

import su.terrafirmagreg.api.base.tesr.BaseTESR;
import su.terrafirmagreg.modules.device.object.tile.TileDryingMat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;

public class TESRDryingMat extends BaseTESR<TileDryingMat> {

  @Override
  public void render(TileDryingMat te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

    ItemStack stack = te.getStack();
    GlStateManager.pushMatrix();
    GlStateManager.translate(x + 0.5D, y + 0.03125D + 0.0625D, z + 0.5D);
    GlStateManager.scale(.5f, .5f, .5f);
    GlStateManager.rotate(90f, 1f, 0f, 0f);
    Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
    GlStateManager.popMatrix();
  }
}
