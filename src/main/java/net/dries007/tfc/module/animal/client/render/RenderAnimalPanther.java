package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.animal.client.model.ModelAnimalPanther;
import net.dries007.tfc.module.animal.common.entity.predator.EntityAnimalPanther;
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
public class RenderAnimalPanther extends RenderLiving<EntityAnimalPanther> {
    private static final ResourceLocation TEXTURE = TerraFirmaCraft.getID("textures/entity/animal/predators/panther.png");

    public RenderAnimalPanther(RenderManager renderManager) {
        super(renderManager, new ModelAnimalPanther(), 0.7F);
    }

    @Override
    public void doRender(@Nonnull EntityAnimalPanther panther, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.35f + (panther.getPercentToAdulthood() * 0.35f));
        super.doRender(panther, par2, par4, par6, par8, par9);
    }

    @Override
    protected float handleRotationFloat(EntityAnimalPanther par1EntityLiving, float par2) {
        return 1.0f;
    }

    @Override
    protected void preRenderCallback(EntityAnimalPanther pantherTFC, float par2) {
        GlStateManager.scale(1.1f, 1.1f, 1.1f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimalPanther entity) {
        return TEXTURE;
    }
}
