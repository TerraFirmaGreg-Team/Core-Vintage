package pieman.caffeineaddon.init;

import net.dries007.tfc.api.capability.food.FoodStatsTFC;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import pieman.caffeineaddon.potion.PotionEffects;

import javax.annotation.Nonnull;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

public class ModFluids {

	private static final ResourceLocation STILL = new ResourceLocation(MODID_TFC, "blocks/fluid_still");
	private static final ResourceLocation FLOW = new ResourceLocation(MODID_TFC, "blocks/fluid_flow");

	public static FluidWrapper COFFEE;
	public static FluidWrapper SWEET_COFFEE;
	public static FluidWrapper TEA;
	public static FluidWrapper SWEET_TEA;

	public static void registerFluids() {
		TEA = registerFluid(new Fluid("tea", STILL, FLOW, 0xFF1C120B)).with(DrinkableProperty.DRINKABLE, player -> {
			if (player.getFoodStats() instanceof FoodStatsTFC) {
				((FoodStatsTFC) player.getFoodStats()).addThirst(40);
				player.addPotionEffect(new PotionEffect(PotionEffects.CAFFEINE, 14400, 0));
			}
		});
		SWEET_TEA = registerFluid(new Fluid("sweet_tea", STILL, FLOW, 0xFF1C120B)).with(DrinkableProperty.DRINKABLE, player -> {
			if (player.getFoodStats() instanceof FoodStatsTFC) {
				((FoodStatsTFC) player.getFoodStats()).addThirst(40);
				player.addPotionEffect(new PotionEffect(PotionEffects.CAFFEINE, 14400, 1));
			}
		});
		COFFEE = registerFluid(new Fluid("coffee", STILL, FLOW, 0xFF210B00)).with(DrinkableProperty.DRINKABLE, player -> {
			if (player.getFoodStats() instanceof FoodStatsTFC) {
				((FoodStatsTFC) player.getFoodStats()).addThirst(40);
				player.addPotionEffect(new PotionEffect(PotionEffects.CAFFEINE, 14400, 2));
			}
		});
		SWEET_COFFEE = registerFluid(new Fluid("sweet_coffee", STILL, FLOW, 0xFF210B00)).with(DrinkableProperty.DRINKABLE, player -> {
			if (player.getFoodStats() instanceof FoodStatsTFC) {
				((FoodStatsTFC) player.getFoodStats()).addThirst(40);
				player.addPotionEffect(new PotionEffect(PotionEffects.CAFFEINE, 14400, 3));
			}
		});
	}

	@Nonnull
	private static FluidWrapper registerFluid(@Nonnull Fluid newFluid) {
		boolean isDefault = FluidRegistry.registerFluid(newFluid);
		if (!isDefault) {
			// Fluid was already registered with this name, default to that fluid
			newFluid = FluidRegistry.getFluid(newFluid.getName());
		}
		//adds vanilla bucket
		FluidRegistry.addBucketForFluid(newFluid);
		//getWrapper will detect that the fluid doesnt have a wrapper. so it will add it to their set of wrappers, and return it
		return FluidsTFC.getWrapper(newFluid);
	}

}
