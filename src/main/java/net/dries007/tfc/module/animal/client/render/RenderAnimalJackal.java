package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.TerraFirmaGreg;
import net.dries007.tfc.module.animal.client.model.ModelAnimalJackal;
import net.dries007.tfc.module.animal.common.entities.predator.EntityAnimalJackal;
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
public class RenderAnimalJackal extends RenderLiving<EntityAnimalJackal> {
    private static final ResourceLocation TEXTURE = TerraFirmaGreg.getID("textures/entity/animal/predators/jackal.png");

    public RenderAnimalJackal(RenderManager renderManager) {
        super(renderManager, new ModelAnimalJackal(), 0.7F);
    }

    @Override
    public void doRender(@Nonnull EntityAnimalJackal jackal, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.35f + (jackal.getPercentToAdulthood() * 0.35f));
        super.doRender(jackal, par2, par4, par6, par8, par9);
    }

    @Override
    protected float handleRotationFloat(EntityAnimalJackal par1EntityLiving, float par2) {
        return 1.0f;
    }

    @Override
    protected void preRenderCallback(EntityAnimalJackal jackalTFC, float par2) {
        GlStateManager.scale(0.8f, 0.8f, 0.8f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimalJackal entity) {
        return TEXTURE;
    }
}
