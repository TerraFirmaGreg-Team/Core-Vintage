package net.dries007.tfc.types;

import su.terrafirmagreg.api.data.enums.Mods;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import net.dries007.firmalife.ConfigFL;
import net.dries007.firmalife.FirmaLife;
import net.dries007.firmalife.init.FoodFL;
import net.dries007.firmalife.registry.ItemsFL;
import net.dries007.tfc.api.recipes.LoomRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeFluidMixing;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeTemperature;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeStone;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.blocks.BlocksTFCF;
import net.dries007.tfc.objects.blocks.blocktype.BlockRockVariantTFCF;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFCF;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.inventory.ingredient.IngredientFluidItem;
import net.dries007.tfc.objects.items.ItemPowder;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.ItemsTFCF;
import net.dries007.tfc.objects.items.rock.ItemFiredMudBrick;
import net.dries007.tfc.objects.items.rock.ItemUnfiredMudBrick;
import net.dries007.tfc.objects.recipes.CrackingRecipe;
import net.dries007.tfc.objects.recipes.DryingRecipe;
import net.dries007.tfc.objects.recipes.NutRecipe;
import net.dries007.tfc.objects.recipes.OvenRecipe;
import net.dries007.tfc.objects.recipes.PlanterRecipe;
import net.dries007.tfc.util.agriculture.CropTFCF;
import net.dries007.tfc.util.agriculture.FruitTree;
import net.dries007.tfc.util.agriculture.SeasonalTrees;
import net.dries007.tfcflorae.ConfigTFCF;
import net.dries007.tfcflorae.TFCFlorae;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFCF;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = TFCF)
public final class RecipesTFCF {

  @SuppressWarnings("rawtypes")
  @SubscribeEvent
  public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event) {
    if (TFCFlorae.FirmaLifeAdded) {
      for (SeasonalTrees fruitTreeTFCF : SeasonalTrees.values()) {
        String nameTFCF = fruitTreeTFCF.getName().toLowerCase();
        IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
        String[] regNames = {
          "wood/fruit_tree/door/" + nameTFCF,
          "wood/fruit_tree/fence/" + nameTFCF,
          "wood/fruit_tree/fence_gate/" + nameTFCF,
          "wood/fruit_tree/trapdoor/" + nameTFCF
        };
        for (String name : regNames) {
          IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
          if (recipe != null) {
            registry.remove(recipe.getRegistryName());
            TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
          }
        }
      }

      for (IFruitTree fruitTree : FruitTree.values()) {
        String nameTFC = fruitTree.getName().toLowerCase();
        IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
        String[] regNames = {
          "wood/fruit_tree/door/" + nameTFC,
          "wood/fruit_tree/fence/" + nameTFC,
          "wood/fruit_tree/fence_gate/" + nameTFC,
          "wood/fruit_tree/trapdoor/" + nameTFC
        };
        for (String name : regNames) {
          IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
          if (recipe != null) {
            registry.remove(recipe.getRegistryName());
            TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
          }
        }
      }
    }

    if (!TFCFlorae.FirmaLifeAdded) {
      for (SeasonalTrees fruitTreeTFCF : SeasonalTrees.values()) {
        String nameTFCF = fruitTreeTFCF.getName().toLowerCase();
        IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
        String[] regNames = {
          "wood/fruit_tree/firmalife/door/" + nameTFCF,
          "wood/fruit_tree/firmalife/fence/" + nameTFCF,
          "wood/fruit_tree/firmalife/fence_gate/" + nameTFCF,
          "wood/fruit_tree/firmalife/trapdoor/" + nameTFCF
        };
        for (String name : regNames) {
          IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
          if (recipe != null) {
            registry.remove(recipe.getRegistryName());
            TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
          }
        }
      }

      for (Tree wood : TFCRegistries.TREES.getValuesCollection()) {
        String nameTFCF = wood.getRegistryName().getPath().toLowerCase();
        IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
        String[] regNames = {
          "wood/fruit_tree/firmalife/door/" + nameTFCF,
          "wood/fruit_tree/firmalife/fence/" + nameTFCF,
          "wood/fruit_tree/firmalife/fence_gate/" + nameTFCF,
          "wood/fruit_tree/firmalife/trapdoor/" + nameTFCF
        };
        for (String name : regNames) {
          IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
          if (recipe != null) {
            registry.remove(recipe.getRegistryName());
            TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
          }
        }
      }

      for (IFruitTree fruitTree : FruitTree.values()) {
        String nameTFC = fruitTree.getName().toLowerCase();
        IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
        String[] regNames = {
          "wood/fruit_tree/firmalife/door/" + nameTFC,
          "wood/fruit_tree/firmalife/fence/" + nameTFC,
          "wood/fruit_tree/firmalife/fence_gate/" + nameTFC,
          "wood/fruit_tree/firmalife/trapdoor/" + nameTFC
        };
        for (String name : regNames) {
          IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
          if (recipe != null) {
            registry.remove(recipe.getRegistryName());
            TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
          }
        }
      }

      IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
      String[] regNames = {
        "food/flatbread_dough/amaranth",
        "food/flatbread_dough/buckwheat",
        "food/flatbread_dough/fonio",
        "food/flatbread_dough/millet",
        "food/flatbread_dough/quinoa",
        "food/flatbread_dough/spelt",
        "food/flatbread_dough/wild_rice",
        "food/dough_yeast/amaranth",
        "food/dough_yeast/buckwheat",
        "food/dough_yeast/fonio",
        "food/dough_yeast/millet",
        "food/dough_yeast/quinoa",
        "food/dough_yeast/spelt",
        "food/dough_yeast/wild_rice",
        "food/sandwich_slice/amaranth",
        "food/sandwich_slice/buckwheat",
        "food/sandwich_slice/fonio",
        "food/sandwich_slice/millet",
        "food/sandwich_slice/quinoa",
        "food/sandwich_slice/spelt",
        "food/sandwich_slice/wild_rice",
        "metal/unmold/mallet_head",
        "food/pinecone",
        "yeast"
      };
      for (String name : regNames) {
        IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
        if (recipe != null) {
          registry.remove(recipe.getRegistryName());
          TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
        }
      }
    }

    if (!ConfigTFCF.General.WORLD.enableAllCoarse) {
      for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
        IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
        String[] regNames = {
          "stone/tfcflorae/coarse_dirt/" + rock
        };
        for (String name : regNames) {
          IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
          if (recipe != null) {
            registry.remove(recipe.getRegistryName());
            TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
          }
        }
      }
      if (!(ConfigTFCF.General.WORLD.enableAllCoarse && ConfigTFCF.General.WORLD.enableAllSpecialSoil)) {
        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
          IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
          String[] regNames = {
            "stone/tfcflorae/coarse_humus/" + rock,
            "stone/tfcflorae/coarse_loam/" + rock,
            "stone/tfcflorae/coarse_loamy_sand/" + rock,
            "stone/tfcflorae/coarse_sandy_loam/" + rock,
            "stone/tfcflorae/coarse_silt/" + rock,
            "stone/tfcflorae/coarse_silt_loam/" + rock
          };
          for (String name : regNames) {
            IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
            if (recipe != null) {
              registry.remove(recipe.getRegistryName());
              TFCFlorae.logger.info("Removed crafting recipe tfcflorae:{}", name);
            }
          }
        }
      }
    }
  }

  @SuppressWarnings("rawtypes")
  @SubscribeEvent
  public static void onRegisterBarrelRecipeEvent(RegistryEvent.Register<BarrelRecipe> event) {
    IForgeRegistry<BarrelRecipe> r = event.getRegistry();

    // Remove recipes
    IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) TFCRegistries.BARREL;
    String[] regNames = {"sugar", "beer", "sake"};
    for (String name : regNames) {
      BarrelRecipe recipe = TFCRegistries.BARREL.getValue(new ResourceLocation("tfc", name));
      if (recipe != null) {
        modRegistry.remove(recipe.getRegistryName());
        TFCFlorae.logger.info("Removed barrel recipe tfc:{}", name);
      }
    }

    for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
      event.getRegistry().registerAll(
        new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 200), IIngredient.of(BlockRockVariant.get(rock, Rock.Type.RAW)), new FluidStack(FluidsCore.FRESH_WATER.get(), 50), new ItemStack(BlockRockVariantTFCF.get(rock, BlockTypesTFCF.RockTFCF.MOSSY_RAW), 1),
          8 * ICalendar.TICKS_IN_HOUR).setRegistryName(Mods.ModIDs.TFCF, "mossy_raw_" + rock.getRegistryName().getPath())
      );
    }

    r.registerAll(

      // Sugar
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 600), IIngredient.of("sugarcane", 5), null, new ItemStack(Items.SUGAR),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_sugar_cane"),

      // Base Potash Liquor
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 500), IIngredient.of("dustPotash"), new FluidStack(FluidsCore.BASE_POTASH_LIQUOR.get(), 500), ItemStack.EMPTY,
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("base_potash_liquor_from_potash"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 500), IIngredient.of("dustAsh"), new FluidStack(FluidsCore.BASE_POTASH_LIQUOR.get(), 500), ItemStack.EMPTY,
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("base_potash_liquor_from_ash"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 500), IIngredient.of("dustWood"), new FluidStack(FluidsCore.BASE_POTASH_LIQUOR.get(), 500), ItemStack.EMPTY,
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("base_potash_liquor_from_wood_dust"),

      // Cellulose Fibers
      new BarrelRecipe(IIngredient.of(FluidsCore.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("sugarcane"), new FluidStack(FluidsCore.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_sugarcane"),
      new BarrelRecipe(IIngredient.of(FluidsCore.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("pulp"), new FluidStack(FluidsCore.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_pulp"),
      new BarrelRecipe(IIngredient.of(FluidsCore.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("cropAgave"), new FluidStack(FluidsCore.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_agave_crop"),
      new BarrelRecipe(IIngredient.of(FluidsCore.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("cropFlax"), new FluidStack(FluidsCore.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_flax_crop"),
      new BarrelRecipe(IIngredient.of(FluidsCore.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("cropHemp"), new FluidStack(FluidsCore.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_hemp_crop"),
      new BarrelRecipe(IIngredient.of(FluidsCore.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of("pulpPapyrus"), new FluidStack(FluidsCore.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_papyrus_crop"),
      new BarrelRecipe(IIngredient.of(FluidsCore.BASE_POTASH_LIQUOR.get(), 150), IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.YUCCA))), new FluidStack(FluidsCore.WASTE.get(), 150), new ItemStack(ItemsTFCF.CELLULOSE_FIBERS),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cellulose_fibers_from_yucca_crop"),

      // Papyrus Fibers
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 600), IIngredient.of("pulpPapyrus", 3), null, new ItemStack(ItemsTFCF.PAPYRUS_FIBER),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("papyrus_fiber_from_papyrus"),

      // Fiber Processing
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("cropAgave"), null, new ItemStack(ItemsTFCF.SISAL_FIBER),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sisal_fiber"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("cropFlax"), null, new ItemStack(ItemsTFCF.FLAX_FIBER),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("flax_fiber"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("cropHemp"), null, new ItemStack(ItemsTFCF.HEMP_FIBER),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("hemp_fiber"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 300), IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.YUCCA))), null, new ItemStack(ItemsTFCF.YUCCA_FIBER),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("yucca_fiber"),

      // Fluid Production from paste

      // Olive
      new BarrelRecipe(IIngredient.of(FluidsCore.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsCore.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("olive_oil_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsCore.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsCore.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("olive_oil_silk"),
      new BarrelRecipe(IIngredient.of(FluidsCore.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsCore.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("olive_oil_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsCore.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsCore.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("olive_oil_linen"),
      new BarrelRecipe(IIngredient.of(FluidsCore.OLIVE_OIL_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsCore.OLIVE_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("olive_oil_hemp"),

      // Soybean
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 125), IIngredient.of("pasteSoybean"), new FluidStack(FluidsCore.SOYBEAN_WATER.get(), 125), ItemStack.EMPTY,
        2 * ICalendar.TICKS_IN_HOUR).setRegistryName("soybean_water"),

      new BarrelRecipe(IIngredient.of(FluidsCore.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsCore.SOY_MILK.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("soy_milk_jute"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsCore.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("soy_milk_silk"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsCore.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("soy_milk_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsCore.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("soy_milk_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsCore.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("soy_milk_linen"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SOYBEAN_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsCore.SOY_MILK.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("soy_milk_hemp"),

      new BarrelRecipeFluidMixing(IIngredient.of(FluidsCore.SOY_MILK.get(), 9), new IngredientFluidItem(FluidsCore.VINEGAR.get(), 1), new FluidStack(FluidsCore.MILK_VINEGAR.get(), 10), 0).setRegistryName("soy_milk_vinegar"),

      // Linseed
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 125), IIngredient.of("pasteLinseed"), new FluidStack(FluidsCore.LINSEED_WATER.get(), 125), ItemStack.EMPTY,
        2 * ICalendar.TICKS_IN_HOUR).setRegistryName("linseed_water"),

      new BarrelRecipe(IIngredient.of(FluidsCore.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsCore.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("linseed_oil_jute"),
      new BarrelRecipe(IIngredient.of(FluidsCore.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsCore.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("linseed_oil_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsCore.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsCore.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("linseed_oil_silk"),
      new BarrelRecipe(IIngredient.of(FluidsCore.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsCore.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("linseed_oil_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsCore.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsCore.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("linseed_oil_linen"),
      new BarrelRecipe(IIngredient.of(FluidsCore.LINSEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsCore.LINSEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("linseed_oil_hemp"),

      // Rape Seed
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 125), IIngredient.of("pasteRapeSeed"), new FluidStack(FluidsCore.RAPE_SEED_WATER.get(), 125), ItemStack.EMPTY,
        2 * ICalendar.TICKS_IN_HOUR).setRegistryName("rape_seed_water"),

      new BarrelRecipe(IIngredient.of(FluidsCore.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsCore.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("rape_seed_oil_jute"),
      new BarrelRecipe(IIngredient.of(FluidsCore.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsCore.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("rape_seed_oil_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsCore.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsCore.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("rape_seed_oil_silk"),
      new BarrelRecipe(IIngredient.of(FluidsCore.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsCore.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("rape_seed_oil_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsCore.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsCore.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("rape_seed_oil_linen"),
      new BarrelRecipe(IIngredient.of(FluidsCore.RAPE_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsCore.RAPE_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("rape_seed_oil_hemp"),

      // Sunflower Seed
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 125), IIngredient.of("pasteSunflowerSeed"), new FluidStack(FluidsCore.SUNFLOWER_SEED_WATER.get(), 125), ItemStack.EMPTY,
        2 * ICalendar.TICKS_IN_HOUR).setRegistryName("sunflower_seed_water"),

      new BarrelRecipe(IIngredient.of(FluidsCore.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsCore.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("sunflower_seed_oil_jute"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsCore.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("sunflower_seed_oil_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsCore.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("sunflower_seed_oil_silk"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsCore.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("sunflower_seed_oil_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsCore.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("sunflower_seed_oil_linen"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SUNFLOWER_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsCore.SUNFLOWER_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("sunflower_seed_oil_hemp"),

      // Opium Poppy Seed
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 125), IIngredient.of("pasteOpiumPoppySeed"), new FluidStack(FluidsCore.OPIUM_POPPY_SEED_WATER.get(), 125), ItemStack.EMPTY,
        2 * ICalendar.TICKS_IN_HOUR).setRegistryName("opium_poppy_seed_water"),

      new BarrelRecipe(IIngredient.of(FluidsCore.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsCore.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("opium_poppy_seed_oil_jute"),
      new BarrelRecipe(IIngredient.of(FluidsCore.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsCore.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("opium_poppy_seed_oil_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsCore.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsCore.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("opium_poppy_seed_oil_silk"),
      new BarrelRecipe(IIngredient.of(FluidsCore.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsCore.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("opium_poppy_seed_oil_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsCore.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsCore.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("opium_poppy_seed_oil_linen"),
      new BarrelRecipe(IIngredient.of(FluidsCore.OPIUM_POPPY_SEED_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsCore.OPIUM_POPPY_SEED_OIL.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("opium_poppy_seed_oil_hemp"),

      // Sugar Beet Water
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 125), IIngredient.of("mashedSugarBeet"), new FluidStack(FluidsCore.SUGAR_BEET_WATER.get(), 125), ItemStack.EMPTY,
        2 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_beet_water"),

      new BarrelRecipe(IIngredient.of(FluidsCore.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidsCore.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("sugar_water_jute"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.SISAL_NET), new FluidStack(FluidsCore.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SISAL_NET), 0).setRegistryName("sugar_water_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.SILK_NET), new FluidStack(FluidsCore.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_SILK_NET), 0).setRegistryName("sugar_water_silk"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.COTTON_NET), new FluidStack(FluidsCore.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_COTTON_NET), 0).setRegistryName("sugar_water_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.LINEN_NET), new FluidStack(FluidsCore.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_LINEN_NET), 0).setRegistryName("sugar_water_linen"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SUGAR_BEET_WATER.get(), 125), IIngredient.of(ItemsTFCF.HEMP_NET), new FluidStack(FluidsCore.SUGAR_WATER.get(), 25), new ItemStack(ItemsTFCF.DIRTY_HEMP_NET), 0).setRegistryName("sugar_water_hemp"),

      // Dirty Nets
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_SISAL_NET), null, new ItemStack(ItemsTFCF.SISAL_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_sisal"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_SILK_NET), null, new ItemStack(ItemsTFCF.SILK_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_silk"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_COTTON_NET), null, new ItemStack(ItemsTFCF.COTTON_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_cotton"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_LINEN_NET), null, new ItemStack(ItemsTFCF.LINEN_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_linen"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 125), IIngredient.of(ItemsTFCF.DIRTY_HEMP_NET), null, new ItemStack(ItemsTFCF.HEMP_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net_hemp"),

      // Sugary Fluids
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 125), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.SUGAR_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("sugar_water_from_sugar_fresh"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 125), IIngredient.of("dropHoney"), new FluidStack(FluidsCore.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_drop_honey_fresh"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 125), IIngredient.of("itemHoney"), new FluidStack(FluidsCore.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_item_honey_fresh"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 125), IIngredient.of("rawHoney"), new FluidStack(FluidsCore.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_raw_honey_fresh"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 125), IIngredient.of(ItemsFL.HONEYCOMB), new FluidStack(FluidsCore.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_fl_raw_honey_fresh"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 125), IIngredient.of("materialHoneycomb"), new FluidStack(FluidsCore.HONEY_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("honey_water_from_material_honeycomb_fresh"),

      //new BarrelRecipe(IIngredient.of(FluidsTFCF.SUGAR_WATER.get(), 125), null, null, new ItemStack(Items.SUGAR), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_sugar_water"),
      //new BarrelRecipe(IIngredient.of(FluidsTFCF.HONEY_WATER.get(), 125), null, null, new ItemStack(Items.SUGAR), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar_from_honey_water"),

      // Dyes
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 1000), IIngredient.of("cropAgave"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN)
        .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("green_dye_agave"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 1000), IIngredient.of("cropIndigo"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE)
        .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_indigo"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 1000), IIngredient.of("cropMadder"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.RED)
        .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("red_dye_madder"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 1000), IIngredient.of("cropWeld"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.YELLOW)
        .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("yellow_dye_weld"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 1000), IIngredient.of("cropWoad"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE)
        .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_woad"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 1000), IIngredient.of("cropRape"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE)
        .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("blue_dye_rape"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 1000), IIngredient.of("boneCharred"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK)
        .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("black_dye_charred_bones"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 1000), IIngredient.of("dustBlackPearl"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK)
        .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("black_dye_black_pearl_powder"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 1000), IIngredient.of("dustPearl"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK)
        .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("pink_dye_pearl_powder"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 1000), IIngredient.of("dustLogwood"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE)
        .get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName("purple_dye_logwood_powder"),

      // Teas
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 200), IIngredient.of("driedWhiteTea", 2), new FluidStack(FluidsCore.WHITE_TEA.get(), 200), ItemStack.EMPTY,
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("white_tea"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 200), IIngredient.of("driedGreenTea", 2), new FluidStack(FluidsCore.GREEN_TEA.get(), 200), ItemStack.EMPTY,
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("green_tea"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 200), IIngredient.of("driedBlackTea", 2), new FluidStack(FluidsCore.BLACK_TEA.get(), 200), ItemStack.EMPTY,
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("black_tea"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 200), IIngredient.of("driedChamomile", 2), new FluidStack(FluidsCore.CHAMOMILE_TEA.get(), 200), ItemStack.EMPTY,
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("chamomile_tea"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 200), IIngredient.of("driedDandelion", 2), new FluidStack(FluidsCore.DANDELION_TEA.get(), 200), ItemStack.EMPTY,
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("dandelion_tea"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 200), IIngredient.of("driedLabradorTea", 2), new FluidStack(FluidsCore.LABRADOR_TEA.get(), 200), ItemStack.EMPTY,
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("labrador_tea"),

      // Coffee
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 200), IIngredient.of("roastedCoffee", 2), new FluidStack(FluidsCore.COFFEE.get(), 200), ItemStack.EMPTY,
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("coffee"),

      // Firma Cola
      new BarrelRecipe(IIngredient.of(FluidsCore.SUGAR_WATER.get(), 250), IIngredient.of("blendFirmaCola"), new FluidStack(FluidsCore.FIRMA_COLA.get(), 1000), ItemStack.EMPTY,
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("firma_cola"),

      // Wort
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 500), IIngredient.of("hops"), new FluidStack(FluidsCore.WORT.get(), 500), ItemStack.EMPTY,
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("wort"),

      // Fermented Alcohol
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_AGAVE.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.AGAVE_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("agave_wine"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_BANANA.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.BANANA_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("banana_wine"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_CHERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.CHERRY_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cherry_wine"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_GREEN_GRAPE.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.WHITE_WINE.get(), 500), new ItemStack(ItemsTFCF.POMACE),
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("white_wine"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_JUNIPER.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.JUNIPER_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("juniper_wine"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_LEMON.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.LEMON_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("lemon_wine"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HONEY_WATER.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.MEAD.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("mead"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_ORANGE.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.ORANGE_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("orange_wine"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_PAPAYA.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.PAPAYA_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("papaya_wine"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_PEACH.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.PEACH_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("peach_wine"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_PEAR.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.PEAR_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("pear_wine"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_PLUM.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.PLUM_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("plum_wine"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_PURPLE_GRAPE.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.RED_WINE.get(), 500), new ItemStack(ItemsTFCF.POMACE),
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("red_wine"),
      new BarrelRecipe(IIngredient.of(FluidsCore.RICE_WATER.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.SAKE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("sake_rice_water"),

      // Berry Wine
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_BLACKBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.BERRY_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_blackberry"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_BLUEBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.BERRY_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_blueberry"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_BUNCH_BERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.BERRY_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_bunch_berry"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_CLOUD_BERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.BERRY_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_cloud_berry"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_CRANBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.BERRY_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_cranberry"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_ELDERBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.BERRY_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_elderberry"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_GOOSEBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.BERRY_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_gooseberry"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_RASPBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.BERRY_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_raspberry"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_SNOW_BERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.BERRY_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_snow_berry"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_STRAWBERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.BERRY_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_strawberry"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUICE_WINTERGREEN_BERRY.get(), 500), IIngredient.of("yeast"), new FluidStack(FluidsCore.BERRY_WINE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_wine_wintergreen_berry"),

      // "Distilled" Alcohol
      new BarrelRecipe(IIngredient.of(FluidsCore.AGAVE_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.TEQUILA.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("tequila"),
      new BarrelRecipe(IIngredient.of(FluidsCore.BANANA_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.BANANA_BRANDY.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("banana_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsCore.BERRY_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.BERRY_BRANDY.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("berry_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsCore.CIDER.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.CALVADOS.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("calvados"),
      new BarrelRecipe(IIngredient.of(FluidsCore.CHERRY_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.CHERRY_BRANDY.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cherry_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsCore.JUNIPER_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.GIN.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("gin"),
      new BarrelRecipe(IIngredient.of(FluidsCore.LEMON_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.LEMON_BRANDY.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("lemon_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsCore.ORANGE_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.ORANGE_BRANDY.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("orange_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsCore.PAPAYA_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.PAPAYA_BRANDY.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("papaya_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsCore.PEACH_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.PEACH_BRANDY.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("peach_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsCore.PEAR_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.PEAR_BRANDY.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("pear_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsCore.PLUM_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.PLUM_BRANDY.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("plum_brandy"),
      new BarrelRecipe(IIngredient.of(FluidsCore.RED_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.BRANDY.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("brandy"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SAKE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.SHOCHU.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("shochu"),
      new BarrelRecipe(IIngredient.of(FluidsCore.WHITE_WINE.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsCore.COGNAC.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cognac"),
      new BarrelRecipe(IIngredient.of(FluidsCore.VODKA.get(), 500), IIngredient.of("pomace"), new FluidStack(FluidsCore.GRAPPA.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("grappa"),

      // Malted Grain
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("grainBarley"), null, new ItemStack(ItemsTFCF.MALT_BARLEY),
        4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_barley"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("grainMaize"), null, new ItemStack(ItemsTFCF.MALT_CORN),
        4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_corn"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("grainRye"), null, new ItemStack(ItemsTFCF.MALT_RYE),
        4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_rye"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("grainRice"), null, new ItemStack(ItemsTFCF.MALT_RICE),
        4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_rice"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("grainWheat"), null, new ItemStack(ItemsTFCF.MALT_WHEAT),
        4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_wheat"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("grainAmaranth"), null, new ItemStack(ItemsTFCF.MALT_AMARANTH),
        4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_amaranth"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("grainBuckwheat"), null, new ItemStack(ItemsTFCF.MALT_BUCKWHEAT),
        4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_buckwheat"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("grainFonio"), null, new ItemStack(ItemsTFCF.MALT_FONIO),
        4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_fonio"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("grainMillet"), null, new ItemStack(ItemsTFCF.MALT_MILLET),
        4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_millet"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("grainQuinoa"), null, new ItemStack(ItemsTFCF.MALT_QUINOA),
        4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_quinoa"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 200), IIngredient.of("grainSpelt"), null, new ItemStack(ItemsTFCF.MALT_SPELT),
        4 * ICalendar.TICKS_IN_HOUR).setRegistryName("malt_spelt"),

      // Beer
      new BarrelRecipe(IIngredient.of(FluidsCore.WORT.get(), 500), IIngredient.of("maltBarley"), new FluidStack(FluidsCore.BEER_BARLEY.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_barley"),
      new BarrelRecipe(IIngredient.of(FluidsCore.WORT.get(), 500), IIngredient.of("maltCorn"), new FluidStack(FluidsCore.BEER_CORN.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_corn"),
      new BarrelRecipe(IIngredient.of(FluidsCore.WORT.get(), 500), IIngredient.of("maltRye"), new FluidStack(FluidsCore.BEER_RYE.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_rye"),
      new BarrelRecipe(IIngredient.of(FluidsCore.WORT.get(), 500), IIngredient.of("maltWheat"), new FluidStack(FluidsCore.BEER_WHEAT.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_wheat"),
      new BarrelRecipe(IIngredient.of(FluidsCore.WORT.get(), 500), IIngredient.of("maltAmaranth"), new FluidStack(FluidsCore.BEER_AMARANTH.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_amaranth"),
      new BarrelRecipe(IIngredient.of(FluidsCore.WORT.get(), 500), IIngredient.of("maltBuckwheat"), new FluidStack(FluidsCore.BEER_BUCKWHEAT.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_buckwheat"),
      new BarrelRecipe(IIngredient.of(FluidsCore.WORT.get(), 500), IIngredient.of("maltFonio"), new FluidStack(FluidsCore.BEER_FONIO.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_fonio"),
      new BarrelRecipe(IIngredient.of(FluidsCore.WORT.get(), 500), IIngredient.of("maltMillet"), new FluidStack(FluidsCore.BEER_MILLET.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_millet"),
      new BarrelRecipe(IIngredient.of(FluidsCore.WORT.get(), 500), IIngredient.of("maltQuinoa"), new FluidStack(FluidsCore.BEER_QUINOA.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_quinoa"),
      new BarrelRecipe(IIngredient.of(FluidsCore.WORT.get(), 500), IIngredient.of("maltSpelt"), new FluidStack(FluidsCore.BEER_SPELT.get(), 500), ItemStack.EMPTY,
        72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer_spelt"),

      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 500), IIngredient.of("rice"), new FluidStack(FluidsCore.RICE_WATER.get(), 500), ItemStack.EMPTY,
        2 * ICalendar.TICKS_IN_HOUR).setRegistryName("rice_water"),
      //new BarrelRecipe(IIngredient.of(FluidsTFC.FRESH_WATER.get(), 500), IIngredient.of("wildRice"), new FluidStack(FluidsTFCF.RICE_WATER.get(), 500), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("wild_rice_water"),

      //Special Clay Washing
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 100), IIngredient.of("clayEarthenware"), null, new ItemStack(Items.CLAY_BALL), 0).setRegistryName("earthenware_clay_wash"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 100), IIngredient.of("clayKaolinite"), null, new ItemStack(Items.CLAY_BALL), 0).setRegistryName("kaolinite_clay_wash"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 100), IIngredient.of("clayStoneware"), null, new ItemStack(Items.CLAY_BALL), 0).setRegistryName("stoneware_clay_wash"),

      // Cooling
      new BarrelRecipeTemperature(IIngredient.of(FluidsCore.DISTILLED_WATER.get(), 1), 50).setRegistryName("distilled_water_cooling")
    );
  }

  @SubscribeEvent
  public static void onRegisterKnappingRecipeEvent(RegistryEvent.Register<KnappingRecipe> event) {
    KnappingRecipe r = new KnappingRecipeStone(KnappingType.MUD, rockIn -> new ItemStack(ItemUnfiredMudBrick.get(rockIn), 3), "XXXXX", "     ", "XXXXX", "     ", "XXXXX");
    event.getRegistry().register(r.setRegistryName(Mods.ModIDs.TFCF, "knapping_mud_brick"));

    event.getRegistry().registerAll(

      // Flint Tool Heads
      new KnappingRecipeSimple(KnappingType.FLINT, true, new ItemStack(ItemsTFCF.FLINT_AXE_HEAD, 1), " X   ", "XXXX ", "XXXXX", "XXXX ", " X   ").setRegistryName(Mods.ModIDs.TFCF, "flint_axe_head"),

      new KnappingRecipeSimple(KnappingType.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HAMMER_HEAD, 1), "     ", "XXXXX", "XXXXX", "  X  ", "     ").setRegistryName(Mods.ModIDs.TFCF, "flint_hammer_head"),

      //new KnappingRecipeSimple(KnappingTypes.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HOE_HEAD, 1), "XXXXX", "   XX").setRegistryName(TFCFlorae.MODID, "flint_hoe_head_1"),
      new KnappingRecipeSimple(KnappingType.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HOE_HEAD, 2), "XXXXX", "XX   ", "     ", "XXXXX", "XX   ").setRegistryName(Mods.ModIDs.TFCF, "flint_hoe_head_2"),
      new KnappingRecipeSimple(KnappingType.FLINT, true, new ItemStack(ItemsTFCF.FLINT_HOE_HEAD, 2), "XXXXX", "XX   ", "     ", "XXXXX", "   XX").setRegistryName(Mods.ModIDs.TFCF, "flint_hoe_head_3"),

      new KnappingRecipeSimple(KnappingType.FLINT, true, new ItemStack(ItemsTFCF.FLINT_JAVELIN_HEAD, 1), "XXX  ", "XXXX ", "XXXXX", " XXX ", "  X  ").setRegistryName(Mods.ModIDs.TFCF, "flint_javelin_head"),

      new KnappingRecipeSimple(KnappingType.FLINT, true, new ItemStack(ItemsTFCF.FLINT_SHOVEL_HEAD, 1), " XXX ", " XXX ", " XXX ", " XXX ", "  X  ").setRegistryName(Mods.ModIDs.TFCF, "flint_shovel_head"),

      new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFCF.UNFIRED_URN), "XX XX", "X   X", "X   X", "X   X", "XXXXX").setRegistryName(Mods.ModIDs.TFCF, "clay_urn"),

      // Containers
      new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(ItemsTFCF.LEATHER_BAG_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("leather_bag_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(ItemsTFCF.LEATHER_BAG_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("leather_bag_pieces_vertical"),
      new KnappingRecipeSimple(KnappingType.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("burlap_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingType.BURLAP_CLOTH, true, new ItemStack(ItemsTFCF.BURLAP_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("burlap_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingType.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("wool_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingType.WOOL_CLOTH, true, new ItemStack(ItemsTFCF.WOOL_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("wool_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingType.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("silk_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingType.SILK_CLOTH, true, new ItemStack(ItemsTFCF.SILK_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("silk_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingType.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("sisal_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingType.SISAL_CLOTH, true, new ItemStack(ItemsTFCF.SISAL_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("sisal_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingType.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("cotton_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingType.COTTON_CLOTH, true, new ItemStack(ItemsTFCF.COTTON_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("cotton_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingType.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("linen_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingType.LINEN_CLOTH, true, new ItemStack(ItemsTFCF.LINEN_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("linen_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingType.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("hemp_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingType.HEMP_CLOTH, true, new ItemStack(ItemsTFCF.HEMP_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("hemp_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingType.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_SACK_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("yucca_sack_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingType.YUCCA_CANVAS, true, new ItemStack(ItemsTFCF.YUCCA_SACK_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("yucca_sack_pieces_vertical"),
      new KnappingRecipeSimple(KnappingType.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_BAG_PIECE, 2), " XXX ", " XXX ", "     ", " XXX ", " XXX ").setRegistryName("pineapple_leather_bag_pieces_horizontal"),
      new KnappingRecipeSimple(KnappingType.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFCF.PINEAPPLE_LEATHER_BAG_PIECE, 2), "     ", "XX XX", "XX XX", "XX XX", "     ").setRegistryName("pineapple_leather_bag_pieces_vertical"),

      // Pineapple Leather
      new KnappingRecipeSimple(KnappingType.PINEAPPLE_LEATHER, true, new ItemStack(Items.SADDLE), "  X  ", "XXXXX", "XXXXX", "XXXXX", "  X  ").setRegistryName("pineapple_leather_saddle"),
      new KnappingRecipeSimple(KnappingType.PINEAPPLE_LEATHER, true, new ItemStack(ItemsTFC.QUIVER), " XXXX", "X XXX", "X XXX", "X XXX", " XXXX").setRegistryName("pineapple_leather_quiver")

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
      HeatRecipe recipe = TFCRegistries.HEAT.getValue(new ResourceLocation("tfc", name));
      if (recipe != null) {
        modRegistry.remove(recipe.getRegistryName());
        TFCFlorae.logger.info("Removed heating recipe tfc:{}", name);
      }
    }

    // Mud Pottery
    for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
      ItemFiredMudBrick firedMudBrick = ItemFiredMudBrick.get(ItemUnfiredMudBrick.get(rock));

      HeatRecipe recipe = new HeatRecipeSimple(IIngredient.of(ItemUnfiredMudBrick.get(rock)), new ItemStack(firedMudBrick), 600f, Metal.Tier.TIER_I);
      event.getRegistry().register(recipe.setRegistryName(rock.getRegistryName().getPath().toLowerCase() + "_unfired_mud_brick"));

      // Fired Pottery - doesn't burn up
      recipe = new HeatRecipeSimple(IIngredient.of(firedMudBrick), new ItemStack(firedMudBrick), 1599f, Metal.Tier.TIER_I);
      event.getRegistry().register(recipe.setRegistryName(rock.getRegistryName().getPath().toLowerCase() + "_fired_mud_brick"));
    }

    // Bread
    if (!ConfigFL.General.COMPAT.removeTFC) {
      r.registerAll(

        new HeatRecipeSimple(IIngredient.of(ItemsTFCF.AMARANTH_DOUGH), new ItemStack(ItemsTFCF.AMARANTH_BREAD), 200, 480).setRegistryName("amaranth_bread"),
        new HeatRecipeSimple(IIngredient.of(ItemsTFCF.BUCKWHEAT_DOUGH), new ItemStack(ItemsTFCF.BUCKWHEAT_BREAD), 200, 480).setRegistryName("buckwheat_bread"),
        new HeatRecipeSimple(IIngredient.of(ItemsTFCF.FONIO_DOUGH), new ItemStack(ItemsTFCF.FONIO_BREAD), 200, 480).setRegistryName("fonio_bread"),
        new HeatRecipeSimple(IIngredient.of(ItemsTFCF.MILLET_DOUGH), new ItemStack(ItemsTFCF.MILLET_BREAD), 200, 480).setRegistryName("millet_bread"),
        new HeatRecipeSimple(IIngredient.of(ItemsTFCF.QUINOA_DOUGH), new ItemStack(ItemsTFCF.QUINOA_BREAD), 200, 480).setRegistryName("quinoa_bread"),
        new HeatRecipeSimple(IIngredient.of(ItemsTFCF.SPELT_DOUGH), new ItemStack(ItemsTFCF.SPELT_BREAD), 200, 480).setRegistryName("spelt_bread")
      );
    }

    // Standard / Simple recipes
    r.registerAll(

      new HeatRecipeSimple(IIngredient.of(ItemsTFCF.UNFIRED_URN), new ItemStack(BlocksTFCF.FIRED_URN), 1599f, Metal.Tier.TIER_I).setRegistryName("unfired_urn"),
      new HeatRecipeSimple(IIngredient.of(BlocksTFCF.FIRED_URN), new ItemStack(BlocksTFCF.FIRED_URN), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_urn"),

      // Bread
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.HASH_MUFFIN), 480).setRegistryName("burned_hash_muffin"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.AMARANTH_BREAD), 480).setRegistryName("burned_barley_bread"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.BUCKWHEAT_BREAD), 480).setRegistryName("burned_cornbread"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.FONIO_BREAD), 480).setRegistryName("burned_oat_bread"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.MILLET_BREAD), 480).setRegistryName("burned_rice_bread"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.QUINOA_BREAD), 480).setRegistryName("burned_rye_bread"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.SPELT_BREAD), 480).setRegistryName("burned_wheat_bread"),

      // Epiphytes
      new HeatRecipeSimple(IIngredient.of("epiphyteArtistsConk"), new ItemStack(ItemsTFCF.ROASTED_ARTISTS_CONK), 200, 480).setRegistryName("roasted_artists_conk"),
      new HeatRecipeSimple(IIngredient.of("epiphyteSulphurShelf"), new ItemStack(ItemsTFCF.ROASTED_SULPHUR_SHELF), 200, 480).setRegistryName("roasted_sulphur_shelf"),
      new HeatRecipeSimple(IIngredient.of("epiphyteTurkeyTail"), new ItemStack(ItemsTFCF.ROASTED_TURKEY_TAIL), 200, 480).setRegistryName("roasted_turkey_tail"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_ARTISTS_CONK), 480).setRegistryName("burned_artists_conk"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_SULPHUR_SHELF), 480).setRegistryName("burned_sulphur_shelf"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_TURKEY_TAIL), 480).setRegistryName("burned_turkey_tail"),

      // Mushrooms
      new HeatRecipeSimple(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.PORCINI))), new ItemStack(ItemsTFCF.ROASTED_PORCINI), 200, 480).setRegistryName("roasted_porcini_specific"),
      new HeatRecipeSimple(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.AMANITA))), new ItemStack(ItemsTFCF.ROASTED_AMANITA), 200, 480).setRegistryName("roasted_amanita_specific"),
      new HeatRecipeSimple(IIngredient.of("mushroomPorcini"), new ItemStack(ItemsTFCF.ROASTED_PORCINI), 200, 480).setRegistryName("roasted_porcini"),
      new HeatRecipeSimple(IIngredient.of("mushroomAmanita"), new ItemStack(ItemsTFCF.ROASTED_AMANITA), 200, 480).setRegistryName("roasted_amanita"),
      new HeatRecipeSimple(IIngredient.of("mushroomBlackPowderpuff"), new ItemStack(ItemsTFCF.ROASTED_BLACK_POWDERPUFF), 200, 480).setRegistryName("roasted_black_powderpuff"),
      new HeatRecipeSimple(IIngredient.of("mushroomChanterelle"), new ItemStack(ItemsTFCF.ROASTED_CHANTERELLE), 200, 480).setRegistryName("roasted_chanterelle"),
      new HeatRecipeSimple(IIngredient.of("mushroomDeathCap"), new ItemStack(ItemsTFCF.ROASTED_DEATH_CAP), 200, 480).setRegistryName("roasted_death_cap"),
      new HeatRecipeSimple(IIngredient.of("mushroomGiantClub"), new ItemStack(ItemsTFCF.ROASTED_GIANT_CLUB), 200, 480).setRegistryName("roasted_giant_club"),
      new HeatRecipeSimple(IIngredient.of("mushroomParasol"), new ItemStack(ItemsTFCF.ROASTED_PARASOL_MUSHROOM), 200, 480).setRegistryName("roasted_parasol_mushroom"),
      new HeatRecipeSimple(IIngredient.of("mushroomStinkhorn"), new ItemStack(ItemsTFCF.ROASTED_STINKHORN), 200, 480).setRegistryName("roasted_stinkhorn"),
      new HeatRecipeSimple(IIngredient.of("mushroomWeepingMilkCap"), new ItemStack(ItemsTFCF.ROASTED_WEEPING_MILK_CAP), 200, 480).setRegistryName("roasted_weeping_milk_cap"),
      new HeatRecipeSimple(IIngredient.of("mushroomWoodBlewit"), new ItemStack(ItemsTFCF.ROASTED_WOOD_BLEWIT), 200, 480).setRegistryName("roasted_wood_blewit"),
      new HeatRecipeSimple(IIngredient.of("mushroomWoollyGomphus"), new ItemStack(ItemsTFCF.ROASTED_WOOLLY_GOMPHUS), 200, 480).setRegistryName("roasted_woolly_gomphus"),

      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PORCINI), 480).setRegistryName("burned_porcini"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_AMANITA), 480).setRegistryName("burned_amanita"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BLACK_POWDERPUFF), 480).setRegistryName("burned_black_powderpuff"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_CHANTERELLE), 480).setRegistryName("burned_chanterelle"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_DEATH_CAP), 480).setRegistryName("burned_death_cap"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_GIANT_CLUB), 480).setRegistryName("burned_giant_club"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PARASOL_MUSHROOM), 480).setRegistryName("burned_parasol_mushroom"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_STINKHORN), 480).setRegistryName("burned_stinkhorn"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WEEPING_MILK_CAP), 480).setRegistryName("burned_weeping_milk_cap"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WOOD_BLEWIT), 480).setRegistryName("burned_wood_blewit"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WOOLLY_GOMPHUS), 480).setRegistryName("burned_woolly_gomphus"),

      // Food
      new HeatRecipeSimple(IIngredient.of("rawEel"), new ItemStack(ItemsTFCF.COOKED_EEL), 200, 480).setRegistryName("cooked_eel"),
      new HeatRecipeSimple(IIngredient.of("rawCrab"), new ItemStack(ItemsTFCF.COOKED_CRAB), 200, 480).setRegistryName("cooked_crab"),
      new HeatRecipeSimple(IIngredient.of("rawClam"), new ItemStack(ItemsTFCF.COOKED_CLAM), 200, 480).setRegistryName("cooked_clam"),
      new HeatRecipeSimple(IIngredient.of("rawScallop"), new ItemStack(ItemsTFCF.COOKED_SCALLOP), 200, 480).setRegistryName("cooked_scallop"),
      new HeatRecipeSimple(IIngredient.of("rawStarfish"), new ItemStack(ItemsTFCF.COOKED_STARFISH), 200, 480).setRegistryName("cooked_starfish"),
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
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BEECHNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BEECHNUT_NUT), 200, 480).setRegistryName("roasted_beechnut"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BLACK_WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BLACK_WALNUT_NUT), 200, 480).setRegistryName("roasted_black_walnut"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BUTTERNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_BUTTERNUT_NUT), 200, 480).setRegistryName("roasted_butternut"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.GINKGO_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_GINKGO_NUT_NUT), 200, 480).setRegistryName("roasted_ginkgo_nut"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.HAZELNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HAZELNUT_NUT), 200, 480).setRegistryName("roasted_hazelnut"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.WALNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_WALNUT_NUT), 200, 480).setRegistryName("roasted_walnut"),

      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BEECHNUT_NUT), 480).setRegistryName("burned_beechnut"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BLACK_WALNUT_NUT), 480).setRegistryName("burned_black_walnut"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_BUTTERNUT_NUT), 480).setRegistryName("burned_butternut"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_GINKGO_NUT_NUT), 480).setRegistryName("burned_ginkgo_nut"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_HAZELNUT_NUT), 480).setRegistryName("burned_hazelnut"),
      HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_WALNUT_NUT), 480).setRegistryName("burned_walnut"),

      // Kaolinite Clay
      new HeatRecipeSimple(IIngredient.of("clayKaolinite"), new ItemStack(ItemPowder.get(Powder.KAOLINITE)), 200).setRegistryName("kaolinite_clay"),
      new HeatRecipeSimple(IIngredient.of(ItemPowder.get(Powder.KAOLINITE)), new ItemStack(ItemPowder.get(Powder.KAOLINITE)), 1599f, Metal.Tier.TIER_I).setRegistryName("burnt_kaolinite_clay"),

      // Torches
      new HeatRecipeSimple(IIngredient.of("twig"), new ItemStack(Blocks.TORCH, 6), 50).setRegistryName("torch_twig"),
      new HeatRecipeSimple(IIngredient.of("driftwood"), new ItemStack(Blocks.TORCH, 12), 60).setRegistryName("torch_driftwood"),

      // Ash
      new HeatRecipeSimple(IIngredient.of("straw"), new ItemStack(ItemsCore.WOOD_ASH.get(), 1), 350, 750).setRegistryName("straw_ash"),
      new HeatRecipeSimple(IIngredient.of("twig"), new ItemStack(ItemsCore.WOOD_ASH.get(), 2), 350, 750).setRegistryName("twig_ash"),
      new HeatRecipeSimple(IIngredient.of("torch"), new ItemStack(ItemsCore.WOOD_ASH.get(), 2), 350, 750).setRegistryName("torch_ash_1"),
      new HeatRecipeSimple(IIngredient.of(Blocks.TORCH), new ItemStack(ItemsCore.WOOD_ASH.get(), 2), 350, 750).setRegistryName("torch_ash_2"),

      // Charred Bones
      new HeatRecipeSimple(IIngredient.of("bone"), new ItemStack(ItemsTFCF.CHARRED_BONES), 425, 850).setRegistryName("charred_bones_heat"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CHARRED_BONES)), new ItemStack(ItemsTFCF.CHARRED_BONES), 1599f, Metal.Tier.TIER_I).setRegistryName("burnt_charred_bones")
    );

    if (!TFCFlorae.FirmaLifeAdded) {
      r.registerAll(

        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.ACORN_NUT)), new ItemStack(ItemsTFCF.ROASTED_ACORN_NUT), 200, 480).setRegistryName("roasted_acorn"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CHESTNUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_CHESTNUT_NUT), 200, 480).setRegistryName("roasted_chestnut"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.HICKORY_NUT_NUT)), new ItemStack(ItemsTFCF.ROASTED_HICKORY_NUT_NUT), 200, 480).setRegistryName("roasted_hickory_nut"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PECAN_NUT)), new ItemStack(ItemsTFCF.ROASTED_PECAN_NUT), 200, 480).setRegistryName("roasted_pecan"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PINE_NUT)), new ItemStack(ItemsTFCF.ROASTED_PINE_NUT), 200, 480).setRegistryName("roasted_pine_nut"),

        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_ACORN_NUT), 480).setRegistryName("burned_acorn"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_CHESTNUT_NUT), 480).setRegistryName("burned_chestnut"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_HICKORY_NUT_NUT), 480).setRegistryName("burned_hickory_nut"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PECAN_NUT), 480).setRegistryName("burned_pecan"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_PINE_NUT), 480).setRegistryName("burned_pine_nut"),

        // Food Roasting/Drying
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.BLACK_TEA)), new ItemStack(ItemsTFCF.DRIED_BLACK_TEA), 200, 480).setRegistryName("dried_black_tea"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.GREEN_TEA)), new ItemStack(ItemsTFCF.DRIED_GREEN_TEA), 200, 480).setRegistryName("dried_green_tea"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.WHITE_TEA)), new ItemStack(ItemsTFCF.DRIED_WHITE_TEA), 200, 480).setRegistryName("dried_white_tea"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CANNABIS_BUD)), new ItemStack(ItemsTFCF.DRIED_CANNABIS_BUD), 200, 480).setRegistryName("dried_cannabis_bud"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CANNABIS_LEAF)), new ItemStack(ItemsTFCF.DRIED_CANNABIS_LEAF), 200, 480).setRegistryName("dried_cannabis_leaf"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.COCA_LEAF)), new ItemStack(ItemsTFCF.DRIED_COCA_LEAF), 200, 480).setRegistryName("dried_coca_leaf"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.OPIUM_POPPY_BULB)), new ItemStack(ItemsTFCF.DRIED_OPIUM_POPPY_BULB), 200, 480).setRegistryName("dried_opium_poppy_bulb"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.PEYOTE)), new ItemStack(ItemsTFCF.DRIED_PEYOTE), 200, 480).setRegistryName("dried_peyote"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.TOBACCO_LEAF)), new ItemStack(ItemsTFCF.DRIED_TOBACCO_LEAF), 200, 480).setRegistryName("dried_tobacco_leaf"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.DRIED_COFFEA_CHERRIES)), new ItemStack(ItemsTFCF.ROASTED_COFFEE_BEANS), 200, 480).setRegistryName("roasted_coffea_cherries_firepit"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.CHAMOMILE_HEAD)), new ItemStack(ItemsTFCF.DRIED_CHAMOMILE_HEAD), 200, 480).setRegistryName("roasted_chamomile_head"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.DANDELION_HEAD)), new ItemStack(ItemsTFCF.DRIED_DANDELION_HEAD), 200, 480).setRegistryName("roasted_dandelion_head"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.LABRADOR_TEA_HEAD)), new ItemStack(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), 200, 480).setRegistryName("roasted_labrador_tea_head"),
        new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFCF.SUNFLOWER_HEAD)), new ItemStack(ItemsTFCF.DRIED_SUNFLOWER_HEAD), 200, 480).setRegistryName("roasted_sunflower_head"),

        // Food Destroy
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_BLACK_TEA), 480).setRegistryName("burned_black_tea"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_GREEN_TEA), 480).setRegistryName("burned_green_tea"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_WHITE_TEA), 480).setRegistryName("burned_white_tea"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_BUD), 480).setRegistryName("burned_cannabis_bud"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_LEAF), 480).setRegistryName("burned_cannabis_leaf"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_COCA_LEAF), 480).setRegistryName("burned_coca_leaf"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_OPIUM_POPPY_BULB), 480).setRegistryName("burned_opium_poppy_bulb"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_PEYOTE), 480).setRegistryName("burned_peyote"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_TOBACCO_LEAF), 480).setRegistryName("burned_tobacco_leaf"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS), 480).setRegistryName("burned_coffea_cherries"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_CHAMOMILE_HEAD), 480).setRegistryName("burned_chamomile_head"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_DANDELION_HEAD), 480).setRegistryName("burned_dandelion_head"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), 480).setRegistryName("burned_labrador_tea_head"),
        HeatRecipe.destroy(IIngredient.of(ItemsTFCF.DRIED_SUNFLOWER_HEAD), 480).setRegistryName("burned_sunflower_head")
      );
    }
  }

  @SubscribeEvent
  public static void onRegisterLoomRecipeEvent(RegistryEvent.Register<LoomRecipe> event) {
    IForgeRegistry<LoomRecipe> r = event.getRegistry();

    r.registerAll(

      new LoomRecipe(new ResourceLocation(TFCF, "yucca_canvas"), IIngredient.of(ItemsTFCF.YUCCA_STRING, 12), new ItemStack(ItemsTFCF.YUCCA_CANVAS), 12, new ResourceLocation(TFCF, "textures/blocks/devices/loom/product/yucca.png")),
      new LoomRecipe(new ResourceLocation(TFCF, "cotton_cloth"), IIngredient.of(ItemsTFCF.COTTON_YARN, 12), new ItemStack(ItemsTFCF.COTTON_CLOTH), 12, new ResourceLocation(TFCF, "textures/blocks/devices/loom/product/cotton.png")),
      new LoomRecipe(new ResourceLocation(TFCF, "hemp_cloth"), IIngredient.of(ItemsTFCF.HEMP_STRING, 12), new ItemStack(ItemsTFCF.HEMP_CLOTH), 12, new ResourceLocation(TFCF, "textures/blocks/devices/loom/product/hemp.png")),
      new LoomRecipe(new ResourceLocation(TFCF, "linen_cloth"), IIngredient.of(ItemsTFCF.LINEN_STRING, 12), new ItemStack(ItemsTFCF.LINEN_CLOTH), 12, new ResourceLocation(TFCF, "textures/blocks/devices/loom/product/linen.png")),
      new LoomRecipe(new ResourceLocation(TFCF, "sisal_cloth"), IIngredient.of(ItemsTFCF.SISAL_STRING, 12), new ItemStack(ItemsTFCF.SISAL_CLOTH), 12, new ResourceLocation(TFCF, "textures/blocks/devices/loom/product/sisal.png")),
      new LoomRecipe(new ResourceLocation(TFCF, "wool_block_cotton"), IIngredient.of(ItemsTFCF.COTTON_CLOTH, 4), new ItemStack(Blocks.WOOL, 8), 4, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png")),
      new LoomRecipe(new ResourceLocation(TFCF, "wool_block_linen"), IIngredient.of(ItemsTFCF.LINEN_CLOTH, 4), new ItemStack(Blocks.WOOL, 8), 4, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png")),
      new LoomRecipe(new ResourceLocation(TFCF, "wool_block_silk"), IIngredient.of(ItemsAnimal.SILK_CLOTH.get(), 4), new ItemStack(Blocks.WOOL, 8), 4, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png"))
    );
  }

  @SubscribeEvent
  public static void onRegisterQuernRecipeEvent(RegistryEvent.Register<QuernRecipe> event) {
    IForgeRegistry<QuernRecipe> r = event.getRegistry();

    r.registerAll(

      new QuernRecipe(IIngredient.of("logWoodLogwood"), new ItemStack((ItemsTFCF.LOGWOOD_CHIPS), 3)).setRegistryName("chipped_logwood_log"),
      new QuernRecipe(IIngredient.of("sugarcane"), new ItemStack((ItemsTFCF.MASHED_SUGAR_CANE))).setRegistryName("mashed_sugar_cane_quern"),
      new QuernRecipe(IIngredient.of("cropSugarBeet"), new ItemStack((ItemsTFCF.MASHED_SUGAR_BEET))).setRegistryName("mashed_sugar_beet_quern"),
      new QuernRecipe(IIngredient.of("grainAmaranth"), new ItemStack((ItemsTFCF.AMARANTH_FLOUR))).setRegistryName("amaranth"),
      new QuernRecipe(IIngredient.of("grainBuckwheat"), new ItemStack((ItemsTFCF.BUCKWHEAT_FLOUR))).setRegistryName("buckwheat"),
      new QuernRecipe(IIngredient.of("grainFonio"), new ItemStack((ItemsTFCF.FONIO_FLOUR))).setRegistryName("fonio"),
      new QuernRecipe(IIngredient.of("grainMillet"), new ItemStack((ItemsTFCF.MILLET_FLOUR))).setRegistryName("millet"),
      new QuernRecipe(IIngredient.of("grainQuinoa"), new ItemStack((ItemsTFCF.QUINOA_FLOUR))).setRegistryName("quinoa"),
      new QuernRecipe(IIngredient.of("grainSpelt"), new ItemStack((ItemsTFCF.SPELT_FLOUR))).setRegistryName("spelt"),
      new QuernRecipe(IIngredient.of(ItemsTFCF.CASSIA_CINNAMON_BARK), new ItemStack(ItemsTFCF.GROUND_CASSIA_CINNAMON, 2)).setRegistryName("ground_cassia_cinnamon"),
      new QuernRecipe(IIngredient.of(ItemsTFCF.CEYLON_CINNAMON_BARK), new ItemStack(ItemsTFCF.GROUND_CEYLON_CINNAMON, 2)).setRegistryName("ground_ceylon_cinnamon"),
      //new QuernRecipe(IIngredient.of(ItemsTFCF.BLACK_PEPPER), new ItemStack(ItemsTFCF.GROUND_BLACK_PEPPER, 2)).setRegistryName("ground_black_pepper"),
      new QuernRecipe(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS), new ItemStack(ItemsTFCF.COFFEE_POWDER, 2)).setRegistryName("ground_coffee_beans"),
      new QuernRecipe(IIngredient.of("pearl"), new ItemStack(ItemPowder.get(Powder.PEARL))).setRegistryName("crushed_pearl"),
      new QuernRecipe(IIngredient.of("pearlBlack"), new ItemStack(ItemPowder.get(Powder.BLACK_PEARL))).setRegistryName("crushed_black_pearl"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PAPYRUS))), new ItemStack(ItemsTFCF.PAPYRUS_PULP, 3)).setRegistryName("crushed_papyrus"),

      // Dye from plants
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CHAMOMILE))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_chamomile"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HYDRANGEA))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_hydrangea"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LILY_OF_THE_VALLEY))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_lily_of_the_valley"),
      new QuernRecipe(IIngredient.of("cropMadder"), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_madder"),
      new QuernRecipe(IIngredient.of("cropWoad"), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("crushed_woad"),
      new QuernRecipe(IIngredient.of("cropIndigo"), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("crushed_indigo"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SUNFLOWER))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_sunflower"),
      new QuernRecipe(IIngredient.of("cropWeld"), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_weld"),
      new QuernRecipe(IIngredient.of("cropRape"), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("crushed_rape"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LILAC))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_lilac"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PEONY))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_peony"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LAVANDULA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("crushed_lavandula"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CATTAIL))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("crushed_cattail"),
      new QuernRecipe(IIngredient.of("cropAgave"), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_agave"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SUGAR_CANE))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_sugar_cane"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BADDERLOCKS))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_badderlocks"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GUTWEED))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_gutweed"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SAGO))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_sago"),
      new QuernRecipe(IIngredient.of("resin"), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("yellow_dye_resin"),
      new QuernRecipe(IIngredient.of("treeLeavesTeak"), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("green_dye_teak_leaves"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SUGAR_CANE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_sugar_cane"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TACKWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_tackweed"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TAKAKIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_takakia"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.VOODOO_LILY))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_voodoo_lily"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.DEVILS_TONGUE))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_devils_tongue"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BROMELIA_HEMISPHERICA))), new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage())).setRegistryName("magenta_dye_bromelia_hemispherica"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BROMELIA_LACINIOSA))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_bromelia_laciniosa"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.KAIETEUR_FALLS))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_kaieteur_falls"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MATTEUCCIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_matteuccia"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CORD_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_cord_grass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.REED_MANNAGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_reed_mannagrass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PRAIRIE_JUNEGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_prairie_junegrass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WOOLLY_BUSH))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_woolly_bush"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CINNAMON_FERN))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_cinnamon_fern"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.JAPANESE_PIERIS))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_japanese_pieris"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BURNING_BUSH))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_burning_bush"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.UNDERGROWTH_SHRUB))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_undergrowth_shrub"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.UNDERGROWTH_SHRUB_SMALL))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_undergrowth_shrub_small"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SEA_OATS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_sea_oats"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BUNCH_GRASS_FLOATING))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dyebunch_grass_floating"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BUNCH_GRASS_REED))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_bunch_grass_reed"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CROWNGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_crowngrass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CAT_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_cat_grass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GOOSEGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_goosegrass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WHEATGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_wheatgrass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HALFA_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_halfa_grass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LEYMUS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_leymus"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MARRAM_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_marram_grass"),
      //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_BARLEY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_wild_barley"),
      //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_RICE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_wild_rice"),
      //new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_WHEAT))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_wild_wheat"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.RATTAN))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_rattan"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_hanging_vines"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BLUE_SKYFLOWER))), new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage())).setRegistryName("light_blue_dye_blue_skyflower"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.JADE_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage())).setRegistryName("light_blue_dye_jade_vine"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.JAPANESE_IVY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_japanese_ivy"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MADEIRA_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_madeira_vine"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MYSORE_TRUMPETVINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_mysore_trumpetvine"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SILVERVEIN_CREEPER))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_silvervein_creeper"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SWEDISH_IVY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_swedish_ivy"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.VARIEGATED_PERSIAN_IVY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_variegated_persian_ivy"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.APACHE_DWARF))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("yellow_dye_apache_dwarf"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ARTISTS_CONK))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_artists_conk"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CLIMBING_CACTUS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_climbing_cactus"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CRIMSON_CATTLEYA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("purple_dye_crimson_cattleya"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CREEPING_MISTLETOE))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("orange_dye_creeping_mistletoe"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CUTHBERTS_DENDROBIUM))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("orange_dye_cuthberts_dendrobium"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.FISH_BONE_CACTUS))), new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage())).setRegistryName("magenta_dye_fish_bone_cactus"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.FRAGRANT_FERN))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_fragrant_fern"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HARLEQUIN_MISTLETOE))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("orange_dye_harlequin_mistletoe"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.KING_ORCHID))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("white_dye_king_orchid"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LANTERN_OF_THE_FOREST))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_lantern_of_the_forest"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LARGE_FOOT_DENDROBIUM))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_large_foot_dendrobium"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.COMMON_MISTLETOE))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("white_dye_common_mistletoe"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SKY_PLANT))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_sky_plant"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SULPHUR_SHELF))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("orange_dye_sulphur_shelf"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TAMPA_BUTTERFLY_ORCHID))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("yellow_dye_tampa_butterfly_orchid"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TURKEY_TAIL))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_turkey_tail"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WILDFIRE))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_wildfire"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BELL_TREE_DAHLIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_bell_tree_dahlia"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BIG_LEAF_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_big_leaf_palm"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.DRAKENSBERG_CYCAD))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_drakensberg_cycad"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.DWARF_SUGAR_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_dwarf_sugar_palm"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GIANT_CANE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_giant_cane"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GIANT_ELEPHANT_EAR))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_giant_elephant_ear"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GIANT_FEATHER_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_giant_feather_grass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MADAGASCAR_OCOTILLO))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_madagascar_ocotillo"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MALAGASY_TREE_ALOE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_malagasy_tree_aloe"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MOUNTAIN_CABBAGE_TREE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_mountain_cabbage_tree"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PYGMY_DATE_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_pygmy_date_palm"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.QUEEN_SAGO))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_queen_sago"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_SEALING_WAX_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_red_sealing_wax_palm"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SUMMER_ASPHODEL))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_summer_asphodel"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ZIMBABWE_ALOE))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_zimbabwe_aloe"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ANTHURIUM))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_anthurium"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ARROWHEAD))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_arrowhead"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ARUNDO))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_arundo"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BLUEGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_bluegrass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BLUE_GINGER))), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("blue_dye_blue_ginger"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BROMEGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_bromegrass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BUR_REED))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("white_dye_bur_reed"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.DESERT_FLAME))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("yellow_dye_desert_flame"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HELICONIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_heliconia"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HIBISCUS))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("white_dye_hibiscus"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.KANGAROO_PAW))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_kangaroo_paw"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.KING_FERN))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_king_fern"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LIPSTICK_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage())).setRegistryName("magenta_dye_lipstick_palm"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MARIGOLD))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("yellow_dye_marigold"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MONSTERA_EPIPHYTE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_monstera_epiphyte"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MONSTERA_GROUND))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_monstera_ground"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PHRAGMITE))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_phragmite"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PICKERELWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("purple_dye_pickerelweed"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BADDERLOCKS))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_badderlocks"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.COONTAIL))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_coontail"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.EEL_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_eel_grass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GIANT_KELP))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_giant_kelp"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GUTWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_gutweed"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HORNWORT))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_hornwort"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LAMINARIA))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("brown_dye_laminaria"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LEAFY_KELP))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_leafy_kelp"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MANATEE_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_manatee_grass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.MILFOIL))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_milfoil"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PONDWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_pondweed"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SAGO))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_sago"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SEAGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_seagrass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SEAWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_seaweed"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.STAR_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_star_grass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TURTLE_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_turtle_grass"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WINGED_KELP))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_winged_kelp"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_ALGAE))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_red_algae"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_SEA_WHIP))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("red_dye_red_sea_whip"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SEA_ANEMONE))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("pink_dye_sea_anemone"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BEARDED_MOSS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_bearded_moss"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_glow_vine"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.HANGING_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_hanging_vine"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.JUNGLE_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_jungle_vine"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.LIANA))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_liana"),
      new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.IVY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("green_dye_ivy"),
      new QuernRecipe(IIngredient.of(BlocksTFCF.BLUESHROOM), new ItemStack(Items.DYE, 1, EnumDyeColor.CYAN.getDyeDamage())).setRegistryName("cyan_dye_blueshroom"),
      new QuernRecipe(IIngredient.of(BlocksTFCF.GLOWSHROOM), new ItemStack(Items.GLOWSTONE_DUST, 1)).setRegistryName("glowstone_dust_glowshroom"),
      new QuernRecipe(IIngredient.of(BlocksTFCF.MAGMA_SHROOM), new ItemStack(Items.MAGMA_CREAM, 1)).setRegistryName("magma_cream_magma_shroom"),
      new QuernRecipe(IIngredient.of(BlocksTFCF.POISON_SHROOM), new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage())).setRegistryName("magenta_dye_poison_shroom"),
      new QuernRecipe(IIngredient.of(BlocksTFCF.SULPHUR_SHROOM), new ItemStack(ItemPowder.get(Powder.SULFUR), 1)).setRegistryName("sulphur_powder_shroom"),
      new QuernRecipe(IIngredient.of(BlocksTFCF.GLOWING_SEA_BANANA), new ItemStack(Items.GLOWSTONE_DUST, 2)).setRegistryName("glowing_sea_banana_glowstone_dust"),
      new QuernRecipe(IIngredient.of(BlocksTFCF.LIGHTSTONE), new ItemStack(Items.GLOWSTONE_DUST, 2)).setRegistryName("glowstone_dust_lightstone")
    );
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
          HeatRecipe recipe = TFCRegistries.HEAT.getValue(new ResourceLocation("tfcflorae", name));
          if (recipe != null) {
            modRegistry.remove(recipe.getRegistryName());
            if (ConfigFL.General.COMPAT.logging) {FirmaLife.logger.info("Removed heating recipe tfcflorae:{}", name);}
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
          "food/dough/amaranth", "food/dough/buckwheat", "food/dough/fonio", "food/dough/millet", "food/dough/quinoa", "food/dough/spelt",
          "food/sandwich/amaranth", "food/sandwich/buckwheat", "food/sandwich/fonio", "food/sandwich/millet", "food/sandwich/quinoa", "food/sandwich/spelt"
        };
        for (String name : regNames) {
          IRecipe recipe = registry.getValue(new ResourceLocation("tfcflorae", name));
          if (recipe != null) {
            registry.remove(recipe.getRegistryName());
            if (ConfigFL.General.COMPAT.logging) {FirmaLife.logger.info("Removed crafting recipe tfcflorae:{}", name);}
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

        new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFC.get(european_oak), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("european_oak_nut"),
        new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_EUROPEAN_OAK), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("european_oak_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_EUROPEAN_OAK), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("european_oak_nut_orange"),
        new NutRecipe(BlockLogTFC.get(european_oak), BlockLeavesTFCF.get(SeasonalTrees.RED_EUROPEAN_OAK), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("european_oak_nut_red"),
        new NutRecipe(BlockLogTFC.get(oak), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_OAK), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("oak_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(oak), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_OAK), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("oak_nut_orange"),
        new NutRecipe(BlockLogTFC.get(oak), BlockLeavesTFCF.get(SeasonalTrees.RED_OAK), new ItemStack(ItemsFL.getFood(FoodFL.ACORNS))).setRegistryName("oak_nut_red"),
        new NutRecipe(BlockLogTFC.get(chestnut), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_CHESTNUT), new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUTS))).setRegistryName("chestnut_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(chestnut), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_CHESTNUT), new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUTS))).setRegistryName("chestnut_nut_orange"),
        new NutRecipe(BlockLogTFC.get(chestnut), BlockLeavesTFCF.get(SeasonalTrees.RED_CHESTNUT), new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUTS))).setRegistryName("chestnut_nut_red"),
        new NutRecipe(BlockLogTFC.get(hickory), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_HICKORY), new ItemStack(ItemsFL.getFood(FoodFL.PECAN_NUTS))).setRegistryName("hickory_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(hickory), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_HICKORY), new ItemStack(ItemsFL.getFood(FoodFL.PECAN_NUTS))).setRegistryName("hickory_nut_orange"),
        new NutRecipe(BlockLogTFC.get(hickory), BlockLeavesTFCF.get(SeasonalTrees.RED_HICKORY), new ItemStack(ItemsFL.getFood(FoodFL.PECAN_NUTS))).setRegistryName("hickory_nut_red"),
        new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFC.get(beech), new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut"),
        new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_BEECH), new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_BEECH), new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut_orange"),
        new NutRecipe(BlockLogTFC.get(beech), BlockLeavesTFCF.get(SeasonalTrees.RED_BEECH), new ItemStack(ItemsTFCF.BEECHNUT)).setRegistryName("beech_nut_red"),
        new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFC.get(black_walnut), new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut"),
        new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_BLACK_WALNUT), new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_BLACK_WALNUT), new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut_orange"),
        new NutRecipe(BlockLogTFC.get(black_walnut), BlockLeavesTFCF.get(SeasonalTrees.RED_BLACK_WALNUT), new ItemStack(ItemsTFCF.BLACK_WALNUT)).setRegistryName("black_walnut_nut_red"),
        new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFC.get(butternut), new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName("butternut_nut"),
        new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_BUTTERNUT), new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName("butternut_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_BUTTERNUT), new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName("butternut_nut_orange"),
        new NutRecipe(BlockLogTFC.get(butternut), BlockLeavesTFCF.get(SeasonalTrees.RED_BUTTERNUT), new ItemStack(ItemsTFCF.BUTTERNUT)).setRegistryName("butternut_nut_red"),
        new NutRecipe(BlockLogTFC.get(ginkgo), BlockLeavesTFC.get(ginkgo), new ItemStack(ItemsTFCF.GINKGO_NUT)).setRegistryName("ginkgo_nut"),
        new NutRecipe(BlockLogTFC.get(ginkgo), BlockLeavesTFCF.get(SeasonalTrees.GINKGO), new ItemStack(ItemsTFCF.GINKGO_NUT)).setRegistryName("ginkgo_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFC.get(hazel), new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut"),
        new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_HAZEL), new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_HAZEL), new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut_orange"),
        new NutRecipe(BlockLogTFC.get(hazel), BlockLeavesTFCF.get(SeasonalTrees.RED_HAZEL), new ItemStack(ItemsTFCF.HAZELNUT)).setRegistryName("hazel_nut_red"),
        new NutRecipe(BlockLogTFC.get(hemlock), BlockLeavesTFC.get(hemlock), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("hemlock_pinecone"),
        new NutRecipe(BlockLogTFC.get(ironwood), BlockLeavesTFC.get(ironwood), new ItemStack(ItemsTFCF.HOPS)).setRegistryName("ironwood_hops"),
        new NutRecipe(BlockLogTFC.get(kauri), BlockLeavesTFC.get(kauri), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("kauri_pinecone"),
        new NutRecipe(BlockLogTFC.get(larch), BlockLeavesTFCF.get(SeasonalTrees.LARCH), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("larch_pinecone"),
        new NutRecipe(BlockLogTFC.get(nordmann_fir), BlockLeavesTFC.get(nordmann_fir), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("nordmann_fir_pinecone"),
        new NutRecipe(BlockLogTFC.get(norway_spruce), BlockLeavesTFC.get(norway_spruce), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("norway_spruce_pinecone"),
        new NutRecipe(BlockLogTFC.get(redwood), BlockLeavesTFC.get(redwood), new ItemStack(ItemsTFCF.PINECONE)).setRegistryName("redwood_pinecone"),
        new NutRecipe(BlockLogTFC.get(walnut), BlockLeavesTFC.get(walnut), new ItemStack(ItemsTFCF.WALNUT)).setRegistryName("walnut_fruit"),
        new NutRecipe(BlockLogTFC.get(walnut), BlockLeavesTFCF.get(SeasonalTrees.YELLOW_WALNUT), new ItemStack(ItemsTFCF.WALNUT)).setRegistryName("walnut_nut_yellow"),
        new NutRecipe(BlockLogTFC.get(walnut), BlockLeavesTFCF.get(SeasonalTrees.ORANGE_WALNUT), new ItemStack(ItemsTFCF.WALNUT)).setRegistryName("walnut_nut_orange"),
        new NutRecipe(BlockLogTFC.get(walnut), BlockLeavesTFCF.get(SeasonalTrees.RED_WALNUT), new ItemStack(ItemsTFCF.WALNUT)).setRegistryName("walnut_nut_red")
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
        new CrackingRecipe(IIngredient.of(ItemsTFCF.BEECHNUT), new ItemStack(ItemsTFCF.BEECHNUT_NUT), 0.5f).setRegistryName("beechnut_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.BLACK_WALNUT), new ItemStack(ItemsTFCF.BLACK_WALNUT_NUT), 0.5f).setRegistryName("black_walnut_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.BUTTERNUT), new ItemStack(ItemsTFCF.BUTTERNUT_NUT), 0.5f).setRegistryName("butternut_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.CHESTNUT), new ItemStack(ItemsTFCF.CHESTNUT_NUT), 0.5f).setRegistryName("chestnut_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.GINKGO_NUT), new ItemStack(ItemsTFCF.GINKGO_NUT_NUT), 0.5f).setRegistryName("ginkgo_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.HAZELNUT), new ItemStack(ItemsTFCF.HAZELNUT_NUT), 0.5f).setRegistryName("hazelnut_fruit"),
        new CrackingRecipe(IIngredient.of(ItemsTFCF.HICKORY_NUT), new ItemStack(ItemsTFCF.HICKORY_NUT_NUT), 0.5f).setRegistryName("hickory_fruit"),
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
      for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
        ItemFiredMudBrick firedMudBrick = ItemFiredMudBrick.get(ItemUnfiredMudBrick.get(rock));

        OvenRecipe mudBrick = new OvenRecipe(IIngredient.of(ItemUnfiredMudBrick.get(rock)), new ItemStack(firedMudBrick), 4 * hour);
        event.getRegistry().register(mudBrick.setRegistryName(rock.getRegistryName().getPath().toLowerCase() + "_unfired_mud_brick"));
      }

      r.registerAll(
        new OvenRecipe(IIngredient.of(ItemsTFCF.HASH_MUFFIN_DOUGH), new ItemStack(ItemsTFCF.HASH_MUFFIN),
          4 * hour).setRegistryName(Mods.ModIDs.TFCF, "hash_muffin_dough_oven"),
        new OvenRecipe(IIngredient.of(ItemsTFCF.AMARANTH_DOUGH), new ItemStack(ItemsTFCF.AMARANTH_BREAD),
          4 * hour).setRegistryName(Mods.ModIDs.TFCF, "amaranth_dough_oven"),
        new OvenRecipe(IIngredient.of(ItemsTFCF.BUCKWHEAT_DOUGH), new ItemStack(ItemsTFCF.BUCKWHEAT_BREAD),
          4 * hour).setRegistryName(Mods.ModIDs.TFCF, "buckwheat_dough_oven"),
        new OvenRecipe(IIngredient.of(ItemsTFCF.FONIO_DOUGH), new ItemStack(ItemsTFCF.FONIO_BREAD),
          4 * hour).setRegistryName(Mods.ModIDs.TFCF, "fonio_dough_oven"),
        new OvenRecipe(IIngredient.of(ItemsTFCF.MILLET_DOUGH), new ItemStack(ItemsTFCF.MILLET_BREAD),
          4 * hour).setRegistryName(Mods.ModIDs.TFCF, "millet_dough_oven"),
        new OvenRecipe(IIngredient.of(ItemsTFCF.QUINOA_DOUGH), new ItemStack(ItemsTFCF.QUINOA_BREAD),
          4 * hour).setRegistryName(Mods.ModIDs.TFCF, "quinoa_dough_oven"),
        new OvenRecipe(IIngredient.of(ItemsTFCF.SPELT_DOUGH), new ItemStack(ItemsTFCF.SPELT_BREAD),
          4 * hour).setRegistryName(Mods.ModIDs.TFCF, "spelt_dough_oven"),

        new OvenRecipe(IIngredient.of("amaranthFlatbreadDough"), new ItemStack(ItemsTFCF.AMARANTH_FLATBREAD),
          4 * hour).setRegistryName(Mods.ModIDs.TFCF, "amaranth_flatbread_dough_oven"),
        new OvenRecipe(IIngredient.of("buckwheatFlatbreadDough"), new ItemStack(ItemsTFCF.BUCKWHEAT_FLATBREAD),
          4 * hour).setRegistryName(Mods.ModIDs.TFCF, "buckwheat_flatbread_dough_oven"),
        new OvenRecipe(IIngredient.of("fonioFlatbreadDough"), new ItemStack(ItemsTFCF.FONIO_FLATBREAD),
          4 * hour).setRegistryName(Mods.ModIDs.TFCF, "fonio_flatbread_dough_oven"),
        new OvenRecipe(IIngredient.of("milletFlatbreadDough"), new ItemStack(ItemsTFCF.MILLET_FLATBREAD),
          4 * hour).setRegistryName(Mods.ModIDs.TFCF, "millet_flatbread_dough_oven"),
        new OvenRecipe(IIngredient.of("quinoaFlatbreadDough"), new ItemStack(ItemsTFCF.QUINOA_FLATBREAD),
          4 * hour).setRegistryName(Mods.ModIDs.TFCF, "quinoa_flatbread_dough_oven"),
        new OvenRecipe(IIngredient.of("speltFlatbreadDough"), new ItemStack(ItemsTFCF.SPELT_FLATBREAD),
          4 * hour).setRegistryName(Mods.ModIDs.TFCF, "spelt_flatbread_dough_oven"),

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

        new OvenRecipe(IIngredient.of("epiphyteArtistsConk"), new ItemStack(ItemsTFCF.ROASTED_ARTISTS_CONK),
          2 * hour).setRegistryName("roasted_artists_conk_oven"),
        new OvenRecipe(IIngredient.of("epiphyteSulphurShelf"), new ItemStack(ItemsTFCF.ROASTED_SULPHUR_SHELF),
          2 * hour).setRegistryName("roasted_sulphur_shelf_oven"),
        new OvenRecipe(IIngredient.of("epiphyteTurkeyTail"), new ItemStack(ItemsTFCF.ROASTED_TURKEY_TAIL),
          2 * hour).setRegistryName("roasted_turkey_tail_oven"),
        new OvenRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.PORCINI))), new ItemStack(ItemsTFCF.ROASTED_PORCINI),
          2 * hour).setRegistryName("roasted_porcini_oven_specific"),
        new OvenRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.AMANITA))), new ItemStack(ItemsTFCF.ROASTED_AMANITA),
          2 * hour).setRegistryName("roasted_amanita_oven_specific"),
        new OvenRecipe(IIngredient.of("mushroomPorcini"), new ItemStack(ItemsTFCF.ROASTED_PORCINI), 2 * hour).setRegistryName("roasted_porcini_oven"),
        new OvenRecipe(IIngredient.of("mushroomAmanita"), new ItemStack(ItemsTFCF.ROASTED_AMANITA), 2 * hour).setRegistryName("roasted_amanita_oven"),
        new OvenRecipe(IIngredient.of("mushroomBlackPowderpuff"), new ItemStack(ItemsTFCF.ROASTED_BLACK_POWDERPUFF),
          2 * hour).setRegistryName("roasted_black_powderpuff_oven"),
        new OvenRecipe(IIngredient.of("mushroomChanterelle"), new ItemStack(ItemsTFCF.ROASTED_CHANTERELLE),
          2 * hour).setRegistryName("roasted_chanterelle_oven"),
        new OvenRecipe(IIngredient.of("mushroomDeathCap"), new ItemStack(ItemsTFCF.ROASTED_DEATH_CAP), 2 * hour).setRegistryName("roasted_death_cap_oven"),
        new OvenRecipe(IIngredient.of("mushroomGiantClub"), new ItemStack(ItemsTFCF.ROASTED_GIANT_CLUB), 2 * hour).setRegistryName("roasted_giant_club_oven"),
        new OvenRecipe(IIngredient.of("mushroomParasol"), new ItemStack(ItemsTFCF.ROASTED_PARASOL_MUSHROOM),
          2 * hour).setRegistryName("roasted_parasol_mushroom_oven"),
        new OvenRecipe(IIngredient.of("mushroomStinkhorn"), new ItemStack(ItemsTFCF.ROASTED_STINKHORN), 2 * hour).setRegistryName("roasted_stinkhorn_oven"),
        new OvenRecipe(IIngredient.of("mushroomWeepingMilkCap"), new ItemStack(ItemsTFCF.ROASTED_WEEPING_MILK_CAP),
          2 * hour).setRegistryName("roasted_weeping_milk_cap_oven"),
        new OvenRecipe(IIngredient.of("mushroomWoodBlewit"), new ItemStack(ItemsTFCF.ROASTED_WOOD_BLEWIT),
          2 * hour).setRegistryName("roasted_wood_blewit_oven"),
        new OvenRecipe(IIngredient.of("mushroomWoollyGomphus"), new ItemStack(ItemsTFCF.ROASTED_WOOLLY_GOMPHUS),
          2 * hour).setRegistryName("roasted_woolly_gomphus_oven"),

        new OvenRecipe(IIngredient.of("rawEel"), new ItemStack(ItemsTFCF.COOKED_EEL), 2 * hour).setRegistryName("cooked_eel_oven"),
        new OvenRecipe(IIngredient.of("rawCrab"), new ItemStack(ItemsTFCF.COOKED_CRAB), 2 * hour).setRegistryName("cooked_crab_oven"),
        new OvenRecipe(IIngredient.of("rawClam"), new ItemStack(ItemsTFCF.COOKED_CLAM), 2 * hour).setRegistryName("cooked_clam_oven"),
        new OvenRecipe(IIngredient.of("rawScallop"), new ItemStack(ItemsTFCF.COOKED_SCALLOP), 2 * hour).setRegistryName("cooked_scallop_oven"),
        new OvenRecipe(IIngredient.of("rawStarfish"), new ItemStack(ItemsTFCF.COOKED_STARFISH), 2 * hour).setRegistryName("cooked_starfish_oven"),
        new OvenRecipe(IIngredient.of("rawSnail"), new ItemStack(ItemsTFCF.COOKED_SNAIL), 2 * hour).setRegistryName("cooked_snail_oven"),
        new OvenRecipe(IIngredient.of("rawWorm"), new ItemStack(ItemsTFCF.COOKED_WORM), 2 * hour).setRegistryName("cooked_worm_oven"),

        new OvenRecipe(IIngredient.of("clayKaolinite"), new ItemStack(ItemPowder.get(Powder.KAOLINITE)), hour).setRegistryName("kaolinite_clay_oven"),
        new OvenRecipe(IIngredient.of("bone"), new ItemStack(ItemsTFCF.CHARRED_BONES), 2 * hour).setRegistryName("charred_bones_heat_oven")
      );
    }
  }

  @SubscribeEvent
  public static void onRegisterDryingRecipeEventFL(RegistryEvent.Register<DryingRecipe> event) {
    if (TFCFlorae.FirmaLifeAdded) {
      IForgeRegistry<DryingRecipe> r = event.getRegistry();
      r.registerAll(
        new DryingRecipe(IIngredient.of(ItemsTFCF.CELLULOSE_FIBERS), new ItemStack(Items.PAPER), 24000).setRegistryName(Mods.ModIDs.TFCF, "paper_from_cellulose_fibers"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.BLACK_TEA), new ItemStack(ItemsTFCF.DRIED_BLACK_TEA), 24000).setRegistryName(Mods.ModIDs.TFCF, "dried_black_tea"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.GREEN_TEA), new ItemStack(ItemsTFCF.DRIED_GREEN_TEA), 24000).setRegistryName(Mods.ModIDs.TFCF, "dried_green_tea"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.WHITE_TEA), new ItemStack(ItemsTFCF.DRIED_WHITE_TEA), 24000).setRegistryName(Mods.ModIDs.TFCF, "dried_white_tea"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.CANNABIS_BUD), new ItemStack(ItemsTFCF.DRIED_CANNABIS_BUD), 24000).setRegistryName(Mods.ModIDs.TFCF, "dried_cannabis_bud"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.CANNABIS_LEAF), new ItemStack(ItemsTFCF.DRIED_CANNABIS_LEAF), 24000).setRegistryName(Mods.ModIDs.TFCF, "dried_cannabis_leaf"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.COCA_LEAF), new ItemStack(ItemsTFCF.DRIED_COCA_LEAF), 24000).setRegistryName(Mods.ModIDs.TFCF, "dried_coca_leaf"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.OPIUM_POPPY_BULB), new ItemStack(ItemsTFCF.DRIED_OPIUM_POPPY_BULB), 24000).setRegistryName(Mods.ModIDs.TFCF, "dried_opium_poppy_bulb"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.PEYOTE), new ItemStack(ItemsTFCF.DRIED_PEYOTE), 24000).setRegistryName(Mods.ModIDs.TFCF, "dried_peyote"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.TOBACCO_LEAF), new ItemStack(ItemsTFCF.DRIED_TOBACCO_LEAF), 24000).setRegistryName(Mods.ModIDs.TFCF, "dried_tobacco_leaf"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.COFFEA_CHERRIES), new ItemStack(ItemsTFCF.DRIED_COFFEA_CHERRIES), 24000).setRegistryName(Mods.ModIDs.TFCF, "dried_coffea_cherries"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.CHAMOMILE_HEAD), new ItemStack(ItemsTFCF.DRIED_CHAMOMILE_HEAD), 24000).setRegistryName(Mods.ModIDs.TFCF, "dried_chamomile_head"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.DANDELION_HEAD), new ItemStack(ItemsTFCF.DRIED_DANDELION_HEAD), 24000).setRegistryName(Mods.ModIDs.TFCF, "dried_dandelion_head"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.LABRADOR_TEA_HEAD), new ItemStack(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), 24000).setRegistryName("dried_labrador_tea_head"),
        new DryingRecipe(IIngredient.of(ItemsTFCF.SUNFLOWER_HEAD), new ItemStack(ItemsTFCF.DRIED_SUNFLOWER_HEAD), 24000).setRegistryName(Mods.ModIDs.TFCF, "dried_sunflower_head")
      );

      // Mud Pottery
      for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
        ItemFiredMudBrick firedMudBrick = ItemFiredMudBrick.get(ItemUnfiredMudBrick.get(rock));

        DryingRecipe mud = new DryingRecipe(IIngredient.of(ItemUnfiredMudBrick.get(rock)), new ItemStack(firedMudBrick), 6000);
        event.getRegistry().register(mud.setRegistryName(rock.getRegistryName().getPath().toLowerCase() + "_wet_mud_brick"));
      }
    }
  }

  @SubscribeEvent
  public static void onRegisterPlanterEvent(RegistryEvent.Register<PlanterRecipe> event) {
    IForgeRegistry<PlanterRecipe> r = event.getRegistry();
    r.registerAll(
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.BLACK_EYED_PEAS)), new ItemStack(ItemsTFCF.BLACK_EYED_PEAS), 6, true).setRegistryName("tfcflorae", "black_eyed_peas"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.CAYENNE_PEPPER)), new ItemStack(ItemsTFCF.RED_CAYENNE_PEPPER), 6, true).setRegistryName("tfcflorae", "red_cayenne_pepper"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.GINSENG)), new ItemStack(ItemsTFCF.GINSENG), 4, false).setRegistryName("tfcflorae", "ginseng"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.RUTABAGA)), new ItemStack(ItemsTFCF.RUTABAGA), 6, false).setRegistryName("tfcflorae", "rutabaga"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.TURNIP)), new ItemStack(ItemsTFCF.TURNIP), 7, false).setRegistryName("tfcflorae", "turnip"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.SUGAR_BEET)), new ItemStack(ItemsTFCF.SUGAR_BEET), 6, true).setRegistryName("tfcflorae", "sugar_beet"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.PURPLE_GRAPE)), new ItemStack(ItemsTFCF.PURPLE_GRAPE), 8, true).setRegistryName("tfcflorae", "purple_grape"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.GREEN_GRAPE)), new ItemStack(ItemsTFCF.GREEN_GRAPE), 8, true).setRegistryName("tfcflorae", "green_grape"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.LIQUORICE_ROOT)), new ItemStack(ItemsTFCF.LIQUORICE_ROOT), 7, false).setRegistryName("tfcflorae", "liquorice_root"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.COFFEA)), new ItemStack(ItemsTFCF.COFFEA_CHERRIES), 7, false).setRegistryName("tfcflorae", "coffea_cherries"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.AGAVE)), new ItemStack(ItemsTFCF.AGAVE), 5, false).setRegistryName("tfcflorae", "agave"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.COCA)), new ItemStack(ItemsTFCF.COCA_LEAF), 5, true).setRegistryName("tfcflorae", "coca"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.COTTON)), new ItemStack(ItemsTFCF.COTTON_BOLL), 5, true).setRegistryName("tfcflorae", "cotton"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.FLAX)), new ItemStack(ItemsTFCF.FLAX), 6, true).setRegistryName("tfcflorae", "flax"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.HEMP)), new ItemStack(ItemsTFCF.HEMP), 4, true).setRegistryName("tfcflorae", "hemp"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.HOP)), new ItemStack(ItemsTFCF.HOPS), 5, true).setRegistryName("tfcflorae", "hop"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.INDIGO)), new ItemStack(ItemsTFCF.INDIGO), 5, true).setRegistryName("tfcflorae", "indigo"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.MADDER)), new ItemStack(ItemsTFCF.MADDER), 4, false).setRegistryName("tfcflorae", "madder"),
//      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.OPIUM_POPPY)), new ItemStack(ItemsTFCF.OPIUM_POPPY_BULB), 6, false).setRegistryName("firmalife", "opium_poppy"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.RAPE)), new ItemStack(ItemsTFCF.RAPE), 5, false).setRegistryName("tfcflorae", "rape"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.WELD)), new ItemStack(ItemsTFCF.WELD), 4, false).setRegistryName("tfcflorae", "weld"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.WOAD)), new ItemStack(ItemsTFCF.WOAD), 5, false).setRegistryName("tfcflorae", "woad"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.TOBACCO)), new ItemStack(ItemsTFCF.TOBACCO_LEAF), 6, true).setRegistryName("tfcflorae", "tobacco"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.AMARANTH)), new ItemStack(ItemsTFCF.AMARANTH), 7, true).setRegistryName("tfcflorae", "amaranth"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.BUCKWHEAT)), new ItemStack(ItemsTFCF.BUCKWHEAT), 7, true).setRegistryName("tfcflorae", "buckwheat"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.FONIO)), new ItemStack(ItemsTFCF.FONIO), 7, true).setRegistryName("tfcflorae", "fonio"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.GINGER)), new ItemStack(ItemsTFCF.GINGER), 6, false).setRegistryName("tfcflorae", "ginger"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.MILLET)), new ItemStack(ItemsTFCF.MILLET), 7, true).setRegistryName("tfcflorae", "millet"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.QUINOA)), new ItemStack(ItemsTFCF.QUINOA), 7, true).setRegistryName("tfcflorae", "quinoa"),
      new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(CropTFCF.SPELT)), new ItemStack(ItemsTFCF.SPELT), 7, true).setRegistryName("tfcflorae", "spelt")
    );
  }
}
