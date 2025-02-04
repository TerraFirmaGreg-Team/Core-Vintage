package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalGazelle;
import su.terrafirmagreg.modules.animal.object.entity.huntable.EntityAnimalGazelle;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalGazelle extends RenderLiving<EntityAnimalGazelle> {

  private static final ResourceLocation TEXTURE = ModUtils.resource(
    "textures/entity/animal/huntable/gazelle.png");

  public RenderAnimalGazelle(RenderManager manager) {
    super(manager, new ModelAnimalGazelle(), 0.7F);
  }

  @Override
  protected float handleRotationFloat(EntityAnimalGazelle gazelle, float par2) {
    return 1.0f;
  }

  @Override
  protected void preRenderCallback(EntityAnimalGazelle gazelleTFC, float par2) {
    GlStateManager.scale(0.9f, 0.9f, 0.9f);
  }

  @Override
  protected ResourceLocation getEntityTexture(EntityAnimalGazelle entity) {
    return TEXTURE;
  }
}
