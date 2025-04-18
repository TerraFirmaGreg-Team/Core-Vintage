package net.dries007.firmalife.registry;

import su.terrafirmagreg.api.data.enums.Mods;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.init.FluidsCore;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
import net.dries007.firmalife.init.KnappingFL;
import net.dries007.firmalife.init.PlantsFL;
import net.dries007.firmalife.init.StemCrop;
import net.dries007.tfc.api.recipes.LoomRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeFoodPreservation;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.inventory.ingredient.IngredientItemFood;
import net.dries007.tfc.objects.items.ItemPowder;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.objects.recipes.KnappingRecipeFood;
import net.dries007.tfc.util.agriculture.Food;

import static net.dries007.firmalife.FirmaLife.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class TFCRegistry {

  public static final ResourceLocation HALITE = new ResourceLocation(Mods.ModIDs.TFC, "halite");

  @SubscribeEvent
  public static void onPreRegisterOre(TFCRegistryEvent.RegisterPreBlock<Ore> event) {
    IForgeRegistry<Ore> r = event.getRegistry();
    r.registerAll(
      new Ore(HALITE)
    );
  }

  @SubscribeEvent
  public static void onPreRegisterPlant(TFCRegistryEvent.RegisterPreBlock<Plant> event) {
    event.getRegistry().registerAll(PlantsFL.WRAPPERS.toArray(new Plant[0]));
  }

  @SubscribeEvent
  public static void onRegisterLoomRecipeEvent(RegistryEvent.Register<LoomRecipe> event) {
    IForgeRegistry<LoomRecipe> r = event.getRegistry();

    r.register(new LoomRecipe(new ResourceLocation(MOD_ID, "pineapple_yarn"), IIngredient.of(ItemsFL.PINEAPPLE_YARN, 8), new ItemStack(ItemsFL.PINEAPPLE_LEATHER), 8, new ResourceLocation(MOD_ID, "textures/blocks/pineapple.png")));
  }

  @SubscribeEvent
  public static void onRegisterQuernRecipeEvent(RegistryEvent.Register<QuernRecipe> event) {
    IForgeRegistry<QuernRecipe> r = event.getRegistry();

    r.register(new QuernRecipe(IIngredient.of("gemHalite"), new ItemStack(ItemPowder.get(Powder.SALT), 2)).setRegistryName("halite"));
    r.register(new QuernRecipe(IIngredient.of(ItemsFL.CINNAMON), new ItemStack(ItemsFL.GROUND_CINNAMON, 2)).setRegistryName("cinnamon"));
    r.register(new QuernRecipe(IIngredient.of(ItemsFL.getFood(FoodFL.ROASTED_CHESTNUTS)), new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUT_FLOUR))).setRegistryName("roasted_chestnuts"));
    r.register(new QuernRecipe(IIngredient.of(ItemFoodTFC.get(Food.SOYBEAN)), new ItemStack(ItemsFL.getFood(FoodFL.GROUND_SOYBEANS))).setRegistryName("soybean"));
    r.register(new QuernRecipe(IIngredient.of(ItemFoodTFC.get(Food.TOMATO)), new ItemStack(ItemsFL.getFood(FoodFL.TOMATO_SAUCE))).setRegistryName("tomato"));
  }

  @SubscribeEvent
  public static void onRegisterKnappingRecipeEvent(RegistryEvent.Register<KnappingRecipe> event) {
    event.getRegistry().registerAll(
      new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(BlocksFL.OVEN), "XXXXX", "XX XX", "X   X", "X   X", "XXXXX").setRegistryName("clay_oven"),
      new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(BlocksFL.OVEN_CHIMNEY), "XX XX", "X   X", "X   X", "X   X", "X   X").setRegistryName("clay_oven_chimney"),
      new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(BlocksFL.OVEN_WALL), "    X", "   XX", "   XX", "  XXX", "  XXX").setRegistryName("clay_oven_wall"),

      new KnappingRecipeFood(KnappingFL.PUMPKIN, true, new ItemStack(ItemSeedsTFC.get(StemCrop.PUMPKIN)), "XXXXX", "X   X", "X   X", "X   X", "XXXXX").setRegistryName("pumpkin_scoop"),
      new KnappingRecipeFood(KnappingFL.PUMPKIN, true, new ItemStack(ItemsFL.getFood(FoodFL.PUMPKIN_CHUNKS), 4), "XX XX", "XX XX", "     ", "XX XX", "XX XX").setRegistryName("pumpkin_chunk")
    );

    event.getRegistry().registerAll(BlocksFL.getAllJackOLanterns().stream()
      .map(j -> new KnappingRecipeSimple(KnappingFL.PUMPKIN, true, new ItemStack(Item.getItemFromBlock(j)), j.getCarving()
        .getCraftPattern()).setRegistryName(
        "pumpkin_carve_" + j.getCarving().getName())).toArray(KnappingRecipe[]::new));
  }

  @SuppressWarnings("rawtypes")
  @SubscribeEvent
  public static void onRegisterBarrelRecipeEvent(RegistryEvent.Register<BarrelRecipe> event) {
    if (ConfigFL.General.COMPAT.removeTFC) {
      IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) TFCRegistries.BARREL;
      String[] regNames = {"milk_vinegar", "curdled_milk", "cheese"};
      for (String name : regNames) {
        BarrelRecipe recipe = TFCRegistries.BARREL.getValue(new ResourceLocation("tfc", name));
        if (recipe != null) {
          modRegistry.remove(recipe.getRegistryName());
          if (ConfigFL.General.COMPAT.logging) {
            FirmaLife.logger.info("Removed barrel recipe from tfc:{}", name);
          }
        }
      }
    }
    int hour = ICalendar.TICKS_IN_HOUR;
    event.getRegistry().registerAll(
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 100), IIngredient.of("fruitDry"), new FluidStack(FluidsCore.YEAST_STARTER.get(), 100), ItemStack.EMPTY,
        hour * 96).setRegistryName("yeast_from_fruit"),
      new BarrelRecipe(IIngredient.of(FluidsCore.YEAST_STARTER.get(), 100), IIngredient.of("flour"), new FluidStack(FluidsCore.YEAST_STARTER.get(), 600), ItemStack.EMPTY,
        hour * 12).setRegistryName("yeast_multiplication"),
      new BarrelRecipe(IIngredient.of(FluidsCore.RUM.get(), 1000), IIngredient.of(ItemsFL.FROTHY_COCONUT), new FluidStack(FluidsCore.PINA_COLADA.get(), 1000), ItemStack.EMPTY, hour).setRegistryName("pina_colada"),
      new BarrelRecipe(IIngredient.of(FluidsCore.HOT_WATER.get(), 250), new IngredientItemFood(IIngredient.of(ItemFoodTFC.get(Food.BEET), 8)), null, new ItemStack(Items.SUGAR),
        hour * 8).setRegistryName("beet_sugar"),
      new BarrelRecipe(IIngredient.of(FluidsCore.FRESH_WATER.get(), 250), new IngredientItemFood(IIngredient.of(ItemsFL.getFood(FoodFL.GROUND_SOYBEANS), 1)), null, new ItemStack(ItemsFL.getFood(FoodFL.TOFU)),
        hour * 8).setRegistryName("tofu"),
      new BarrelRecipeFoodPreservation(IIngredient.of(FluidsCore.LIMEWATER.get(), 125), IIngredient.of(new ItemStack(ItemsFL.getFood(FoodFL.PICKLED_EGG))), FoodTrait.PRESERVED, "barrel_recipe_lime").setRegistryName("pickle_egg"),
      new BarrelRecipe(IIngredient.of(FluidsCore.MILK.get(), 2000), IIngredient.of(ItemsFL.RENNET), new FluidStack(FluidsCore.CURDLED_MILK.get(), 2000), ItemStack.EMPTY,
        hour * 4).setRegistryName("curdled_milk"),
      new BarrelRecipe(IIngredient.of(FluidsCore.YAK_MILK.get(), 2000), IIngredient.of(ItemsFL.RENNET), new FluidStack(FluidsCore.CURDLED_YAK_MILK.get(), 2000), ItemStack.EMPTY,
        hour * 4).setRegistryName("curdled_yak_milk"),
      new BarrelRecipe(IIngredient.of(FluidsCore.GOAT_MILK.get(), 2000), IIngredient.of(ItemsFL.RENNET), new FluidStack(FluidsCore.CURDLED_GOAT_MILK.get(), 2000), ItemStack.EMPTY,
        hour * 4).setRegistryName("curdled_goat_milk"),
      new BarrelRecipe(IIngredient.of(FluidsCore.ZEBU_MILK.get(), 2000), IIngredient.of(ItemsFL.RENNET), new FluidStack(FluidsCore.CURDLED_MILK.get(), 2000), ItemStack.EMPTY,
        hour * 4).setRegistryName("curdled_zebu_milk"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SALT_WATER.get(), 750), new IngredientItemFood(IIngredient.of(ItemsFL.getFood(FoodFL.YAK_CURD), 3)), null, new ItemStack(BlocksFL.SHOSHA_WHEEL),
        hour * 16).setRegistryName("shosha_wheel"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SALT_WATER.get(), 750), new IngredientItemFood(IIngredient.of(ItemsFL.getFood(FoodFL.GOAT_CURD), 3)), null, new ItemStack(BlocksFL.FETA_WHEEL),
        hour * 16).setRegistryName("feta_wheel"),
      new BarrelRecipe(IIngredient.of(FluidsCore.SALT_WATER.get(), 750), new IngredientItemFood(IIngredient.of(ItemsFL.getFood(FoodFL.MILK_CURD), 3)), null, new ItemStack(BlocksFL.GOUDA_WHEEL),
        hour * 16).setRegistryName("gouda_wheel"),
      new BarrelRecipe(IIngredient.of(FluidsCore.OLIVE_OIL.get(), 500), IIngredient.of("lumber"), null, new ItemStack(ItemsFL.TREATED_LUMBER),
        hour * 8).setRegistryName("treat_lumber")
    );
  }

  @SuppressWarnings("rawtypes")
  @SubscribeEvent
  public static void onRegisterHeatRecipeEvent(RegistryEvent.Register<HeatRecipe> event) {
    IForgeRegistry<HeatRecipe> r = event.getRegistry();

    r.registerAll(
      new HeatRecipeSimple(IIngredient.of("slice"), new ItemStack(ItemsFL.getFood(FoodFL.TOAST)), 150, 400).setRegistryName("slice"),
      new HeatRecipeSimple(IIngredient.of(ItemsFL.HONEYCOMB), new ItemStack(ItemsFL.BEESWAX), 150, 400).setRegistryName("honeycomb")
    );

    //Remove recipes
    if (ConfigFL.General.COMPAT.removeTFC) {
      IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) TFCRegistries.HEAT;
      String[] regNames = {"barley_bread", "cornbread", "oat_bread", "rice_bread", "rye_bread", "wheat_bread"};
      for (String name : regNames) {
        HeatRecipe recipe = TFCRegistries.HEAT.getValue(new ResourceLocation("tfc", name));
        if (recipe != null) {
          modRegistry.remove(recipe.getRegistryName());
          if (ConfigFL.General.COMPAT.logging) {FirmaLife.logger.info("Removed heating recipe tfc:{}", name);}
        }
      }
    }

  }

  @SubscribeEvent
  public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event) {
    if (ConfigFL.General.COMPAT.removeTFC) {
      IForgeRegistryModifiable<IRecipe> registry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
      String[] regNames = {"food/barley/barley_dough", "food/cornmeal/cornmeal_dough", "food/oat/oat_dough", "food/rice/rice_dough", "food/rye/rye_dough",
                           "food/wheat/wheat_dough",
                           "food/barley/barley_bread_sandwich", "food/cornmeal/cornbread_sandwich", "food/oat/oat_bread_sandwich",
                           "food/rice/rice_bread_sandwich", "food/rye/rye_bread_sandwich", "food/wheat/wheat_bread_sandwich"};
      for (String name : regNames) {
        IRecipe recipe = registry.getValue(new ResourceLocation("tfc", name));
        if (recipe != null) {
          registry.remove(recipe.getRegistryName());
          if (ConfigFL.General.COMPAT.logging) {FirmaLife.logger.info("Removed crafting recipe tfc:{}", name);}
        }

      }
    }
  }
}
