package com.eerussianguy.firmalife.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import com.eerussianguy.firmalife.init.RegistryNamesFL;
import com.eerussianguy.firmalife.recipe.CrackingRecipe;
import com.eerussianguy.firmalife.recipe.DryingRecipe;
import com.eerussianguy.firmalife.recipe.NutRecipe;
import com.eerussianguy.firmalife.recipe.OvenRecipe;
import com.eerussianguy.firmalife.recipe.PlanterRecipe;
import com.eerussianguy.firmalife.recipe.StrainingRecipe;

import static su.terrafirmagreg.api.lib.Constants.MODID_FL;

@Mod.EventBusSubscriber(modid = MODID_FL)
@GameRegistry.ObjectHolder(MODID_FL)
public class RegistriesFL {

    @SubscribeEvent
    public static void onNewRegistryEvent(RegistryEvent.NewRegistry event) {
        newRegistry(RegistryNamesFL.OVEN_RECIPE, OvenRecipe.class);
        newRegistry(RegistryNamesFL.DRYING_RECIPE, DryingRecipe.class);
        newRegistry(RegistryNamesFL.PLANTER_QUAD_REGISTRY, PlanterRecipe.class);
        newRegistry(RegistryNamesFL.NUT_TREES_REGISTRY, NutRecipe.class);
        newRegistry(RegistryNamesFL.CRACKING_RECIPE, CrackingRecipe.class);
        newRegistry(RegistryNamesFL.STRAINING_RECIPE, StrainingRecipe.class);
    }

    private static <T extends IForgeRegistryEntry<T>> void newRegistry(ResourceLocation name, Class<T> tClass) {
        IForgeRegistry<T> r = new RegistryBuilder<T>().setName(name).allowModification().setType(tClass).create();
    }
}
