package com.speeedcraft.tfgmod.mixins.tfcflorae.objects.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfcflorae.objects.blocks.BlockUrnLoot;

@Mixin(value = BlockUrnLoot.class, remap = false)
public class BlockUrnLootMixin {

	// TODO: make custom drop from loot, some seeds maybe?
	@Inject(method = "getDrops", at = @At(value = "HEAD"), remap = false, cancellable = true)
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune, CallbackInfo ci) {
		ci.cancel();
	}
}
