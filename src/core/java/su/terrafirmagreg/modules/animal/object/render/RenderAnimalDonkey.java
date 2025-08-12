package su.terrafirmagreg.modules.animal.object.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.object.model.ModelAnimalHorse;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalDonkey;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")
@SideOnly(Side.CLIENT)
public class RenderAnimalDonkey extends RenderLiving<EntityAnimalDonkey> {

  private static final ResourceLocation TEXTURE = ModUtils.resource("textures/entity/horse/donkey.png");

  private final float scale;

  public RenderAnimalDonkey(RenderManager manager) {
    this(manager, 1.0F);
  }

  public RenderAnimalDonkey(RenderManager renderManagerIn, float scaleIn) {
    super(renderManagerIn, new ModelAnimalHorse(), 0.75F);
    this.scale = scaleIn;
  }

  @Override
  protected void preRenderCallback(@NotNull EntityAnimalDonkey entitylivingbaseIn, float partialTickTime) {
    GlStateManager.scale(this.scale, this.scale, this.scale);
    super.preRenderCallback(entitylivingbaseIn, partialTickTime);
  }

  @Override
  protected ResourceLocation getEntityTexture(EntityAnimalDonkey entity) {
    return TEXTURE;
  }
}
