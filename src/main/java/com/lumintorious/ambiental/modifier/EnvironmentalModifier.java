package com.lumintorious.ambiental.modifier;

import com.lumintorious.ambiental.TFCAmbientalConfig;
import com.lumintorious.ambiental.api.IEnvironmentalTemperatureProvider;
import com.lumintorious.ambiental.api.AmbientalRegistry;
import com.lumintorious.ambiental.capability.TemperatureCapability;

import com.lumintorious.ambiental.effects.TempEffect;
import net.dries007.tfc.api.capability.food.IFoodStatsTFC;
import net.dries007.tfc.api.capability.food.Nutrient;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

import static com.lumintorious.ambiental.capability.TemperatureCapability.*;

public class EnvironmentalModifier {

	public static float getEnvironmentTemperatureWithTimeOfDay(EntityPlayer player) {
		return getEnvironmentTemperature(player) + handleTimeOfDay(player).get().getChange();
	}

	public static float getEnvironmentTemperature(EntityPlayer player) {
		float avg = ClimateTFC.getAvgTemp(player.world, player.getPosition());
		float actual = ClimateTFC.getActualTemp(player.world, player.getPosition());
        if(TFCAmbientalConfig.GENERAL.harsherTemperateAreas) {
		float diff = actual - AVERAGE;
		float sign = Math.signum(diff);
		float generalDiff = Math.abs(avg - 15);
		float mult0 = Math.max(0f, TFCAmbientalConfig.GENERAL.harsherMultiplier - 1f);
		float multiplier = 1 + Math.max(0, 1 - generalDiff / 55) * mult0;
		actual = 20 + (diff + 0.5f * sign);
        }
		return actual;
	}

	public static float getEnvironmentHumidity(EntityPlayer player) {
		return ClimateTFC.getRainfall(player.world, player.getPosition()) / 3000;
	}

	public static Optional<TempModifier> handleFire(EntityPlayer player) {
		return player.isBurning() ? TempModifier.defined("on_fire", 4f, 4f) : TempModifier.none();
	}

	public static Optional<TempModifier> handleGeneralTemperature(EntityPlayer player) {
		return Optional.of(new TempModifier("environment", getEnvironmentTemperature(player), getEnvironmentHumidity(player)));
	}

	public static Optional<TempModifier> handleTimeOfDay(EntityPlayer player) {
		int dayTicks = (int) (player.world.getWorldTime() % 24000);
		if(dayTicks < 6000) return TempModifier.defined("morning", 2f, 0);
		else if(dayTicks < 12000) return TempModifier.defined("afternoon", 4f, 0);
		else if(dayTicks < 18000) return TempModifier.defined("evening", 1f, 0);
		else return TempModifier.defined("night", 1f, 0);
	}

	public static Optional<TempModifier> handleWater(EntityPlayer player) {
		if (player.isInWater()) {
			BlockPos pos = player.getPosition();
			IBlockState state = player.world.getBlockState(pos);
			if (state.getBlock() == FluidsTFC.HOT_WATER.get().getBlock()) {
				return TempModifier.defined("in_hot_water", 5f, 6f);
			} else if (state.getBlock() == Blocks.LAVA) {
				return TempModifier.defined("in_lava", 10f, 5f);
			}else if(state.getBlock() == FluidsTFC.SALT_WATER.get().getBlock() && player.world.getBiome(pos).getTempCategory() == Biome.TempCategory.OCEAN ){
                return TempModifier.defined("in_ocean_water", -8f, 6f);
			} else {
				return TempModifier.defined("in_water", -5f, 6f);
			}
		} else {
			return TempModifier.none();
		}
	}

	public static Optional<TempModifier> handleRain(EntityPlayer player) {
		if(player.world.isRaining()) {
			if(getSkylight(player) < 15) {
				return TempModifier.defined("weather", -2f, 0.1f);
			}else {
				return TempModifier.defined("weather", -4f, 0.3f);
			}
		}else {
			return TempModifier.none();
		}
	}

	public static Optional<TempModifier> handleSprinting(EntityPlayer player) {
		if(player.isSprinting()) {
			return TempModifier.defined("sprint", 2f, 0.3f);
		}else {
			return TempModifier.none();
		}
	}

	public static Optional<TempModifier> handleUnderground(EntityPlayer player) {
		if(getSkylight(player) < 2) {
			return TempModifier.defined("underground", -6f, 0.2f);
		}else{
			return TempModifier.none();
		}
	}

	public static Optional<TempModifier> handleShade(EntityPlayer player) {
		int light = getSkylight(player);
		light = Math.max(12, light);
		float temp = getEnvironmentTemperatureWithTimeOfDay(player);
		float avg = AVERAGE;
		if(light < 15 && temp > avg) {
			return TempModifier.defined("shade", -Math.abs(avg - temp) * 0.6f, 0f);
		}else{
			return TempModifier.none();
		}
	}

	public static Optional<TempModifier> handleCozy(EntityPlayer player) {
		int skyLight = getSkylight(player);
		skyLight = Math.max(12, skyLight);
		int blockLight = getBlockLight(player);
		float temp = getEnvironmentTemperatureWithTimeOfDay(player);
		float avg = AVERAGE;
//		if(EnvironmentalTemperatureProvider.calculateEnclosure(player, 30) && temp < avg - 1) {
		if(skyLight < 15 && blockLight > 4 && temp < avg - 1) {
			return TempModifier.defined("cozy", Math.abs(avg - 1 - temp) * 0.6f, 0f);
		}else{
			return TempModifier.none();
		}
	}

	public static Optional<TempModifier> handleThirst(EntityPlayer player) {
		if(player.getFoodStats() instanceof IFoodStatsTFC) {
			IFoodStatsTFC stats = (IFoodStatsTFC) player.getFoodStats();
			if(getEnvironmentTemperatureWithTimeOfDay(player) > AVERAGE + 3 && stats.getThirst() > 80f) {
				return TempModifier.defined("well_hidrated", -2.5f, 0f);
			}
		}
		return TempModifier.none();
	}

	public static Optional<TempModifier> handleFood(EntityPlayer player) {
		if(getEnvironmentTemperatureWithTimeOfDay(player) < TFCAmbientalConfig.GENERAL.averageTemperature - 3 && player.getFoodStats().getFoodLevel() > 14) {
			return TempModifier.defined("well_fed", 2.5f, 0f);
		}
		return TempModifier.none();
	}

	public static Optional<TempModifier> handleDiet(EntityPlayer player) {
		if(player.getFoodStats() instanceof IFoodStatsTFC) {
			IFoodStatsTFC stats = (IFoodStatsTFC) player.getFoodStats();
			if(getEnvironmentTemperatureWithTimeOfDay(player) < TFCAmbientalConfig.GENERAL.coolThreshold) {
				float grainLevel = stats.getNutrition().getNutrient(Nutrient.GRAIN);
				float meatLevel = stats.getNutrition().getNutrient(Nutrient.PROTEIN);
				return TempModifier.defined("nutrients", 4f * grainLevel * meatLevel, 0f);
			}
			if(getEnvironmentTemperatureWithTimeOfDay(player) > TFCAmbientalConfig.GENERAL.hotThreshold) {
				float fruitLevel = stats.getNutrition().getNutrient(Nutrient.FRUIT);
				float veggieLevel = stats.getNutrition().getNutrient(Nutrient.VEGETABLES);
				return TempModifier.defined("nutrients", -4f  * fruitLevel * veggieLevel, 0f);
			}
		}
		return TempModifier.none();
	}

	public static Optional<TempModifier> handlePotionEffects(EntityPlayer player) {
		if(player.isPotionActive(TempEffect.COOL)){
			return TempModifier.defined("cooling_effect", -10F, 0);
		}
		if(player.isPotionActive(TempEffect.WARM)){
			return TempModifier.defined("heating_effect", 10F, 0);
		}
		return TempModifier.none();
	}

	public static int getSkylight(EntityPlayer player) {
		BlockPos pos = new BlockPos(player.getPosition());
		BlockPos pos2 = pos.add(0, 1, 0);
		return player.world.getLightFor(EnumSkyBlock.SKY, pos2);
	}

	public static int getBlockLight(EntityPlayer player) {
		BlockPos pos = new BlockPos(player.getPosition());
		BlockPos pos2 = pos.add(0, 1, 0);
		return player.world.getLightFor(EnumSkyBlock.BLOCK, pos2);
	}

	public static void evaluateAll(EntityPlayer player, TempModifierStorage storage) {
		for(IEnvironmentalTemperatureProvider provider : AmbientalRegistry.ENVIRONMENT) {
			storage.add(provider.getModifier(player));
		}
	}

}
