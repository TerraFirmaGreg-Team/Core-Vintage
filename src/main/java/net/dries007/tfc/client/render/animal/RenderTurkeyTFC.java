package net.dries007.tfc.client.render.animal;

import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalBase;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.client.model.animal.ModelTurkeyTFC;
import net.dries007.tfc.objects.entity.animal.EntityTurkeyTFC;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.data.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderTurkeyTFC extends RenderLiving<EntityTurkeyTFC> {

  private static final ResourceLocation MALE = new ResourceLocation(MODID_TFC, "textures/entity/animal/huntable/turkeym.png");
  private static final ResourceLocation FEMALE = new ResourceLocation(MODID_TFC, "textures/entity/animal/huntable/turkeyf.png");

  public RenderTurkeyTFC(RenderManager manager) {
    super(manager, new ModelTurkeyTFC(), 0.5F);
  }

  @Override
  public void doRender(@NotNull EntityTurkeyTFC turkey, double par2, double par4, double par6, float par8, float par9) {
    this.shadowSize = (float) (0.35f + (turkey.getPercentToAdulthood() * 0.35f));
    super.doRender(turkey, par2, par4, par6, par8, par9);
  }

  @Override
  protected ResourceLocation getEntityTexture(EntityTurkeyTFC turkey) {
    if (turkey.getGender() == EntityAnimalBase.Gender.MALE) {
      return MALE;
    } else {
      return FEMALE;
    }
  }

  @Override
  protected void preRenderCallback(EntityTurkeyTFC tukeyTFC, float par2) {
    GlStateManager.scale(0.8f, 0.8f, 0.8f);
  }
}
