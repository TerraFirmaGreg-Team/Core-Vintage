package net.dries007.tfc.client.render.animal;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.client.model.animal.ModelWildebeestTFC;
import net.dries007.tfc.objects.entity.animal.EntityWildebeestTFC;

import static su.terrafirmagreg.data.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderWildebeestTFC extends RenderLiving<EntityWildebeestTFC> {

  private static final ResourceLocation TEXTURE = new ResourceLocation(MODID_TFC, "textures/entity/animal/huntable/wildebeest.png");

  public RenderWildebeestTFC(RenderManager manager) {
    super(manager, new ModelWildebeestTFC(), 0.7F);
  }

  @Override
  protected float handleRotationFloat(EntityWildebeestTFC wildebeest, float par2) {
    return 1.0f;
  }

  @Override
  protected ResourceLocation getEntityTexture(EntityWildebeestTFC entity) {
    return TEXTURE;
  }
}
