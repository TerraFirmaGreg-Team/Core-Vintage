package su.terrafirmagreg.modules.device.feature.sharpness;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.framework.module.spi.StateEvent;
import su.terrafirmagreg.modules.device.feature.sharpness.capability.CapabilitySharpness;
import su.terrafirmagreg.modules.device.feature.sharpness.capability.CapabilitySharpness.Handler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.entity.projectile.EntityThrownRopeJavelin;
import net.dries007.tfc.objects.entity.projectile.EntityThrownWeapon;
import net.dries007.tfcthings.main.ConfigTFCThings.Items;

public class FeatureSharpness extends BaseFeature {

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  @SideOnly(Side.CLIENT)
  public static void onItemTooltipEvent(ItemTooltipEvent event) {
    var stack = event.getItemStack();
    var tooltip = event.getToolTip();
    if (!StackUtils.isValid(stack)) {return;}

    CapabilityUtils.getOptional(stack, CapabilitySharpness.CAPABILITY).ifPresent(cap -> {
      if (cap.getCharges() > 0) {
        TextFormatting color = cap.getCharges() > 64 ? cap.getCharges() > 256 ? TextFormatting.DARK_PURPLE : TextFormatting.BLUE : TextFormatting.DARK_GREEN;
        tooltip.add(I18n.format(ModUtils.localize(LocalizeKeys.TOOLTIP, "device.sharpness"), color, "" + cap.getCharges()));
      }
    });
  }

  @SubscribeEvent
  public static void onAttachCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event) {
    ItemStack stack = event.getObject();
    if (!StackUtils.isValid(stack)) {return;}

    ICapabilityProvider provider = CapabilitySharpness.getCustom(stack);
    if (provider == null) {
      return;
    }

    event.addCapability(CapabilitySharpness.KEY, provider);
  }

  @SubscribeEvent
  public static void onBlockBreak(BreakEvent event) {
    var player = event.getPlayer();
    var itemStack = player.getHeldItemMainhand();
    CapabilityUtils.getOptional(itemStack, CapabilitySharpness.CAPABILITY).ifPresent(cap -> {
      if (cap.getCharges() > 0) {
        cap.removeCharge();

        if (cap.getCharges() > 256) {
          if (Math.random() < 0.2 && itemStack.getItemDamage() > 0) {
            itemStack.setItemDamage(itemStack.getItemDamage() - 1);
          }
        } else if (cap.getCharges() > 64 && itemStack.getItemDamage() > 0) {
          if (Math.random() < 0.1) {
            itemStack.setItemDamage(itemStack.getItemDamage() - 1);
          }
        }
      }
    });
  }

  @SubscribeEvent
  public static void modifyBreakSpeed(BreakSpeed event) {
    var entityPlayer = event.getEntityPlayer();
    var itemStack = entityPlayer.getHeldItemMainhand();
    var state = event.getState();
    CapabilityUtils.getOptional(itemStack, CapabilitySharpness.CAPABILITY).ifPresent(cap -> {

      if (!shouldBoostSpeed(itemStack, state)) {
        return;
      }
      if (state.getBlock() instanceof BlockLogTFC && !state.getValue(BlockLogTFC.PLACED)) {
        return;
      }
      if (cap.getCharges() > 256) {
        event.setNewSpeed(event.getNewSpeed() + Items.WHETSTONE.bonusSpeed + 4);
      } else if (cap.getCharges() > 64) {
        event.setNewSpeed(event.getNewSpeed() + Items.WHETSTONE.bonusSpeed + 2);
      } else if (cap.getCharges() > 0) {
        event.setNewSpeed(event.getNewSpeed() + Items.WHETSTONE.bonusSpeed);
      }

    });
  }

  private static boolean shouldBoostSpeed(ItemStack stack, IBlockState state) {
    var item = stack.getItem();
    if (item.canHarvestBlock(state)) {return true;}
    for (String type : item.getToolClasses(stack)) {
      if (state.getBlock().isToolEffective(type, state)) {return true;}
    }
    return false;
  }

  @SubscribeEvent
  public static void onLivingAttack(LivingDamageEvent event) {
    if (event.getSource() instanceof EntityDamageSource source) {
      if (source.getTrueSource() instanceof EntityPlayer player) {
        ItemStack weapon;
        if (source instanceof EntityDamageSourceIndirect) {
          if (source.getImmediateSource() instanceof EntityThrownWeapon entityThrownWeapon) {
            weapon = entityThrownWeapon.getWeapon();
          } else if (source.getImmediateSource() instanceof EntityThrownRopeJavelin entityThrownRopeJavelin) {
            weapon = entityThrownRopeJavelin.getWeapon();
          } else {
            weapon = ItemStack.EMPTY;
          }
        } else {
          weapon = player.getHeldItemMainhand();
        }

        CapabilityUtils.getOptional(weapon, CapabilitySharpness.CAPABILITY).ifPresent(cap -> {
          if (event.getAmount() > 2.0f) {
            if (cap.getCharges() > 256) {
              event.setAmount(event.getAmount() + (Items.WHETSTONE.damageBoost * 3));
              cap.removeCharge();
            } else if (cap.getCharges() > 64) {
              event.setAmount(event.getAmount() + (Items.WHETSTONE.damageBoost * 2));
              cap.removeCharge();
            } else if (cap.getCharges() > 0) {
              event.setAmount(event.getAmount() + Items.WHETSTONE.damageBoost);
              cap.removeCharge();
            }
            if (cap.getCharges() > 256) {
              if (Math.random() < 0.2 && weapon.getItemDamage() > 0) {
                weapon.setItemDamage(weapon.getItemDamage() - 1);
              }
            } else if (cap.getCharges() > 64) {
              if (Math.random() < 0.1 && weapon.getItemDamage() > 0) {
                weapon.setItemDamage(weapon.getItemDamage() - 1);
              }
            }
          }
        });
      }
    }
  }

  @SubscribeEvent
  public static void onPreInit(StateEvent.PreInitialization event) {

    CapabilitySharpness.register();
  }

  @SubscribeEvent
  public static void onPostInit(StateEvent.PostInitialization event) {

    Handler.init();
  }


}
