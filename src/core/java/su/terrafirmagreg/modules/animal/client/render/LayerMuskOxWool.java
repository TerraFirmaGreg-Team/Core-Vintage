package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.modules.animal.client.model.ModelAnimalMuskOxWool;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalMuskOx;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class LayerMuskOxWool implements LayerRenderer<EntityAnimalMuskOx> {

  private final RenderAnimalMuskOx muskoxRenderer;
  private final ModelAnimalMuskOxWool muskoxModel = new ModelAnimalMuskOxWool();

  public LayerMuskOxWool(RenderAnimalMuskOx renderer) {
    this.muskoxRenderer = renderer;
  }

  @Override
  public void doRenderLayer(EntityAnimalMuskOx muskox, float limbSwing, float limbSwingAmount,
                            float partialTicks, float ageInTicks,
                            float netHeadYaw, float headPitch, float scale) {
    if (muskox.hasWool() && !muskox.isInvisible()) {
      this.muskoxRenderer.bindTexture(this.muskoxRenderer.getEntityTexture(muskox));

      float[] afloat = EntitySheep.getDyeRgb(muskox.getDyeColor());
      GlStateManager.color(afloat[0], afloat[1], afloat[2]);

      this.muskoxModel.setModelAttributes(this.muskoxRenderer.getMainModel());
      this.muskoxModel.setLivingAnimations(muskox, limbSwing, limbSwingAmount, partialTicks);
      this.muskoxModel.render(muskox, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
                              scale);
    }
  }

  @Override
  public boolean shouldCombineTextures() {
    return true;
  }
}
