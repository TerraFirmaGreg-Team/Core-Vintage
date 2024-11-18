package net.dries007.tfc.objects.fluids;

import su.terrafirmagreg.api.util.MathUtils;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;
import su.terrafirmagreg.modules.core.capabilities.player.CapabilityPlayer;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.init.FluidsCore;
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
import net.dries007.caffeineaddon.potion.PotionEffects;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.fluids.properties.MetalProperty;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.awt.Color;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static su.terrafirmagreg.api.data.Reference.MODID_TFC;

public final class FluidsTFC {

  public static final ResourceLocation STILL = new ResourceLocation(MODID_TFC, "blocks/fluid_still");
  public static final ResourceLocation FLOW = new ResourceLocation(MODID_TFC, "blocks/fluid_flow");

  public static final HashBiMap<Fluid, FluidWrapper> WRAPPERS = HashBiMap.create();
  private static final ResourceLocation LAVA_STILL = new ResourceLocation(MODID_TFC, "blocks/lava_still");
  private static final ResourceLocation LAVA_FLOW = new ResourceLocation(MODID_TFC, "blocks/lava_flow");
  private static final Map<EnumDyeColor, FluidWrapper> DYE_FLUIDS = new EnumMap<>(EnumDyeColor.class);

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
    FluidsCore.FRESH_WATER = registerFluid(new Fluid("fresh_water", STILL, FLOW, 0xFF296ACD))
      .with(DrinkableProperty.DRINKABLE, player -> {
        if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
          foodStats.addThirst(40);
        }
      });
    FluidsCore.HOT_WATER = registerFluid(new Fluid("hot_water", STILL, FLOW, 0xFF345FDA).setTemperature(350));
    FluidsCore.SALT_WATER = registerFluid(new Fluid("salt_water", STILL, FLOW, 0xFF1F5099)).with(DrinkableProperty.DRINKABLE, player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
        foodStats.addThirst(-10);
        if (MathUtils.RNG.nextDouble() < ConfigTFC.General.PLAYER.chanceThirstOnSaltyDrink) {
          player.addPotionEffect(new PotionEffect(PotionsCore.THIRST, 600, 0));
        }
      }
    });

    DrinkableProperty alcoholProperty = player -> {
      var cap = CapabilityPlayer.get(player);
      if (player.getFoodStats() instanceof FoodStatsTFC foodStats && cap != null) {
        foodStats.addThirst(10);
        cap.addIntoxicatedTime(4 * ICalendar.TICKS_IN_HOUR);
        if (cap.getIntoxicatedTime() > 24 * ICalendar.TICKS_IN_HOUR && MathUtils.RNG.nextFloat() < 0.5f) {
          player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1200, 1));
        }

      }
    };

    allAlcoholsFluids = ImmutableSet.<FluidWrapper>builder().add(
                                      FluidsCore.RUM = registerFluid(new Fluid("rum", STILL, FLOW, 0xFF6E0123).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.BEER = registerFluid(new Fluid("beer", STILL, FLOW, 0xFFC39E37).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.WHISKEY = registerFluid(new Fluid("whiskey", STILL, FLOW, 0xFF583719).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.RYE_WHISKEY = registerFluid(new Fluid("rye_whiskey", STILL, FLOW, 0xFFC77D51).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.CORN_WHISKEY = registerFluid(new Fluid("corn_whiskey", STILL, FLOW, 0xFFD9C7B7).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.SAKE = registerFluid(new Fluid("sake", STILL, FLOW, 0xFFB7D9BC).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.VODKA = registerFluid(new Fluid("vodka", STILL, FLOW, 0xFFDCDCDC).setRarity(EnumRarity.UNCOMMON)).with(
                                        DrinkableProperty.DRINKABLE, alcoholProperty),
                                      FluidsCore.CIDER = registerFluid(new Fluid("cider", STILL, FLOW, 0xFFB0AE32).setRarity(EnumRarity.UNCOMMON)).with(
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
                                         FluidsCore.VINEGAR = registerFluid(new Fluid("vinegar", STILL, FLOW, 0xFFC7C2AA)),
                                         FluidsCore.BRINE = registerFluid(new Fluid("brine", STILL, FLOW, 0xFFDCD3C9)),
                                         FluidsCore.MILK = registerFluid(new Fluid("milk", STILL, FLOW, 0xFFFFFFFF)).with(DrinkableProperty.DRINKABLE, milkProperty),
                                         FluidsCore.OLIVE_OIL = registerFluid(new Fluid("olive_oil", STILL, FLOW, 0xFF6A7537).setRarity(EnumRarity.RARE)),
                                         FluidsCore.OLIVE_OIL_WATER = registerFluid(new Fluid("olive_oil_water", STILL, FLOW, 0xFF4A4702)),
                                         FluidsCore.TANNIN = registerFluid(new Fluid("tannin", STILL, FLOW, 0xFF63594E)),
                                         FluidsCore.LIMEWATER = registerFluid(new Fluid("limewater", STILL, FLOW, 0xFFB4B4B4)),
                                         FluidsCore.CURDLED_MILK = registerFluid(new Fluid("milk_curdled", STILL, FLOW, 0xFFFFFBE8)),
                                         FluidsCore.MILK_VINEGAR = registerFluid(new Fluid("milk_vinegar", STILL, FLOW, 0xFFFFFBE8)),
                                         FluidsCore.LYE = registerFluid(new Fluid("lye", STILL, FLOW, 0xFFfeffde)),
                                         FluidsCore.YEAST_STARTER = registerFluid(new Fluid("yeast_starter", STILL, FLOW, 0xFFa79464)),
                                         FluidsCore.COCONUT_MILK = registerFluid(new Fluid("coconut_milk", STILL, FLOW, 0xFFfcfae2)).with(DrinkableProperty.DRINKABLE, milkProperty),
                                         FluidsCore.YAK_MILK = registerFluid(new Fluid("yak_milk", STILL, FLOW, 0xFFfcfaec)).with(DrinkableProperty.DRINKABLE, milkProperty),
                                         FluidsCore.GOAT_MILK = registerFluid(new Fluid("goat_milk", STILL, FLOW, 0xFFf6f6eb)).with(DrinkableProperty.DRINKABLE, milkProperty),
                                         FluidsCore.ZEBU_MILK = registerFluid(new Fluid("zebu_milk", STILL, FLOW, 0xFFefede6)).with(DrinkableProperty.DRINKABLE, milkProperty),
                                         FluidsCore.CURDLED_YAK_MILK = registerFluid(new Fluid("curdled_yak_milk", STILL, FLOW, 0xFFf9f4d6)),
                                         FluidsCore.CURDLED_GOAT_MILK = registerFluid(new Fluid("curdled_goat_milk", STILL, FLOW, 0xFFeeeed9)),
                                         FluidsCore.PINA_COLADA = registerFluid(new Fluid("pina_colada", STILL, FLOW, 0xFFE4C06A)).with(DrinkableProperty.DRINKABLE, player -> {
                                           if (player.getFoodStats() instanceof IFoodStatsTFC foodStats) {
                                             foodStats.addThirst(10);
                                             foodStats.getNutrition().addBuff(FoodData.MILK);
                                             foodStats.getNutrition().addBuff(FoodData.GOLDEN_CARROT);
                                             player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 100, 0));
                                             player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
                                           }
                                         }),
                                         FluidsCore.LATEX = registerFluid(new Fluid("latex", STILL, FLOW, 0xFFF8F8F8)),
                                         FluidsCore.GLASS = registerFluid(new Fluid("glass", STILL, FLOW, 0xFFED97B5).setTemperature(1073)),

                                         FluidsCore.TEA = registerFluid(new Fluid("tea", STILL, FLOW, 0xFF1C120B)).with(DrinkableProperty.DRINKABLE, player -> {
                                           if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
                                             foodStats.addThirst(40);
                                             player.addPotionEffect(new PotionEffect(PotionEffects.CAFFEINE, 14400, 0));
                                           }
                                         }),
                                         FluidsCore.SWEET_TEA = registerFluid(new Fluid("sweet_tea", STILL, FLOW, 0xFF1C120B)).with(DrinkableProperty.DRINKABLE, player -> {
                                           if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
                                             foodStats.addThirst(40);
                                             player.addPotionEffect(new PotionEffect(PotionEffects.CAFFEINE, 14400, 1));
                                           }
                                         }),
                                         FluidsCore.COFFEE = registerFluid(new Fluid("coffee", STILL, FLOW, 0xFF210B00)).with(DrinkableProperty.DRINKABLE, player -> {
                                           if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
                                             foodStats.addThirst(40);
                                             player.addPotionEffect(new PotionEffect(PotionEffects.CAFFEINE, 14400, 2));
                                           }
                                         }),
                                         FluidsCore.SWEET_COFFEE = registerFluid(new Fluid("sweet_coffee", STILL, FLOW, 0xFF210B00)).with(DrinkableProperty.DRINKABLE, player -> {
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
