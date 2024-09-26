package net.dries007.tfc.client.render.animal;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.client.model.animal.ModelAlpacaBodyTFC;
import net.dries007.tfc.objects.entity.animal.EntityAlpacaTFC;

import static su.terrafirmagreg.data.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderAlpacaTFC extends RenderAnimalTFC<EntityAlpacaTFC> {

  private static final ResourceLocation ALPACA_OLD = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/alpaca_old.png");
  private static final ResourceLocation ALPACA_YOUNG = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/alpaca_young.png");

  public RenderAlpacaTFC(RenderManager renderManager) {
    super(renderManager, new ModelAlpacaBodyTFC(), 0.7F, ALPACA_YOUNG, ALPACA_OLD);
    this.addLayer(new LayerAlpacaWoolTFC(this));
  }
}
