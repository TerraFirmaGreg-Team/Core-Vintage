package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.modules.animal.client.model.ModelAnimalHorse;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalDonkey;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalMule;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Maps;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Used for mule and donkey, because vanilla uses it's own map from class -> resource
 */
@SuppressWarnings("WeakerAccess")
@SideOnly(Side.CLIENT)
public class RenderAnimalAbstractHorse extends RenderLiving<AbstractHorse> {

  private static final Map<Class<?>, ResourceLocation> MAP = Maps.newHashMap();

  static {
    // Those are grabbed from vanilla, please don't change unless we add our own textures first.
    MAP.put(EntityAnimalDonkey.class, new ResourceLocation("textures/entity/horse/donkey.png"));
    MAP.put(EntityAnimalMule.class, new ResourceLocation("textures/entity/horse/mule.png"));
  }

  private final float scale;

  public RenderAnimalAbstractHorse(RenderManager manager) {
    this(manager, 1.0F);
  }

  public RenderAnimalAbstractHorse(RenderManager renderManagerIn, float scaleIn) {
    super(renderManagerIn, new ModelAnimalHorse(), 0.75F);
    this.scale = scaleIn;
  }

  @Override
  protected void preRenderCallback(@NotNull AbstractHorse entitylivingbaseIn,
                                   float partialTickTime) {
    GlStateManager.scale(this.scale, this.scale, this.scale);
    super.preRenderCallback(entitylivingbaseIn, partialTickTime);
  }

  @Override
  protected ResourceLocation getEntityTexture(AbstractHorse entity) {
    return MAP.get(entity.getClass());
  }
}
