package com.example.examplemod;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeFluidMixing;
import net.dries007.tfc.compat.jei.wrappers.BarrelRecipeWrapper;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.recipes.ingredients.FluidIngredient;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;

import org.apache.logging.log4j.Logger;

@Mod(modid = TFCAgedDrinks.MODID, name = TFCAgedDrinks.NAME, version = TFCAgedDrinks.VERSION)
public class TFCAgedDrinks
{
    public static final String MODID = "aged_drinks";
    public static final String NAME = "TFC Aged Drinks";
    public static final String VERSION = "1.0.1";

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	
    }
    
    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
    	@SubscribeEvent
        public static void onRegisterBarrelRecipeEvent(RegistryEvent.Register<BarrelRecipe> event) {
    		AgedRegistry.registerAgedDrinks(event);
        }
    }
    
}
