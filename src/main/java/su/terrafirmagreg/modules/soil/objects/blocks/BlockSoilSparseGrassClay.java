package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BlockSoilSparseGrassClay extends BlockSoilSparseGrass {

    public BlockSoilSparseGrassClay(SoilBlockVariant variant, SoilType type) {
        super(variant, type);

        //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return random.nextInt(4);
    }

    @NotNull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.CLAY_BALL;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
