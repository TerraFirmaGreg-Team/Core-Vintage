package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.module.animal.client.model.ModelAnimalGoat;
import net.dries007.tfc.module.animal.objects.entities.livestock.EntityAnimalGoat;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalGoat extends RenderAnimal<EntityAnimalGoat> {
    private static final ResourceLocation GOAT_OLD = Helpers.getID("textures/entity/animal/livestock/goat_old.png");
    private static final ResourceLocation GOAT_YOUNG = Helpers.getID("textures/entity/animal/livestock/goat_young.png");

    public RenderAnimalGoat(RenderManager renderManager) {
        super(renderManager, new ModelAnimalGoat(), 0.7F, GOAT_YOUNG, GOAT_OLD);
    }
}
