package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.module.animal.client.model.ModelAnimalZebu;
import net.dries007.tfc.module.animal.common.entities.livestock.EntityAnimalZebu;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalZebu extends RenderAnimal<EntityAnimalZebu> {
    private static final ResourceLocation ZEBU_YOUNG = Helpers.getID("textures/entity/animal/livestock/zebu_young.png");
    private static final ResourceLocation ZEBU_OLD = Helpers.getID("textures/entity/animal/livestock/zebu_old.png");

    public RenderAnimalZebu(RenderManager renderManager) {
        super(renderManager, new ModelAnimalZebu(), 0.7F, ZEBU_YOUNG, ZEBU_OLD);
    }

    protected void preRenderCallback(EntityAnimalZebu zebuTFC, float par2) {
        GlStateManager.scale(0.9f, 0.9f, 0.9f);
    }
}
