package net.dries007.firmalife.registry;

import su.terrafirmagreg.api.data.enums.Mods;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.food.api.IFoodStatsTFC;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableSet;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;

import javax.annotation.Nonnull;

//duplicating the fluid registration logic from TFC as not to create conflicts
public final class FluidsFL {

  private static final ResourceLocation STILL = new ResourceLocation(Mods.ModIDs.TFC, "blocks/fluid_still");
  private static final ResourceLocation FLOW = new ResourceLocation(Mods.ModIDs.TFC, "blocks/fluid_flow");

  private static final HashBiMap<Fluid, FluidWrapper> WRAPPERS = HashBiMap.create();

  private static ImmutableSet<FluidWrapper> allFiniteFluids;

  public static ImmutableSet<FluidWrapper> getAllFiniteFluids() {
    return allFiniteFluids;
  }

  public static void registerFluids() {
    DrinkableProperty milkProperty = player -> {
      if (player.getFoodStats() instanceof IFoodStatsTFC foodStats) {
        foodStats.addThirst(10);
        foodStats.getNutrition().addBuff(FoodData.MILK);
      }
    };

    allFiniteFluids = ImmutableSet.<FluidWrapper>builder().add(
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
      })
    ).build();
  }

  private static FluidWrapper registerFluid(@Nonnull Fluid newFluid) {
    boolean isDefault = !FluidRegistry.isFluidRegistered(newFluid.getName());

    if (!isDefault) {
      // Fluid was already registered with this name, default to that fluid
      newFluid = FluidRegistry.getFluid(newFluid.getName());
    } else {
      // No fluid found, we are safe to register our default
      FluidRegistry.registerFluid(newFluid);
    }
    FluidRegistry.addBucketForFluid(newFluid);
    FluidWrapper wrapper = FluidsTFC.getWrapper(newFluid);
    WRAPPERS.put(newFluid, wrapper);
    return wrapper;
  }
}
