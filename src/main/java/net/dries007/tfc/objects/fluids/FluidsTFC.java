package net.dries007.tfc.objects.fluids;

import su.terrafirmagreg.data.MathConstants;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;
import su.terrafirmagreg.modules.core.capabilities.player.CapabilityPlayer;
import su.terrafirmagreg.modules.core.init.PotionsCore;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;
import su.terrafirmagreg.modules.food.api.IFoodStatsTFC;

import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.EnumRarity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.fluids.properties.MetalProperty;
import net.dries007.tfc.util.calendar.ICalendar;
import pieman.caffeineaddon.potion.PotionEffects;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.awt.Color;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static su.terrafirmagreg.data.Constants.MODID_TFC;

public final class FluidsTFC {

  private static final ResourceLocation STILL = new ResourceLocation(MODID_TFC, "blocks/fluid_still");
  private static final ResourceLocation FLOW = new ResourceLocation(MODID_TFC, "blocks/fluid_flow");

  private static final HashBiMap<Fluid, FluidWrapper> WRAPPERS = HashBiMap.create();
  private static final ResourceLocation LAVA_STILL = new ResourceLocation(MODID_TFC, "blocks/lava_still");
  private static final ResourceLocation LAVA_FLOW = new ResourceLocation(MODID_TFC, "blocks/lava_flow");
  private static final Map<EnumDyeColor, FluidWrapper> DYE_FLUIDS = new EnumMap<>(EnumDyeColor.class);

  // Water variants
  public static FluidWrapper HOT_WATER;
  public static FluidWrapper FRESH_WATER;
  public static FluidWrapper SALT_WATER;

  // Other fluids
  public static FluidWrapper LATEX;
  public static FluidWrapper GLASS;
  public static FluidWrapper LIMEWATER;
  public static FluidWrapper TANNIN;
  public static FluidWrapper VINEGAR;
  public static FluidWrapper BRINE;
  public static FluidWrapper MILK;
  public static FluidWrapper CURDLED_MILK;
  public static FluidWrapper MILK_VINEGAR;
  public static FluidWrapper OLIVE_OIL;
  public static FluidWrapper OLIVE_OIL_WATER;
  public static FluidWrapper LYE;
  public static FluidWrapper YEAST_STARTER;
  public static FluidWrapper COCONUT_MILK;
  public static FluidWrapper YAK_MILK;
  public static FluidWrapper GOAT_MILK;
  public static FluidWrapper ZEBU_MILK;
  public static FluidWrapper CURDLED_YAK_MILK;
  public static FluidWrapper CURDLED_GOAT_MILK;
  public static FluidWrapper PINA_COLADA;
  public static FluidWrapper COFFEE;
  public static FluidWrapper SWEET_COFFEE;
  public static FluidWrapper TEA;
  public static FluidWrapper SWEET_TEA;

  // Alcohols
  public static FluidWrapper CIDER;
  public static FluidWrapper VODKA;
  public static FluidWrapper SAKE;
  public static FluidWrapper CORN_WHISKEY;
  public static FluidWrapper RYE_WHISKEY;
  public static FluidWrapper WHISKEY;
  public static FluidWrapper BEER;
  public static FluidWrapper RUM;

  // Other
  public static FluidWrapper DISTILLED_WATER;
  public static FluidWrapper WASTE;
  public static FluidWrapper BASE_POTASH_LIQUOR;

  // Tea
  public static FluidWrapper WHITE_TEA;
  public static FluidWrapper GREEN_TEA;
  public static FluidWrapper BLACK_TEA;
  public static FluidWrapper CHAMOMILE_TEA;
  public static FluidWrapper DANDELION_TEA;
  public static FluidWrapper LABRADOR_TEA;

  // Coffee & Coke
  public static FluidWrapper FIRMA_COLA; //Obviously a reference to Coca Cola.

  // Fermented Alcohols
  public static FluidWrapper AGAVE_WINE;
  public static FluidWrapper BARLEY_WINE;
  public static FluidWrapper BANANA_WINE;
  public static FluidWrapper BERRY_WINE;
  public static FluidWrapper CHERRY_WINE;
  public static FluidWrapper JUNIPER_WINE;
  public static FluidWrapper LEMON_WINE;
  public static FluidWrapper ORANGE_WINE;
  public static FluidWrapper PAPAYA_WINE;
  public static FluidWrapper PEACH_WINE;
  public static FluidWrapper PEAR_WINE;
  public static FluidWrapper PLUM_WINE;
  public static FluidWrapper MEAD;
  public static FluidWrapper RED_WINE;
  public static FluidWrapper WHEAT_WINE;
  public static FluidWrapper WHITE_WINE;

  // Alcohols
  public static FluidWrapper CALVADOS;
  public static FluidWrapper GIN;
  public static FluidWrapper TEQUILA;
  public static FluidWrapper SHOCHU;
  public static FluidWrapper BANANA_BRANDY;
  public static FluidWrapper CHERRY_BRANDY;
  public static FluidWrapper LEMON_BRANDY;
  public static FluidWrapper ORANGE_BRANDY;
  public static FluidWrapper PAPAYA_BRANDY;
  public static FluidWrapper PEACH_BRANDY;
  public static FluidWrapper PEAR_BRANDY;
  public static FluidWrapper PLUM_BRANDY;
  public static FluidWrapper BERRY_BRANDY;
  public static FluidWrapper BRANDY;
  public static FluidWrapper COGNAC;
  public static FluidWrapper GRAPPA;

  // Beer
  public static FluidWrapper BEER_BARLEY;
  public static FluidWrapper BEER_CORN;
  public static FluidWrapper BEER_RYE;
  public static FluidWrapper BEER_WHEAT;
  public static FluidWrapper BEER_AMARANTH;
  public static FluidWrapper BEER_BUCKWHEAT;
  public static FluidWrapper BEER_FONIO;
  public static FluidWrapper BEER_MILLET;
  public static FluidWrapper BEER_QUINOA;
  public static FluidWrapper BEER_SPELT;

  // Misc
  public static FluidWrapper SUGAR_WATER;
  public static FluidWrapper HONEY_WATER;
  public static FluidWrapper RICE_WATER;
  public static FluidWrapper SOYBEAN_WATER;
  public static FluidWrapper LINSEED_WATER;
  public static FluidWrapper RAPE_SEED_WATER;
  public static FluidWrapper SUNFLOWER_SEED_WATER;
  public static FluidWrapper OPIUM_POPPY_SEED_WATER;
  public static FluidWrapper SUGAR_BEET_WATER;
  public static FluidWrapper SOY_MILK;
  public static FluidWrapper LINSEED_OIL;
  public static FluidWrapper RAPE_SEED_OIL;
  public static FluidWrapper SUNFLOWER_SEED_OIL;
  public static FluidWrapper OPIUM_POPPY_SEED_OIL;
  public static FluidWrapper WORT;

  // Juice - Berries
  public static FluidWrapper JUICE_BLACKBERRY;
  public static FluidWrapper JUICE_BLUEBERRY;
  public static FluidWrapper JUICE_BUNCH_BERRY;
  public static FluidWrapper JUICE_CLOUD_BERRY;
  public static FluidWrapper JUICE_CRANBERRY;
  public static FluidWrapper JUICE_ELDERBERRY;
  public static FluidWrapper JUICE_GOOSEBERRY;
  public static FluidWrapper JUICE_RASPBERRY;
  public static FluidWrapper JUICE_SNOW_BERRY;
  public static FluidWrapper JUICE_STRAWBERRY;
  public static FluidWrapper JUICE_WINTERGREEN_BERRY;

  // Juice - Fruits
  public static FluidWrapper JUICE_AGAVE;
  public static FluidWrapper JUICE_APPLE;
  public static FluidWrapper JUICE_BANANA;
  public static FluidWrapper JUICE_CHERRY;
  public static FluidWrapper JUICE_GREEN_GRAPE;
  public static FluidWrapper JUICE_JUNIPER;
  public static FluidWrapper JUICE_LEMON;
  public static FluidWrapper JUICE_ORANGE;
  public static FluidWrapper JUICE_PAPAYA;
  public static FluidWrapper JUICE_PEACH;
  public static FluidWrapper JUICE_PEAR;
  public static FluidWrapper JUICE_PLUM;
  public static FluidWrapper JUICE_PURPLE_GRAPE;
  public static FluidWrapper JUICE_BARREL_CACTUS;

  @Getter
  private static ImmutableSet<FluidWrapper> allAlcoholsFluids;
  private static ImmutableMap<Metal, FluidWrapper> allMetalFluids;
  @Getter
  private static ImmutableSet<FluidWrapper> allOtherFiniteFluids;

  public static ImmutableCollection<FluidWrapper> getAllMetalFluids() {
    return allMetalFluids.values();
  }

  @NotNull
  public static Set<FluidWrapper> getAllWrappers() {
    return WRAPPERS.values();
  }

  @NotNull
  public static Fluid getFluidFromMetal(@NotNull Metal metal) {
    return allMetalFluids.get(metal).get();
  }

  @NotNull
  public static Metal getMetalFromFluid(@NotNull Fluid fluid) {
    return getWrapper(fluid).get(MetalProperty.METAL).getMetal();
  }

  @NotNull
  @SuppressWarnings("ConstantConditions")
  public static FluidWrapper getWrapper(@NotNull Fluid fluid) {
    if (!WRAPPERS.containsKey(fluid)) {
      // Should only ever get called for non-tfc fluids, but in which case prevents a null wrapper getting returned
      WRAPPERS.put(fluid, new FluidWrapper(fluid, false));
    }
    return WRAPPERS.get(fluid);
  }

  @NotNull
  public static FluidWrapper getFluidFromDye(@NotNull EnumDyeColor dyeColor) {
    return DYE_FLUIDS.get(dyeColor);
  }

  public static void registerFluids() {
    FRESH_WATER = registerFluid(new Fluid("fresh_water", STILL, FLOW, 0xFF296ACD))
      .with(DrinkableProperty.DRINKABLE, player -> {
        if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
          foodStats.addThirst(40);
        }
      });
    HOT_WATER = registerFluid(new Fluid("hot_water", STILL, FLOW, 0xFF345FDA).setTemperature(350));
    SALT_WATER = registerFluid(new Fluid("salt_water", STILL, FLOW, 0xFF1F5099)).with(DrinkableProperty.DRINKABLE, player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
        foodStats.addThirst(-10);
        if (MathConstants.RNG.nextDouble() < ConfigTFC.General.PLAYER.chanceThirstOnSaltyDrink) {
          player.addPotionEffect(new PotionEffect(PotionsCore.THIRST, 600, 0));
        }
      }
    });

    DrinkableProperty alcoholProperty = player -> {
      var cap = CapabilityPlayer.get(player);
      if (player.getFoodStats() instanceof FoodStatsTFC foodStats && cap != null) {
        foodStats.addThirst(10);
        cap.addIntoxicatedTime(4 * ICalendar.TICKS_IN_HOUR);
        if (cap.getIntoxicatedTime() > 24 * ICalendar.TICKS_IN_HOUR && MathConstants.RNG.nextFloat() < 0.5f) {
          player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1200, 1));
        }

      }
    };

    allAlcoholsFluids = ImmutableSet.<FluidWrapper>builder()
                                    .add(
                                      RUM = registerFluid(new Fluid("rum", STILL, FLOW, 0xFF6E0123).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      BEER = registerFluid(new Fluid("beer", STILL, FLOW, 0xFFC39E37).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      WHISKEY = registerFluid(new Fluid("whiskey", STILL, FLOW, 0xFF583719).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      RYE_WHISKEY = registerFluid(new Fluid("rye_whiskey", STILL, FLOW, 0xFFC77D51).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      CORN_WHISKEY = registerFluid(new Fluid("corn_whiskey", STILL, FLOW, 0xFFD9C7B7).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      SAKE = registerFluid(new Fluid("sake", STILL, FLOW, 0xFFB7D9BC).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      VODKA = registerFluid(new Fluid("vodka", STILL, FLOW, 0xFFDCDCDC).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      CIDER = registerFluid(new Fluid("cider", STILL, FLOW, 0xFFB0AE32).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty)
                                    )
                                    .build();

    DrinkableProperty milkProperty = player -> {
      if (player.getFoodStats() instanceof IFoodStatsTFC foodStats) {
        foodStats.addThirst(10);
        foodStats.getNutrition().addBuff(FoodData.MILK);
      }
    };

    allOtherFiniteFluids = ImmutableSet.<FluidWrapper>builder()
                                       .add(
                                         VINEGAR = registerFluid(new Fluid("vinegar", STILL, FLOW, 0xFFC7C2AA)),
                                         BRINE = registerFluid(new Fluid("brine", STILL, FLOW, 0xFFDCD3C9)),
                                         MILK = registerFluid(new Fluid("milk", STILL, FLOW, 0xFFFFFFFF)).with(DrinkableProperty.DRINKABLE, milkProperty),
                                         OLIVE_OIL = registerFluid(new Fluid("olive_oil", STILL, FLOW, 0xFF6A7537).setRarity(EnumRarity.RARE)),
                                         OLIVE_OIL_WATER = registerFluid(new Fluid("olive_oil_water", STILL, FLOW, 0xFF4A4702)),
                                         TANNIN = registerFluid(new Fluid("tannin", STILL, FLOW, 0xFF63594E)),
                                         LIMEWATER = registerFluid(new Fluid("limewater", STILL, FLOW, 0xFFB4B4B4)),
                                         CURDLED_MILK = registerFluid(new Fluid("milk_curdled", STILL, FLOW, 0xFFFFFBE8)),
                                         MILK_VINEGAR = registerFluid(new Fluid("milk_vinegar", STILL, FLOW, 0xFFFFFBE8)),
                                         LYE = registerFluid(new Fluid("lye", STILL, FLOW, 0xFFfeffde)),
                                         YEAST_STARTER = registerFluid(new Fluid("yeast_starter", STILL, FLOW, 0xFFa79464)),
                                         COCONUT_MILK = registerFluid(new Fluid("coconut_milk", STILL, FLOW, 0xFFfcfae2)).with(DrinkableProperty.DRINKABLE, milkProperty),
                                         YAK_MILK = registerFluid(new Fluid("yak_milk", STILL, FLOW, 0xFFfcfaec)).with(DrinkableProperty.DRINKABLE, milkProperty),
                                         GOAT_MILK = registerFluid(new Fluid("goat_milk", STILL, FLOW, 0xFFf6f6eb)).with(DrinkableProperty.DRINKABLE, milkProperty),
                                         ZEBU_MILK = registerFluid(new Fluid("zebu_milk", STILL, FLOW, 0xFFefede6)).with(DrinkableProperty.DRINKABLE, milkProperty),
                                         CURDLED_YAK_MILK = registerFluid(new Fluid("curdled_yak_milk", STILL, FLOW, 0xFFf9f4d6)),
                                         CURDLED_GOAT_MILK = registerFluid(new Fluid("curdled_goat_milk", STILL, FLOW, 0xFFeeeed9)),
                                         PINA_COLADA = registerFluid(new Fluid("pina_colada", STILL, FLOW, 0xFFE4C06A)).with(DrinkableProperty.DRINKABLE, player -> {
                                           if (player.getFoodStats() instanceof IFoodStatsTFC foodStats) {
                                             foodStats.addThirst(10);
                                             foodStats.getNutrition().addBuff(FoodData.MILK);
                                             foodStats.getNutrition().addBuff(FoodData.GOLDEN_CARROT);
                                             player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 100, 0));
                                             player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
                                           }
                                         }),
                                         LATEX = registerFluid(new Fluid("latex", STILL, FLOW, 0xFFF8F8F8)),
                                         GLASS = registerFluid(new Fluid("glass", STILL, FLOW, 0xFFED97B5).setTemperature(1073)),

                                         TEA = registerFluid(new Fluid("tea", STILL, FLOW, 0xFF1C120B)).with(DrinkableProperty.DRINKABLE, player -> {
                                           if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
                                             foodStats.addThirst(40);
                                             player.addPotionEffect(new PotionEffect(PotionEffects.CAFFEINE, 14400, 0));
                                           }
                                         }),
                                         SWEET_TEA = registerFluid(new Fluid("sweet_tea", STILL, FLOW, 0xFF1C120B)).with(DrinkableProperty.DRINKABLE, player -> {
                                           if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
                                             foodStats.addThirst(40);
                                             player.addPotionEffect(new PotionEffect(PotionEffects.CAFFEINE, 14400, 1));
                                           }
                                         }),
                                         COFFEE = registerFluid(new Fluid("coffee", STILL, FLOW, 0xFF210B00)).with(DrinkableProperty.DRINKABLE, player -> {
                                           if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
                                             foodStats.addThirst(40);
                                             player.addPotionEffect(new PotionEffect(PotionEffects.CAFFEINE, 14400, 2));
                                           }
                                         }),
                                         SWEET_COFFEE = registerFluid(new Fluid("sweet_coffee", STILL, FLOW, 0xFF210B00)).with(DrinkableProperty.DRINKABLE, player -> {
                                           if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
                                             foodStats.addThirst(40);
                                             player.addPotionEffect(new PotionEffect(PotionEffects.CAFFEINE, 14400, 3));
                                           }
                                         })
                                       )
                                       .build();

    //noinspection ConstantConditions
    allMetalFluids = ImmutableMap.<Metal, FluidWrapper>builder()
                                 .putAll(
                                   TFCRegistries.METALS.getValuesCollection()
                                                       .stream()
                                                       .collect(Collectors.toMap(
                                                         metal -> metal,
                                                         metal -> registerFluid(new Fluid(metal.getRegistryName()
                                                                                               .getPath(), LAVA_STILL, LAVA_FLOW, metal.getColor())).with(MetalProperty.METAL,
                                                                                                                                                          new MetalProperty(metal))
                                                       ))
                                 )
                                 .build();

    DYE_FLUIDS.putAll(Arrays.stream(EnumDyeColor.values()).collect(Collectors.toMap(
      color -> color,
      color -> {
        float[] c = color.getColorComponentValues();
        String actualName = color == EnumDyeColor.SILVER ? "light_gray" : color.getName();
        return registerFluid(new Fluid(actualName + "_dye", STILL, FLOW, new Color(c[0], c[1], c[2]).getRGB()));
      })));
  }

  @NotNull
  private static FluidWrapper registerFluid(@NotNull Fluid newFluid) {
    boolean isDefault = !FluidRegistry.isFluidRegistered(newFluid.getName());

    if (!isDefault) {
      // Fluid was already registered with this name, default to that fluid
      newFluid = FluidRegistry.getFluid(newFluid.getName());
    } else {
      // No fluid found we are safe to register our default
      FluidRegistry.registerFluid(newFluid);
    }
    FluidRegistry.addBucketForFluid(newFluid);
    FluidWrapper properties = new FluidWrapper(newFluid, isDefault);
    WRAPPERS.put(newFluid, properties);
    return properties;
  }
}
