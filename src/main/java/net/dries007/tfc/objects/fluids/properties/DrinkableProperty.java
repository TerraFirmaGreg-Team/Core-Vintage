package net.dries007.tfc.objects.fluids.properties;

import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface DrinkableProperty {

  FluidProperty<DrinkableProperty> DRINKABLE = new FluidProperty<>("drinkable");

  void onDrink(@Nonnull EntityPlayer player);
}
