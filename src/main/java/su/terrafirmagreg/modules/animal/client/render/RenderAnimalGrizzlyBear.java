package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalGrizzlyBear;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalGrizzlyBear;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)

public class RenderAnimalGrizzlyBear extends RenderLiving<EntityAnimalGrizzlyBear> {

  private static final ResourceLocation BEAR_TEXTURE = ModUtils.resource(
      "textures/entity/animal/predators/grizzlybear.png");

  public RenderAnimalGrizzlyBear(RenderManager renderManager) {
    super(renderManager, new ModelAnimalGrizzlyBear(), 0.7F);
  }

  @Override
  public void doRender(@NotNull EntityAnimalGrizzlyBear bear, double par2, double par4, double par6,
      float par8, float par9) {
    this.shadowSize = (float) (0.35f + (bear.getPercentToAdulthood() * 0.35f));
    super.doRender(bear, par2, par4, par6, par8, par9);
  }

  @Override
  protected float handleRotationFloat(EntityAnimalGrizzlyBear par1EntityLiving, float par2) {
    return 1.0f;
  }

  @Override
  protected void preRenderCallback(EntityAnimalGrizzlyBear bearTFC, float par2) {
    GlStateManager.scale(1.4f, 1.4f, 1.4f);
  }

  @Override
  protected ResourceLocation getEntityTexture(EntityAnimalGrizzlyBear entity) {
    return BEAR_TEXTURE;
  }
}
