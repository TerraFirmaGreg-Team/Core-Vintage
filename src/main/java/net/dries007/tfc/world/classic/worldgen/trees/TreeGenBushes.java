package net.dries007.tfc.world.classic.worldgen.trees;

import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.trees.ITreeGenerator;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariants;
import net.dries007.tfc.common.objects.blocks.BlocksTFC_old;
import net.dries007.tfc.common.objects.blocks.wood.BlockWoodSapling;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

import static net.dries007.tfc.common.objects.blocks.wood.BlockWoodLog.PLACED;
import static net.minecraft.block.BlockLeaves.DECAYABLE;
import static net.minecraft.block.BlockLog.LOG_AXIS;

public class TreeGenBushes implements ITreeGenerator {
    @Override
    public void generateTree(TemplateManager manager, World world, BlockPos pos, WoodType woodType, Random rand, boolean isWorldGen) {
        IBlockState leaves = TFCStorage.getWoodBlock(WoodBlockVariants.LEAVES, woodType).getDefaultState().withProperty(DECAYABLE, true);

        // Has to fake being placed, otherwise the log will just poof out of existence. todo: better fix for this.
        checkAndPlace(TFCStorage.getWoodBlock(WoodBlockVariants.LOG, woodType).getDefaultState()
                .withProperty(PLACED, true)
                .withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE), world, pos);
        checkAndPlace(leaves, world, pos.add(0, 1, 0));

        for (EnumFacing face : EnumFacing.HORIZONTALS) {
            if (rand.nextFloat() < 0.9) {
                checkAndPlace(leaves, world, pos.offset(face));
                checkAndPlace(leaves, world, pos.offset(face).add(0, -1, 0));
                if (rand.nextFloat() < 0.7)
                    checkAndPlace(leaves, world, pos.offset(face).add(0, 1, 0));

                if (rand.nextFloat() < 0.5)
                    checkAndPlace(leaves, world, pos.offset(face).offset(face.rotateY()));
            }

        }
    }

    @Override
    public boolean canGenerateTree(World world, BlockPos pos, WoodType woodType) {
        // Check if there is soil beneath
        if (!BlocksTFC_old.isSoil(world.getBlockState(pos.down())))
            return false;

        // Check the position for liquids, etc.
        if (world.getBlockState(pos).getMaterial().isLiquid() || !world.getBlockState(pos).getMaterial().isReplaceable())
            if (!(world.getBlockState(pos) instanceof BlockWoodSapling))
                return false;

        // Check if there is sufficient light level
        return world.getLightFromNeighbors(pos) >= 7;
    }

    private void checkAndPlace(IBlockState state, World world, BlockPos pos) {
        if (world.getBlockState(pos).getMaterial().isReplaceable())
            world.setBlockState(pos, state);
    }
}
