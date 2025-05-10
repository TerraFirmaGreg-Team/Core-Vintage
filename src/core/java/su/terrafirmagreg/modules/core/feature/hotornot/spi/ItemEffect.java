package su.terrafirmagreg.modules.core.feature.hotornot.spi;

import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.modules.core.ConfigCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

public enum ItemEffect {
  ITEM_HOT(itemStack ->
    StackUtils.compareStackToList(itemStack, Arrays.asList(ConfigCore.FEATURE.HOT_OR_NOT.hotItemAdditions))
//      || CapabilityUtils.getOptional(itemStack, CapabilityHeat.CAPABILITY).ifPresent(capabilityHeat -> {
//        return capabilityHeat.getTemperature() >= ConfigCore.FEATURE.HOT_OR_NOT.hotItem;
//      })
    , entityPlayer -> {

    entityPlayer.setFire(1);

  }, TextFormatting.RED, "tooltip.tfg.core.hot_or_not.hot"),

  ITEM_COLD(itemStack ->
    StackUtils.compareStackToList(itemStack, Arrays.asList(ConfigCore.FEATURE.HOT_OR_NOT.coldItemAdditions)),
    entityPlayer -> {

      entityPlayer.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 21, 1));
      entityPlayer.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 21, 1));

    }, TextFormatting.AQUA, "tooltip.tfg.core.hot_or_not.cold"),

  ITEM_GAS(itemStack ->
    StackUtils.compareStackToList(itemStack, Arrays.asList(ConfigCore.FEATURE.HOT_OR_NOT.gaseousItemAdditions)),
    entityPlayer -> {

      entityPlayer.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 21, 1));

    }, TextFormatting.YELLOW, "tooltip.tfg.core.hot_or_not.light");


  public final Predicate<ItemStack> isValid;
  public final Consumer<EntityPlayer> interactPlayer;
  public final TextFormatting color;
  public final String tooltip;

  ItemEffect(Predicate<ItemStack> isValid, Consumer<EntityPlayer> interactPlayer, TextFormatting color, String tooltip) {
    this.isValid = isValid;
    this.interactPlayer = interactPlayer;
    this.color = color;
    this.tooltip = tooltip;
  }
}
