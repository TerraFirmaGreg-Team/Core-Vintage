package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.module.animal.client.model.ModelAnimalDeer;
import net.dries007.tfc.module.animal.objects.entities.huntable.EntityAnimalDeer;
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
public class RenderAnimalDeer extends RenderLiving<EntityAnimalDeer> {
    private static final ResourceLocation DEER_TEXTURE = Helpers.getID("textures/entity/animal/huntable/deer.png");

    private static final ResourceLocation FAWN_TEXTURE = Helpers.getID("textures/entity/animal/huntable/deer_fawn.png");

    public RenderAnimalDeer(RenderManager manager) {
        super(manager, new ModelAnimalDeer(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimalDeer deer) {
        if (deer.isChild()) {
            return FAWN_TEXTURE;
        } else {
            return DEER_TEXTURE;
        }
    }

    @Override
    protected float handleRotationFloat(EntityAnimalDeer deer, float par2) {
        return 1.0f;
    }

    protected void preRenderCallback(EntityAnimalDeer deerTFC, float par2) {
        GlStateManager.scale(0.8f, 0.8f, 0.8f);
    }
}
