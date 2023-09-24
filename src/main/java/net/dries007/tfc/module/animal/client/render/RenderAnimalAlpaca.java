package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.animal.client.model.ModelAnimalAlpacaBody;
import net.dries007.tfc.module.animal.common.entity.livestock.EntityAnimalAlpaca;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;


@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAnimalAlpaca extends RenderAnimal<EntityAnimalAlpaca> {
    private static final ResourceLocation ALPACA_OLD = TerraFirmaCraft.getID("textures/entity/animal/livestock/alpaca_old.png");
    private static final ResourceLocation ALPACA_YOUNG = TerraFirmaCraft.getID("textures/entity/animal/livestock/alpaca_young.png");

    public RenderAnimalAlpaca(RenderManager renderManager) {
        super(renderManager, new ModelAnimalAlpacaBody(), 0.7F, ALPACA_YOUNG, ALPACA_OLD);
        this.addLayer(new LayerAlpacaWool(this));
    }
}
