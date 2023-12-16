package com.speeedcraft.tfgmod.mixins.gregtech;

import com.speeedcraft.tfgmod.gregtech.oreprefix.TFGOrePrefix;
import gregtech.api.capability.impl.miner.MinerLogic;
import gregtech.api.unification.OreDictUnifier;
import gregtech.common.blocks.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinerLogic.class, remap = false)
public class MinerLogicMixin {

	@Inject(method = "getRegularBlockDrops", at = @At(value = "HEAD"), remap = false, cancellable = true)
	protected void onGetRegularBlockDrops(NonNullList<ItemStack> blockDrops, WorldServer world, BlockPos blockToMine, IBlockState blockState, CallbackInfo ci) {
		if (blockState.getBlock() instanceof BlockOre blockOre) {
			blockDrops.add(OreDictUnifier.get(TFGOrePrefix.oreChunk, blockOre.material));
			ci.cancel();
		}
	}
}
