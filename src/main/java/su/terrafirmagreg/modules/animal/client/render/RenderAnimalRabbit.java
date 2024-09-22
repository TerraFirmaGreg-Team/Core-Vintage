package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.modules.animal.client.model.ModelAnimalRabbit;
import su.terrafirmagreg.modules.animal.object.entity.huntable.EntityAnimalRabbit;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalRabbit extends RenderLiving<EntityAnimalRabbit> {

  private static final ResourceLocation WHITE = new ResourceLocation(
          "textures/entity/rabbit/white.png");
  private static final ResourceLocation GOLD = new ResourceLocation(
          "textures/entity/rabbit/gold.png");
  private static final ResourceLocation SALT = new ResourceLocation(
          "textures/entity/rabbit/salt.png");
  private static final ResourceLocation WHITE_SPLOTCHED = new ResourceLocation(
          "textures/entity/rabbit/white_splotched.png");
  private static final ResourceLocation CAERBANNOG = new ResourceLocation(
          "textures/entity/rabbit/caerbannog.png");

  public RenderAnimalRabbit(RenderManager renderManager) {
    super(renderManager, new ModelAnimalRabbit(), 0.3F);
  }

  @Override
  public void doRender(EntityAnimalRabbit rabbit, double par2, double par4, double par6, float par8,
          float par9) {
    this.shadowSize = (float) (0.15f + rabbit.getPercentToAdulthood() * 0.15f);
    super.doRender(rabbit, par2, par4, par6, par8, par9);
  }

  @Override
  protected ResourceLocation getEntityTexture(EntityAnimalRabbit entity) {
    switch (entity.getRabbitType()) {
      case 0:
      default:
        return WHITE;
      case 1:
        return WHITE_SPLOTCHED;
      case 2:
        return GOLD;
      case 3:
        return SALT;
      case 4:
        return CAERBANNOG;
    }
  }
}
