package net.dries007.tfc.api.types.wood;

import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.wood.BlockWoodSapling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public interface ITreeGenerator {

    /**
     * Called to generate a tree. Each Tree must have one of these. Used for world gen and sapling growth
     *
     * @param manager  an instance of the world's template manager. Used for getting structures.
     * @param world    The world
     * @param pos      The position where the sapling was / would've been
     * @param woodType The tree type to spawn
     * @param rand     A random to use in generation
     */
    void generateTree(TemplateManager manager, World world, BlockPos pos, WoodType woodType, Random rand, boolean isWorldGen);

    /**
     * Checks if a tree can be generated. This implementation checks height, radius, and light level
     *
     * @param world    The world
     * @param pos      The pos of the tree
     * @param woodType The tree type (for checking if the tree can generate)
     * @return true if the tree can generate.
     */
    default boolean canGenerateTree(World world, BlockPos pos, WoodType woodType) {
        // Check if ground is flat enough
        final int radius = woodType.getMaxGrowthRadius();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if ((x == 0 && z == 0) ||
                        world.getBlockState(pos.add(x, 0, z)).getMaterial().isReplaceable() ||
                        ((x > 1 || z > 1) && world.getBlockState(pos.add(x, 1, z)).getMaterial().isReplaceable()))
                    continue;
                return false;
            }
        }
        // Check if there is room directly upwards
        final int height = woodType.getMaxHeight();
        for (int y = 1; y <= height; y++) {
            IBlockState state = world.getBlockState(pos.up(y));
            if (!state.getMaterial().isReplaceable() && state.getMaterial() != Material.LEAVES) {
                return false;
            }
        }

        // Check if there is a solid block beneath
        if (!BlocksTFC.isGrowableSoil(world.getBlockState(pos.down()))) {
            return false;
        }

        // Check the position for liquids, etc.
        IBlockState stateAt = world.getBlockState(pos);
        return !stateAt.getMaterial().isLiquid() && (stateAt.getMaterial().isReplaceable() || stateAt.getBlock() instanceof BlockWoodSapling);
    }
}
