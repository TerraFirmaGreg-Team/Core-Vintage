package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.animal.client.model.ModelAnimalAlpacaBody;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalAlpaca;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderAnimalAlpaca extends RenderAnimal<EntityAnimalAlpaca> {

    private static final ResourceLocation ALPACA_OLD = ModUtils.getID("textures/entity/animal/livestock/alpaca_old.png");
    private static final ResourceLocation ALPACA_YOUNG = ModUtils.getID("textures/entity/animal/livestock/alpaca_young.png");

    public RenderAnimalAlpaca(RenderManager renderManager) {
        super(renderManager, new ModelAnimalAlpacaBody(), 0.7F, ALPACA_YOUNG, ALPACA_OLD);
        this.addLayer(new LayerAlpacaWool(this));
    }
}
