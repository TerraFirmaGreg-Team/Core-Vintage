package net.dries007.tfc.client.render.animal;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.client.model.animal.ModelDireWolfTFC;
import net.dries007.tfc.objects.entity.animal.EntityDireWolfTFC;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.data.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderDireWolfTFC extends RenderLiving<EntityDireWolfTFC> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID_TFC, "textures/entity/animal/predators/direwolf.png");

    public RenderDireWolfTFC(RenderManager renderManager) {
        super(renderManager, new ModelDireWolfTFC(), 0.7F);
    }

    @Override
    public void doRender(@NotNull EntityDireWolfTFC direwolf, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.35f + (direwolf.getPercentToAdulthood() * 0.35f));
        super.doRender(direwolf, par2, par4, par6, par8, par9);
    }

    @Override
    protected float handleRotationFloat(EntityDireWolfTFC par1EntityLiving, float par2) {
        return 1.0f;
    }

    @Override
    protected void preRenderCallback(EntityDireWolfTFC direwolfTFC, float par2) {
        GlStateManager.scale(1.15f, 1.15f, 1.15f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityDireWolfTFC entity) {
        return TEXTURE;
    }
}
