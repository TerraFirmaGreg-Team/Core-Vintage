/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.module.core.submodule.soil.common.blocks;


import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import net.dries007.tfc.module.core.submodule.soil.api.variant.block.ISoilBlock;
import net.dries007.tfc.module.core.submodule.soil.api.type.SoilType;
import net.dries007.tfc.module.core.submodule.soil.api.variant.block.SoilBlockVariant;

public class BlockSoilDryGrass extends BlockSoilGrass implements ISoilBlock {

    public BlockSoilDryGrass(SoilBlockVariant variant, SoilType type) {
        super(variant, type);

        DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
    }
}
