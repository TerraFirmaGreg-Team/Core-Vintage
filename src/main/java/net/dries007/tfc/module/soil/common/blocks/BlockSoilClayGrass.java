package net.dries007.tfc.module.soil.common.blocks;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import net.dries007.tfc.module.soil.api.type.SoilType;
import net.dries007.tfc.module.soil.api.variant.block.SoilBlockVariant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockSoilClayGrass extends BlockSoilGrass {

    public BlockSoilClayGrass(SoilBlockVariant variant, SoilType type) {
        super(variant, type);

        DirtHelper.registerSoil(this.getDefaultState().getBlock(), DirtHelper.DIRTLIKE);
    }

    @Override
    public int quantityDropped(@Nonnull IBlockState state, int fortune, @Nonnull Random random) {
        return random.nextInt(4);
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return Items.CLAY_BALL;
    }
}
