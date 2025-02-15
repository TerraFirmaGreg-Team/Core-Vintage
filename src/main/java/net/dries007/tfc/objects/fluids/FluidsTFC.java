package net.dries007.tfc.objects.fluids;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;
import su.terrafirmagreg.modules.core.capabilities.playerdata.CapabilityPlayerData;
import su.terrafirmagreg.modules.core.capabilities.playerdata.ICapabilityPlayerData;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.init.EffectsCore;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;
import su.terrafirmagreg.modules.food.api.IFoodStatsTFC;

import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.EnumRarity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.ConfigTFC.General;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.fluids.properties.MetalProperty;

import lombok.Getter;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFC;

public final class FluidsTFC {

  private static final ResourceLocation STILL = new ResourceLocation(TFC, "blocks/fluid_still");
  private static final ResourceLocation FLOW = new ResourceLocation(TFC, "blocks/fluid_flow");

  private static final ResourceLocation WATER_STILL = new ResourceLocation(TFC, "blocks/water_still");
  private static final ResourceLocation WATER_FLOW = new ResourceLocation(TFC, "blocks/water_flow");
  private static final ResourceLocation FRESH_WATER_STILL = new ResourceLocation(TFC, "blocks/fresh_water_still");
  private static final ResourceLocation FRESH_WATER_FLOW = new ResourceLocation(TFC, "blocks/fresh_water_flow");

  private static final HashBiMap<Fluid, FluidWrapper> WRAPPERS = HashBiMap.create();
  private static final ResourceLocation LAVA_STILL = new ResourceLocation(TFC, "blocks/lava_still");
  private static final ResourceLocation LAVA_FLOW = new ResourceLocation(TFC, "blocks/lava_flow");
  private static final Map<EnumDyeColor, FluidWrapper> DYE_FLUIDS = new EnumMap<>(EnumDyeColor.class);

  @Getter
  private static ImmutableSet<FluidWrapper> allAlcoholsFluids;
  private static ImmutableMap<Metal, FluidWrapper> allMetalFluids;
  @Getter
  private static ImmutableSet<FluidWrapper> allOtherFiniteFluids;

  public static ImmutableCollection<FluidWrapper> getAllMetalFluids() {
    return allMetalFluids.values();
  }

  @Nonnull
  @SuppressWarnings("ConstantConditions")
  public static FluidWrapper getWrapper(@Nonnull Fluid fluid) {
    if (!WRAPPERS.containsKey(fluid)) {
      // Should only ever get called for non-tfc fluids, but in which case prevents a null wrapper getting returned
      WRAPPERS.put(fluid, new FluidWrapper(fluid, false));
    }
    return WRAPPERS.get(fluid);
  }

  @Nonnull
  public static Set<FluidWrapper> getAllWrappers() {
    return WRAPPERS.values();
  }

  @Nonnull
  public static Fluid getFluidFromMetal(@Nonnull Metal metal) {
    return allMetalFluids.get(metal).get();
  }

  @Nonnull
  public static Metal getMetalFromFluid(@Nonnull Fluid fluid) {
    return getWrapper(fluid).get(MetalProperty.METAL).getMetal();
  }

  @Nonnull
  public static FluidWrapper getFluidFromDye(@Nonnull EnumDyeColor dyeColor) {
    return DYE_FLUIDS.get(dyeColor);
  }

  public static void registerFluidsPost() {
//noinspection ConstantConditions
    allMetalFluids = ImmutableMap.<Metal, FluidWrapper>builder().putAll(
      TFCRegistries.METALS.getValuesCollection()
        .stream()
        .collect(Collectors.toMap(
          metal -> metal,
          metal -> registerFluid(new Fluid(metal.getRegistryName().getPath(), LAVA_STILL, LAVA_FLOW, metal.getColor()))
            .with(MetalProperty.METAL, new MetalProperty(metal))
        ))
    ).build();
  }

  public static void registerFluids() {
    FluidsCore.FRESH_WATER = registerFluid(new Fluid("fresh_water", FRESH_WATER_STILL, FRESH_WATER_FLOW, 0xFF296ACD)).with(DrinkableProperty.DRINKABLE, player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC foodStatsTFC) {
        foodStatsTFC.addThirst(40);
      }
    });
    FluidsCore.HOT_WATER = registerFluid(new Fluid("hot_water", WATER_STILL, WATER_FLOW, 0xFF345FDA).setTemperature(350));
    FluidsCore.SALT_WATER = registerFluid(new Fluid("salt_water", WATER_STILL, WATER_FLOW, 0xFF1F5099)).with(DrinkableProperty.DRINKABLE, player -> {
      if (player.getFoodStats() instanceof FoodStatsTFC) {
        ((FoodStatsTFC) player.getFoodStats()).addThirst(-10);
        if (Constants.RNG.nextDouble() < ConfigTFC.General.PLAYER.chanceThirstOnSaltyDrink) {
          player.addPotionEffect(new PotionEffect(EffectsCore.THIRST.get(), 600, 0));
        }
      }
    });

    DrinkableProperty alcoholProperty = player -> {
      ICapabilityPlayerData playerData = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
      if (player.getFoodStats() instanceof FoodStatsTFC && playerData != null) {
        ((FoodStatsTFC) player.getFoodStats()).addThirst(10);
        playerData.addIntoxicatedTime(4 * ICalendar.TICKS_IN_HOUR);
        if (playerData.getIntoxicatedTime() > 24 * ICalendar.TICKS_IN_HOUR && Constants.RNG.nextFloat() < 0.5f) {
          player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1200, 1));
        }
      }
    };

    allAlcoholsFluids = ImmutableSet.<FluidWrapper>builder().add(
      FluidsCore.RUM = registerFluid(new Fluid("rum", STILL, FLOW, 0xFF6E0123).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
      FluidsCore.BEER = registerFluid(new Fluid("beer", STILL, FLOW, 0xFFC39E37).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
      FluidsCore.WHISKEY = registerFluid(new Fluid("whiskey", STILL, FLOW, 0xFF583719).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
      FluidsCore.RYE_WHISKEY = registerFluid(new Fluid("rye_whiskey", STILL, FLOW, 0xFFC77D51).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
      FluidsCore.CORN_WHISKEY = registerFluid(new Fluid("corn_whiskey", STILL, FLOW, 0xFFD9C7B7).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
      FluidsCore.SAKE = registerFluid(new Fluid("sake", STILL, FLOW, 0xFFB7D9BC).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
      FluidsCore.VODKA = registerFluid(new Fluid("vodka", STILL, FLOW, 0xFFDCDCDC).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),
      FluidsCore.CIDER = registerFluid(new Fluid("cider", STILL, FLOW, 0xFFB0AE32).setRarity(EnumRarity.UNCOMMON)).with(DrinkableProperty.DRINKABLE, alcoholProperty),

      FluidsCore.AGED_RUM = createAgedDrink("rum", MobEffects.JUMP_BOOST, General.PLAYER.jumpBoostTicks, General.PLAYER.jumpBoostLevel),
      FluidsCore.AGED_BEER = createAgedDrink("beer", MobEffects.SPEED, General.PLAYER.speedTicks, General.PLAYER.speedLevel),
      FluidsCore.AGED_WHISKEY = createAgedDrink("whiskey", MobEffects.HASTE, General.PLAYER.hasteTicks, General.PLAYER.hasteLevel),
      FluidsCore.AGED_RYE_WHISKEY = createAgedDrink("rye_whiskey", MobEffects.HASTE, General.PLAYER.hasteTicks, General.PLAYER.hasteLevel),
      FluidsCore.AGED_CORN_WHISKEY = createAgedDrink("corn_whiskey", MobEffects.HASTE, General.PLAYER.hasteTicks, General.PLAYER.hasteLevel),
      FluidsCore.AGED_SAKE = createAgedDrink("sake", MobEffects.STRENGTH, General.PLAYER.strengthTicks, General.PLAYER.strengthLevel),
      FluidsCore.AGED_VODKA = createAgedDrink("vodka", MobEffects.RESISTANCE, General.PLAYER.resistanceTicks, General.PLAYER.resistanceLevel),
      FluidsCore.AGED_CIDER = createAgedDrink("cider", MobEffects.SPEED, General.PLAYER.speedTicks, General.PLAYER.speedLevel)
    ).build();

    allOtherFiniteFluids = ImmutableSet.<FluidWrapper>builder().add(
      FluidsCore.VINEGAR = registerFluid(new Fluid("vinegar", STILL, FLOW, 0xFFC7C2AA)),
      FluidsCore.BRINE = registerFluid(new Fluid("brine", STILL, FLOW, 0xFFDCD3C9)),
      FluidsCore.MILK = registerFluid(new Fluid("milk", STILL, FLOW, 0xFFFFFFFF)).with(DrinkableProperty.DRINKABLE, player -> {
        if (player.getFoodStats() instanceof IFoodStatsTFC foodStats) {
          foodStats.addThirst(10);
          foodStats.getNutrition().addBuff(FoodData.MILK);
        }
      }),
      FluidsCore.OLIVE_OIL = registerFluid(new Fluid("olive_oil", STILL, FLOW, 0xFF6A7537).setRarity(EnumRarity.RARE)),
      FluidsCore.OLIVE_OIL_WATER = registerFluid(new Fluid("olive_oil_water", STILL, FLOW, 0xFF4A4702)),
      FluidsCore.TANNIN = registerFluid(new Fluid("tannin", STILL, FLOW, 0xFF63594E)),
      FluidsCore.LIMEWATER = registerFluid(new Fluid("limewater", STILL, FLOW, 0xFFB4B4B4)),
      FluidsCore.CURDLED_MILK = registerFluid(new Fluid("milk_curdled", STILL, FLOW, 0xFFFFFBE8)),
      FluidsCore.MILK_VINEGAR = registerFluid(new Fluid("milk_vinegar", STILL, FLOW, 0xFFFFFBE8)),
      FluidsCore.LYE = registerFluid(new Fluid("lye", STILL, FLOW, 0xFFfeffde)),
      FluidsCore.TEA = registerFluid(new Fluid("tea", STILL, FLOW, 0xFF1C120B)).with(DrinkableProperty.DRINKABLE, player -> {
        if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
          foodStats.addThirst(40);
          player.addPotionEffect(new PotionEffect(EffectsCore.CAFFEINE.get(), 14400, 0));
        }
      }),
      FluidsCore.SWEET_TEA = registerFluid(new Fluid("sweet_tea", STILL, FLOW, 0xFF1C120B)).with(DrinkableProperty.DRINKABLE, player -> {
        if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
          foodStats.addThirst(40);
          player.addPotionEffect(new PotionEffect(EffectsCore.CAFFEINE.get(), 14400, 1));
        }
      }),
      FluidsCore.SWEET_COFFEE = registerFluid(new Fluid("sweet_coffee", STILL, FLOW, 0xFF210B00)).with(DrinkableProperty.DRINKABLE, player -> {
        if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
          foodStats.addThirst(40);
          player.addPotionEffect(new PotionEffect(EffectsCore.CAFFEINE.get(), 14400, 3));
        }
      })
    ).build();

    DYE_FLUIDS.putAll(Arrays.stream(EnumDyeColor.values()).collect(Collectors.toMap(
      color -> color,
      color -> {
        float[] c = color.getColorComponentValues();
        String actualName = color == EnumDyeColor.SILVER ? "light_gray" : color.getName();
        return registerFluid(new Fluid(actualName + "_dye", STILL, FLOW, new Color(c[0], c[1], c[2]).getRGB()));
      })));
  }

  public static FluidWrapper createAgedDrink(String name, Potion potion, int duration, int amplifier) {
    Fluid youngFluid = FluidRegistry.getFluid(name);
    DrinkableProperty property = (player) -> {
      player.addPotionEffect(new PotionEffect(potion, duration, amplifier, false, false));
      if (General.PLAYER.enableDrunkness) {
        if (Math.random() < 0.25) {
          PotionEffect effect = new PotionEffect(MobEffects.NAUSEA, 1500, 0, false, false);
          effect.setCurativeItems(new ArrayList<>());
          player.addPotionEffect(effect);
        }
      }
    };

    Fluid agedFluid = new Fluid("aged_" + name, STILL, FLOW, youngFluid.getColor()).setRarity(EnumRarity.UNCOMMON);

    return registerFluid(agedFluid).with(DrinkableProperty.DRINKABLE, property);
  }

  @Nonnull
  private static FluidWrapper registerFluid(@Nonnull Fluid newFluid) {
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
