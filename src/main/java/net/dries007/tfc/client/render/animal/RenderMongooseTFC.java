package net.dries007.tfc.client.render.animal;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.client.model.animal.ModelMongooseTFC;
import net.dries007.tfc.objects.entity.animal.EntityMongooseTFC;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.api.data.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderMongooseTFC extends RenderLiving<EntityMongooseTFC> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID_TFC, "textures/entity/animal/huntable/mongoose.png");

    public RenderMongooseTFC(RenderManager renderManager) {super(renderManager, new ModelMongooseTFC(), 0.7F);}

    @Override
    public void doRender(@NotNull EntityMongooseTFC mongoose, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.35f + (mongoose.getPercentToAdulthood() * 0.35f));
        super.doRender(mongoose, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMongooseTFC entity) {
        return TEXTURE;
    }
}
