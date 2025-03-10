package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalDeer;
import su.terrafirmagreg.modules.animal.object.entity.huntable.EntityAnimalDeer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalDeer extends RenderLiving<EntityAnimalDeer> {

  private static final ResourceLocation DEER_TEXTURE = ModUtils.resource(
    "textures/entity/animal/huntable/deer.png");

  private static final ResourceLocation FAWN_TEXTURE = ModUtils.resource(
    "textures/entity/animal/huntable/deer_fawn.png");

  public RenderAnimalDeer(RenderManager manager) {
    super(manager, new ModelAnimalDeer(), 0.7F);
  }

  @Override
  protected ResourceLocation getEntityTexture(EntityAnimalDeer deer) {
    if (deer.isChild()) {
      return FAWN_TEXTURE;
    } else {
      return DEER_TEXTURE;
    }
  }

  @Override
  protected float handleRotationFloat(EntityAnimalDeer deer, float par2) {
    return 1.0f;
  }

  protected void preRenderCallback(EntityAnimalDeer deerTFC, float par2) {
    GlStateManager.scale(0.8f, 0.8f, 0.8f);
  }
}
