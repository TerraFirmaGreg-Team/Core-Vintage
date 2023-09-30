package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.module.animal.client.model.ModelAnimalPig;
import net.dries007.tfc.module.animal.objects.entities.livestock.EntityAnimalPig;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalPig extends RenderAnimal<EntityAnimalPig> {
    private static final ResourceLocation PIG_YOUNG = Helpers.getID("textures/entity/animal/livestock/pig_young.png");
    private static final ResourceLocation PIG_OLD = Helpers.getID("textures/entity/animal/livestock/pig_old.png");

    public RenderAnimalPig(RenderManager renderManager) {
        super(renderManager, new ModelAnimalPig(), 0.7F, PIG_YOUNG, PIG_OLD);
    }

    @Override
    public void doRender(EntityAnimalPig pig, double par2, double par4, double par6, float par8, float par9) {
        this.shadowSize = (float) (0.35f + pig.getPercentToAdulthood() * 0.35f);
        super.doRender(pig, par2, par4, par6, par8, par9);
    }
}
