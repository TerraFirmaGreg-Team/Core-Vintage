package com.lumintorious.ambiental.api;

import com.lumintorious.ambiental.modifier.EnvironmentalModifier;
import com.lumintorious.ambiental.modifier.TempModifier;
import com.lumintorious.ambiental.modifier.TempModifierStorage;
import net.dries007.tfc.objects.blocks.stone.BlockRockRaw;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

@FunctionalInterface
public interface IBlockTemperatureProvider {
	Optional<TempModifier> getModifier(EntityPlayer player, BlockPos pos, IBlockState state);

	static void evaluateAll(EntityPlayer player, TempModifierStorage storage) {
		BlockPos p = player.getPosition();
		BlockPos pos1 = new BlockPos(p.getX() - 9, p.getY() - 3, p.getZ() - 9);
		BlockPos pos2 = new BlockPos(p.getX() + 9, p.getY() + 5, p.getZ() + 9);
		Iterable<BlockPos.MutableBlockPos> allPositions = BlockPos.getAllInBoxMutable(pos1, pos2);
		IBlockState skipState = Blocks.AIR.getDefaultState();
		for(BlockPos pos : allPositions) {
			IBlockState state = player.world.getBlockState(pos);
			if(state == skipState) {
				continue;
			}
            if(state.getBlock() instanceof BlockRockVariant || state.getBlock() instanceof BlockRockRaw) {
                continue;
            }
			Block block = state.getBlock();
			double distance = Math.sqrt(player.getPosition().distanceSq(pos));
			float distanceMultiplier = (float) distance / 9f;
			distanceMultiplier = Math.min(1f, Math.max(0f, distanceMultiplier));
			distanceMultiplier = 1f - distanceMultiplier;
			boolean isInside = EnvironmentalModifier.getSkylight(player) < 14 && EnvironmentalModifier.getBlockLight(player) > 3;
			if(isInside) {
				distanceMultiplier *= 1.3f;
			}
			final float finalDistanceMultiplier = distanceMultiplier;
			for(IBlockTemperatureProvider provider : AmbientalRegistry.BLOCKS) {
				storage.add(provider.getModifier(player, pos, state));
			}
			TileEntity tile = player.world.getTileEntity(pos);
			if(tile != null) {
				for(ITileEntityTemperatureProvider provider : AmbientalRegistry.TILE_ENTITIES) {
					provider.getModifier(player, tile).ifPresent((mod) -> {
						mod.setChange(mod.getChange() * finalDistanceMultiplier);
						mod.setPotency(mod.getPotency() * finalDistanceMultiplier);
						storage.add(mod);
					});
				}
			}
		}
	}
}
