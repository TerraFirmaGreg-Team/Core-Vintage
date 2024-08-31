package net.dries007.tfc.util;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorWildCrops;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import net.dries007.tfc.objects.blocks.plants.BlockShortGrassTFC;

public class RegenWildCrops extends GeneratorWildCrops {

    @Override
    protected boolean isValidPosition(World world, BlockPos pos) {
        //Modified to allow replacement of grass during spring regen
        Block block = world.getBlockState(pos).getBlock();
        return (block instanceof BlockShortGrassTFC ||
                block.isAir(world.getBlockState(pos), world, pos) && BlockUtils.isSoil(world.getBlockState(pos.down())));
    }
}
