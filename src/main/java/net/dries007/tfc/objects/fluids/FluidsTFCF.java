package net.dries007.tfc.objects.fluids;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;
import su.terrafirmagreg.modules.food.api.IFoodStatsTFC;

import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.google.common.collect.ImmutableSet;
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import static net.dries007.tfc.objects.fluids.FluidsTFC.FLOW;
import static net.dries007.tfc.objects.fluids.FluidsTFC.STILL;
import static su.terrafirmagreg.api.util.MathUtils.RNG;

public final class FluidsTFCF {

  @Getter
  private static ImmutableSet<FluidWrapper> allFiniteFluids;
  @Getter
  private static ImmutableSet<FluidWrapper> allFermentedAlcoholsFluids;
  @Getter
  private static ImmutableSet<FluidWrapper> allAlcoholsFluids;
  @Getter
  private static ImmutableSet<FluidWrapper> allBeerFluids;
  @Getter
  private static ImmutableSet<FluidWrapper> allTeaFluids;
  @Getter
  private static ImmutableSet<FluidWrapper> allCoffeeFluids;
  @Getter
  private static ImmutableSet<FluidWrapper> allJuiceBerryFluids;
  @Getter
  private static ImmutableSet<FluidWrapper> allJuiceFruitFluids;
  @Getter
  private static ImmutableSet<FluidWrapper> allMiscFluids;

  public static void registerFluids() {
    DrinkableProperty milkProperty = player -> {
      if (player.getFoodStats() instanceof IFoodStatsTFC foodStats) {
        foodStats.addThirst(10);
        foodStats.getNutrition().addBuff(FoodData.MILK);
      }
    };

    FluidsCore.DISTILLED_WATER = registerFluid(new Fluid("distilled_water", STILL, FLOW, 0xFF1F32DA)).with(DrinkableProperty.DRINKABLE, player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC) {
        ((FoodStatsTFC) player.getFoodStats()).addThirst(20);
      }
    });
    FluidsCore.WASTE = registerFluid(new Fluid("waste", STILL, FLOW, 0xFF858678)).with(DrinkableProperty.DRINKABLE, player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC) {
        ((FoodStatsTFC) player.getFoodStats()).addThirst(-20);
        if (RNG.nextFloat() < 0.25f) {
          player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 400, 1));
          player.addPotionEffect(new PotionEffect(MobEffects.POISON, 400, 1));
        }
      }
    });
    FluidsCore.BASE_POTASH_LIQUOR = registerFluid(new Fluid("base_potash_liquor", STILL, FLOW, 0xFF86888B)).with(DrinkableProperty.DRINKABLE, player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC) {
        ((FoodStatsTFC) player.getFoodStats()).addThirst(-20);
        if (RNG.nextFloat() < 0.25f) {
          player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 400, 1));
          player.addPotionEffect(new PotionEffect(MobEffects.POISON, 400, 1));
        }
      }
    });

    DrinkableProperty teaProperty = player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC) {
        ((FoodStatsTFC) player.getFoodStats()).addThirst(20);
        if (RNG.nextFloat() < 0.25f) {
          player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1200, 1));
        }
      }
    };
    allTeaFluids = ImmutableSet.<FluidWrapper>builder()
                               .add(
                                 FluidsCore.WHITE_TEA = registerFluid(new Fluid("white_tea", STILL, FLOW, 0xFFC48E69).setRarity(EnumRarity.UNCOMMON)).with(
                                   DrinkableProperty.DRINKABLE, teaProperty),
                                 FluidsCore.GREEN_TEA = registerFluid(new Fluid("green_tea", STILL, FLOW, 0xFFD9D08F).setRarity(EnumRarity.UNCOMMON)).with(
                                   DrinkableProperty.DRINKABLE, teaProperty),
                                 FluidsCore.BLACK_TEA = registerFluid(new Fluid("black_tea", STILL, FLOW, 0xFF923C01).setRarity(EnumRarity.UNCOMMON)).with(
                                   DrinkableProperty.DRINKABLE, teaProperty),
                                 FluidsCore.CHAMOMILE_TEA = registerFluid(new Fluid("chamomile_tea", STILL, FLOW, 0xFFFFE089).setRarity(EnumRarity.UNCOMMON)).with(
                                   DrinkableProperty.DRINKABLE, teaProperty),
                                 FluidsCore.DANDELION_TEA = registerFluid(new Fluid("dandelion_tea", STILL, FLOW, 0xFFE3BA66).setRarity(EnumRarity.UNCOMMON)).with(
                                   DrinkableProperty.DRINKABLE, teaProperty),
                                 FluidsCore.LABRADOR_TEA = registerFluid(new Fluid("labrador_tea", STILL, FLOW, 0xFFF6E469).setRarity(EnumRarity.UNCOMMON)).with(
                                   DrinkableProperty.DRINKABLE, teaProperty)
                               )
                               .build();

    DrinkableProperty coffeeProperty = player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC) {
        ((FoodStatsTFC) player.getFoodStats()).addThirst(20);
        if (RNG.nextFloat() < 0.25f) {
          player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 1));
        }
      }
    };

    allCoffeeFluids = ImmutableSet.<FluidWrapper>builder().add(
                                    FluidsCore.COFFEE = registerFluid(new Fluid("coffee", STILL, FLOW, 0xFF6F4E37).setRarity(EnumRarity.UNCOMMON)).with(
                                      DrinkableProperty.DRINKABLE, coffeeProperty),
                                    FluidsCore.FIRMA_COLA = registerFluid(new Fluid("firma_cola", STILL, FLOW, 0xFF521810).setRarity(EnumRarity.RARE)).with(
                                      DrinkableProperty.DRINKABLE, coffeeProperty)
                                  )
                                  .build();

    DrinkableProperty miscFluidsProperty = player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC) {
        ((FoodStatsTFC) player.getFoodStats()).addThirst(15);
      }
    };
    allMiscFluids = ImmutableSet.<FluidWrapper>builder()
                                .add(
                                  FluidsCore.SUGAR_WATER = registerFluid(new Fluid("sugar_water", STILL, FLOW, 0xFFEEEFDF).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.HONEY_WATER = registerFluid(new Fluid("honey_water", STILL, FLOW, 0xFFFBE2A1).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.RICE_WATER = registerFluid(new Fluid("rice_water", STILL, FLOW, 0xFFEFE0CB).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.SOYBEAN_WATER = registerFluid(new Fluid("soybean_water", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.LINSEED_WATER = registerFluid(new Fluid("linseed_water", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.RAPE_SEED_WATER = registerFluid(new Fluid("rape_seed_water", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.SUNFLOWER_SEED_WATER = registerFluid(
                                    new Fluid("sunflower_seed_water", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.OPIUM_POPPY_SEED_WATER = registerFluid(
                                    new Fluid("opium_poppy_seed_water", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.SUGAR_BEET_WATER = registerFluid(new Fluid("sugar_beet_water", STILL, FLOW, 0xFFEEEFDF).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.SOY_MILK = registerFluid(new Fluid("soy_milk", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.LINSEED_OIL = registerFluid(new Fluid("linseed_oil", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.RAPE_SEED_OIL = registerFluid(new Fluid("rape_seed_oil", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.SUNFLOWER_SEED_OIL = registerFluid(
                                    new Fluid("sunflower_seed_oil", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.OPIUM_POPPY_SEED_OIL = registerFluid(
                                    new Fluid("opium_poppy_seed_oil", STILL, FLOW, 0xFFDBD7CB).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty),
                                  FluidsCore.WORT = registerFluid(new Fluid("wort", STILL, FLOW, 0xFF654321).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, miscFluidsProperty)
                                )
                                .build();

    DrinkableProperty fermentedAlcoholProperty = player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC) {
        ((FoodStatsTFC) player.getFoodStats()).addThirst(10);
        if (RNG.nextFloat() < 0.25f) {
          player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 400, 1));
        }
      }
    };
    allFermentedAlcoholsFluids = ImmutableSet.<FluidWrapper>builder()
                                             .add(
                                               FluidsCore.AGAVE_WINE = registerFluid(new Fluid("agave_wine", STILL, FLOW, 0xFFDBB35A).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.BARLEY_WINE = registerFluid(new Fluid("barley_wine", STILL, FLOW, 0xFF851401).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.BANANA_WINE = registerFluid(new Fluid("banana_wine", STILL, FLOW, 0xFFB76A02).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.BERRY_WINE = registerFluid(new Fluid("berry_wine", STILL, FLOW, 0xFFE13F43).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.CHERRY_WINE = registerFluid(new Fluid("cherry_wine", STILL, FLOW, 0xFF790604).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.JUNIPER_WINE = registerFluid(new Fluid("juniper_wine", STILL, FLOW, 0xFF863136).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.LEMON_WINE = registerFluid(new Fluid("lemon_wine", STILL, FLOW, 0xFFF7EAB7).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.MEAD = registerFluid(new Fluid("mead", STILL, FLOW, 0xFFE1701B).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.ORANGE_WINE = registerFluid(new Fluid("orange_wine", STILL, FLOW, 0xFFC56707).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.PAPAYA_WINE = registerFluid(new Fluid("papaya_wine", STILL, FLOW, 0xFFB37E2C).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.PEACH_WINE = registerFluid(new Fluid("peach_wine", STILL, FLOW, 0xFFD19088).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.PEAR_WINE = registerFluid(new Fluid("pear_Wine", STILL, FLOW, 0xFFF2E4BD).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.PLUM_WINE = registerFluid(new Fluid("plum_wine", STILL, FLOW, 0xFF870910).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.RED_WINE = registerFluid(new Fluid("red_wine", STILL, FLOW, 0xFF5E1224).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.WHEAT_WINE = registerFluid(new Fluid("wheat_wine", STILL, FLOW, 0xFF7C1B18).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty),
                                               FluidsCore.WHITE_WINE = registerFluid(new Fluid("white_wine", STILL, FLOW, 0xFFFCF1D2).setRarity(EnumRarity.UNCOMMON)).with(
                                                 DrinkableProperty.DRINKABLE, fermentedAlcoholProperty)
                                             )
                                             .build();

    DrinkableProperty alcoholProperty = player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC) {
        ((FoodStatsTFC) player.getFoodStats()).addThirst(10);
        if (RNG.nextFloat() < 0.25f) {
          player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1200, 2));
        }
      }
    };
    allAlcoholsFluids = ImmutableSet.<FluidWrapper>builder()
                                    .add(
                                      FluidsCore.CALVADOS = registerFluid(new Fluid("calvados", STILL, FLOW, 0xFFBE2D02).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.BANANA_BRANDY = registerFluid(new Fluid("banana_brandy", STILL, FLOW, 0xFFD49B36).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.BERRY_BRANDY = registerFluid(new Fluid("berry_brandy", STILL, FLOW, 0xFF85413A).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.BRANDY = registerFluid(new Fluid("brandy", STILL, FLOW, 0xFF87413F).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.COGNAC = registerFluid(new Fluid("cognac", STILL, FLOW, 0xFFA3481B).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.GIN = registerFluid(new Fluid("gin", STILL, FLOW, 0xFFDAE2C8).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.CHERRY_BRANDY = registerFluid(new Fluid("cherry_brandy", STILL, FLOW, 0xFFAD495D).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.LEMON_BRANDY = registerFluid(new Fluid("lemon_brandy", STILL, FLOW, 0xFFC89C4E).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.ORANGE_BRANDY = registerFluid(new Fluid("orange_brandy", STILL, FLOW, 0xFFCF7F26).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.PAPAYA_BRANDY = registerFluid(new Fluid("papaya_brandy", STILL, FLOW, 0xFFDF9724).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.PEACH_BRANDY = registerFluid(new Fluid("peach_brandy", STILL, FLOW, 0xFFCA8550).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.PEAR_BRANDY = registerFluid(new Fluid("pear_brandy", STILL, FLOW, 0xFFCC9A48).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.PLUM_BRANDY = registerFluid(new Fluid("plum_brandy", STILL, FLOW, 0xFF941254).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.SHOCHU = registerFluid(new Fluid("shochu", STILL, FLOW, 0xFFF8F9F9).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.TEQUILA = registerFluid(new Fluid("tequila", STILL, FLOW, 0xFFF7D0A1).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.GRAPPA = registerFluid(new Fluid("grappa", STILL, FLOW, 0xFFF7D0A1).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty)
                                    )
                                    .build();

    DrinkableProperty alcoholBeer = player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC) {
        ((FoodStatsTFC) player.getFoodStats()).addThirst(10);
        if (RNG.nextFloat() < 0.25f) {
          player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 1));
        }
      }
    };
    allBeerFluids = ImmutableSet.<FluidWrapper>builder()
                                .add(
                                  FluidsCore.BEER_BARLEY = registerFluid(new Fluid("beer_barley", STILL, FLOW, 0xFFF6B848).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, alcoholBeer),
                                  FluidsCore.BEER_CORN = registerFluid(new Fluid("beer_corn", STILL, FLOW, 0xFFE9BA40).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, alcoholBeer),
                                  FluidsCore.BEER_RYE = registerFluid(new Fluid("beer_rye", STILL, FLOW, 0xFF8B1A02).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, alcoholBeer),
                                  FluidsCore.BEER_WHEAT = registerFluid(new Fluid("beer_wheat", STILL, FLOW, 0xFFE3A926).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, alcoholBeer),
                                  FluidsCore.BEER_AMARANTH = registerFluid(new Fluid("beer_amaranth", STILL, FLOW, 0xFF51150B).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, alcoholBeer),
                                  FluidsCore.BEER_BUCKWHEAT = registerFluid(new Fluid("beer_buckwheat", STILL, FLOW, 0xFFB55506).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, alcoholBeer),
                                  FluidsCore.BEER_FONIO = registerFluid(new Fluid("beer_fonio", STILL, FLOW, 0xFFC28A10).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, alcoholBeer),
                                  FluidsCore.BEER_MILLET = registerFluid(new Fluid("beer_millet", STILL, FLOW, 0xFF982801).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, alcoholBeer),
                                  FluidsCore.BEER_QUINOA = registerFluid(new Fluid("beer_quinoa", STILL, FLOW, 0xFFB46419).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, alcoholBeer),
                                  FluidsCore.BEER_SPELT = registerFluid(new Fluid("beer_spelt", STILL, FLOW, 0xFFC87828).setRarity(EnumRarity.UNCOMMON)).with(
                                    DrinkableProperty.DRINKABLE, alcoholBeer)
                                )
                                .build();

    DrinkableProperty juiceBerryProperty = player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC) {
        ((FoodStatsTFC) player.getFoodStats()).addThirst(15);
        if (RNG.nextFloat() < 0.25f) {
          player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 300, 1));
        }
      }
    };
    allJuiceBerryFluids = ImmutableSet.<FluidWrapper>builder()
                                      .add(
                                        FluidsCore.JUICE_BLACKBERRY = registerFluid(new Fluid("juice_blackberry", STILL, FLOW, 0xFF32001B).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceBerryProperty),
                                        FluidsCore.JUICE_BLUEBERRY = registerFluid(new Fluid("juice_blueberry", STILL, FLOW, 0xFF70324E).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceBerryProperty),
                                        FluidsCore.JUICE_BUNCH_BERRY = registerFluid(
                                          new Fluid("juice_bunch_berry", STILL, FLOW, 0xFFC04C62).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceBerryProperty),
                                        FluidsCore.JUICE_CLOUD_BERRY = registerFluid(
                                          new Fluid("juice_cloud_berry", STILL, FLOW, 0xFFB6B3C0).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceBerryProperty),
                                        FluidsCore.JUICE_CRANBERRY = registerFluid(new Fluid("juice_cranberry", STILL, FLOW, 0xFFCB4C78).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceBerryProperty),
                                        FluidsCore.JUICE_ELDERBERRY = registerFluid(new Fluid("juice_elderberry", STILL, FLOW, 0xFF17182B).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceBerryProperty),
                                        FluidsCore.JUICE_GOOSEBERRY = registerFluid(new Fluid("juice_gooseberry", STILL, FLOW, 0xFFBEC1A4).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceBerryProperty),
                                        FluidsCore.JUICE_RASPBERRY = registerFluid(new Fluid("juice_raspberry", STILL, FLOW, 0xFFE30B5D).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceBerryProperty),
                                        FluidsCore.JUICE_SNOW_BERRY = registerFluid(new Fluid("juice_snow_berry", STILL, FLOW, 0xFFF6F9E1).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceBerryProperty),
                                        FluidsCore.JUICE_STRAWBERRY = registerFluid(new Fluid("juice_strawberry", STILL, FLOW, 0xFFC83F49).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceBerryProperty),
                                        FluidsCore.JUICE_WINTERGREEN_BERRY = registerFluid(
                                          new Fluid("juice_wintergreen_berry", STILL, FLOW, 0xFFEA3441).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceBerryProperty)
                                      )
                                      .build();

    DrinkableProperty juiceFruitProperty = player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC) {
        ((FoodStatsTFC) player.getFoodStats()).addThirst(15);
        if (RNG.nextFloat() < 0.25f) {
          player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 300, 1));
        }
      }
    };
    allJuiceFruitFluids = ImmutableSet.<FluidWrapper>builder()
                                      .add(
                                        FluidsCore.JUICE_AGAVE = registerFluid(new Fluid("juice_agave", STILL, FLOW, 0xFFCCD8D4).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty),
                                        FluidsCore.JUICE_APPLE = registerFluid(new Fluid("juice_apple", STILL, FLOW, 0xFFFEB500).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty),
                                        FluidsCore.JUICE_BANANA = registerFluid(new Fluid("juice_banana", STILL, FLOW, 0xFFFDE39F).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty),
                                        FluidsCore.JUICE_CHERRY = registerFluid(new Fluid("juice_cherry", STILL, FLOW, 0xFF5E1224).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty),
                                        FluidsCore.JUICE_GREEN_GRAPE = registerFluid(
                                          new Fluid("juice_green_grape", STILL, FLOW, 0xFFCCE2A0).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty),
                                        FluidsCore.JUICE_JUNIPER = registerFluid(new Fluid("juice_juniper", STILL, FLOW, 0xFFBDC975).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty),
                                        FluidsCore.JUICE_LEMON = registerFluid(new Fluid("juice_lemon", STILL, FLOW, 0xFFFCFAC9).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty),
                                        FluidsCore.JUICE_ORANGE = registerFluid(new Fluid("juice_orange", STILL, FLOW, 0xFFFCA43C).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty),
                                        FluidsCore.JUICE_PAPAYA = registerFluid(new Fluid("juice_papaya", STILL, FLOW, 0xFFFCA018).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty),
                                        FluidsCore.JUICE_PEACH = registerFluid(new Fluid("juice_peach", STILL, FLOW, 0xFFF7C0A2).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty),
                                        FluidsCore.JUICE_PEAR = registerFluid(new Fluid("juice_pear", STILL, FLOW, 0xFFE9DF96).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty),
                                        FluidsCore.JUICE_PLUM = registerFluid(new Fluid("juice_plum", STILL, FLOW, 0xFF885375).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty),
                                        FluidsCore.JUICE_PURPLE_GRAPE = registerFluid(
                                          new Fluid("juice_purple_grape", STILL, FLOW, 0xFF63344B).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty),
                                        FluidsCore.JUICE_BARREL_CACTUS = registerFluid(
                                          new Fluid("juice_barrel_cactus", STILL, FLOW, 0xFFBBE16A).setRarity(EnumRarity.UNCOMMON)).with(
                                          DrinkableProperty.DRINKABLE, juiceFruitProperty)
                                      )
                                      .build();
  }

  private static FluidWrapper registerFluid(@NotNull Fluid newFluid) {
        /*boolean isDefault = !FluidRegistry.isFluidRegistered(newFluid.getName());

        if (!isDefault)
        {
            // Fluid was already registered with this name, default to that fluid
            newFluid = FluidRegistry.getFluid(newFluid.getName());
        }
        else
        {
            // No fluid found, we are safe to register our default
            FluidRegistry.registerFluid(newFluid);
        }
        FluidRegistry.addBucketForFluid(newFluid);
        FluidWrapper properties = new FluidWrapper(newFluid, isDefault);
        WRAPPERS.put(newFluid, properties);
        return properties;*/

    boolean isDefault = FluidRegistry.registerFluid(newFluid);
    if (!isDefault) {
      newFluid = FluidRegistry.getFluid(newFluid.getName());
    } else {
      // No fluid found, we are safe to register our default
      FluidRegistry.registerFluid(newFluid);
    }
    FluidRegistry.addBucketForFluid(newFluid);
    FluidWrapper properties = FluidsTFC.getWrapper(newFluid);
    FluidsTFC.WRAPPERS.put(newFluid, properties);
    return properties;
  }
}
