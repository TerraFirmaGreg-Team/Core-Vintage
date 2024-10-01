package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalJackal;
import su.terrafirmagreg.modules.animal.object.entity.predator.EntityAnimalJackal;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)

public class RenderAnimalJackal extends RenderLiving<EntityAnimalJackal> {

  private static final ResourceLocation TEXTURE = ModUtils.resource(
    "textures/entity/animal/predators/jackal.png");

  public RenderAnimalJackal(RenderManager renderManager) {
    super(renderManager, new ModelAnimalJackal(), 0.7F);
  }

  @Override
  public void doRender(@NotNull EntityAnimalJackal jackal, double par2, double par4, double par6,
                       float par8, float par9) {
    this.shadowSize = (float) (0.35f + (jackal.getPercentToAdulthood() * 0.35f));
    super.doRender(jackal, par2, par4, par6, par8, par9);
  }

  @Override
  protected float handleRotationFloat(EntityAnimalJackal par1EntityLiving, float par2) {
    return 1.0f;
  }

  @Override
  protected void preRenderCallback(EntityAnimalJackal jackalTFC, float par2) {
    GlStateManager.scale(0.8f, 0.8f, 0.8f);
  }

  @Override
  protected ResourceLocation getEntityTexture(EntityAnimalJackal entity) {
    return TEXTURE;
  }
}
