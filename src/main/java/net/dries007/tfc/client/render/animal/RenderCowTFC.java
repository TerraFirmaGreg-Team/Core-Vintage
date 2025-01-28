package net.dries007.tfc.client.render.animal;

import net.dries007.tfc.client.model.animal.ModelCowTFC;
import net.dries007.tfc.objects.entity.animal.EntityCowTFC;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFC;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderCowTFC extends RenderAnimalTFC<EntityCowTFC> {

  private static final ResourceLocation COW_YOUNG = new ResourceLocation(TFC, "textures/entity/animal/livestock/cow_young.png");
  private static final ResourceLocation COW_OLD = new ResourceLocation(TFC, "textures/entity/animal/livestock/cow_old.png");

  public RenderCowTFC(RenderManager renderManager) {
    super(renderManager, new ModelCowTFC(), 0.7F, COW_YOUNG, COW_OLD);
  }
}
