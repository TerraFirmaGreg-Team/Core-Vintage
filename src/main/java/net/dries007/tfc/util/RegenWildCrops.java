package net.dries007.tfc.util;


import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.plant.common.blocks.BlockPlantShortGrass;
import net.dries007.tfc.world.classic.worldgen.WorldGenWildCrops;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RegenWildCrops extends WorldGenWildCrops {
    @Override
    protected boolean isValidPosition(World world, BlockPos pos) {
        //Modified to allow replacement of grass during spring regen
        Block test = world.getBlockState(pos).getBlock();
        return (test instanceof BlockPlantShortGrass || test.isAir(world.getBlockState(pos), world, pos) && Helpers.isSoil(world.getBlockState(pos.down())));
    }
}
