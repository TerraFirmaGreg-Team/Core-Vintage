package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.module.animal.client.model.ModelAnimalCamel;
import net.dries007.tfc.module.animal.objects.entities.livestock.EntityAnimalCamel;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalCamel extends RenderAnimal<EntityAnimalCamel> {
    private static final ResourceLocation OLD = Helpers.getID("textures/entity/animal/livestock/camel_old.png");
    private static final ResourceLocation YOUNG = Helpers.getID("textures/entity/animal/livestock/camel_young.png");

    public RenderAnimalCamel(RenderManager p_i47203_1_) {
        super(p_i47203_1_, new ModelAnimalCamel(0.0F), 0.7F, YOUNG, OLD);
        this.addLayer(new LayerCamelDecor(this));
    }

}
