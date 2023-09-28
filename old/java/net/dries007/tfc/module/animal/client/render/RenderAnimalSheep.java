package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.TerraFirmaGreg;
import net.dries007.tfc.module.animal.client.model.ModelAnimalSheepBody;
import net.dries007.tfc.module.animal.common.entities.livestock.EntityAnimalSheep;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalSheep extends RenderAnimal<EntityAnimalSheep> {
    private static final ResourceLocation SHEEP_YOUNG = TerraFirmaGreg.getID("textures/entity/animal/livestock/sheep_young.png");
    private static final ResourceLocation SHEEP_OLD = TerraFirmaGreg.getID("textures/entity/animal/livestock/sheep_old.png");

    public RenderAnimalSheep(RenderManager renderManager) {
        super(renderManager, new ModelAnimalSheepBody(), 0.7F, SHEEP_YOUNG, SHEEP_OLD);
        this.addLayer(new LayerSheepWool(this));
    }
}
