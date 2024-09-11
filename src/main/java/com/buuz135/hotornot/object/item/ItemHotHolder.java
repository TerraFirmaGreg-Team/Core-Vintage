package com.buuz135.hotornot.object.item;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


import net.dries007.tfc.api.types.Metal.Tier;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.Helpers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    return switch (tier) {
      case TIER_0, TIER_I -> 1;
      case TIER_II -> 0.9F;
      case TIER_III -> 0.8F;
      case TIER_IV -> 0.7F;
      case TIER_V -> 0.6F;
      case TIER_VI -> 0.5F;
    };
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
  public @NotNull Weight getWeight(final ItemStack itemStack) {
    return Weight.HEAVY;
  }

  @Override
  public @NotNull Size getSize(final ItemStack itemStack) {
    return Size.VERY_LARGE;
  }

  @Override
  public boolean canStack(final ItemStack itemStack) {
    return false;
  }
}
