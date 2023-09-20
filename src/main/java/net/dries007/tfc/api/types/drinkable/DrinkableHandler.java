package net.dries007.tfc.api.types.drinkable;

import gregtech.api.unification.material.Materials;
import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.api.capability.food.FoodStatsTFC;
import net.dries007.tfc.api.capability.food.IFoodStatsTFC;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.player.IPlayerData;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.dries007.tfc.module.core.common.objects.effects.PotionEffectsTFC;
import net.dries007.tfc.module.core.config.ConfigTFC;
import net.dries007.tfc.util.Constants;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

import static net.dries007.tfc.api.types.drinkable.Drinkables.*;

public class DrinkableHandler {

    private static final IActionAfterDrink alcoholAction = player -> {
        IPlayerData playerData = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
        if (player.getFoodStats() instanceof FoodStatsTFC && playerData != null) {
            ((FoodStatsTFC) player.getFoodStats()).addThirst(10);
            playerData.addIntoxicatedTime(4 * ICalendar.TICKS_IN_HOUR);
            if (playerData.getIntoxicatedTime() > 24 * ICalendar.TICKS_IN_HOUR && Constants.RNG.nextFloat() < 0.5f) {
                player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1200, 1));
            }
        }
    };

    public static void preInit() {
        FRESH_WATER = new Drinkable(() -> TFGMaterials.FreshWater.getFluid(),
                (player) -> {
                    if (player.getFoodStats() instanceof FoodStatsTFC) {
                        ((FoodStatsTFC) player.getFoodStats()).addThirst(40);
                    }
                });

        SALT_WATER = new Drinkable(() -> Materials.SaltWater.getFluid(),
                (player) -> {
                    if (player.getFoodStats() instanceof FoodStatsTFC) {
                        ((FoodStatsTFC) player.getFoodStats()).addThirst(-10);
                        if (Constants.RNG.nextDouble() < ConfigTFC.General.PLAYER.chanceThirstOnSaltyDrink) {
                            player.addPotionEffect(new PotionEffect(PotionEffectsTFC.THIRST, 600, 0));
                        }
                    }
                });

        MILK = new Drinkable(() -> Materials.Milk.getFluid(), (player -> {
            if (player.getFoodStats() instanceof IFoodStatsTFC foodStats) {
                foodStats.addThirst(10);
                foodStats.getNutrition().addBuff(FoodData.MILK);
            }
        }));

        CIDER = new Drinkable(() -> TFGMaterials.Cider.getFluid(), alcoholAction);
        VODKA = new Drinkable(() -> TFGMaterials.Vodka.getFluid(), alcoholAction);
        SAKE = new Drinkable(() -> TFGMaterials.Sake.getFluid(), alcoholAction);
        CORN_WHISKEY = new Drinkable(() -> TFGMaterials.CornWhiskey.getFluid(), alcoholAction);
        RYE_WHISKEY = new Drinkable(() -> TFGMaterials.RyeWhiskey.getFluid(), alcoholAction);
        WHISKEY = new Drinkable(() -> TFGMaterials.Whiskey.getFluid(), alcoholAction);
        BEER = new Drinkable(() -> TFGMaterials.Beer.getFluid(), alcoholAction);
        RUM = new Drinkable(() -> TFGMaterials.Rum.getFluid(), alcoholAction);
    }

}
