package su.terrafirmagreg.modules.core.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.block.SoundType;
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

public class BlockThatch extends BaseBlock {

    public BlockThatch() {
        super(Settings.of()
                .material(Material.PLANTS)
                .soundType(SoundType.PLANT)
                .nonOpaque()
                .nonFullCube()
                .hardness(0.6F));

        BlockUtils.setFireInfo(this, 60, 20);
    }

    @Override
    public String getName() {
        return "core/thatch";
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "thatch");
        OreDictUtils.register(this, "block", "straw");
    }

    @SideOnly(Side.CLIENT)
    @Override
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        // Player will take damage when hitting thatch if fall is over 13 blocks, fall damage is then set to 0.
        entityIn.fall((entityIn.fallDistance - 10), 1.0F); // TODO: 17/4/18 balance fall damage reduction.
        entityIn.fallDistance = 0;

        entityIn.motionX *= 0.1;
        entityIn.motionZ *= 0.1;

        // This makes the player way too slow
        //entityIn.setInWeb();
    }
}
