package net.dries007.tfc.client.render;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.objects.entity.projectile.EntityRopeBridgeThrown;

public class RenderThrownRopeBridge extends Render<EntityRopeBridgeThrown> {

  private static final ResourceLocation BRIDGE_TEXTURE = new ResourceLocation("tfcthings:textures/items/rope_bridge_bundle.png");

  public RenderThrownRopeBridge(RenderManager renderManagerIn) {
    super(renderManagerIn);
  }

  /**
   * Renders the desired {@code T} type Entity.
   */
  public void doRender(EntityRopeBridgeThrown entity, double x, double y, double z, float entityYaw, float partialTicks) {
    GlStateManager.pushMatrix();
    this.bindEntityTexture(entity);
    GlStateManager.translate((float) x, (float) y, (float) z);
    GlStateManager.enableRescaleNormal();
    GlStateManager.scale(0.6F, 0.6F, 0.6F);
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate((float) (this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

    if (this.renderOutlines) {
      GlStateManager.enableColorMaterial();
      GlStateManager.enableOutlineMode(this.getTeamColor(entity));
    }

    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
    bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(0.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
    bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(1.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
    bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(1.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
    bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(0.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
    tessellator.draw();

    if (this.renderOutlines) {
      GlStateManager.disableOutlineMode();
      GlStateManager.disableColorMaterial();
    }

    GlStateManager.disableRescaleNormal();
    GlStateManager.popMatrix();
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }

  /**
   * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
   */
  protected ResourceLocation getEntityTexture(EntityRopeBridgeThrown entity) {
    return BRIDGE_TEXTURE;
  }

}
