package su.terrafirmagreg.modules.core.objects.blocks;

import su.terrafirmagreg.api.spi.block.BlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlockPuddle extends BlockBase {

    public BlockPuddle() {
        super(Material.GROUND);
        setBlockUnbreakable();
        setTickRandomly(true);
    }

    @Override
    public @NotNull String getName() {
        return "core/puddle";
    }

    @Override
    @Nullable
    @SuppressWarnings("deprecation")
    public RayTraceResult collisionRayTrace(@NotNull IBlockState blockState, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Vec3d start,
                                            @NotNull Vec3d end) {
        return null;
    }

    @Override
    public void updateTick(@NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Random rand) {
        super.updateTick(world, pos, state, rand);
        if (!world.isRaining()) {
            if (rand.nextInt(3) == 0) {
                world.setBlockToAir(pos);
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(@NotNull IBlockState state, World world, BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos) {
        if (!world.isSideSolid(pos.down(), EnumFacing.UP)) {
            world.setBlockToAir(pos);
        }
    }

    @Override
    public boolean isReplaceable(@NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
        return true;
    }

    @Override
    public boolean isPassable(@NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos,
                                                     @NotNull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(@NotNull IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, @NotNull EnumFacing side) {
        return blockAccess.getBlockState(pos.offset(side)).getBlock() != this;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isTranslucent(@NotNull IBlockState state) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public @NotNull BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.00625D, 1.0D);
    }

    @Override
    @Nullable
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(@NotNull IBlockState blockState, @NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
        return null;
    }

    @Override
    public int quantityDropped(@NotNull Random random) {
        return 0;
    }
}
