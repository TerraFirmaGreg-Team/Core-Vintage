package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalPig;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalPig;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalPig extends RenderAnimal<EntityAnimalPig> {

  private static final ResourceLocation PIG_YOUNG = ModUtils.resource(
          "textures/entity/animal/livestock/pig_young.png");
  private static final ResourceLocation PIG_OLD = ModUtils.resource(
          "textures/entity/animal/livestock/pig_old.png");

  public RenderAnimalPig(RenderManager renderManager) {
    super(renderManager, new ModelAnimalPig(), 0.7F, PIG_YOUNG, PIG_OLD);
  }

  @Override
  public void doRender(EntityAnimalPig pig, double par2, double par4, double par6, float par8,
          float par9) {
    this.shadowSize = (float) (0.35f + pig.getPercentToAdulthood() * 0.35f);
    super.doRender(pig, par2, par4, par6, par8, par9);
  }
}
