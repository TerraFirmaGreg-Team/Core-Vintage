package net.dries007.tfc.module.core.submodule.soil.common.blocks;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import net.dries007.tfc.module.core.submodule.soil.api.type.SoilType;
import net.dries007.tfc.module.core.submodule.soil.api.variant.block.SoilBlockVariant;

public class BlockSoilDirt extends BlockSoil {

    public BlockSoilDirt(SoilBlockVariant variant, SoilType type) {
        super(variant, type);

        DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
    }
}
