package net.dries007.tfc.module.core.client.render.animal;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.core.client.model.animal.ModelGrizzlyBearTFC;
import net.dries007.tfc.module.core.common.objects.entity.animal.EntityGrizzlyBearTFC;
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
public class RenderGrizzlyBearTFC extends RenderLiving<EntityGrizzlyBearTFC> {
    private static final ResourceLocation BEAR_TEXTURE = TerraFirmaCraft.getID("textures/entity/animal/predators/grizzlybear.png");

    public RenderGrizzlyBearTFC(RenderManager renderManager) {
        super(renderManager, new ModelGrizzlyBearTFC(), 0.7F);
    }

    @Override
    public void doRender(@Nonnull EntityGrizzlyBearTFC bear, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.35f + (bear.getPercentToAdulthood() * 0.35f));
        super.doRender(bear, par2, par4, par6, par8, par9);
    }

    @Override
    protected float handleRotationFloat(EntityGrizzlyBearTFC par1EntityLiving, float par2) {
        return 1.0f;
    }

    @Override
    protected void preRenderCallback(EntityGrizzlyBearTFC bearTFC, float par2) {
        GlStateManager.scale(1.4f, 1.4f, 1.4f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityGrizzlyBearTFC entity) {
        return BEAR_TEXTURE;
    }
}
