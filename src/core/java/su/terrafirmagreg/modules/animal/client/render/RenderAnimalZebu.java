package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalZebu;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalZebu;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalZebu extends RenderAnimal<EntityAnimalZebu> {

  private static final ResourceLocation ZEBU_YOUNG = ModUtils.resource(
    "textures/entity/animal/livestock/zebu_young.png");
  private static final ResourceLocation ZEBU_OLD = ModUtils.resource(
    "textures/entity/animal/livestock/zebu_old.png");

  public RenderAnimalZebu(RenderManager renderManager) {
    super(renderManager, new ModelAnimalZebu(), 0.7F, ZEBU_YOUNG, ZEBU_OLD);
  }

  protected void preRenderCallback(EntityAnimalZebu zebuTFC, float par2) {
    GlStateManager.scale(0.9f, 0.9f, 0.9f);
  }
}
