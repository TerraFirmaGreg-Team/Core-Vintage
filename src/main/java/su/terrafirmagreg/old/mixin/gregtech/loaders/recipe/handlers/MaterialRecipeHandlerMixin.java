package su.terrafirmagreg.old.mixin.gregtech.loaders.recipe.handlers;

import net.minecraft.item.ItemStack;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import gregtech.loaders.recipe.handlers.MaterialRecipeHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.M;
import static gregtech.api.GTValues.ULV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.ALLOY_SMELTER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.BENDER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.COMPRESSOR_RECIPES;
import static gregtech.api.recipes.RecipeMaps.EXTRUDER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.FLUID_SOLIDFICATION_RECIPES;
import static gregtech.api.recipes.RecipeMaps.FORGE_HAMMER_RECIPES;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_PLATE;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_ROD;
import static gregtech.api.unification.material.info.MaterialFlags.MORTAR_GRINDABLE;
import static gregtech.api.unification.material.info.MaterialFlags.NO_SMASHING;
import static gregtech.api.unification.material.info.MaterialFlags.NO_WORKING;
import static gregtech.api.unification.ore.OrePrefix.block;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.ingot;
import static gregtech.api.unification.ore.OrePrefix.nugget;
import static gregtech.api.unification.ore.OrePrefix.plate;

@Mixin(value = MaterialRecipeHandler.class, remap = false)
public class MaterialRecipeHandlerMixin {

  /**
   * Disable 2x ingot -> plate recipe generation (@Redirect doesn't work, or I am stupid)
   */
  @Inject(method = "processIngot", at = @At(value = "HEAD"), remap = false, cancellable = true)
  private static void onProcessIngot(OrePrefix ingotPrefix, Material material, IngotProperty property, CallbackInfo ci) {
    if (material.hasFlag(MORTAR_GRINDABLE)) {
      ModHandler.addShapedRecipe(String.format("mortar_grind_%s", material),
                                 OreDictUnifier.get(OrePrefix.dust, material), "X", "m", 'X',
                                 new UnificationEntry(ingotPrefix, material));
    }

    if (material.hasFlag(GENERATE_ROD)) {
      ModHandler.addShapedRecipe(String.format("stick_%s", material),
                                 OreDictUnifier.get(OrePrefix.stick, material, 1),
                                 "f ", " X",
                                 'X', new UnificationEntry(ingotPrefix, material));

      if (!material.hasFlag(NO_WORKING)) {
        EXTRUDER_RECIPES.recipeBuilder()
                        .input(ingotPrefix, material)
                        .notConsumable(MetaItems.SHAPE_EXTRUDER_ROD)
                        .outputs(OreDictUnifier.get(OrePrefix.stick, material, 2))
                        .duration((int) material.getMass() * 2)
                        .EUt(6 * IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material))
                        .buildAndRegister();
      }
    }

    if (material.hasFluid()) {
      FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                                 .notConsumable(MetaItems.SHAPE_MOLD_INGOT)
                                 .fluidInputs(material.getFluid(L))
                                 .outputs(OreDictUnifier.get(ingotPrefix, material))
                                 .duration(20).EUt(VA[ULV])
                                 .buildAndRegister();
    }

    if (material.hasFlag(NO_SMASHING)) {
      EXTRUDER_RECIPES.recipeBuilder()
                      .input(OrePrefix.dust, material)
                      .notConsumable(MetaItems.SHAPE_EXTRUDER_INGOT)
                      .outputs(OreDictUnifier.get(OrePrefix.ingot, material))
                      .duration(10)
                      .EUt(4 * IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material))
                      .buildAndRegister();
    }

    ALLOY_SMELTER_RECIPES.recipeBuilder()
                         .EUt(VA[ULV])
                         .duration((int) material.getMass())
                         .input(ingot, material)
                         .notConsumable(MetaItems.SHAPE_MOLD_NUGGET.getStackForm())
                         .output(nugget, material, 9)
                         .buildAndRegister();

    if (!OreDictUnifier.get(block, material).isEmpty()) {
      ALLOY_SMELTER_RECIPES.recipeBuilder()
                           .EUt(VA[ULV])
                           .duration((int) material.getMass() * 9)
                           .input(block, material)
                           .notConsumable(MetaItems.SHAPE_MOLD_INGOT.getStackForm())
                           .output(ingot, material, 9)
                           .buildAndRegister();

      COMPRESSOR_RECIPES.recipeBuilder()
                        .EUt(2)
                        .duration(300)
                        .input(ingot, material, (int) (block.getMaterialAmount(material) / M))
                        .output(block, material)
                        .buildAndRegister();
    }

    if (material.hasFlag(GENERATE_PLATE) && !material.hasFlag(NO_WORKING)) {

      if (!material.hasFlag(NO_SMASHING)) {
        ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, material);
        if (!plateStack.isEmpty()) {
          BENDER_RECIPES.recipeBuilder()
                        .notConsumable(new IntCircuitIngredient(1))
                        .input(ingotPrefix, material)
                        .outputs(plateStack)
                        .EUt(24).duration((int) (material.getMass()))
                        .buildAndRegister();

          FORGE_HAMMER_RECIPES.recipeBuilder()
                              .input(ingotPrefix, material, 3)
                              .outputs(GTUtility.copy(2, plateStack))
                              .EUt(16).duration((int) material.getMass())
                              .buildAndRegister();
        }
      }

      int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);
      if (!OreDictUnifier.get(plate, material).isEmpty()) {
        EXTRUDER_RECIPES.recipeBuilder()
                        .input(ingotPrefix, material)
                        .notConsumable(MetaItems.SHAPE_EXTRUDER_PLATE)
                        .outputs(OreDictUnifier.get(OrePrefix.plate, material))
                        .duration((int) material.getMass())
                        .EUt(8 * voltageMultiplier)
                        .buildAndRegister();

        if (material.hasFlag(NO_SMASHING)) {
          EXTRUDER_RECIPES.recipeBuilder()
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
