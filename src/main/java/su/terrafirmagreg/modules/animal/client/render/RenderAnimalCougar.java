package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalCougar;
import su.terrafirmagreg.modules.animal.object.entity.predator.EntityAnimalCougar;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)

public class RenderAnimalCougar extends RenderLiving<EntityAnimalCougar> {

  private static final ResourceLocation TEXTURE = ModUtils.resource(
          "textures/entity/animal/predators/cougar.png");

  public RenderAnimalCougar(RenderManager renderManager) {
    super(renderManager, new ModelAnimalCougar(), 0.7F);
  }

  @Override
  public void doRender(@NotNull EntityAnimalCougar cougar, double par2, double par4, double par6,
          float par8, float par9) {
    this.shadowSize = (float) (0.35f + (cougar.getPercentToAdulthood() * 0.35f));
    super.doRender(cougar, par2, par4, par6, par8, par9);
  }

  @Override
  protected float handleRotationFloat(EntityAnimalCougar par1EntityLiving, float par2) {
    return 1.0f;
  }

  @Override
  protected void preRenderCallback(EntityAnimalCougar cougarTFC, float par2) {
    GlStateManager.scale(1.1f, 1.1f, 1.1f);
  }

  @Override
  protected ResourceLocation getEntityTexture(EntityAnimalCougar entity) {
    return TEXTURE;
  }
}
