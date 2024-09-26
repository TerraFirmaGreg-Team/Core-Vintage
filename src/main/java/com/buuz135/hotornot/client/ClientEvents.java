package com.buuz135.hotornot.client;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import com.buuz135.hotornot.config.HotConfig;
import com.buuz135.hotornot.util.ItemEffect;

import static su.terrafirmagreg.data.Constants.MODID_HOTORNOT;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = MODID_HOTORNOT, value = Side.CLIENT)
public final class ClientEvents {

  @SubscribeEvent
  public static void onRenderItemTooltip(final ItemTooltipEvent event) {
    // Quit early if we shouldn't add a tooltip
    if (!HotConfig.renderEffectTooltip) {
      return;
    }

    final ItemStack itemStack = event.getItemStack();

    if (itemStack.isEmpty()) {
      return;
    }

    final boolean checkContents = itemStack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                                                          null) && HotConfig.EFFECT_HANDLING.checkItemContainerContents;

    for (final ItemEffect effect : ItemEffect.values()) {

      // This stack doesn't have this effect
      if (!effect.stackHasEffect(itemStack)) {
        if (checkContents) {
          final IItemHandler itemHandler = itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
          // Checked this in order to reach here
          assert itemHandler != null;

          if (ItemEffect.contentsHaveEffect(itemHandler, effect)) {
            event.getToolTip().add(I18n.format(effect.tooltip));
          }
        }
        continue;
      }

      event.getToolTip().add(I18n.format(effect.tooltip));
    }
  }
}
