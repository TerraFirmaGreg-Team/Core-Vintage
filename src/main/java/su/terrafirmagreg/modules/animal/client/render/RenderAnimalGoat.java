package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalGoat;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalGoat;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalGoat extends RenderAnimal<EntityAnimalGoat> {

  private static final ResourceLocation GOAT_OLD = ModUtils.resource(
    "textures/entity/animal/livestock/goat_old.png");
  private static final ResourceLocation GOAT_YOUNG = ModUtils.resource(
    "textures/entity/animal/livestock/goat_young.png");

  public RenderAnimalGoat(RenderManager renderManager) {
    super(renderManager, new ModelAnimalGoat(), 0.7F, GOAT_YOUNG, GOAT_OLD);
  }
}
