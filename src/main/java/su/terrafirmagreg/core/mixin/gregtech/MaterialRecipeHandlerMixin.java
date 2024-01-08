package su.terrafirmagreg.core.mixin.gregtech;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import gregtech.loaders.recipe.handlers.MaterialRecipeHandler;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ALLOY_SMELTER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.COMPRESSOR_RECIPES;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.ore.OrePrefix.*;

@Mixin(value = MaterialRecipeHandler.class, remap = false)
public class MaterialRecipeHandlerMixin {

	/**
	 * Disable 2x ingot -> plate recipe generation (@Redirect doesn't work, or I am stupid)
	 */
	@Inject(method = "processIngot", at = @At(value = "HEAD"), remap = false, cancellable = true)
	private static void onProcessIngot(OrePrefix ingotPrefix, Material material, IngotProperty property, CallbackInfo ci) {
		if (material.hasFlag(MORTAR_GRINDABLE)) {
			ModHandler.addShapedRecipe(String.format("mortar_grind_%s", material),
					OreDictUnifier.get(OrePrefix.dust, material), "X", "m", 'X', new UnificationEntry(ingotPrefix, material));
		}

		if (material.hasFlag(GENERATE_ROD)) {
			ModHandler.addShapedRecipe(String.format("stick_%s", material),
					OreDictUnifier.get(OrePrefix.stick, material, 1),
					"f ", " X",
					'X', new UnificationEntry(ingotPrefix, material));
			if (!material.hasFlag(NO_WORKING)) {
				RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
				                           .input(ingotPrefix, material)
				                           .notConsumable(MetaItems.SHAPE_EXTRUDER_ROD)
				                           .outputs(OreDictUnifier.get(OrePrefix.stick, material, 2))
				                           .duration((int) material.getMass() * 2)
				                           .EUt(6 * IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material))
				                           .buildAndRegister();
			}
		}

		if (material.hasFluid()) {
			RecipeMaps.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
			                                      .notConsumable(MetaItems.SHAPE_MOLD_INGOT)
			                                      .fluidInputs(material.getFluid(L))
			                                      .outputs(OreDictUnifier.get(ingotPrefix, material))
			                                      .duration(20).EUt(VA[ULV])
			                                      .buildAndRegister();
		}

		if (material.hasFlag(NO_SMASHING)) {
			RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
			                           .input(OrePrefix.dust, material)
			                           .notConsumable(MetaItems.SHAPE_EXTRUDER_INGOT)
			                           .outputs(OreDictUnifier.get(OrePrefix.ingot, material))
			                           .duration(10)
			                           .EUt(4 * IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material))
			                           .buildAndRegister();
		}

		ALLOY_SMELTER_RECIPES.recipeBuilder().EUt(VA[ULV]).duration((int) material.getMass())
		                     .input(ingot, material)
		                     .notConsumable(MetaItems.SHAPE_MOLD_NUGGET.getStackForm())
		                     .output(nugget, material, 9)
		                     .buildAndRegister();

		if (!OreDictUnifier.get(block, material).isEmpty()) {
			ALLOY_SMELTER_RECIPES.recipeBuilder().EUt(VA[ULV]).duration((int) material.getMass() * 9)
			                     .input(block, material)
			                     .notConsumable(MetaItems.SHAPE_MOLD_INGOT.getStackForm())
			                     .output(ingot, material, 9)
			                     .buildAndRegister();

			COMPRESSOR_RECIPES.recipeBuilder().EUt(2).duration(300)
			                  .input(ingot, material, (int) (block.getMaterialAmount(material) / M))
			                  .output(block, material)
			                  .buildAndRegister();
		}

		if (material.hasFlag(GENERATE_PLATE) && !material.hasFlag(NO_WORKING)) {

			if (!material.hasFlag(NO_SMASHING)) {
				ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, material);
				if (!plateStack.isEmpty()) {
					RecipeMaps.BENDER_RECIPES.recipeBuilder()
					                         .notConsumable(new IntCircuitIngredient(1))
					                         .input(ingotPrefix, material)
					                         .outputs(plateStack)
					                         .EUt(24).duration((int) (material.getMass()))
					                         .buildAndRegister();

					RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
					                               .input(ingotPrefix, material, 3)
					                               .outputs(GTUtility.copy(2, plateStack))
					                               .EUt(16).duration((int) material.getMass())
					                               .buildAndRegister();
				}
			}

			int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);
			if (!OreDictUnifier.get(plate, material).isEmpty()) {
				RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
				                           .input(ingotPrefix, material)
				                           .notConsumable(MetaItems.SHAPE_EXTRUDER_PLATE)
				                           .outputs(OreDictUnifier.get(OrePrefix.plate, material))
				                           .duration((int) material.getMass())
				                           .EUt(8 * voltageMultiplier)
				                           .buildAndRegister();

				if (material.hasFlag(NO_SMASHING)) {
					RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
					                           .input(dust, material)
					                           .notConsumable(MetaItems.SHAPE_EXTRUDER_PLATE)
					                           .outputs(OreDictUnifier.get(OrePrefix.plate, material))
					                           .duration((int) material.getMass())
					                           .EUt(8 * voltageMultiplier)
					                           .buildAndRegister();
				}
			}
		}

		ci.cancel();
	}
}
