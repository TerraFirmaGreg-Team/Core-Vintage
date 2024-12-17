/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.client.render.animal;

import net.dries007.tfc.client.model.animal.ModelAlpacaBodyTFC;
import net.dries007.tfc.objects.entity.animal.EntityAlpacaTFC;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

import static su.terrafirmagreg.api.data.Reference.TFC;


@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAlpacaTFC extends RenderAnimalTFC<EntityAlpacaTFC> {

  private static final ResourceLocation ALPACA_OLD = new ResourceLocation(TFC, "textures/entity/animal/livestock/alpaca_old.png");
  private static final ResourceLocation ALPACA_YOUNG = new ResourceLocation(TFC, "textures/entity/animal/livestock/alpaca_young.png");

  public RenderAlpacaTFC(RenderManager renderManager) {
    super(renderManager, new ModelAlpacaBodyTFC(), 0.7F, ALPACA_YOUNG, ALPACA_OLD);
    this.addLayer(new LayerAlpacaWoolTFC(this));
  }
}
