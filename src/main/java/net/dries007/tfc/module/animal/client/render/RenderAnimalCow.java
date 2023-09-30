package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.module.animal.client.model.ModelAnimalCow;
import net.dries007.tfc.module.animal.common.entities.livestock.EntityAnimalCow;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalCow extends RenderAnimal<EntityAnimalCow> {
    private static final ResourceLocation COW_YOUNG = Helpers.getID("textures/entity/animal/livestock/cow_young.png");
    private static final ResourceLocation COW_OLD = Helpers.getID("textures/entity/animal/livestock/cow_old.png");

    public RenderAnimalCow(RenderManager renderManager) {
        super(renderManager, new ModelAnimalCow(), 0.7F, COW_YOUNG, COW_OLD);
    }
}
