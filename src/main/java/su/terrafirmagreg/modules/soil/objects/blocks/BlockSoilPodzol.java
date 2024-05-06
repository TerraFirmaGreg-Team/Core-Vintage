package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.api.data.Blockstates.*;

public class BlockSoilPodzol extends BlockSoil {

    public BlockSoilPodzol(SoilBlockVariant variant, SoilType type) {
        super(variant, type);

        setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, Boolean.FALSE)
                .withProperty(EAST, Boolean.FALSE)
                .withProperty(SOUTH, Boolean.FALSE)
                .withProperty(WEST, Boolean.FALSE));

        //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        pos = pos.add(0, -1, 0);
        return state
                .withProperty(NORTH, world.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() instanceof BlockSoilPodzol)
                .withProperty(EAST, world.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof BlockSoilPodzol)
                .withProperty(SOUTH, world.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof BlockSoilPodzol)
                .withProperty(WEST, world.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() instanceof BlockSoilPodzol);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH);
    }

    @NotNull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
