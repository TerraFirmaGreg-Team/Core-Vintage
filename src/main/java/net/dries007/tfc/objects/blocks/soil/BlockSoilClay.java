package net.dries007.tfc.objects.blocks.soil;

import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockSoilClay extends BlockSoil {

    public BlockSoilClay(SoilBlockVariant soilBlockVariant, SoilType soilType) {
        super(soilBlockVariant, soilType);
    }

    @Override
    public int quantityDropped(@Nonnull IBlockState state, int fortune, Random random) {
        return random.nextInt(4);
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return Items.CLAY_BALL;
    }
}
