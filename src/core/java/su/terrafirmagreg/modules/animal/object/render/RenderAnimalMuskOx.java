package su.terrafirmagreg.modules.animal.object.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.object.model.ModelAnimalMuskOxBody;
import su.terrafirmagreg.modules.animal.object.model.ModelAnimalMuskOxWool;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalMuskOx;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalMuskOx extends RenderAnimal<EntityAnimalMuskOx> {

  private static final ResourceLocation TEXTURE_YOUNG = ModUtils.resource(
    "textures/entity/animal/livestock/muskox_young.png");
  private static final ResourceLocation TEXTURE_OLD = ModUtils.resource(
    "textures/entity/animal/livestock/muskox_old.png");

  public RenderAnimalMuskOx(RenderManager renderManager) {
    super(renderManager, new ModelAnimalMuskOxBody(), 0.8F, TEXTURE_YOUNG, TEXTURE_OLD);
    this.addLayer(new LayerMuskOxWool(this));
  }

  @Override
  protected void preRenderCallback(EntityAnimalMuskOx muskoxTFC, float par2) {
    if (muskoxTFC.getGender() == EntityAnimalBase.Gender.MALE) {
      GlStateManager.scale(1.2f, 1.2f, 1.2f);
    } else {
      GlStateManager.scale(1.0f, 1.0f, 1.0f);
    }
  }

  @SideOnly(Side.CLIENT)
  public static class LayerMuskOxWool implements LayerRenderer<EntityAnimalMuskOx> {

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
}
