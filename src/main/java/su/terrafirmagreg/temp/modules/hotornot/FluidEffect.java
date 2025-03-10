package su.terrafirmagreg.temp.modules.hotornot;

import su.terrafirmagreg.temp.config.TFGConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;
import java.util.function.Predicate;

public enum FluidEffect {
  HOT(fluidStack -> fluidStack.getFluid().getTemperature(fluidStack) >= TFGConfig.General.HOT_FLUID + 273
                    && TFGConfig.General.HOT_FLUIDS, entityPlayerMP -> {
    entityPlayerMP.setFire(1);
  }, TextFormatting.RED, "tooltip.tfg.toohot"),

  COLD(fluidStack -> fluidStack.getFluid().getTemperature(fluidStack) <= TFGConfig.General.COLD_FLUID + 273
                     && TFGConfig.General.COLD_FLUIDS, entityPlayerMP -> {
    entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 21, 1));
    entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 21, 1));
  }, TextFormatting.AQUA, "tooltip.tfg.toocold"),

  GAS(fluidStack -> fluidStack.getFluid().isGaseous(fluidStack)
                    && TFGConfig.General.GASEOUS_FLUIDS, entityPlayerMP -> {
    entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 21, 1));
  }, TextFormatting.YELLOW, "tooltip.tfg.toolight");


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
