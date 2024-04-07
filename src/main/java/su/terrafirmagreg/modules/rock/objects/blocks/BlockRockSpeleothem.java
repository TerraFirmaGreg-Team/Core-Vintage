package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariants;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import mcp.MethodsReturnNonnullByDefault;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

@MethodsReturnNonnullByDefault

public class BlockRockSpeleothem extends BlockRock {

    public static final PropertyEnum<EnumSize> SIZE = PropertyEnum.create("size", EnumSize.class);

    private final RockType type;

    public BlockRockSpeleothem(RockBlockVariant variant, RockType type) {
        super(variant, type);

        this.type = type;

        setDefaultState(blockState.getBaseState()
                .withProperty(SIZE, EnumSize.MEDIUM));
    }

    @Override
    public boolean canPlaceBlockAt(@NotNull World worldIn, @NotNull BlockPos pos) {
        return getBearing(worldIn, pos) > 0;
    }

    @Override
    public void onBlockPlacedBy(@NotNull World worldIn, @NotNull BlockPos pos, IBlockState state, @NotNull EntityLivingBase placer,
                                @NotNull ItemStack stack) {
        var size = EnumSize.values()[Math.max(0, getBearing(worldIn, pos) - 1)];
        worldIn.setBlockState(pos, state.withProperty(SIZE, size));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos) {
        int size = state.getValue(SIZE).strength;
        if (getBearing(worldIn, pos) < size + 1) {
            worldIn.playEvent(2001, pos, Block.getStateId(worldIn.getBlockState(pos)));
            dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public int quantityDropped(@NotNull IBlockState state, int fortune, Random random) {
        return 1 + random.nextInt(3);
    }

    @Override
    public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
        return RockItemVariants.LOOSE.get(type);
    }

    @Override
    public boolean canSilkHarvest(@NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer player) {
        return true;
    }

    @Override
    public boolean isPassable(@NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
        return false;
    }

    private int getBearing(IBlockAccess world, BlockPos pos) {
        return Math.max(getStrength(world, pos.down()), getStrength(world, pos.up()));
    }

    private int getStrength(IBlockAccess world, BlockPos pos) {
        var state = world.getBlockState(pos);
        if (state.isFullBlock()) return 3;

        if (state.getPropertyKeys().contains(SIZE)) return state.getValue(SIZE).strength;

        return 0;
    }

    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        return state.getValue(SIZE).aabb;
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(@NotNull IBlockState blockState, @NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
        return getBoundingBox(blockState, worldIn, pos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullBlock(@NotNull IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos blockPos,
                                            @NotNull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean canPlaceTorchOnTop(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos) {
        return true;
    }

    @NotNull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, SIZE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(SIZE).ordinal();
    }

    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(SIZE, EnumSize.values()[Math.min(EnumSize.values().length - 1, meta)]);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getActualState(final @NotNull IBlockState state, final @NotNull IBlockAccess worldIn, final @NotNull BlockPos pos) {
        var size = EnumSize.values()[Math.max(0, getBearing(worldIn, pos) - 1)];
        if (isCenter(worldIn, pos)) size = EnumSize.MEDIUM;
        return state.withProperty(SIZE, size);
    }

    private boolean isCenter(IBlockAccess world, BlockPos pos) {
        return isThis(world, pos.down()) && isThis(world, pos.up());
    }

    private boolean isThis(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof BlockRockSpeleothem;
    }

    public enum EnumSize implements IStringSerializable {

        SMALL(0, 2),
        MEDIUM(1, 4),
        BIG(2, 8);

        public final int strength;
        public final AxisAlignedBB aabb;

        EnumSize(int strength, int width) {
            this.strength = strength;

            float pad = (((16 - width) / 2f) / 16F);
            aabb = new AxisAlignedBB(pad, 0F, pad, 1F - pad, 1F, 1F - pad);
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}
