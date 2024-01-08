package com.buuz135.hotornot.object.item;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal.Tier;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.Helpers;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemHotHolder extends ItemTFC {

	private final Tier tier;

	/**
	 * @param tier A TFC metal tier to easily display our tier levels
	 */
	public ItemHotHolder(final Tier tier) {
		this.tier = tier;
		setMaxStackSize(1);
	}

	/**
	 * @param itemStack The passed in item stack unused in the default implementation
	 * @return The damage chance of this Hot Holder Item
	 */
	public float damageChance(final ItemStack itemStack) {
		switch (tier) {
			case TIER_0:
			case TIER_I:
				return 1;
			case TIER_II:
				return 0.9F;
			case TIER_III:
				return 0.8F;
			case TIER_IV:
				return 0.7F;
			case TIER_V:
				return 0.6F;
			case TIER_VI:
				return 0.5F;
		}
		return 1;
	}

	@Override
	public void addInformation(final ItemStack itemStack, final @Nullable World world, final List<String> tooltip, final ITooltipFlag tooltipFlag) {
		tooltip.add(I18n.format(Helpers.getEnumName(tier)) + " - " + I18n.format("tooltip.hotornot.hot_holder_tooltip"));
	}

	@Override
	public boolean doesSneakBypassUse(final ItemStack itemStack, final IBlockAccess world, final BlockPos blockPos, final EntityPlayer player) {
		return true;
	}

	@Override
	public boolean shouldCauseReequipAnimation(final ItemStack oldStack, final ItemStack newStack, final boolean slotChanged) {
		// Prevents the reequip animation upon damage
		if (ItemStack.areItemsEqualIgnoreDurability(oldStack, newStack) && !slotChanged) {
			return false;
		}

		return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
	}

	@Override
	public Size getSize(final ItemStack itemStack) {
		return Size.VERY_LARGE;
	}

	@Override
	public Weight getWeight(final ItemStack itemStack) {
		return Weight.HEAVY;
	}

	@Override
	public boolean canStack(final ItemStack itemStack) {
		return false;
	}
}
