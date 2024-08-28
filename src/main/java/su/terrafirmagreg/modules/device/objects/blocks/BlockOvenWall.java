package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


import static net.minecraft.block.BlockHorizontal.FACING;
import static su.terrafirmagreg.api.data.Blockstates.CURED;
import static su.terrafirmagreg.api.lib.MathConstants.RNG;

@SuppressWarnings("deprecation")
public class BlockOvenWall extends BaseBlock {

    public static final AxisAlignedBB OVEN_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 9.0 / 16, 16.0D / 16, 16.0D / 16, 16.0D / 16);
    public static final AxisAlignedBB OVEN_WALL_EAST = new AxisAlignedBB(0.0D, 0.0D, 7.0D / 16, 16.0D / 16, 16.0D / 16, 0.0D);
    public static final AxisAlignedBB OVEN_WALL_NORTH = new AxisAlignedBB(7.0D / 16, 0.0D, 0.0D, 0.0D, 16.0D / 16, 16.0D / 16);
    public static final AxisAlignedBB OVEN_WALL_SOUTH = new AxisAlignedBB(9.0D / 16, 0.0D, 0.0D, 16.0D / 16, 16.0D / 16, 16.0D / 16);

    public BlockOvenWall() {
        super(Settings.of(Material.ROCK, MapColor.RED_STAINED_HARDENED_CLAY));

        getSettings()
                .registryKey("device/oven_wall")
                .hardness(2.0F)
                .resistance(3.0F)
                .lightValue(0)
                .nonOpaque()
                .nonFullCube()
                .size(Size.NORMAL)
                .weight(Weight.HEAVY);

        setDefaultState(getBlockState().getBaseState()
                .withProperty(CURED, Boolean.FALSE)
                .withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (facing.getAxis() == EnumFacing.Axis.Y) {
            facing = placer.getHorizontalFacing().getOpposite();
        }
        return getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.byHorizontalIndex(meta))
                .withProperty(CURED, meta > 3);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(CURED) ? 4 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, CURED);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> OVEN_WALL_SOUTH;
            case WEST -> OVEN_WALL_WEST;
            case EAST -> OVEN_WALL_EAST;
            default -> OVEN_WALL_NORTH;
        };
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (state.getValue(CURED)) {
            drops.add(new ItemStack(Items.BRICK, 3 + RNG.nextInt(3)));
        } else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
