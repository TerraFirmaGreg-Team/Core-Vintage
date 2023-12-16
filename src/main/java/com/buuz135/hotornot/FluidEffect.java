package com.buuz135.hotornot;

import com.buuz135.hotornot.config.HotConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;
import java.util.function.Predicate;

public enum FluidEffect {
    HOT(fluidStack -> fluidStack.getFluid().getTemperature(fluidStack) >= HotConfig.HOT_FLUID + 273 && HotConfig.HOT_FLUIDS, entityPlayerMP -> entityPlayerMP.setFire(1), TextFormatting.RED, "tooltip.hotornot.toohot"),
    COLD(fluidStack -> fluidStack.getFluid().getTemperature(fluidStack) <= HotConfig.COLD_FLUID + 273 && HotConfig.COLD_FLUIDS, entityPlayerMP ->
    {
        entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 21, 1));
        entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 21, 1));
    }, TextFormatting.AQUA, "tooltip.hotornot.toocold"),
    GAS(fluidStack -> fluidStack.getFluid().isGaseous(fluidStack) && HotConfig.GASEOUS_FLUIDS, entityPlayerMP -> entityPlayerMP.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 21, 1)), TextFormatting.YELLOW, "tooltip.hotornot.toolight");

    public final Predicate<FluidStack> isValid;
    public final Consumer<EntityPlayer> interactPlayer;
    public final TextFormatting color;
    public final String tooltip;

    FluidEffect(Predicate<FluidStack> isValid, Consumer<EntityPlayer> interactPlayer, TextFormatting color, String tooltip)
    {
        this.isValid = isValid;
        this.interactPlayer = interactPlayer;
        this.color = color;
        this.tooltip = tooltip;
    }
}
