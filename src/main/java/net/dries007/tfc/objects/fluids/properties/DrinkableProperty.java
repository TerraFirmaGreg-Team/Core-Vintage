package net.dries007.tfc.objects.fluids.properties;

import net.minecraft.entity.player.EntityPlayer;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface DrinkableProperty {

  FluidProperty<DrinkableProperty> DRINKABLE = new FluidProperty<>("drinkable");

  void onDrink(@NotNull EntityPlayer player);
}
