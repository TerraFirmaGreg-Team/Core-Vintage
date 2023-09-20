package net.dries007.tfc.module.core.client.render.animal;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.core.client.model.animal.ModelAlpacaBodyTFC;
import net.dries007.tfc.module.core.common.objects.entity.animal.EntityAlpacaTFC;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;


@SideOnly(Side.CLIENT)
@ParametersAreNonnullByDefault
public class RenderAlpacaTFC extends RenderAnimalTFC<EntityAlpacaTFC> {
    private static final ResourceLocation ALPACA_OLD = TerraFirmaCraft.identifier("textures/entity/animal/livestock/alpaca_old.png");
    private static final ResourceLocation ALPACA_YOUNG = TerraFirmaCraft.identifier("textures/entity/animal/livestock/alpaca_young.png");

    public RenderAlpacaTFC(RenderManager renderManager) {
        super(renderManager, new ModelAlpacaBodyTFC(), 0.7F, ALPACA_YOUNG, ALPACA_OLD);
        this.addLayer(new LayerAlpacaWoolTFC(this));
    }
}
