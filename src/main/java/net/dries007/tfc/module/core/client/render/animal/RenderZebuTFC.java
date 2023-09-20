package net.dries007.tfc.module.core.client.render.animal;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.core.client.model.animal.ModelZebuTFC;
import net.dries007.tfc.module.core.common.objects.entity.animal.EntityZebuTFC;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderZebuTFC extends RenderAnimalTFC<EntityZebuTFC> {
    private static final ResourceLocation ZEBU_YOUNG = TerraFirmaCraft.identifier("textures/entity/animal/livestock/zebu_young.png");
    private static final ResourceLocation ZEBU_OLD = TerraFirmaCraft.identifier("textures/entity/animal/livestock/zebu_old.png");

    public RenderZebuTFC(RenderManager renderManager) {
        super(renderManager, new ModelZebuTFC(), 0.7F, ZEBU_YOUNG, ZEBU_OLD);
    }

    protected void preRenderCallback(EntityZebuTFC zebuTFC, float par2) {
        GlStateManager.scale(0.9f, 0.9f, 0.9f);
    }
}
