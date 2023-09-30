package net.dries007.tfc.module.animal.client.render;

import com.google.common.collect.Maps;
import net.dries007.tfc.module.animal.client.model.ModelAnimalHorse;
import net.dries007.tfc.module.animal.objects.entities.livestock.EntityAnimalDonkey;
import net.dries007.tfc.module.animal.objects.entities.livestock.EntityAnimalMule;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
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
    protected void preRenderCallback(@Nonnull AbstractHorse entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(this.scale, this.scale, this.scale);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }

    @Override
    protected ResourceLocation getEntityTexture(AbstractHorse entity) {
        return MAP.get(entity.getClass());
    }
}
