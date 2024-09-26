package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.modules.animal.client.model.ModelAnimalAlpacaWool;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalAlpaca;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class LayerAlpacaWool implements LayerRenderer<EntityAnimalAlpaca> {

  private final RenderAnimalAlpaca alpacaRenderer;
  private final ModelAnimalAlpacaWool alpacaModel = new ModelAnimalAlpacaWool();

  public LayerAlpacaWool(RenderAnimalAlpaca renderer) {
    this.alpacaRenderer = renderer;
  }

  @Override
  public void doRenderLayer(EntityAnimalAlpaca alpaca, float limbSwing, float limbSwingAmount,
                            float partialTicks, float ageInTicks,
                            float netHeadYaw, float headPitch, float scale) {
    if (alpaca.hasWool() && !alpaca.isInvisible()) {
      this.alpacaRenderer.bindTexture(this.alpacaRenderer.getEntityTexture(alpaca));

      float[] afloat = EntitySheep.getDyeRgb(alpaca.getDyeColor());
      GlStateManager.color(afloat[0], afloat[1], afloat[2]);

      this.alpacaModel.setModelAttributes(this.alpacaRenderer.getMainModel());
      this.alpacaModel.setLivingAnimations(alpaca, limbSwing, limbSwingAmount, partialTicks);
      this.alpacaModel.render(alpaca, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
                              scale);
    }
  }

  @Override
  public boolean shouldCombineTextures() {
    return true;
  }
}
