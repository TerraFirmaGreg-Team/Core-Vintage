package su.terrafirmagreg.modules.device.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.device.client.model.ModelSlingStone;
import su.terrafirmagreg.modules.device.object.entity.EntitySlingStone;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RenderSlingStone extends Render<EntitySlingStone> {

  private static final ResourceLocation SLING_STONE_TEXTURE = ModUtils.resource(
          "textures/blocks/rock/raw/dacite.png");
  private final ModelSlingStone slingStoneModel = new ModelSlingStone();

  public RenderSlingStone(RenderManager renderManager) {
    super(renderManager);
  }

  public void doRender(EntitySlingStone entity, double x, double y, double z, float entityYaw,
          float partialTicks) {
    GlStateManager.pushMatrix();
    GlStateManager.disableCull();
    float f = this.getRenderYaw(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
    float f1 =
            entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
    GlStateManager.translate((float) x, (float) y, (float) z);
    GlStateManager.rotate(
            entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks
                    - 90.0F, 0.0F, 1.0F, 0.0F);
    float f2 = 0.0625F;
    GlStateManager.enableRescaleNormal();
    GlStateManager.scale(-1.0F, -1.0F, 1.0F);
    GlStateManager.enableAlpha();
    this.bindEntityTexture(entity);

    if (this.renderOutlines) {
      GlStateManager.enableColorMaterial();
      GlStateManager.enableOutlineMode(this.getTeamColor(entity));
    }
    this.slingStoneModel.render(entity, 0.0F, 0.0F, 0.0F, f, f1, 0.0625F);

    if (this.renderOutlines) {
      GlStateManager.disableOutlineMode();
      GlStateManager.disableColorMaterial();
    }

    GlStateManager.popMatrix();
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }

  private float getRenderYaw(float p_82400_1_, float p_82400_2_, float p_82400_3_) {
    float f;

    for (f = p_82400_2_ - p_82400_1_; f < -180.0F; f += 360.0F) {
    }

    while (f >= 180.0F) {
      f -= 360.0F;
    }

    return p_82400_1_ + p_82400_3_ * f;
  }

  @Nullable
  @Override
  protected ResourceLocation getEntityTexture(@NotNull EntitySlingStone entity) {
    return SLING_STONE_TEXTURE;
  }

}
