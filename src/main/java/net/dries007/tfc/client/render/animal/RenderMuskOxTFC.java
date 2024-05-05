package net.dries007.tfc.client.render.animal;

import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.client.model.animal.ModelMuskOxBodyTFC;
import net.dries007.tfc.objects.entity.animal.EntityMuskOxTFC;

import static su.terrafirmagreg.api.data.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderMuskOxTFC extends RenderAnimalTFC<EntityMuskOxTFC> {

    private static final ResourceLocation TEXTURE_YOUNG = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/muskox_young.png");
    private static final ResourceLocation TEXTURE_OLD = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/muskox_old.png");

    public RenderMuskOxTFC(RenderManager renderManager) {
        super(renderManager, new ModelMuskOxBodyTFC(), 0.8F, TEXTURE_YOUNG, TEXTURE_OLD);
        this.addLayer(new LayerMuskOxWoolTFC(this));
    }

    @Override
    protected void preRenderCallback(EntityMuskOxTFC muskoxTFC, float par2) {
        if (muskoxTFC.getGender() == EntityAnimalBase.Gender.MALE)
            GlStateManager.scale(1.2f, 1.2f, 1.2f);
        else {
            GlStateManager.scale(1.0f, 1.0f, 1.0f);
        }
    }
}
