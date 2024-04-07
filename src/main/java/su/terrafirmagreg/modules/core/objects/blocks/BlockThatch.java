package su.terrafirmagreg.modules.core.objects.blocks;

import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

public class BlockThatch extends BlockBase {

    public BlockThatch() {
        super(new Material(MapColor.FOLIAGE) {

            @Override
            public boolean isOpaque() {
                return false;
            }
        });
        setSoundType(SoundType.PLANT);
        setHardness(0.6F);
        setLightOpacity(255); //Blocks light

        BlockUtils.setFireInfo(this, 60, 20);
    }

    @Override
    public @NotNull String getName() {
        return "core/thatch";
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "thatch");
        OreDictUtils.register(this, "block", "straw");
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(@NotNull IBlockState blockState, @NotNull IBlockAccess world, @NotNull BlockPos pos,
                                        @NotNull EnumFacing side) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getCollisionBoundingBox(@NotNull IBlockState blockState, @NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
        return NULL_AABB;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        // Return false in order to stop xray through blocks
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public @NotNull BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public void onEntityCollision(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, Entity entityIn) {
        // Player will take damage when hitting thatch if fall is over 13 blocks, fall damage is then set to 0.
        entityIn.fall((entityIn.fallDistance - 10), 1.0F); // TODO: 17/4/18 balance fall damage reduction.
        entityIn.fallDistance = 0;

        entityIn.motionX *= 0.1;
        entityIn.motionZ *= 0.1;

        // This makes the player way too slow
        //entityIn.setInWeb();
    }
}
