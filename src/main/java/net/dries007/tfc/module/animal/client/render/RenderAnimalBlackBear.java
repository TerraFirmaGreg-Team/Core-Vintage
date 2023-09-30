package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.module.animal.client.model.ModelAnimalBlackBear;
import net.dries007.tfc.module.animal.common.entities.predator.EntityAnimalBlackBear;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalBlackBear extends RenderLiving<EntityAnimalBlackBear> {
    private static final ResourceLocation BLACKBEAR_TEXTURE = Helpers.getID("textures/entity/animal/predators/blackbear.png");

    public RenderAnimalBlackBear(RenderManager renderManager) {
        super(renderManager, new ModelAnimalBlackBear(), 0.7F);
    }

    @Override
    public void doRender(@Nonnull EntityAnimalBlackBear blackbear, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.35f + (blackbear.getPercentToAdulthood() * 0.35f));
        super.doRender(blackbear, par2, par4, par6, par8, par9);
    }

    @Override
    protected float handleRotationFloat(EntityAnimalBlackBear par1EntityLiving, float par2) {
        return 1.0f;
    }

    @Override
    protected void preRenderCallback(EntityAnimalBlackBear blackbearTFC, float par2) {
        GlStateManager.scale(1.4f, 1.4f, 1.4f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimalBlackBear entity) {
        return BLACKBEAR_TEXTURE;
    }
}
