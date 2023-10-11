package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.module.animal.client.model.ModelAnimalSaberTooth;
import net.dries007.tfc.module.animal.objects.entities.predator.EntityAnimalSaberTooth;
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
public class RenderAnimalSaberTooth extends RenderLiving<EntityAnimalSaberTooth> {
    private static final ResourceLocation TEXTURE = Helpers.getID("textures/entity/animal/predators/sabertooth.png");

    public RenderAnimalSaberTooth(RenderManager renderManager) {
        super(renderManager, new ModelAnimalSaberTooth(), 0.7F);
    }

    @Override
    public void doRender(@Nonnull EntityAnimalSaberTooth sabertooth, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.35f + (sabertooth.getPercentToAdulthood() * 0.35f));
        super.doRender(sabertooth, par2, par4, par6, par8, par9);
    }

    @Override
    protected float handleRotationFloat(EntityAnimalSaberTooth par1EntityLiving, float par2) {
        return 1.0f;
    }

    @Override
    protected void preRenderCallback(EntityAnimalSaberTooth sabertooth, float par2) {
        GlStateManager.scale(1.3f, 1.3f, 1.3f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimalSaberTooth entity) {
        return TEXTURE;
    }
}
