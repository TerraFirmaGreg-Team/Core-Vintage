/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.common.objects.blocks.soil;


import net.dries007.tfc.api.types.soil.ISoilBlock;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariant;

public class BlockSoilDryGrass extends BlockSoilGrass implements ISoilBlock {

    public BlockSoilDryGrass(SoilBlockVariant variant, SoilType type) {
        super(variant, type);
    }
}
