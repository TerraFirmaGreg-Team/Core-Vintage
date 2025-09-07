package su.terrafirmagreg.modules.animal.content.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.content.model.ModelAnimalHorse;
import su.terrafirmagreg.modules.animal.content.entity.livestock.EntityAnimalMule;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")
@SideOnly(Side.CLIENT)
public class RenderAnimalMule extends RenderLiving<EntityAnimalMule> {

  private static final ResourceLocation TEXTURE = ModUtils.resource("textures/entity/horse/mule.png");

  private final float scale;

  public RenderAnimalMule(RenderManager manager) {
    this(manager, 1.0F);
  }

  public RenderAnimalMule(RenderManager renderManagerIn, float scaleIn) {
    super(renderManagerIn, new ModelAnimalHorse(), 0.75F);
    this.scale = scaleIn;
  }

  @Override
  protected void preRenderCallback(@NotNull EntityAnimalMule entitylivingbaseIn, float partialTickTime) {
    GlStateManager.scale(this.scale, this.scale, this.scale);
    super.preRenderCallback(entitylivingbaseIn, partialTickTime);
  }

  @Override
  protected ResourceLocation getEntityTexture(EntityAnimalMule entity) {
    return TEXTURE;
  }
}
