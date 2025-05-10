package su.terrafirmagreg.modules.core.feature.hotornot.spi;

import su.terrafirmagreg.modules.core.ConfigCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;
import java.util.function.Predicate;

public enum FluidEffect {
  FLUID_HOT(fluidStack ->
    fluidStack.getFluid().getTemperature(fluidStack) >= ConfigCore.FEATURE.HOT_OR_NOT.hotFluid + 273,
    entityPlayer -> {

      entityPlayer.setFire(1);

    }, TextFormatting.RED, "tooltip.tfg.core.hot_or_not.hot"),

  FLUID_COLD(fluidStack ->
    fluidStack.getFluid().getTemperature(fluidStack) <= ConfigCore.FEATURE.HOT_OR_NOT.coldFluid + 273,
    entityPlayer -> {

      entityPlayer.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 21, 1));
      entityPlayer.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 21, 1));

    }, TextFormatting.AQUA, "tooltip.tfg.core.hot_or_not.cold"),

  FLUID_GAS(fluidStack ->
    fluidStack.getFluid().isGaseous(fluidStack),
    entityPlayer -> {

      entityPlayer.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 21, 1));

    }, TextFormatting.YELLOW, "tooltip.tfg.core.hot_or_not.light");


  public final Predicate<FluidStack> isValid;
  public final Consumer<EntityPlayer> interactPlayer;
  public final TextFormatting color;
  public final String tooltip;

  FluidEffect(Predicate<FluidStack> isValid, Consumer<EntityPlayer> interactPlayer, TextFormatting color, String tooltip) {
    this.isValid = isValid;
    this.interactPlayer = interactPlayer;
    this.color = color;
    this.tooltip = tooltip;
  }
}
