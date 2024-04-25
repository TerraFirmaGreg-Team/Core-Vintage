package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalPolarBear;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalPolarBear;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)

public class RenderAnimalPolarBear extends RenderLiving<EntityAnimalPolarBear> {

    private static final ResourceLocation POLARBEAR_TEXTURE = ModUtils.id("textures/entity/animal/predators/polarbear.png");

    public RenderAnimalPolarBear(RenderManager renderManager) {
        super(renderManager, new ModelAnimalPolarBear(), 0.7F);
    }

    @Override
    public void doRender(@NotNull EntityAnimalPolarBear polarBearTFC, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.35f + (polarBearTFC.getPercentToAdulthood() * 0.35f));
        super.doRender(polarBearTFC, par2, par4, par6, par8, par9);
    }

    @Override
    protected float handleRotationFloat(EntityAnimalPolarBear par1EntityLiving, float par2) {
        return 1.0f;
    }

    @Override
    protected void preRenderCallback(EntityAnimalPolarBear polarBearTFC, float par2) {
        GlStateManager.scale(1.3f, 1.3f, 1.3f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimalPolarBear entity) {
        return POLARBEAR_TEXTURE;
    }
}
