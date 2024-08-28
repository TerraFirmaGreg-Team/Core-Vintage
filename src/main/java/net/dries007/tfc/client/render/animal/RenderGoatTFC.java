package net.dries007.tfc.client.render.animal;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.client.model.animal.ModelGoatTFC;
import net.dries007.tfc.objects.entity.animal.EntityGoatTFC;

import static su.terrafirmagreg.data.Constants.MODID_TFC;

@SideOnly(Side.CLIENT)

public class RenderGoatTFC extends RenderAnimalTFC<EntityGoatTFC> {

    private static final ResourceLocation GOAT_OLD = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/goat_old.png");
    private static final ResourceLocation GOAT_YOUNG = new ResourceLocation(MODID_TFC, "textures/entity/animal/livestock/goat_young.png");

    public RenderGoatTFC(RenderManager renderManager) {
        super(renderManager, new ModelGoatTFC(), 0.7F, GOAT_YOUNG, GOAT_OLD);
    }
}
