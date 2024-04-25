package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalCamel;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalCamel;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalCamel extends RenderAnimal<EntityAnimalCamel> {

    private static final ResourceLocation OLD = ModUtils.id("textures/entity/animal/livestock/camel_old.png");
    private static final ResourceLocation YOUNG = ModUtils.id("textures/entity/animal/livestock/camel_young.png");

    public RenderAnimalCamel(RenderManager renderManager) {
        super(renderManager, new ModelAnimalCamel(0.0F), 0.7F, YOUNG, OLD);
        this.addLayer(new LayerCamelDecor(this));
    }

}
