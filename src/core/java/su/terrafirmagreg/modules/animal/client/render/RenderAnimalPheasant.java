package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalPheasant;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.object.entity.huntable.EntityAnimalPheasant;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalPheasant extends RenderLiving<EntityAnimalPheasant> {

  private static final ResourceLocation CHICK_TEXTURE = ModUtils.resource(
    "textures/entity/animal/huntable/pheasant_chick.png");
  private static final ResourceLocation MALE_TEXTURE = ModUtils.resource(
    "textures/entity/animal/huntable/pheasant_male.png");
  private static final ResourceLocation FEMALE_TEXTURE = ModUtils.resource(
    "textures/entity/animal/huntable/pheasant_female.png");

  public RenderAnimalPheasant(RenderManager manager) {
    super(manager, new ModelAnimalPheasant(), 0.3F);
  }

  @Override
  public void doRender(EntityAnimalPheasant pheasent, double par2, double par4, double par6,
                       float par8, float par9) {
    this.shadowSize = (float) (0.15f + pheasent.getPercentToAdulthood() * 0.15f);
    super.doRender(pheasent, par2, par4, par6, par8, par9);
  }

  @Override
  protected ResourceLocation getEntityTexture(EntityAnimalPheasant pheasent) {
    float percent = (float) pheasent.getPercentToAdulthood();

    if (percent < 0.65f) {
      return CHICK_TEXTURE;
    } else if (pheasent.getGender() == EntityAnimalBase.Gender.MALE) {
      return MALE_TEXTURE;
    } else {
      return FEMALE_TEXTURE;
    }
  }

  @Override
  protected float handleRotationFloat(EntityAnimalPheasant livingBase, float partialTicks) {
    float f = livingBase.oFlap + (livingBase.wingRotation - livingBase.oFlap) * partialTicks;
    float f1 = livingBase.oFlapSpeed + (livingBase.destPos - livingBase.oFlapSpeed) * partialTicks;
    return (MathHelper.sin(f) + 1.0F) * f1;
  }

  @Override
  protected void preRenderCallback(EntityAnimalPheasant bear, float par2) {
    GlStateManager.scale(0.7f, 0.7f, 0.7f);
    GlStateManager.rotate(90, 0, 1, 0);
  }
}
