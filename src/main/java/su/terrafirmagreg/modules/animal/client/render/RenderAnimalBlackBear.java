package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalBlackBear;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalBlackBear;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)

public class RenderAnimalBlackBear extends RenderLiving<EntityAnimalBlackBear> {

    private static final ResourceLocation BLACKBEAR_TEXTURE = ModUtils.id("textures/entity/animal/predators/blackbear.png");

    public RenderAnimalBlackBear(RenderManager renderManager) {
        super(renderManager, new ModelAnimalBlackBear(), 0.7F);
    }

    @Override
    public void doRender(@NotNull EntityAnimalBlackBear blackbear, double par2, double par4, double par6, float par8, float par9) {
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
