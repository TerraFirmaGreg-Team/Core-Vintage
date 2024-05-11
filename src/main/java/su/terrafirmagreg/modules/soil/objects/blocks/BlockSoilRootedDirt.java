package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.util.BlockRenderLayer;

public class BlockSoilRootedDirt extends BlockSoil {

    public BlockSoilRootedDirt(SoilBlockVariant variant, SoilType type) {
        super(variant, type);

        getSettings()
                .renderLayer(BlockRenderLayer.CUTOUT);

        //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
    }
}
