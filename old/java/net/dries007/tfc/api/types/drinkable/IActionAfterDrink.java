package net.dries007.tfc.api.types.drinkable;

import net.minecraft.entity.player.EntityPlayer;

@FunctionalInterface
public interface IActionAfterDrink {

    void onDrink(EntityPlayer entityPlayer);
}
