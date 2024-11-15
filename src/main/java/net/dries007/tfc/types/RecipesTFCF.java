package net.dries007.tfc.types;

import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.plant.api.types.type.PlantTypes;
import su.terrafirmagreg.modules.plant.init.BlocksPlant;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.rock.init.ItemsRock;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import net.dries007.eerussianguy.firmalife.ConfigFL;
import net.dries007.eerussianguy.firmalife.FirmaLife;
import net.dries007.eerussianguy.firmalife.init.FoodFL;
import net.dries007.eerussianguy.firmalife.recipe.CrackingRecipe;
import net.dries007.eerussianguy.firmalife.recipe.DryingRecipe;
import net.dries007.eerussianguy.firmalife.recipe.NutRecipe;
import net.dries007.eerussianguy.firmalife.recipe.OvenRecipe;
import net.dries007.eerussianguy.firmalife.recipe.PlanterRecipe;
import net.dries007.eerussianguy.firmalife.registry.ItemsFL;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeFluidMixing;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeTemperature;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingTypes;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.blocks.BlocksTFCF;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFCF;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.FluidsTFCF;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.inventory.ingredient.IngredientFluidItem;
import net.dries007.tfc.objects.items.ItemPowder;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.ItemsTFCF;
import net.dries007.tfc.objects.items.metal.ItemMetal;
import net.dries007.tfc.objects.recipes.ShapelessDamageRecipe;
import net.dries007.tfc.util.agriculture.CropTFCF;
import net.dries007.tfc.util.agriculture.SeasonalTrees;

import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;

import net.dries007.tfcflorae.TFCFlorae;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.api.data.Reference.MODID_TFC;
import static su.terrafirmagreg.api.data.Reference.MODID_TFCF;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID_TFCF)
public final class RecipesTFCF {

  @SuppressWarnings("rawtypes")
  @SubscribeEvent
  public static void onRegisterBarrelRecipeEvent(RegistryEvent.Register<BarrelRecipe> event) {
    IForgeRegistry<BarrelRecipe> r = event.getRegistry();

    // Remove recipes
    IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) TFCRegistries.BARREL;
    String[] regNames = {"sugar", "beer", "sake"};
    for (String name : regNames) {
      BarrelRecipe recipe = TFCRegistries.BARREL.getValue(new ResourceLocation(MODID_TFC, name));
      if (recipe != null) {
        modRegistry.remove(recipe.getRegistryName());
        TFCFlorae.LOGGER.info("Removed barrel recipe tfc:{}", name);
      }
    }

    r.registerAll(

      // Sugar
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 600), IIngredient.of("sugarcane", 5), null, new ItemStack(Items.SUGAR),
                       8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_sugar_cane"),

      // Base Potash Liquor
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("dustPotash"),
                       new FluidStack(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 500), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "base_potash_liquor_from_potash"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("dustAsh"),
                       new FluidStack(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 500), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "base_potash_liquor_from_ash"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("dustWood"),
                       new FluidStack(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 500), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "base_potash_liquor_from_wood_dust"),

      // Cellulose Fibers
      new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("sugarcane"),
                       new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
                       8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_sugarcane"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("pulp"),
                       new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
                       8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_pulp"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("cropAgave"),
                       new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
                       8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_agave_crop"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("cropFlax"),
                       new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
                       8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_flax_crop"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("cropHemp"),
                       new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
                       8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_hemp_crop"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("pulpPapyrus"),
                       new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
                       8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_papyrus_crop"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.BASE_POTASH_LIQUOR.get(), 150),
                       IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.YUCCA)),
                       new FluidStack(FluidsTFCF.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
                       8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_yucca_crop"),

      // Papyrus Fibers
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 600), IIngredient.of("pulpPapyrus", 3), null,
                       new ItemStack(ItemsTFCF.PAPYRUS_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("papyrus_fiber_from_papyrus"),

      // Fiber Processing
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("cropAgave"), null,
                       new ItemStack(ItemsTFCF.SISAL_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sisal_fiber"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("cropFlax"), null,
                       new ItemStack(ItemsTFCF.FLAX_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("flax_fiber"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("cropHemp"), null,
                       new ItemStack(ItemsTFCF.HEMP_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("hemp_fiber"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 300),
                       IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.YUCCA)), null,
                       new ItemStack(ItemsTFCF.YUCCA_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("yucca_fiber"),

      // Fluid Production from paste

      // Olive
      new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET),
                       new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName(
        "olive_oil_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET),
                       new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("olive_oil_silk"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET),
                       new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName(
        "olive_oil_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET),
                       new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName(
        "olive_oil_linen"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET),
                       new FluidStack(FluidsTFC.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("olive_oil_hemp"),

      // Soybean
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteSoybean"),
                       new FluidStack(FluidsTFCF.SOYBEAN_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "soybean_water"),

      new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET),
                       new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("soy_milk_jute"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET),
                       new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("soy_milk_silk"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET),
                       new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("soy_milk_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET),
                       new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName(
        "soy_milk_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET),
                       new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("soy_milk_linen"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET),
                       new FluidStack(FluidsTFCF.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("soy_milk_hemp"),

      new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFCF.SOY_MILK.get(), 9), new IngredientFluidItem(FluidsTFC.VINEGAR.get(), 1),
                                  new FluidStack(FluidsTFC.MILK_VINEGAR.get(), 10), 0).setRegistryName("soy_milk_vinegar"),

      // Linseed
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteLinseed"),
                       new FluidStack(FluidsTFCF.LINSEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "linseed_water"),

      new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET),
                       new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName(
        "linseed_oil_jute"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET),
                       new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName(
        "linseed_oil_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET),
                       new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName(
        "linseed_oil_silk"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET),
                       new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName(
        "linseed_oil_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET),
                       new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName(
        "linseed_oil_linen"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET),
                       new FluidStack(FluidsTFCF.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName(
        "linseed_oil_hemp"),

      // Rape Seed
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteRapeSeed"),
                       new FluidStack(FluidsTFCF.RAPE_SEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "rape_seed_water"),

      new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET),
                       new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName(
        "rape_seed_oil_jute"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET),
                       new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName(
        "rape_seed_oil_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET),
                       new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName(
        "rape_seed_oil_silk"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET),
                       new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName(
        "rape_seed_oil_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET),
                       new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName(
        "rape_seed_oil_linen"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET),
                       new FluidStack(FluidsTFCF.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName(
        "rape_seed_oil_hemp"),

      // Sunflower Seed
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteSunflowerSeed"),
                       new FluidStack(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "sunflower_seed_water"),

      new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET),
                       new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName(
        "sunflower_seed_oil_jute"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET),
                       new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName(
        "sunflower_seed_oil_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET),
                       new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName(
        "sunflower_seed_oil_silk"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET),
                       new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName(
        "sunflower_seed_oil_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET),
                       new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName(
        "sunflower_seed_oil_linen"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET),
                       new FluidStack(FluidsTFCF.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName(
        "sunflower_seed_oil_hemp"),

      // Opium Poppy Seed
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("pasteOpiumPoppySeed"),
                       new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "opium_poppy_seed_water"),

      new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET),
                       new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName(
        "opium_poppy_seed_oil_jute"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET),
                       new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName(
        "opium_poppy_seed_oil_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET),
                       new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName(
        "opium_poppy_seed_oil_silk"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET),
                       new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName(
        "opium_poppy_seed_oil_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET),
                       new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName(
        "opium_poppy_seed_oil_linen"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET),
                       new FluidStack(FluidsTFCF.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName(
        "opium_poppy_seed_oil_hemp"),

      // Sugar Beet Water
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 125), IIngredient.of("mashedSugarBeet"),
                       new FluidStack(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "sugar_beet_water"),

      new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET),
                       new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName(
        "sugar_water_jute"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET),
                       new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName(
        "sugar_water_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET),
                       new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName(
        "sugar_water_silk"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET),
                       new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName(
        "sugar_water_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET),
                       new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName(
        "sugar_water_linen"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET),
                       new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName(
        "sugar_water_hemp"),

      // Dirty Nets
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_SISAL_NET), null,
                       new ItemStack(ItemsTFCF.SISAL_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_SILK_NET), null,
                       new ItemStack(ItemsTFCF.SILK_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_silk"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_COTTON_NET), null,
                       new ItemStack(ItemsTFCF.COTTON_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_LINEN_NET), null,
                       new ItemStack(ItemsTFCF.LINEN_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_linen"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_HEMP_NET), null,
                       new ItemStack(ItemsTFCF.HEMP_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_hemp"),

      // Sugary Fluids
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.SUGAR_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("sugar_water_from_sugar_fresh"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of("dropHoney"),
                       new FluidStack(FluidsTFCF.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_drop_honey_fresh"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of("itemHoney"),
                       new FluidStack(FluidsTFCF.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_item_honey_fresh"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of("rawHoney"),
                       new FluidStack(FluidsTFCF.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_raw_honey_fresh"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of(ItemsFL.HONEYCOMB),
                       new FluidStack(FluidsTFCF.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_fl_raw_honey_fresh"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 125), IIngredient.of("materialHoneycomb"),
                       new FluidStack(FluidsTFCF.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName(
        "honey_water_from_material_honeycomb_fresh"),

      //new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_WATER.get(), 125), null, null, new ItemStack(Items.SUGAR), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_sugar_water"),
      //new BarrelRecipe(IIngredient.of(FluidsTFCF.HONEY_WATER.get(), 125), null, null, new ItemStack(Items.SUGAR), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_honey_water"),

      // Dyes
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropAgave"), new FluidStack(FluidsTFC
                                                                                                                      .getFluidFromDye(EnumDyeColor.GREEN)
                                                                                                                      .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("green_dye_agave"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropIndigo"), new FluidStack(FluidsTFC
                                                                                                                       .getFluidFromDye(EnumDyeColor.BLUE)
                                                                                                                       .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_indigo"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropMadder"), new FluidStack(FluidsTFC
                                                                                                                       .getFluidFromDye(EnumDyeColor.RED)
                                                                                                                       .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("red_dye_madder"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropWeld"), new FluidStack(FluidsTFC
                                                                                                                     .getFluidFromDye(EnumDyeColor.YELLOW)
                                                                                                                     .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("yellow_dye_weld"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropWoad"), new FluidStack(FluidsTFC
                                                                                                                     .getFluidFromDye(EnumDyeColor.BLUE)
                                                                                                                     .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_woad"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("cropRape"), new FluidStack(FluidsTFC
                                                                                                                     .getFluidFromDye(EnumDyeColor.BLUE)
                                                                                                                     .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_rape"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("boneCharred"), new FluidStack(FluidsTFC
                                                                                                                        .getFluidFromDye(EnumDyeColor.BLACK)
                                                                                                                        .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("black_dye_charred_bones"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("dustBlackPearl"), new FluidStack(FluidsTFC
                                                                                                                           .getFluidFromDye(EnumDyeColor.BLACK)
                                                                                                                           .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("black_dye_black_pearl_powder"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("dustPearl"), new FluidStack(FluidsTFC
                                                                                                                      .getFluidFromDye(EnumDyeColor.PINK)
                                                                                                                      .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("pink_dye_pearl_powder"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 1000), IIngredient.of("dustLogwood"), new FluidStack(FluidsTFC
                                                                                                                        .getFluidFromDye(EnumDyeColor.PURPLE)
                                                                                                                        .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("purple_dye_logwood_powder"),

      // Teas
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("driedWhiteTea", 2),
                       new FluidStack(FluidsTFCF.WHITE_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("white_tea"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("driedGreenTea", 2),
                       new FluidStack(FluidsTFCF.GREEN_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("green_tea"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("driedBlackTea", 2),
                       new FluidStack(FluidsTFCF.BLACK_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("black_tea"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("driedChamomile", 2),
                       new FluidStack(FluidsTFCF.CHAMOMILE_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "chamomile_tea"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("driedDandelion", 2),
                       new FluidStack(FluidsTFCF.DANDELION_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "dandelion_tea"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("driedLabradorTea", 2),
                       new FluidStack(FluidsTFCF.LABRADOR_TEA.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "labrador_tea"),

      // Coffee
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 200), IIngredient.of("roastedCoffee", 2),
                       new FluidStack(FluidsTFCF.COFFEE.get(), 200), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("coffee"),

      // Firma Cola
      new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_WATER.get(), 250), IIngredient.of("blendFirmaCola"),
                       new FluidStack(FluidsTFCF.FIRMA_COLA.get(), 1000), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "firma_cola"),

      // Wort
      new BarrelRecipe(IIngredient.of(FluidsTFC.HOT_WATER.get(), 500), IIngredient.of("hops"), new FluidStack(FluidsTFCF.WORT.get(), 500),
                       ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("wort"),

      // Fermented Alcohol
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_AGAVE.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.AGAVE_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "agave_wine"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_BANANA.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.BANANA_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "banana_wine"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_CHERRY.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.CHERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "cherry_wine"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_GREEN_GRAPE.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.WHITE_WINE.get(), 500), new ItemStack(ItemsTFCF.POMACE),
                       72 * ICalendar.TICKS_IN_HOUR).setRegistryName("white_wine"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_JUNIPER.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.JUNIPER_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "juniper_wine"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_LEMON.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.LEMON_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "lemon_wine"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.HONEY_WATER.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.MEAD.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("mead"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_ORANGE.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.ORANGE_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "orange_wine"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PAPAYA.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.PAPAYA_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "papaya_wine"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PEACH.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.PEACH_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "peach_wine"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PEAR.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.PEAR_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("pear_wine"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PLUM.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.PLUM_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("plum_wine"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_PURPLE_GRAPE.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.RED_WINE.get(), 500), new ItemStack(ItemsTFCF.POMACE),
                       72 * ICalendar.TICKS_IN_HOUR).setRegistryName("red_wine"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.RICE_WATER.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsTFC.SAKE.get(), 500),
                       ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("sake_rice_water"),

      // Berry Wine
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_BLACKBERRY.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "berry_wine_blackberry"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_BLUEBERRY.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "berry_wine_blueberry"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_BUNCH_BERRY.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "berry_wine_bunch_berry"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_CLOUD_BERRY.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "berry_wine_cloud_berry"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_CRANBERRY.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "berry_wine_cranberry"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_ELDERBERRY.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "berry_wine_elderberry"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_GOOSEBERRY.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "berry_wine_gooseberry"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_RASPBERRY.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "berry_wine_raspberry"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_SNOW_BERRY.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "berry_wine_snow_berry"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_STRAWBERRY.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "berry_wine_strawberry"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUICE_WINTERGREEN_BERRY.get(), 500), IIngredient.of("yeast"),
                       new FluidStack(FluidsTFCF.BERRY_WINE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "berry_wine_wintergreen_berry"),

      // "Distilled" Alcohol
      new BarrelRecipe(IIngredient.of(FluidsTFCF.AGAVE_WINE.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.TEQUILA.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("tequila"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.BANANA_WINE.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.BANANA_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "banana_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.BERRY_WINE.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.BERRY_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "berry_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.CIDER.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.CALVADOS.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("calvados"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.CHERRY_WINE.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.CHERRY_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "cherry_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.JUNIPER_WINE.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.GIN.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("gin"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.LEMON_WINE.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.LEMON_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "lemon_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.ORANGE_WINE.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.ORANGE_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "orange_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.PAPAYA_WINE.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.PAPAYA_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "papaya_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.PEACH_WINE.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.PEACH_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "peach_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.PEAR_WINE.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.PEAR_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "pear_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.PLUM_WINE.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.PLUM_BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "plum_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.RED_WINE.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.BRANDY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("brandy"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.SAKE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFCF.SHOCHU.get(), 500),
                       ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("shochu"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.WHITE_WINE.get(), 500), IIngredient.of(Items.SUGAR),
                       new FluidStack(FluidsTFCF.COGNAC.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cognac"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.VODKA.get(), 500), IIngredient.of("pomace"), new FluidStack(FluidsTFCF.GRAPPA.get(), 500),
                       ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("grappa"),

      // Malted Grain
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainBarley"), null,
                       new ItemStack(ItemsTFCF.MALT_BARLEY), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_barley"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainMaize"), null,
                       new ItemStack(ItemsTFCF.MALT_CORN), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_corn"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainRye"), null,
                       new ItemStack(ItemsTFCF.MALT_RYE), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_rye"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainRice"), null,
                       new ItemStack(ItemsTFCF.MALT_RICE), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_rice"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainWheat"), null,
                       new ItemStack(ItemsTFCF.MALT_WHEAT), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_wheat"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainAmaranth"), null,
                       new ItemStack(ItemsTFCF.MALT_AMARANTH), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_amaranth"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainBuckwheat"), null,
                       new ItemStack(ItemsTFCF.MALT_BUCKWHEAT), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_buckwheat"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainFonio"), null,
                       new ItemStack(ItemsTFCF.MALT_FONIO), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_fonio"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainMillet"), null,
                       new ItemStack(ItemsTFCF.MALT_MILLET), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_millet"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainQuinoa"), null,
                       new ItemStack(ItemsTFCF.MALT_QUINOA), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_quinoa"),
      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 200), IIngredient.of("grainSpelt"), null,
                       new ItemStack(ItemsTFCF.MALT_SPELT), 4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_spelt"),

      // Beer
      new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltBarley"),
                       new FluidStack(FluidsTFCF.BEER_BARLEY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "beer_barley"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltCorn"),
                       new FluidStack(FluidsTFCF.BEER_CORN.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_corn"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltRye"),
                       new FluidStack(FluidsTFCF.BEER_RYE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_rye"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltWheat"),
                       new FluidStack(FluidsTFCF.BEER_WHEAT.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "beer_wheat"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltAmaranth"),
                       new FluidStack(FluidsTFCF.BEER_AMARANTH.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "beer_amaranth"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltBuckwheat"),
                       new FluidStack(FluidsTFCF.BEER_BUCKWHEAT.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "beer_buckwheat"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltFonio"),
                       new FluidStack(FluidsTFCF.BEER_FONIO.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "beer_fonio"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltMillet"),
                       new FluidStack(FluidsTFCF.BEER_MILLET.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "beer_millet"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltQuinoa"),
                       new FluidStack(FluidsTFCF.BEER_QUINOA.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "beer_quinoa"),
      new BarrelRecipe(IIngredient.of(FluidsTFCF.WORT.get(), 500), IIngredient.of("maltSpelt"),
                       new FluidStack(FluidsTFCF.BEER_SPELT.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName(
        "beer_spelt"),

      new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("rice"),
                       new FluidStack(FluidsTFCF.RICE_WATER.get(), 500), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("rice_water"),
      //new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("wildRice"), new FluidStack(FluidsTFCF.RICE_WATER.get(), 500), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("wild_rice_water"),

      // Cooling
      new BarrelRecipeTemperature(IIngredient.of(FluidsTFCF.DISTILLED_WATER.get(), 1), 50).setRegistryName("distilled_water_cooling")
    );
  }

  @SubscribeEvent
  public static void onRegisterKnappingRecipeEvent(RegistryEvent.Register<KnappingRecipe> event) {

//    KnappingRecipe r = new KnappingRecipeStone(KnappingTypes.MUD, type -> new ItemStack(ItemsSoil.MUD_BRICK_WET.get(type), 3), "XXXXX", "     ",
//        "XXXXX", "     ", "XXXXX");
//    event.getRegistry().register(r.setRegistryName(MODID_TFCF, "knapping_mud_brick"));

    event.getRegistry().registerAll(

      // Flint Tool Heads
      new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_AXE_HEAD, 1), " X   ", "XXXX ", "XXXXX", "XXXX ",
                               " X   ").setRegistryName(MODID_TFCF, "flint_axe_head"),

      new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HAMMER_HEAD, 1), "     ", "XXXXX", "XXXXX", "  X  ",
                               "     ").setRegistryName(MODID_TFCF, "flint_hammer_head"),

      //new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HOE_HEAD, 1), "XXXXX", "   XX").setRegistryName(TFCFlorae.MODID, "flint_hoe_head_1"),
      new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HOE_HEAD, 2), "XXXXX", "XX   ", "     ", "XXXXX",
                               "XX   ").setRegistryName(MODID_TFCF, "flint_hoe_head_2"),
      new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HOE_HEAD, 2), "XXXXX", "XX   ", "     ", "XXXXX",
                               "   XX").setRegistryName(MODID_TFCF, "flint_hoe_head_3"),

      new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_JAVELIN_HEAD, 1), "XXX  ", "XXXX ", "XXXXX",
                               " XXX ", "  X  ").setRegistryName(MODID_TFCF, "flint_javelin_head"),

            /*new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 1), "X ", "XX", "XX", "XX", "XX").setRegistryName(TFCFlorae.MODID, "flint_knife_head_1"),
            new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 1), " X", "XX", "XX", "XX", "XX").setRegistryName(TFCFlorae.MODID, "flint_knife_head_2"),*/
      new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 2), "X  X ", "XX XX", "XX XX", "XX XX",
                               "XX XX").setRegistryName(MODID_TFCF, "flint_knife_head_3"),
      new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 2), "X   X", "XX XX", "XX XX", "XX XX",
                               "XX XX").setRegistryName(MODID_TFCF, "flint_knife_head_4"),
      new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_KNIFE_HEAD, 2), " X X ", "XX XX", "XX XX", "XX XX",
                               "XX XX").setRegistryName(MODID_TFCF, "flint_knife_head_5"),

      new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_SHOVEL_HEAD, 1), " XXX ", " XXX ", " XXX ", " XXX ",
                               "  X  ").setRegistryName(MODID_TFCF, "flint_shovel_head"),

      new KnappingRecipeSimple(KnappingTypes.CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_URN), "XX XX", "X   X", "X   X", "X   X",
                               "XXXXX").setRegistryName(MODID_TFCF, "clay_urn"),

      // Containers
      new KnappingRecipeSimple(KnappingTypes.LEATHER, true, new ItemStack(ItemsTFCF.LEATHER_BAG_PIECE, 2), " XXX ", " XXX ", "     ",
                               " XXX ", " XXX ").setRegistryName("leather_bag_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingTypes.LEATHER, true, new ItemStack(ItemsTFCF.LEATHER_BAG_PIECE, 2), "     ", "XX XX", "XX XX",
                               "XX XX", "     ").setRegistryName("leather_bag_pieces_vertical"),
      new KnappingRecipeSimple(KnappingTypes.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_SACK_PIECE, 2), " XXX ", " XXX ", "     ",
                               " XXX ", " XXX ").setRegistryName("burlap_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingTypes.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_SACK_PIECE, 2), "     ", "XX XX", "XX XX",
                               "XX XX", "     ").setRegistryName("burlap_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingTypes.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_SACK_PIECE, 2), " XXX ", " XXX ", "     ",
                               " XXX ", " XXX ").setRegistryName("wool_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingTypes.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_SACK_PIECE, 2), "     ", "XX XX", "XX XX",
                               "XX XX", "     ").setRegistryName("wool_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingTypes.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_SACK_PIECE, 2), " XXX ", " XXX ", "     ",
                               " XXX ", " XXX ").setRegistryName("silk_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingTypes.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_SACK_PIECE, 2), "     ", "XX XX", "XX XX",
                               "XX XX", "     ").setRegistryName("silk_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingTypes.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_SACK_PIECE, 2), " XXX ", " XXX ", "     ",
                               " XXX ", " XXX ").setRegistryName("sisal_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingTypes.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_SACK_PIECE, 2), "     ", "XX XX", "XX XX",
                               "XX XX", "     ").setRegistryName("sisal_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingTypes.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_SACK_PIECE, 2), " XXX ", " XXX ", "     ",
                               " XXX ", " XXX ").setRegistryName("cotton_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingTypes.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_SACK_PIECE, 2), "     ", "XX XX", "XX XX",
                               "XX XX", "     ").setRegistryName("cotton_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingTypes.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_SACK_PIECE, 2), " XXX ", " XXX ", "     ",
                               " XXX ", " XXX ").setRegistryName("linen_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingTypes.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_SACK_PIECE, 2), "     ", "XX XX", "XX XX",
                               "XX XX", "     ").setRegistryName("linen_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingTypes.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_SACK_PIECE, 2), " XXX ", " XXX ", "     ",
                               " XXX ", " XXX ").setRegistryName("hemp_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingTypes.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_SACK_PIECE, 2), "     ", "XX XX", "XX XX",
                               "XX XX", "     ").setRegistryName("hemp_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingTypes.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_SACK_PIECE, 2), " XXX ", " XXX ", "     ",
                               " XXX ", " XXX ").setRegistryName("yucca_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingTypes.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_SACK_PIECE, 2), "     ", "XX XX", "XX XX",
                               "XX XX", "     ").setRegistryName("yucca_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_BAG_PIECE, 2), " XXX ",
                               " XXX ", "     ", " XXX ", " XXX ").setRegistryName("pineapple_leather_bag_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_BAG_PIECE, 2), "     ",
                               "XX XX", "XX XX", "XX XX", "     ").setRegistryName("pineapple_leather_bag_pieces_vertical"),

      // Pineapple Leather
      new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(Items.SADDLE), "  X  ", "XXXXX", "XXXXX", "XXXXX",
                               "  X  ").setRegistryName("pineapple_leather_saddle"),
      new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFC.QUIVER), " XXXX", "X XXX", "X XXX", "X XXX",
                               " XXXX").setRegistryName("pineapple_leather_quiver")

      // Armor Knapping
            /*new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("pineapple_leather_helmet"),
            new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("pineapple_leather_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("pineapple_leather_leggings"),
            new KnappingRecipeSimple(KnappingTypes.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("pineapple_leather_boots"),

            new KnappingRecipeSimple(KnappingTypes.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("burlap_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("burlap_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("burlap_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("burlap_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("wool_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("wool_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("wool_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("wool_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("silk_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("silk_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("silk_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("silk_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("sisal_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("sisal_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("sisal_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("sisal_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("cotton_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("cotton_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("cotton_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("cotton_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("linen_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("linen_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("linen_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("linen_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_CLOTH_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("hemp_cloth_helmet"),
            new KnappingRecipeSimple(KnappingTypes.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_CLOTH_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("hemp_cloth_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_CLOTH_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("hemp_cloth_leggings"),
            new KnappingRecipeSimple(KnappingTypes.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_CLOTH_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("hemp_cloth_boots"),

            new KnappingRecipeSimple(KnappingTypes.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_CANVAS_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("yucca_canvas_helmet"),
            new KnappingRecipeSimple(KnappingTypes.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_CANVAS_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("yucca_canvas_chestplate"),
            new KnappingRecipeSimple(KnappingTypes.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_CANVAS_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("yucca_canvas_leggings"),
            new KnappingRecipeSimple(KnappingTypes.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_CANVAS_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("yucca_canvas_boots")*/
    );
  }

  @SuppressWarnings("rawtypes")
  @SubscribeEvent
  public static void onRegisterHeatRecipeEvent(RegistryEvent.Register<HeatRecipe> event) {
    IForgeRegistry<HeatRecipe> r = event.getRegistry();

    // Remove recipes
    IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) TFCRegistries.HEAT;
    String[] regNames = {"grilled_mushroom"};
    for (String name : regNames) {
      HeatRecipe recipe = TFCRegistries.HEAT.getValue(new ResourceLocation(MODID_TFC, name));
      if (recipe != null) {
        modRegistry.remove(recipe.getRegistryName());
        TFCFlorae.LOGGER.info("Removed heating recipe tfc:{}", name);
      }
    }

    // Mud Pottery
    SoilType.getTypes().forEach(type -> {

      HeatRecipe recipe = new HeatRecipeSimple(IIngredient.of(ItemsSoil.MUD_BRICK_WET.get(type)), new ItemStack(ItemsSoil.MUD_BRICK.get(type)), 600f,
                                               Metal.Tier.TIER_I);
      event.getRegistry().register(recipe.setRegistryName(type.getName().toLowerCase() + "_unfired_mud_brick"));

      // Fired Pottery - doesn't burn up
      recipe = new HeatRecipeSimple(IIngredient.of(ItemsSoil.MUD_BRICK.get(type)), new ItemStack(ItemsSoil.MUD_BRICK.get(type)), 1599f,
                                    Metal.Tier.TIER_I);
      event.getRegistry().register(recipe.setRegistryName(type.getName().toLowerCase() + "_fired_mud_brick"));
    });

    // Bread
    if (!ConfigFL.General.COMPAT.removeTFC) {
      r.registerAll(

        new HeatRecipeSimple(IIngredient.of(ItemsTFCF.AMARANTH_DOUGH), new ItemStack(ItemsTFCF.AMARANTH_BREAD), 200, 480).setRegistryName(
          "amaranth_bread"),
        new HeatRecipeSimple(IIngredient.of(ItemsTFCF.BUCKWHEAT_DOUGH), new ItemStack(ItemsTFCF.BUCKWHEAT_BREAD), 200,
                             480).setRegistryName("buckwheat_bread"),
        new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FONIO_DOUGH), new ItemStack(ItemsTFCF.FONIO_BREAD), 200, 480).setRegistryName(
          "fonio_bread"),
        new HeatRecipeSimple(IIngredient.of(ItemsTFCF.MILLET_DOUGH), new ItemStack(ItemsTFCF.MILLET_BREAD), 200, 480).setRegistryName(
          "millet_bread"),
        new HeatRecipeSimple(IIngredient.of(ItemsTFCF.QUINOA_DOUGH), new ItemStack(ItemsTFCF.QUINOA_BREAD), 200, 480).setRegistryName(
          "quinoa_bread"),
        new HeatRecipeSimple(IIngredient.of(ItemsTFCF.SPELT_DOUGH), new ItemStack(ItemsTFCF.SPELT_BREAD), 200, 480).setRegistryName(
          "spelt_bread")
      );
    }

    // Standard / Simple recipes
    r.registerAll(

      new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_URN), new ItemStack(BlocksTFCF.FIRED_URN), 1599f,
                           Metal.Tier.TIER_I).setRegistryName("unfired_urn"),
      new HeatRecipeSimple(IIngredient.of(BlocksTFCF.FIRED_URN), new ItemStack(BlocksTFCF.FIRED_URN), 1599f,
                           Metal.Tier.TIER_I).setRegistryName("fired_urn"),

      // Bread
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.HASH_MUFFIN), 480).setRegistryName("burned_hash_muffin"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.AMARANTH_BREAD), 480)
                .setRegistryName("burned_barley_bread"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.BUCKWHEAT_BREAD), 480).setRegistryName("burned_cornbread"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.FONIO_BREAD), 480).setRegistryName("burned_oat_bread"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.MILLET_BREAD), 480).setRegistryName("burned_rice_bread"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.QUINOA_BREAD), 480).setRegistryName("burned_rye_bread"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.SPELT_BREAD), 480).setRegistryName("burned_wheat_bread"),

      // Epiphytes
      new HeatRecipeSimple(IIngredient.of("epiphyteArtistsConk"), new ItemStack(ItemsTFCF.ROASTED_ARTISTS_CONK), 200, 480).setRegistryName(
        "roasted_artists_conk"),
      new HeatRecipeSimple(IIngredient.of("epiphyteSulphurShelf"), new ItemStack(ItemsTFCF.ROASTED_SULPHUR_SHELF), 200,
                           480).setRegistryName("roasted_sulphur_shelf"),
      new HeatRecipeSimple(IIngredient.of("epiphyteTurkeyTail"), new ItemStack(ItemsTFCF.ROASTED_TURKEY_TAIL), 200, 480).setRegistryName(
        "roasted_turkey_tail"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_ARTISTS_CONK), 480)
                .setRegistryName("burned_artists_conk"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_SULPHUR_SHELF), 480)
                .setRegistryName("burned_sulphur_shelf"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_TURKEY_TAIL), 480)
                .setRegistryName("burned_turkey_tail"),

      // Mushrooms
      new HeatRecipeSimple(IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.PORCINI)),
                           new ItemStack(ItemsTFCF.ROASTED_PORCINI), 200, 480).setRegistryName("roasted_porcini_specific"),
      new HeatRecipeSimple(IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.AMANITA)),
                           new ItemStack(ItemsTFCF.ROASTED_AMANITA), 200, 480).setRegistryName("roasted_amanita_specific"),
      new HeatRecipeSimple(IIngredient.of("mushroomPorcini"), new ItemStack(ItemsTFCF.ROASTED_PORCINI), 200, 480).setRegistryName(
        "roasted_porcini"),
      new HeatRecipeSimple(IIngredient.of("mushroomAmanita"), new ItemStack(ItemsTFCF.ROASTED_AMANITA), 200, 480).setRegistryName(
        "roasted_amanita"),
      new HeatRecipeSimple(IIngredient.of("mushroomBlackPowderpuff"), new ItemStack(ItemsTFCF.ROASTED_BLACK_POWDERPUFF), 200,
                           480).setRegistryName("roasted_black_powderpuff"),
      new HeatRecipeSimple(IIngredient.of("mushroomChanterelle"), new ItemStack(ItemsTFCF.ROASTED_CHANTERELLE), 200, 480).setRegistryName(
        "roasted_chanterelle"),
      new HeatRecipeSimple(IIngredient.of("mushroomDeathCap"), new ItemStack(ItemsTFCF.ROASTED_DEATH_CAP), 200, 480).setRegistryName(
        "roasted_death_cap"),
      new HeatRecipeSimple(IIngredient.of("mushroomGiantClub"), new ItemStack(ItemsTFCF.ROASTED_GIANT_CLUB), 200, 480).setRegistryName(
        "roasted_giant_club"),
      new HeatRecipeSimple(IIngredient.of("mushroomParasol"), new ItemStack(ItemsTFCF.ROASTED_PARASOL_MUSHROOM), 200, 480).setRegistryName(
        "roasted_parasol_mushroom"),
      new HeatRecipeSimple(IIngredient.of("mushroomStinkhorn"), new ItemStack(ItemsTFCF.ROASTED_STINKHORN), 200, 480).setRegistryName(
        "roasted_stinkhorn"),
      new HeatRecipeSimple(IIngredient.of("mushroomWeepingMilkCap"), new ItemStack(ItemsTFCF.ROASTED_WEEPING_MILK_CAP), 200,
                           480).setRegistryName("roasted_weeping_milk_cap"),
      new HeatRecipeSimple(IIngredient.of("mushroomWoodBlewit"), new ItemStack(ItemsTFCF.ROASTED_WOOD_BLEWIT), 200, 480).setRegistryName(
        "roasted_wood_blewit"),
      new HeatRecipeSimple(IIngredient.of("mushroomWoollyGomphus"), new ItemStack(ItemsTFCF.ROASTED_WOOLLY_GOMPHUS), 200,
                           480).setRegistryName("roasted_woolly_gomphus"),

      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PORCINI), 480).setRegistryName("burned_porcini"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_AMANITA), 480).setRegistryName("burned_amanita"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BLACK_POWDERPUFF), 480)
                .setRegistryName("burned_black_powderpuff"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_CHANTERELLE), 480)
                .setRegistryName("burned_chanterelle"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_DEATH_CAP), 480)
                .setRegistryName("burned_death_cap"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_GIANT_CLUB), 480)
                .setRegistryName("burned_giant_club"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PARASOL_MUSHROOM), 480)
                .setRegistryName("burned_parasol_mushroom"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_STINKHORN), 480)
                .setRegistryName("burned_stinkhorn"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WEEPING_MILK_CAP), 480)
                .setRegistryName("burned_weeping_milk_cap"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WOOD_BLEWIT), 480)
                .setRegistryName("burned_wood_blewit"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WOOLLY_GOMPHUS), 480)
                .setRegistryName("burned_woolly_gomphus"),

      // Food
      new HeatRecipeSimple(IIngredient.of("rawEel"), new ItemStack(ItemsTFCF.COOKED_EEL), 200, 480).setRegistryName("cooked_eel"),
      new HeatRecipeSimple(IIngredient.of("rawCrab"), new ItemStack(ItemsTFCF.COOKED_CRAB), 200, 480).setRegistryName("cooked_crab"),
      new HeatRecipeSimple(IIngredient.of("rawClam"), new ItemStack(ItemsTFCF.COOKED_CLAM), 200, 480).setRegistryName("cooked_clam"),
      new HeatRecipeSimple(IIngredient.of("rawScallop"), new ItemStack(ItemsTFCF.COOKED_SCALLOP), 200, 480).setRegistryName(
        "cooked_scallop"),
      new HeatRecipeSimple(IIngredient.of("rawStarfish"), new ItemStack(ItemsTFCF.COOKED_STARFISH), 200, 480).setRegistryName(
        "cooked_starfish"),
      new HeatRecipeSimple(IIngredient.of("rawSnail"), new ItemStack(ItemsTFCF.COOKED_SNAIL), 200, 480).setRegistryName("cooked_snail"),
      new HeatRecipeSimple(IIngredient.of("rawWorm"), new ItemStack(ItemsTFCF.COOKED_WORM), 200, 480).setRegistryName("cooked_worm"),

      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_EEL), 480).setRegistryName("burned_eel"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_CRAB), 480).setRegistryName("burned_crab"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_CLAM), 480).setRegistryName("burned_clam"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_SCALLOP), 480).setRegistryName("burned_scallop"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_STARFISH), 480).setRegistryName("burned_starfish"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_SNAIL), 480).setRegistryName("burned_snail"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.COOKED_WORM), 480).setRegistryName("burned_worm"),

      // Nut Roasting
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BEECHNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BEECHNUT_NUT), 200,
                           480).setRegistryName("roasted_beechnut"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BLACK_WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BLACK_WALNUT_NUT),
                           200, 480).setRegistryName("roasted_black_walnut"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BUTTERNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BUTTERNUT_NUT), 200,
                           480).setRegistryName("roasted_butternut"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.GINKGO_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_GINKGO_NUT_NUT), 200,
                           480).setRegistryName("roasted_ginkgo_nut"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.HAZELNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HAZELNUT_NUT), 200,
                           480).setRegistryName("roasted_hazelnut"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_WALNUT_NUT), 200,
                           480).setRegistryName("roasted_walnut"),

      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BEECHNUT_NUT), 480)
                .setRegistryName("burned_beechnut"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BLACK_WALNUT_NUT), 480)
                .setRegistryName("burned_black_walnut"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BUTTERNUT_NUT), 480)
                .setRegistryName("burned_butternut"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_GINKGO_NUT_NUT), 480)
                .setRegistryName("burned_ginkgo_nut"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_HAZELNUT_NUT), 480)
                .setRegistryName("burned_hazelnut"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WALNUT_NUT), 480).setRegistryName("burned_walnut"),

      // Kaolinite Clay
      new HeatRecipeSimple(IIngredient.of("clayKaolinite"), new ItemStack(ItemPowder.get(Powder.KAOLINITE)), 200).setRegistryName(
        "kaolinite_clay"),
      new HeatRecipeSimple(IIngredient.of(ItemPowder.get(Powder.KAOLINITE)), new ItemStack(ItemPowder.get(Powder.KAOLINITE)), 1599f,
                           Metal.Tier.TIER_I).setRegistryName("burnt_kaolinite_clay"),

      // Torches
      new HeatRecipeSimple(IIngredient.of("twig"), new ItemStack(Blocks.TORCH, 6), 50).setRegistryName("torch_twig"),
      new HeatRecipeSimple(IIngredient.of("driftwood"), new ItemStack(Blocks.TORCH, 12), 60).setRegistryName("torch_driftwood"),

      // Ash
      new HeatRecipeSimple(IIngredient.of("straw"), new ItemStack(ItemsCore.WOOD_ASH, 1), 350, 750).setRegistryName("straw_ash"),
      new HeatRecipeSimple(IIngredient.of("twig"), new ItemStack(ItemsCore.WOOD_ASH, 2), 350, 750).setRegistryName("twig_ash"),
      new HeatRecipeSimple(IIngredient.of("torch"), new ItemStack(ItemsCore.WOOD_ASH, 2), 350, 750).setRegistryName("torch_ash_1"),
      new HeatRecipeSimple(IIngredient.of(Blocks.TORCH), new ItemStack(ItemsCore.WOOD_ASH, 2), 350, 750).setRegistryName("torch_ash_2"),

      // Charred Bones
      new HeatRecipeSimple(IIngredient.of("bone"), new ItemStack(ItemsTFCF.CHARRED_BONES), 425, 850).setRegistryName("charred_bones_heat"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CHARRED_BONES)), new ItemStack(ItemsTFCF.CHARRED_BONES), 1599f,
                           Metal.Tier.TIER_I).setRegistryName("burnt_charred_bones")
    );

    if (!TFCFlorae.FirmaLifeAdded) {
      r.registerAll(

        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.ACORN_NUT)), new ItemStack(ItemsTFCF.ROASTED_ACORN_NUT), 200,
                             480).setRegistryName("roasted_acorn"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CHESTNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_CHESTNUT_NUT), 200,
                             480).setRegistryName("roasted_chestnut"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.HICKORY_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HICKORY_NUT_NUT),
                             200, 480).setRegistryName("roasted_hickory_nut"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PECAN_NUT)), new ItemStack(ItemsTFCF.ROASTED_PECAN_NUT), 200,
                             480).setRegistryName("roasted_pecan"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PINE_NUT)), new ItemStack(ItemsTFCF.ROASTED_PINE_NUT), 200,
                             480).setRegistryName("roasted_pine_nut"),

        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_ACORN_NUT), 480)
                  .setRegistryName("burned_acorn"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_CHESTNUT_NUT), 480)
                  .setRegistryName("burned_chestnut"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_HICKORY_NUT_NUT), 480)
                  .setRegistryName("burned_hickory_nut"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PECAN_NUT), 480)
                  .setRegistryName("burned_pecan"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PINE_NUT), 480)
                  .setRegistryName("burned_pine_nut"),

        // Food Roasting/Drying
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BLACK_TEA)), new ItemStack(ItemsTFCF.DRIED_BLACK_TEA), 200,
                             480).setRegistryName("dried_black_tea"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.GREEN_TEA)), new ItemStack(ItemsTFCF.DRIED_GREEN_TEA), 200,
                             480).setRegistryName("dried_green_tea"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.WHITE_TEA)), new ItemStack(ItemsTFCF.DRIED_WHITE_TEA), 200,
                             480).setRegistryName("dried_white_tea"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CANNABIS_BUD)), new ItemStack(ItemsTFCF.DRIED_CANNABIS_BUD), 200,
                             480).setRegistryName("dried_cannabis_bud"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CANNABIS_LEAF)), new ItemStack(ItemsTFCF.DRIED_CANNABIS_LEAF), 200,
                             480).setRegistryName("dried_cannabis_leaf"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.COCA_LEAF)), new ItemStack(ItemsTFCF.DRIED_COCA_LEAF), 200,
                             480).setRegistryName("dried_coca_leaf"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.OPIUM_POPPY_BULB)), new ItemStack(ItemsTFCF.DRIED_OPIUM_POPPY_BULB),
                             200, 480).setRegistryName("dried_opium_poppy_bulb"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PEYOTE)), new ItemStack(ItemsTFCF.DRIED_PEYOTE), 200,
                             480).setRegistryName("dried_peyote"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.TOBACCO_LEAF)), new ItemStack(ItemsTFCF.DRIED_TOBACCO_LEAF), 200,
                             480).setRegistryName("dried_tobacco_leaf"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.DRIED_COFFEA_CHERRIES)),
                             new ItemStack(ItemsTFCF.ROASTED_COFFEE_BEANS), 200, 480).setRegistryName("roasted_coffea_cherries_firepit"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CHAMOMILE_HEAD)), new ItemStack(ItemsTFCF.DRIED_CHAMOMILE_HEAD), 200,
                             480).setRegistryName("roasted_chamomile_head"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.DANDELION_HEAD)), new ItemStack(ItemsTFCF.DRIED_DANDELION_HEAD), 200,
                             480).setRegistryName("roasted_dandelion_head"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.LABRADOR_TEA_HEAD)), new ItemStack(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD),
                             200, 480).setRegistryName("roasted_labrador_tea_head"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.SUNFLOWER_HEAD)), new ItemStack(ItemsTFCF.DRIED_SUNFLOWER_HEAD), 200,
                             480).setRegistryName("roasted_sunflower_head"),

        // Food Destroy
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_BLACK_TEA), 480)
                  .setRegistryName("burned_black_tea"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_GREEN_TEA), 480)
                  .setRegistryName("burned_green_tea"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_WHITE_TEA), 480)
                  .setRegistryName("burned_white_tea"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_BUD), 480)
                  .setRegistryName("burned_cannabis_bud"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_LEAF), 480)
                  .setRegistryName("burned_cannabis_leaf"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_COCA_LEAF), 480)
                  .setRegistryName("burned_coca_leaf"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_OPIUM_POPPY_BULB), 480)
                  .setRegistryName("burned_opium_poppy_bulb"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_PEYOTE), 480).setRegistryName("burned_peyote"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_TOBACCO_LEAF), 480)
                  .setRegistryName("burned_tobacco_leaf"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS), 480)
                  .setRegistryName("burned_coffea_cherries"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_CHAMOMILE_HEAD), 480)
                  .setRegistryName("burned_chamomile_head"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_DANDELION_HEAD), 480)
                  .setRegistryName("burned_dandelion_head"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), 480)
                  .setRegistryName("burned_labrador_tea_head"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_SUNFLOWER_HEAD), 480)
                  .setRegistryName("burned_sunflower_head")
      );
    }
  }

  @SubscribeEvent
  public static void onRegisterCraftingRecipeEvent(RegistryEvent.Register<IRecipe> event) {
    IForgeRegistry<IRecipe> r = event.getRegistry();
    //Register all strips
    List<ItemStack> allHammers = new ArrayList<>();
    for (Metal metal : TFCRegistries.METALS.getValuesCollection()) {
      if (!metal.isToolMetal()) {
        continue;
      }
      allHammers.add(new ItemStack(ItemMetal.get(metal, Metal.ItemType.HAMMER), 1, OreDictionary.WILDCARD_VALUE));
    }
    Ingredient hammer = Ingredient.fromStacks(allHammers.toArray(new ItemStack[0]));

    ResourceLocation groupSurfaceRock = new ResourceLocation(MODID_TFCF, "surface_rock");

    RockType.getTypes().forEach(rock -> {
      /*
       * Surface rocks to TFC rocks
       */
      Ingredient ingredient = Ingredient.fromStacks(new ItemStack(BlocksRock.SURFACE.get(rock)));
      ItemStack output = new ItemStack(ItemsRock.LOOSE.get(rock), 1);
      if (!output.isEmpty()) {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(hammer);
        list.add(ingredient);
        //noinspection ConstantConditions
        r.register(
          new ShapelessDamageRecipe(groupSurfaceRock, list, output, 1).setRegistryName(MODID_TFCF, rock.getName().toLowerCase() + "_rock_hammer"));
      }
    });
  }

  @SuppressWarnings("rawtypes")
  @SubscribeEvent
  public static void onRegisterHeatRecipeEventFL(RegistryEvent.Register<HeatRecipe> event) {
    if (TFCFlorae.FirmaLifeAdded) {
      IForgeRegistry<HeatRecipe> r = event.getRegistry();

      //Remove recipes
      if (ConfigFL.General.COMPAT.removeTFC) {
        IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) TFCRegistries.HEAT;
        String[] regNames = {"amaranth_bread", "buckwheat_bread", "fonio_bread", "millet_bread", "quinoa_bread", "spelt_bread"};
        for (String name : regNames) {
          HeatRecipe recipe = TFCRegistries.HEAT.getValue(new ResourceLocation(MODID_TFCF, name));
          if (recipe != null) {
            modRegistry.remove(recipe.getRegistryName());
            if (ConfigFL.General.COMPAT.logging) {
              FirmaLife.LOGGER.info("Removed heating recipe tfcflorae:{}", name);
            }
          }
        }
      }
    }
  }

  @SubscribeEvent
  public static void onRecipeRegisterFL(RegistryEvent.Register<IRecipe> event) {
    if (TFCFlorae.FirmaLifeAdded) {
      if (ConfigFL.General.COMPAT.removeTFC) {
        IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
        String[] regNames = {
          "food/dough/amaranth", "food/dough/buckwheat", "food/dough/fonio", "food/dough/millet", "food/dough/quinoa",
          "food/dough/spelt",
          "food/sandwich/amaranth", "food/sandwich/buckwheat", "food/sandwich/fonio", "food/sandwich/millet", "food/sandwich/quinoa",
          "food/sandwich/spelt"
        };
        for (String name : regNames) {
          IRecipe recipe = registry.getValue(new ResourceLocation(MODID_TFCF, name));
          if (recipe != null) {
            registry.remove(recipe.getRegistryName());
            if (ConfigFL.General.COMPAT.logging) {
              FirmaLife.LOGGER.info("Removed crafting recipe tfcflorae:{}", name);
            }
          }
        }
      }
    }
  }

  @SubscribeEvent
  @SuppressWarnings("ConstantConditions")
  public static void onRegisterNutTreeEvent(RegistryEvent.Register<NutRecipe> event) {
    if (TFCFlorae.FirmaLifeAdded) {
      IForgeRegistry<NutRecipe> r = event.getRegistry();

      Tree chestnut = TFCRegistries.TREES.getValue(DefaultTrees.CHESTNUT);
      Tree oak = TFCRegistries.TREES.getValue(DefaultTrees.OAK);
      Tree hickory = TFCRegistries.TREES.getValue(DefaultTrees.HICKORY);
      Tree beech = TFCRegistries.TREES.getValue(TreesTFCF.BEECH);
      Tree black_walnut = TFCRegistries.TREES.getValue(TreesTFCF.BLACK_WALNUT);
      Tree butternut = TFCRegistries.TREES.getValue(TreesTFCF.BUTTERNUT);
      Tree european_oak = TFCRegistries.TREES.getValue(TreesTFCF.EUROPEAN_OAK);
      Tree ginkgo = TFCRegistries.TREES.getValue(TreesTFCF.GINKGO);
      Tree hazel = TFCRegistries.TREES.getValue(TreesTFCF.HAZEL);
      Tree hemlock = TFCRegistries.TREES.getValue(TreesTFCF.HEMLOCK);
      Tree ironwood = TFCRegistries.TREES.getValue(TreesTFCF.IRONWOOD);
      Tree kauri = TFCRegistries.TREES.getValue(TreesTFCF.KAURI);
      Tree larch = TFCRegistries.TREES.getValue(TreesTFCF.LARCH);
      Tree nordmann_fir = TFCRegistries.TREES.getValue(TreesTFCF.NORDMANN_FIR);
      Tree norway_spruce = TFCRegistries.TREES.getValue(TreesTFCF.NORWAY_SPRUCE);
      Tree redwood = TFCRegistries.TREES.getValue(TreesTFCF.REDWOOD);
      Tree walnut = TFCRegistries.TREES.getValue(TreesTFCF.WALNUT);

      r.registerAll(

        new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFC.get(european_oak),
                      new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("european_oak_nut"),
        new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_EUROPEAN_OAK),
                      new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("european_oak_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_EUROPEAN_OAK),
                      new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("european_oak_nut_orange"),
        new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFCF.get(SeasonalTrees.RED_EUROPEAN_OAK),
                      new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("european_oak_nut_red"),
        new NutRecipe(BlockLogTFC.get(oak), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_OAK),
                      new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("oak_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(oak), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_OAK),
                      new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("oak_nut_orange"),
        new NutRecipe(BlockLogTFC.get(oak), BlockLeavesTFCF.get(SeasonalTrees.RED_OAK),
                      new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("oak_nut_red"),
        new NutRecipe(BlockLogTFC.get(chestnut), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_CHESTNUT),
                      new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUTS))).setRegistryName("chestnut_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(chestnut), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_CHESTNUT),
                      new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUTS))).setRegistryName("chestnut_nut_orange"),
        new NutRecipe(BlockLogTFC.get(chestnut), BlockLeavesTFCF.get(SeasonalTrees.RED_CHESTNUT),
                      new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUTS))).setRegistryName("chestnut_nut_red"),
        new NutRecipe(BlockLogTFC.get(hickory), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_HICKORY),
                      new ItemStack(ItemsFL.getFood(FoodFL.PECAN_NUTS))).setRegistryName("hickory_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(hickory), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_HICKORY),
                      new ItemStack(ItemsFL.getFood(FoodFL.PECAN_NUTS))).setRegistryName("hickory_nut_orange"),
        new NutRecipe(BlockLogTFC.get(hickory), BlockLeavesTFCF.get(SeasonalTrees.RED_HICKORY),
                      new ItemStack(ItemsFL.getFood(FoodFL.PECAN_NUTS))).setRegistryName("hickory_nut_red"),
        new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFC.get(beech), new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut"),
        new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_BEECH),
                      new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_BEECH),
                      new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut_orange"),
        new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFCF.get(SeasonalTrees.RED_BEECH),
                      new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut_red"),
        new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFC.get(black_walnut),
                      new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut"),
        new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_BLACK_WALNUT),
                      new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_BLACK_WALNUT),
                      new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut_orange"),
        new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFCF.get(SeasonalTrees.RED_BLACK_WALNUT),
                      new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut_red"),
        new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFC.get(butternut), new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName(
          "butternut_nut"),
        new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_BUTTERNUT),
                      new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName("butternut_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_BUTTERNUT),
                      new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName("butternut_nut_orange"),
        new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFCF.get(SeasonalTrees.RED_BUTTERNUT),
                      new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName("butternut_nut_red"),
        new NutRecipe(BlockLogTFC.get(ginkgo), BlockLeavesTFC.get(ginkgo), new ItemStack(ItemsTFCF.GINKGO_NUT)).setRegistryName(
          "ginkgo_nut"),
        new NutRecipe(BlockLogTFC.get(ginkgo), BlockLeavesTFCF.get(SeasonalTrees.GINKGO),
                      new ItemStack(ItemsTFCF.GINKGO_NUT)).setRegistryName("ginkgo_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFC.get(hazel), new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut"),
        new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_HAZEL),
                      new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_HAZEL),
                      new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut_orange"),
        new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFCF.get(SeasonalTrees.RED_HAZEL),
                      new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut_red"),
        new NutRecipe(BlockLogTFC.get(hemlock), BlockLeavesTFC.get(hemlock), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName(
          "hemlock_pinecone"),
        new NutRecipe(BlockLogTFC.get(ironwood), BlockLeavesTFC.get(ironwood), new ItemStack(ItemsTFCF.HOPS)).setRegistryName(
          "ironwood_hops"),
        new NutRecipe(BlockLogTFC.get(kauri), BlockLeavesTFC.get(kauri), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName(
          "kauri_pinecone"),
        new NutRecipe(BlockLogTFC.get(larch), BlockLeavesTFCF.get(SeasonalTrees.LARCH),
                      new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("larch_pinecone"),
        new NutRecipe(BlockLogTFC.get(nordmann_fir), BlockLeavesTFC.get(nordmann_fir), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName(
          "nordmann_fir_pinecone"),
        new NutRecipe(BlockLogTFC.get(norway_spruce), BlockLeavesTFC.get(norway_spruce),
                      new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("norway_spruce_pinecone"),
        new NutRecipe(BlockLogTFC.get(redwood), BlockLeavesTFC.get(redwood), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName(
          "redwood_pinecone"),
        new NutRecipe(BlockLogTFC.get(walnut), BlockLeavesTFC.get(walnut), new ItemStack(ItemsTFCF.WALNUT)).setRegistryName(
          "walnut_fruit"),
        new NutRecipe(BlockLogTFC.get(walnut), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_WALNUT),
                      new ItemStack(ItemsTFCF.WALNUT)).setRegistryName("walnut_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(walnut), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_WALNUT),
                      new ItemStack(ItemsTFCF.WALNUT)).setRegistryName("walnut_nut_orange"),
        new NutRecipe(BlockLogTFC.get(walnut), BlockLeavesTFCF.get(SeasonalTrees.RED_WALNUT),
                      new ItemStack(ItemsTFCF.WALNUT)).setRegistryName("walnut_nut_red")
      );
    }
  }

  @SubscribeEvent
  public static void onRegisterCrackingRecipeEvent(RegistryEvent.Register<CrackingRecipe> event) {
    if (TFCFlorae.FirmaLifeAdded) {
      IForgeRegistry<CrackingRecipe> r = event.getRegistry();
      r.registerAll(

        // Regular Trees
        new CrackingRecipe(IIngredient.of(ItemsTFCF.ACORN), new ItemStack(ItemsTFCF.ACORN_NUT), 0.5f).setRegistryName("acorn_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.BEECHNUT), new ItemStack(ItemsTFCF.BEECHNUT_NUT), 0.5f).setRegistryName(
          "beechnut_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.BLACK_WALNUT), new ItemStack(ItemsTFCF.BLACK_WALNUT_NUT), 0.5f).setRegistryName(
          "black_walnut_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.BUTTERNUT), new ItemStack(ItemsTFCF.BUTTERNUT_NUT), 0.5f).setRegistryName(
          "butternut_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.CHESTNUT), new ItemStack(ItemsTFCF.CHESTNUT_NUT), 0.5f).setRegistryName(
          "chestnut_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.GINKGO_NUT), new ItemStack(ItemsTFCF.GINKGO_NUT_NUT), 0.5f).setRegistryName(
          "ginkgo_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.HAZELNUT), new ItemStack(ItemsTFCF.HAZELNUT_NUT), 0.5f).setRegistryName(
          "hazelnut_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.HICKORY_NUT), new ItemStack(ItemsTFCF.HICKORY_NUT_NUT), 0.5f).setRegistryName(
          "hickory_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.PINECONE), new ItemStack(ItemsTFCF.PINE_NUT), 0.5f).setRegistryName("pine_nut_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.PECAN), new ItemStack(ItemsTFCF.PECAN_NUT), 0.5f).setRegistryName("pecan_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.WALNUT), new ItemStack(ItemsTFCF.WALNUT_NUT), 0.5f).setRegistryName("walnut_fruit")
      );
    }
  }

  @SubscribeEvent
  public static void onRegisterOvenRecipeEventFL(RegistryEvent.Register<OvenRecipe> event) {
    if (TFCFlorae.FirmaLifeAdded) {
      IForgeRegistry<OvenRecipe> r = event.getRegistry();
      int hour = ICalendar.TICKS_IN_HOUR;

      // Mud Pottery
      SoilType.getTypes().forEach(type -> {

        OvenRecipe mudBrick = new OvenRecipe(IIngredient.of(ItemsSoil.MUD_BRICK_WET.get(type)), new ItemStack(ItemsSoil.MUD_BRICK.get(type)),
                                             4 * hour);
        event.getRegistry().register(mudBrick.setRegistryName(type.getName().toLowerCase() + "_unfired_mud_brick"));
      });

      r.registerAll(
        new OvenRecipe(IIngredient.of(ItemsTFCF.HASH_MUFFIN_DOUGH), new ItemStack(ItemsTFCF.HASH_MUFFIN), 4 * hour).setRegistryName(
          MODID_TFCF, "hash_muffin_dough_oven"),
        new OvenRecipe(IIngredient.of(ItemsTFCF.AMARANTH_DOUGH), new ItemStack(ItemsTFCF.AMARANTH_BREAD), 4 * hour).setRegistryName(
          MODID_TFCF, "amaranth_dough_oven"),
        new OvenRecipe(IIngredient.of(ItemsTFCF.BUCKWHEAT_DOUGH), new ItemStack(ItemsTFCF.BUCKWHEAT_BREAD), 4 * hour).setRegistryName(
          MODID_TFCF, "buckwheat_dough_oven"),
        new OvenRecipe(IIngredient.of(ItemsTFCF.FONIO_DOUGH), new ItemStack(ItemsTFCF.FONIO_BREAD), 4 * hour).setRegistryName(MODID_TFCF,
                                                                                                                              "fonio_dough_oven"),
        new OvenRecipe(IIngredient.of(ItemsTFCF.MILLET_DOUGH), new ItemStack(ItemsTFCF.MILLET_BREAD), 4 * hour).setRegistryName(
          MODID_TFCF, "millet_dough_oven"),
        new OvenRecipe(IIngredient.of(ItemsTFCF.QUINOA_DOUGH), new ItemStack(ItemsTFCF.QUINOA_BREAD), 4 * hour).setRegistryName(
          MODID_TFCF, "quinoa_dough_oven"),
        new OvenRecipe(IIngredient.of(ItemsTFCF.SPELT_DOUGH), new ItemStack(ItemsTFCF.SPELT_BREAD), 4 * hour).setRegistryName(MODID_TFCF,
                                                                                                                              "spelt_dough_oven"),

        new OvenRecipe(IIngredient.of("amaranthFlatbreadDough"), new ItemStack(ItemsTFCF.AMARANTH_FLATBREAD), 4 * hour).setRegistryName(
          MODID_TFCF, "amaranth_flatbread_dough_oven"),
        new OvenRecipe(IIngredient.of("buckwheatFlatbreadDough"), new ItemStack(ItemsTFCF.BUCKWHEAT_FLATBREAD), 4 * hour).setRegistryName(
          MODID_TFCF, "buckwheat_flatbread_dough_oven"),
        new OvenRecipe(IIngredient.of("fonioFlatbreadDough"), new ItemStack(ItemsTFCF.FONIO_FLATBREAD), 4 * hour).setRegistryName(
          MODID_TFCF, "fonio_flatbread_dough_oven"),
        new OvenRecipe(IIngredient.of("milletFlatbreadDough"), new ItemStack(ItemsTFCF.MILLET_FLATBREAD), 4 * hour).setRegistryName(
          MODID_TFCF, "millet_flatbread_dough_oven"),
        new OvenRecipe(IIngredient.of("quinoaFlatbreadDough"), new ItemStack(ItemsTFCF.QUINOA_FLATBREAD), 4 * hour).setRegistryName(
          MODID_TFCF, "quinoa_flatbread_dough_oven"),
        new OvenRecipe(IIngredient.of("speltFlatbreadDough"), new ItemStack(ItemsTFCF.SPELT_FLATBREAD), 4 * hour).setRegistryName(
          MODID_TFCF, "spelt_flatbread_dough_oven"),

        //new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.ACORN_NUT)), new ItemStack(ItemsTFCF.ROASTED_ACORN_NUT), 2 * hour).setRegistryName("acorn_roasted_oven"),
        new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.BEECHNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BEECHNUT_NUT),
                       2 * hour).setRegistryName("beechnut_roasted_oven"),
        new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.BLACK_WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BLACK_WALNUT_NUT),
                       2 * hour).setRegistryName("black_walnut_roasted_oven"),
        new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.BUTTERNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BUTTERNUT_NUT),
                       2 * hour).setRegistryName("butternut_roasted_oven"),
        //new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.CHESTNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_CHESTNUT_NUT), 2 * hour).setRegistryName("chestnut_roasted_oven"),
        new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.GINKGO_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_GINKGO_NUT_NUT),
                       2 * hour).setRegistryName("ginkgo_roasted_oven"),
        new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.HAZELNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HAZELNUT_NUT),
                       2 * hour).setRegistryName("hazelnut_roasted_oven"),
        //new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.HICKORY_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HICKORY_NUT_NUT), 2 * hour).setRegistryName("hickory_roasted_oven"),
        //new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.PINE_NUT)), new ItemStack(ItemsTFCF.ROASTED_PINE_NUT), 2 * hour).setRegistryName("pine_nut_roasted_oven"),
        //new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.PECAN_NUT)), new ItemStack(ItemsTFCF.ROASTED_PECAN_NUT), 2 * hour).setRegistryName("pecan_roasted_oven"),
        new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_WALNUT_NUT),
                       2 * hour).setRegistryName("walnut_roasted_oven"),
        new OvenRecipe(IIngredient.of(new ItemStack(ItemsFL.getFood(FoodFL.ACORN_FRUIT))), new ItemStack(ItemsTFCF.ROASTED_ACORN_NUT),
                       2 * hour).setRegistryName("acorn_roasted_oven_fl"),
        new OvenRecipe(IIngredient.of(new ItemStack(ItemsFL.getFood(FoodFL.PINE_NUTS))), new ItemStack(ItemsTFCF.ROASTED_PINE_NUT),
                       2 * hour).setRegistryName("pine_nut_roasted_oven_fl"),
        new OvenRecipe(IIngredient.of(new ItemStack(ItemsFL.getFood(FoodFL.PECAN_NUTS))), new ItemStack(ItemsTFCF.ROASTED_PECAN_NUT),
                       2 * hour).setRegistryName("pecan_roasted_oven_fl"),

        new OvenRecipe(IIngredient.of(new ItemStack(ItemsTFCF.DRIED_COFFEA_CHERRIES)), new ItemStack(ItemsTFCF.ROASTED_COFFEE_BEANS),
                       2 * hour).setRegistryName("coffee_beans_roasted_oven"),

        new OvenRecipe(IIngredient.of("epiphyteArtistsConk"), new ItemStack(ItemsTFCF.ROASTED_ARTISTS_CONK), 2 * hour).setRegistryName(
          "roasted_artists_conk_oven"),
        new OvenRecipe(IIngredient.of("epiphyteSulphurShelf"), new ItemStack(ItemsTFCF.ROASTED_SULPHUR_SHELF), 2 * hour).setRegistryName(
          "roasted_sulphur_shelf_oven"),
        new OvenRecipe(IIngredient.of("epiphyteTurkeyTail"), new ItemStack(ItemsTFCF.ROASTED_TURKEY_TAIL), 2 * hour).setRegistryName(
          "roasted_turkey_tail_oven"),
        new OvenRecipe(IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.PORCINI)),
                       new ItemStack(ItemsTFCF.ROASTED_PORCINI), 2 * hour).setRegistryName("roasted_porcini_oven_specific"),
        new OvenRecipe(IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.AMANITA)),
                       new ItemStack(ItemsTFCF.ROASTED_AMANITA), 2 * hour).setRegistryName("roasted_amanita_oven_specific"),
        new OvenRecipe(IIngredient.of("mushroomPorcini"), new ItemStack(ItemsTFCF.ROASTED_PORCINI), 2 * hour).setRegistryName(
          "roasted_porcini_oven"),
        new OvenRecipe(IIngredient.of("mushroomAmanita"), new ItemStack(ItemsTFCF.ROASTED_AMANITA), 2 * hour).setRegistryName(
          "roasted_amanita_oven"),
        new OvenRecipe(IIngredient.of("mushroomBlackPowderpuff"), new ItemStack(ItemsTFCF.ROASTED_BLACK_POWDERPUFF),
                       2 * hour).setRegistryName("roasted_black_powderpuff_oven"),
        new OvenRecipe(IIngredient.of("mushroomChanterelle"), new ItemStack(ItemsTFCF.ROASTED_CHANTERELLE), 2 * hour).setRegistryName(
          "roasted_chanterelle_oven"),
        new OvenRecipe(IIngredient.of("mushroomDeathCap"), new ItemStack(ItemsTFCF.ROASTED_DEATH_CAP), 2 * hour).setRegistryName(
          "roasted_death_cap_oven"),
        new OvenRecipe(IIngredient.of("mushroomGiantClub"), new ItemStack(ItemsTFCF.ROASTED_GIANT_CLUB), 2 * hour).setRegistryName(
          "roasted_giant_club_oven"),
        new OvenRecipe(IIngredient.of("mushroomParasol"), new ItemStack(ItemsTFCF.ROASTED_PARASOL_MUSHROOM), 2 * hour).setRegistryName(
          "roasted_parasol_mushroom_oven"),
        new OvenRecipe(IIngredient.of("mushroomStinkhorn"), new ItemStack(ItemsTFCF.ROASTED_STINKHORN), 2 * hour).setRegistryName(
          "roasted_stinkhorn_oven"),
        new OvenRecipe(IIngredient.of("mushroomWeepingMilkCap"), new ItemStack(ItemsTFCF.ROASTED_WEEPING_MILK_CAP),
                       2 * hour).setRegistryName("roasted_weeping_milk_cap_oven"),
        new OvenRecipe(IIngredient.of("mushroomWoodBlewit"), new ItemStack(ItemsTFCF.ROASTED_WOOD_BLEWIT), 2 * hour).setRegistryName(
          "roasted_wood_blewit_oven"),
        new OvenRecipe(IIngredient.of("mushroomWoollyGomphus"), new ItemStack(ItemsTFCF.ROASTED_WOOLLY_GOMPHUS),
                       2 * hour).setRegistryName("roasted_woolly_gomphus_oven"),

        new OvenRecipe(IIngredient.of("rawEel"), new ItemStack(ItemsTFCF.COOKED_EEL), 2 * hour).setRegistryName("cooked_eel_oven"),
        new OvenRecipe(IIngredient.of("rawCrab"), new ItemStack(ItemsTFCF.COOKED_CRAB), 2 * hour).setRegistryName("cooked_crab_oven"),
        new OvenRecipe(IIngredient.of("rawClam"), new ItemStack(ItemsTFCF.COOKED_CLAM), 2 * hour).setRegistryName("cooked_clam_oven"),
        new OvenRecipe(IIngredient.of("rawScallop"), new ItemStack(ItemsTFCF.COOKED_SCALLOP), 2 * hour).setRegistryName(
          "cooked_scallop_oven"),
        new OvenRecipe(IIngredient.of("rawStarfish"), new ItemStack(ItemsTFCF.COOKED_STARFISH), 2 * hour).setRegistryName(
          "cooked_starfish_oven"),
        new OvenRecipe(IIngredient.of("rawSnail"), new ItemStack(ItemsTFCF.COOKED_SNAIL), 2 * hour).setRegistryName("cooked_snail_oven"),
        new OvenRecipe(IIngredient.of("rawWorm"), new ItemStack(ItemsTFCF.COOKED_WORM), 2 * hour).setRegistryName("cooked_worm_oven"),

        new OvenRecipe(IIngredient.of("clayKaolinite"), new ItemStack(ItemPowder.get(Powder.KAOLINITE)), hour).setRegistryName(
          "kaolinite_clay_oven"),
        new OvenRecipe(IIngredient.of("bone"), new ItemStack(ItemsTFCF.CHARRED_BONES), 2 * hour).setRegistryName(
          "charred_bones_heat_oven")
      );
    }
  }

  @SubscribeEvent
  public static void onRegisterDryingRecipeEventFL(RegistryEvent.Register<DryingRecipe> event) {
    if (TFCFlorae.FirmaLifeAdded) {
      IForgeRegistry<DryingRecipe> r = event.getRegistry();
      r.registerAll(
        new DryingRecipe(IIngredient.of(ItemsTFCF.CELLULOSE_FIBERS), new ItemStack(Items.PAPER), 24000).setRegistryName(MODID_TFCF,
                                                                                                                        "paper_from_cellulose_fibers"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.BLACK_TEA), new ItemStack(ItemsTFCF.DRIED_BLACK_TEA), 24000).setRegistryName(MODID_TFCF,
                                                                                                                               "dried_black_tea"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.GREEN_TEA), new ItemStack(ItemsTFCF.DRIED_GREEN_TEA), 24000).setRegistryName(MODID_TFCF,
                                                                                                                               "dried_green_tea"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.WHITE_TEA), new ItemStack(ItemsTFCF.DRIED_WHITE_TEA), 24000).setRegistryName(MODID_TFCF,
                                                                                                                               "dried_white_tea"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.CANNABIS_BUD), new ItemStack(ItemsTFCF.DRIED_CANNABIS_BUD), 24000).setRegistryName(
          MODID_TFCF, "dried_cannabis_bud"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.CANNABIS_LEAF), new ItemStack(ItemsTFCF.DRIED_CANNABIS_LEAF), 24000).setRegistryName(
          MODID_TFCF, "dried_cannabis_leaf"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.COCA_LEAF), new ItemStack(ItemsTFCF.DRIED_COCA_LEAF), 24000).setRegistryName(MODID_TFCF,
                                                                                                                               "dried_coca_leaf"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.OPIUM_POPPY_BULB), new ItemStack(ItemsTFCF.DRIED_OPIUM_POPPY_BULB),
                         24000).setRegistryName(MODID_TFCF, "dried_opium_poppy_bulb"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.PEYOTE), new ItemStack(ItemsTFCF.DRIED_PEYOTE), 24000).setRegistryName(MODID_TFCF,
                                                                                                                         "dried_peyote"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.TOBACCO_LEAF), new ItemStack(ItemsTFCF.DRIED_TOBACCO_LEAF), 24000).setRegistryName(
          MODID_TFCF, "dried_tobacco_leaf"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.COFFEA_CHERRIES), new ItemStack(ItemsTFCF.DRIED_COFFEA_CHERRIES),
                         24000).setRegistryName(MODID_TFCF, "dried_coffea_cherries"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.CHAMOMILE_HEAD), new ItemStack(ItemsTFCF.DRIED_CHAMOMILE_HEAD), 24000).setRegistryName(
          MODID_TFCF, "dried_chamomile_head"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.DANDELION_HEAD), new ItemStack(ItemsTFCF.DRIED_DANDELION_HEAD), 24000).setRegistryName(
          MODID_TFCF, "dried_dandelion_head"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.LABRADOR_TEA_HEAD), new ItemStack(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD),
                         24000).setRegistryName("dried_labrador_tea_head"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.SUNFLOWER_HEAD), new ItemStack(ItemsTFCF.DRIED_SUNFLOWER_HEAD), 24000).setRegistryName(
          MODID_TFCF, "dried_sunflower_head")
      );

      // Mud Pottery
      SoilType.getTypes().forEach(type -> {

        DryingRecipe mud = new DryingRecipe(IIngredient.of(ItemsSoil.MUD_BRICK_WET.get(type)), new ItemStack(ItemsSoil.MUD_BRICK.get(type)), 6000);

        event.getRegistry().register(mud.setRegistryName(type.getName().toLowerCase() + "_wet_mud_brick"));
      });

    }
  }

  @SubscribeEvent
  public static void onRegisterPlanterEvent(RegistryEvent.Register<PlanterRecipe> event) {
    IForgeRegistry<PlanterRecipe> r = event.getRegistry();
    r.registerAll(
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.BLACK_EYED_PEAS)), new ItemStack(ItemsTFCF.BLACK_EYED_PEAS), 7,
                        true).setRegistryName("black_eyed_peas"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.CAYENNE_PEPPER)), new ItemStack(ItemsTFCF.RED_CAYENNE_PEPPER), 7,
                        true).setRegistryName("red_cayenne_pepper"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.GINSENG)), new ItemStack(ItemsTFCF.GINSENG), 5, false).setRegistryName(
        "ginseng"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.RUTABAGA)), new ItemStack(ItemsTFCF.RUTABAGA), 7, false).setRegistryName(
        "rutabaga"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.TURNIP)), new ItemStack(ItemsTFCF.TURNIP), 8, false).setRegistryName(
        "turnip"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.SUGAR_BEET)), new ItemStack(ItemsTFCF.SUGAR_BEET), 7,
                        false).setRegistryName("sugar_beet"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.PURPLE_GRAPE)), new ItemStack(ItemsTFCF.PURPLE_GRAPE), 8,
                        false).setRegistryName("purple_grape"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.GREEN_GRAPE)), new ItemStack(ItemsTFCF.GREEN_GRAPE), 8,
                        false).setRegistryName("green_grape"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.LIQUORICE_ROOT)), new ItemStack(ItemsTFCF.LIQUORICE_ROOT), 8,
                        false).setRegistryName("liquorice_root"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.COFFEA)), new ItemStack(ItemsTFCF.COFFEA_CHERRIES), 8,
                        false).setRegistryName("coffea_cherries"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.AGAVE)), new ItemStack(ItemsTFCF.AGAVE), 6, false).setRegistryName(
        "agave"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.COCA)), new ItemStack(ItemsTFCF.COCA_LEAF), 6, true).setRegistryName(
        "coca"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.COTTON)), new ItemStack(ItemsTFCF.COTTON_BOLL), 6, false).setRegistryName(
        "cotton"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.FLAX)), new ItemStack(ItemsTFCF.FLAX), 6, true).setRegistryName("flax"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.HEMP)), new ItemStack(ItemsTFCF.HEMP), 5, true).setRegistryName("hemp"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.HOP)), new ItemStack(ItemsTFCF.HOPS), 6, true).setRegistryName("hop"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.INDIGO)), new ItemStack(ItemsTFCF.INDIGO), 5, true).setRegistryName(
        "indigo"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.MADDER)), new ItemStack(ItemsTFCF.MADDER), 5, false).setRegistryName(
        "madder"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.OPIUM_POPPY)), new ItemStack(ItemsTFCF.OPIUM_POPPY_BULB), 6,
                        false).setRegistryName("opium_poppy"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.RAPE)), new ItemStack(ItemsTFCF.RAPE), 6, false).setRegistryName("rape"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.WELD)), new ItemStack(ItemsTFCF.WELD), 5, true).setRegistryName("weld"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.WOAD)), new ItemStack(ItemsTFCF.WOAD), 6, false).setRegistryName("woad"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.TOBACCO)), new ItemStack(ItemsTFCF.TOBACCO_LEAF), 7, true).setRegistryName(
        "tobacco")
    );
  }

}
