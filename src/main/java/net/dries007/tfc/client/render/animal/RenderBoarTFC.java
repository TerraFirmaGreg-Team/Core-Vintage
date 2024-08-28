package net.dries007.tfc.client.render.animal;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.client.model.animal.ModelBoarTFC;
import net.dries007.tfc.objects.entity.animal.EntityBoarTFC;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.data.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderBoarTFC extends RenderLiving<EntityBoarTFC> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID_TFC, "textures/entity/animal/huntable/boar.png");

    public RenderBoarTFC(RenderManager renderManager) {super(renderManager, new ModelBoarTFC(), 0.7F);}

    @Override
    public void doRender(@NotNull EntityBoarTFC hog, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.35f + (hog.getPercentToAdulthood() * 0.35f));
        super.doRender(hog, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBoarTFC entity) {
        return TEXTURE;
    }
}
