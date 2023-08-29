package net.dries007.tfc.common.objects.blocks.soil;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.block.SoilBlockVariant;

public class BlockSoilDirt extends BlockSoil {

    public BlockSoilDirt(SoilBlockVariant variant, SoilType type) {
        super(variant, type);

        DirtHelper.registerSoil(this.getDefaultState().getBlock(), DirtHelper.DIRTLIKE);
    }
}
