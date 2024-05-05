package net.dries007.tfc.client.render.animal;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.client.model.animal.ModelSheepBodyTFC;
import net.dries007.tfc.objects.entity.animal.EntitySheepTFC;

import static su.terrafirmagreg.api.data.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderSheepTFC extends RenderAnimalTFC<EntitySheepTFC> {

    private static final ResourceLocation SHEEP_YOUNG = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/sheep_young.png");
    private static final ResourceLocation SHEEP_OLD = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/sheep_old.png");

    public RenderSheepTFC(RenderManager renderManager) {
        super(renderManager, new ModelSheepBodyTFC(), 0.7F, SHEEP_YOUNG, SHEEP_OLD);
        this.addLayer(new LayerSheepWoolTFC(this));
    }
}
