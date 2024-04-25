package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.modules.device.objects.tiles.TEInfectedAir;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockInfectedAir extends BlockBase implements ITEBlock {

    public BlockInfectedAir() {
        super(Material.AIR);

        setHardness(2F);
    }

    public EnumBlockRenderType getRenderType(IBlockState p_getRenderType_1_) {
        return EnumBlockRenderType.MODEL;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState p_getCollisionBoundingBox_1_, IBlockAccess p_getCollisionBoundingBox_2_,
                                                 BlockPos p_getCollisionBoundingBox_3_) {
        return NULL_AABB;
    }

    public boolean isOpaqueCube(IBlockState p_isOpaqueCube_1_) {
        return false;
    }

    public boolean canCollideCheck(IBlockState p_canCollideCheck_1_, boolean p_canCollideCheck_2_) {
        return false;
    }

    public void dropBlockAsItemWithChance(World p_dropBlockAsItemWithChance_1_, BlockPos p_dropBlockAsItemWithChance_2_,
                                          IBlockState p_dropBlockAsItemWithChance_3_, float p_dropBlockAsItemWithChance_4_,
                                          int p_dropBlockAsItemWithChance_5_) {
    }

    public boolean isReplaceable(IBlockAccess p_isReplaceable_1_, BlockPos p_isReplaceable_2_) {
        return true;
    }

    public boolean isFullCube(IBlockState p_isFullCube_1_) {
        return false;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess p_getBlockFaceShape_1_, IBlockState p_getBlockFaceShape_2_, BlockPos p_getBlockFaceShape_3_,
                                            EnumFacing p_getBlockFaceShape_4_) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public @NotNull String getName() {
        return "device/infected_air";
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TEInfectedAir.class;
    }
}
