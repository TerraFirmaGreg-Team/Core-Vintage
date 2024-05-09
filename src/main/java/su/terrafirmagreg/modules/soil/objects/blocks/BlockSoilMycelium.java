package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.spi.itemblock.BaseItemBlock;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.api.util.FallingBlockManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.api.data.Blockstates.*;

@Getter
public class BlockSoilMycelium extends BlockMycelium implements ISoilBlock {

    private final SoilBlockVariant variant;
    private final SoilType type;

    public BlockSoilMycelium(SoilBlockVariant variant, SoilType type) {

        this.variant = variant;
        this.type = type;

        FallingBlockManager.registerFallable(this, variant.getSpecification());

        setDefaultState(getBlockState().getBaseState()
                .withProperty(NORTH, Boolean.FALSE)
                .withProperty(EAST, Boolean.FALSE)
                .withProperty(SOUTH, Boolean.FALSE)
                .withProperty(WEST, Boolean.FALSE)
                .withProperty(SNOWY, Boolean.FALSE)
                .withProperty(CLAY, Boolean.FALSE)
        );

        //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
    }

    @Override
    public @Nullable BaseItemBlock getItemBlock() {
        return new BaseItemBlock(this);
    }

    @NotNull
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        pos = pos.add(0, -1, 0);
        Block blockUp = world.getBlockState(pos.up()).getBlock();
        return state
                .withProperty(NORTH, world.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() instanceof BlockSoilMycelium)
                .withProperty(EAST, world.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof BlockSoilMycelium)
                .withProperty(SOUTH, world.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof BlockSoilMycelium)
                .withProperty(WEST, world.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() instanceof BlockSoilMycelium)
                .withProperty(SNOWY, blockUp == Blocks.SNOW || blockUp == Blocks.SNOW_LAYER);
    }

    @NotNull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, SNOWY, CLAY);
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return state.getValue(CLAY) ? random.nextInt(4) : super.quantityDropped(state, fortune, random);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(CLAY) ? Items.CLAY_BALL : SoilItemVariants.PILE.get(getType());
    }

    @NotNull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
