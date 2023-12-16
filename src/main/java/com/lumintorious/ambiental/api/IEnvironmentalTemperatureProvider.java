package com.lumintorious.ambiental.api;

import com.lumintorious.ambiental.modifier.EnvironmentalModifier;

import com.lumintorious.ambiental.modifier.TempModifier;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Optional;

@FunctionalInterface
public interface IEnvironmentalTemperatureProvider {
	Optional<TempModifier> getModifier(EntityPlayer player);
}
