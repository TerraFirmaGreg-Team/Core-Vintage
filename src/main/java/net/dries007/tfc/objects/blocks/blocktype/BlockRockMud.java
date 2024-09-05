package net.dries007.tfc.objects.blocks.blocktype;

import su.terrafirmagreg.modules.core.features.falling.FallingBlockManager;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.items.rock.ItemMud;
import net.dries007.tfc.types.BlockTypesTFCF.RockTFCF;

import org.jetbrains.annotations.Nullable;

import java.util.Random;

@MethodsReturnNonnullByDefault

public class BlockRockMud extends BlockRockVariantTFCF {

    protected static final AxisAlignedBB MUD_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6D, 1.0D);

    public BlockRockMud(RockTFCF rockTFCF, Rock rock) {
        super(rockTFCF, rock);
        if (rockTFCF.canFall()) {
            FallingBlockManager.Specification spec = rockTFCF.getFallingSpecification();
            switch (rockTFCF) {
                case MUD:
                    spec = new FallingBlockManager.Specification(spec);

                    RockTFCF tempRock = RockTFCF.getNonGrassVersionStatic(rockTFCF);
                    if (tempRock != null)
                        spec.setResultingState(BlockRockVariantTFCF.get(rock, tempRock).getDefaultState());
                    else {
                        Rock.Type tempRockTFC = RockTFCF.getNonGrassVersionTFCStatic(rockTFCF);
                        if (tempRockTFC != null)
                            spec.setResultingState(BlockRockVariant.get(rock, tempRockTFC).getDefaultState());
                    }

                    FallingBlockManager.registerFallable(this, spec);
                    break;
                default:
                    spec = new FallingBlockManager.Specification(spec);
                    FallingBlockManager.registerFallable(this, rockTFCF.getFallingSpecification());
            }
        }
    }

    public static boolean isSupportingSideBlock(IBlockState state) {
        return state.isNormalCube() || (state.getBlock() instanceof BlockRockVariantTFCF);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return MUD_AABB;
    }

    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        entityIn.motionX *= 0.7D;
        entityIn.motionZ *= 0.7D;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (this.rockTFCF.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
            double d0 = (float) pos.getX() + rand.nextFloat();
            double d1 = (double) pos.getY() - 0.05D;
            double d2 = (float) pos.getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (rockTFCF == RockTFCF.MUD) {
            if (fortune > 3) {
                fortune = 3;
            }

            if (rand.nextInt(10 - fortune * 3) == 0) {
                return ItemMud.get(rock);
            }
        }
        return super.getItemDropped(state, rand, fortune);
    }

}
