package com.speeedcraft.tfgmod.mixins.tfctech;

import gregtech.common.items.ToolItems;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import tfctech.client.TechSounds;
import tfctech.objects.blocks.devices.BlockLatexExtractor;
import tfctech.objects.tileentities.TELatexExtractor;

import javax.annotation.Nonnull;

@Mixin(value = BlockLatexExtractor.class, remap = false)
public abstract class BlockLatexExtractorMixin extends Block {

	public BlockLatexExtractorMixin(Material materialIn) {
		super(materialIn);
	}

	/**
	 * @author SpeeeDCraft
	 * @reason Allows to use knife on latex extractor installing
	 */
	@Overwrite
	public boolean onBlockActivated(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
		TELatexExtractor te = Helpers.getTE(world, pos, TELatexExtractor.class);
		if (te != null && hand == EnumHand.MAIN_HAND) {
			ItemStack stack = player.getHeldItem(hand);
			if (stack.getItem() == ToolItems.KNIFE) {
				if (te.makeCut()) {
					world.playSound(null, pos, TechSounds.RUBBER_TRUNK_SCRATH, SoundCategory.BLOCKS, 1.0F, 1.0F);
					return true;
				}
			} else if (!te.hasPot() && te.isValidPot(stack) && te.addPot(stack)) {
				stack.shrink(1);
				world.playSound(null, pos, TechSounds.RUBBER_BOWL_FIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			} else if (!te.hasBase() && te.isValidBase(stack) && te.addBase(stack)) {
				stack.shrink(1);
				world.playSound(null, pos, TechSounds.RUBBER_MOUNT_FIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			} else if (stack.isEmpty() && te.hasPot()) {
				player.setHeldItem(hand, te.removePot());
				world.playSound(null, pos, TechSounds.RUBBER_BOWL_GRAB, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			} else if (stack.isEmpty() && te.hasBase()) {
				player.setHeldItem(hand, te.removeBase());
				world.playSound(null, pos, TechSounds.RUBBER_GROOVE_FIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			}
		}
		return (super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ));
	}
}
