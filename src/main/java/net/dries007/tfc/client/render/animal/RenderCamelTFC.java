/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.client.render.animal;

import net.dries007.tfc.client.model.animal.ModelCamelTFC;
import net.dries007.tfc.objects.entity.animal.EntityCamelTFC;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

import static su.terrafirmagreg.old.api.data.Reference.TFC;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderCamelTFC extends RenderAnimalTFC<EntityCamelTFC> {

  private static final ResourceLocation OLD = new ResourceLocation(TFC, "textures/entity/animal/livestock/camel_old.png");
  private static final ResourceLocation YOUNG = new ResourceLocation(TFC, "textures/entity/animal/livestock/camel_young.png");

  public RenderCamelTFC(RenderManager p_i47203_1_) {
    super(p_i47203_1_, new ModelCamelTFC(0.0F), 0.7F, YOUNG, OLD);
    this.addLayer(new LayerCamelDecor(this));
  }

}
