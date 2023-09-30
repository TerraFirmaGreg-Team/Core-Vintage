package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.module.animal.client.model.ModelAnimalLion;
import net.dries007.tfc.module.animal.common.entities.predator.EntityAnimalLion;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalLion extends RenderLiving<EntityAnimalLion> {
    private static final ResourceLocation LIONS_TEXTURE = Helpers.getID("textures/entity/animal/predators/lions.png");

    public RenderAnimalLion(RenderManager manager) {
        super(manager, new ModelAnimalLion(), 0.3F);
    }

    @Override
    public void doRender(EntityAnimalLion lion, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.4f + lion.getPercentToAdulthood() * 0.4f);
        super.doRender(lion, par2, par4, par6, par8, par9);
    }

    protected void preRenderCallback(EntityAnimalLion lionTFC, float par2) {
        GlStateManager.scale(1.1f, 1.1f, 1.1f);
    }


    protected ResourceLocation getEntityTexture(EntityAnimalLion lion) {
        return LIONS_TEXTURE;
    }
}
