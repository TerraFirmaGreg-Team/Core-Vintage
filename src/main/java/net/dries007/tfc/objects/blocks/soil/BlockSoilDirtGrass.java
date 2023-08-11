/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.objects.blocks.soil;


import net.dries007.tfc.api.types.soil.ISoilBlock;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariant;
import net.minecraft.block.SoundType;

public class BlockSoilDirtGrass extends BlockSoilGrass implements ISoilBlock {

    public BlockSoilDirtGrass(SoilBlockVariant soilBlockVariant, SoilType soilType) {
        super(soilBlockVariant, soilType);
    }
}
