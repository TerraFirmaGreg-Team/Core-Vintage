package net.dries007.tfc.objects.blocks.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public class BlockSoilDirt extends BlockSoil {

    public BlockSoilDirt(SoilBlockVariant soilBlockVariant, SoilType soilType) {
        super(soilBlockVariant, soilType);
    }
}
