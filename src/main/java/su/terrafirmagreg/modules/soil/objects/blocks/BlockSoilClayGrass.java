package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BlockSoilClayGrass extends BlockSoilGrass {

    public BlockSoilClayGrass(SoilBlockVariant blockVariant, SoilType type) {
        super(blockVariant, type);

        //DirtHelper.registerSoil(this.getDefaultState().get(), DirtHelper.DIRTLIKE);
    }

    @Override
    public int quantityDropped(@NotNull IBlockState state, int fortune, @NotNull Random random) {
        return random.nextInt(4);
    }

    @NotNull
    @Override
    public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
        return Items.CLAY_BALL;
    }

}
