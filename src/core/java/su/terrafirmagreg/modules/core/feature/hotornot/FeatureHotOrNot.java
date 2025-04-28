package su.terrafirmagreg.modules.core.feature.hotornot;

import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.framework.manager.feature.spi.FeatureBase;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.feature.hotornot.spi.FluidEffect;
import su.terrafirmagreg.modules.core.feature.hotornot.spi.ItemEffect;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

import gregtech.api.items.toolitem.ToolHelper;

import java.util.Arrays;
import java.util.List;

import static su.terrafirmagreg.api.data.ToolClasses.TONGS;

public class FeatureHotOrNot extends FeatureBase {

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public static void onItemTooltip(ItemTooltipEvent event) {

    ItemStack itemStack = event.getItemStack();
    List<String> tooltip = event.getToolTip();
    if (itemStack.isEmpty()) {return;}

    if (isRemoved(itemStack)) {
      return;
    }

    // Fluids
    CapabilityUtils.getOptional(itemStack, CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(capabilityFluidItem -> {
      FluidStack fluidStack = capabilityFluidItem.drain(1000, false);
      if (fluidStack != null) {
        for (FluidEffect effect : FluidEffect.values()) {
          if (effect.isValid.test(fluidStack)) {
            tooltip.add(effect.color + new TextComponentTranslation(effect.tooltip).getUnformattedText());
            return;
          }
        }
      }
    });

    for (ItemEffect effect : ItemEffect.values()) {
      if (effect.isValid.test(itemStack)) {
        tooltip.add(effect.color + new TextComponentTranslation(effect.tooltip).getUnformattedText());
        return;
      }
    }
  }

  @SubscribeEvent
  public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

    var durabilityDecreasing = ConfigCore.FEATURE.HOT_OR_NOT.durabilityDecreasing;

    EntityPlayer entityPlayer = event.player;
    World world = entityPlayer.world;
    ItemStack offHand = entityPlayer.getHeldItemOffhand();

    if (world.isRemote) {
      return;
    }

    if (entityPlayer.isBurning() || entityPlayer.isCreative()) {
      return;
    }

    if (event.phase != TickEvent.Phase.START) {
      return;
    }

    CapabilityUtils.getOptional(entityPlayer, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(capabilityItem -> {
      for (int slot = 0; slot < capabilityItem.getSlots(); slot++) {
        ItemStack itemStack = capabilityItem.getStackInSlot(slot);

        if (itemStack.isEmpty()) {
          continue;
        }

        if (isRemoved(itemStack)) {
          return;
        }

        // Fluids
        CapabilityUtils.getOptional(itemStack, CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(capabilityFluidItem -> {

          FluidStack fluidStack = capabilityFluidItem.drain(1000, false);
          if (fluidStack != null) {
            if (!ConfigCore.FEATURE.HOT_OR_NOT.enableFluidEffect) {
              return;
            }
            for (FluidEffect effect : FluidEffect.values()) {
              if (effect.isValid.test(fluidStack)) {
                if (StackUtils.doesStackMatchTool(offHand, TONGS)) {
                  if ((world.getTotalWorldTime() % durabilityDecreasing == 0)) {
                    ToolHelper.damageItem(offHand, entityPlayer);
                    return;
                  }
                } else if (world.getTotalWorldTime() % 20 == 0) {
                  effect.interactPlayer.accept(entityPlayer);

                  if (ConfigCore.FEATURE.HOT_OR_NOT.yeet) {
                    entityPlayer.inventory.deleteStack(itemStack);
                    entityPlayer.dropItem(itemStack, false, true);
                    return;
                  }
                }
              }
            }
          }
        });

        // Items
        if (!ConfigCore.FEATURE.HOT_OR_NOT.enableItemEffect) {
          return;
        }

        for (ItemEffect effect : ItemEffect.values()) {
          if (effect.isValid.test(itemStack)) {
            if (StackUtils.doesStackMatchTool(offHand, TONGS)) {
              if ((world.getTotalWorldTime() % durabilityDecreasing == 0)) {
                ToolHelper.damageItem(offHand, entityPlayer);
                return;
              }
            } else if (world.getTotalWorldTime() % 20 == 0) {
              effect.interactPlayer.accept(entityPlayer);

              if (ConfigCore.FEATURE.HOT_OR_NOT.yeet) {
                entityPlayer.closeScreen();

                entityPlayer.inventory.deleteStack(itemStack);
                entityPlayer.dropItem(itemStack, false, true);
                return;
              }
            }
          }
        }

      }
    });

  }

  private static boolean isRemoved(ItemStack stack) {
    return StackUtils.compareStackToList(stack, Arrays.asList(ConfigCore.FEATURE.HOT_OR_NOT.itemRemovals));
  }

  @Override
  public boolean isEnabled() {
    return ConfigCore.FEATURE.HOT_OR_NOT.enable;
  }


}
