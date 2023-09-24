package net.dries007.tfc.common.objects.effects;


import net.dries007.tfc.Tags;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static net.dries007.tfc.util.Helpers.getNull;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
@GameRegistry.ObjectHolder(Tags.MOD_ID)
public final class PotionEffectsTFC {
    public static final Potion OVERBURDENED = getNull();
    public static final Potion THIRST = getNull();
    public static final Potion FOOD_POISON = getNull();

    @SubscribeEvent
    public static void registerPotionEffects(RegistryEvent.Register<Potion> event) {
        event.getRegistry().registerAll(
                new PotionOverburdened().setRegistryName(Tags.MOD_ID, "overburdened"),
                new PotionThirst().setRegistryName(Tags.MOD_ID, "thirst"),
                new PotionFoodPoison().setRegistryName(Tags.MOD_ID, "food_poison")
        );
    }
}
