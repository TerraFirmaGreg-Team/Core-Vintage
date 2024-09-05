package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalCow;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalCow;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalCow extends RenderAnimal<EntityAnimalCow> {

  private static final ResourceLocation COW_YOUNG = ModUtils.resource(
      "textures/entity/animal/livestock/cow_young.png");
  private static final ResourceLocation COW_OLD = ModUtils.resource(
      "textures/entity/animal/livestock/cow_old.png");

  public RenderAnimalCow(RenderManager renderManager) {
    super(renderManager, new ModelAnimalCow(), 0.7F, COW_YOUNG, COW_OLD);
  }
}
