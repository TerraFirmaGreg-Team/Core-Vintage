package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariants;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static su.terrafirmagreg.api.data.Blockstates.CLAY;

public class BlockSoilDirt extends BlockSoil {

    public BlockSoilDirt(SoilBlockVariant variant, SoilType type) {
        super(variant, type);

        setDefaultState(getBlockState().getBaseState().withProperty(CLAY, Boolean.FALSE));

        //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return state.getValue(CLAY) ? random.nextInt(4) : super.quantityDropped(state, fortune, random);
    }

    @NotNull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(CLAY) ? Items.CLAY_BALL : SoilItemVariants.PILE.get(getType());
    }

    @NotNull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return this.getBlockState().getBaseState().getValue(CLAY) ? BlockRenderLayer.CUTOUT : BlockRenderLayer.SOLID;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CLAY);
    }
}
