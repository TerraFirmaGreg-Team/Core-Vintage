package su.terrafirmagreg.core.mixin.gregtech;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.ToolProperty;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.ToolItems;
import gregtech.loaders.recipe.handlers.ToolRecipeHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import su.terrafirmagreg.core.modules.gregtech.items.tools.TFGToolItems;
import su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix;

import static gregtech.loaders.recipe.handlers.ToolRecipeHandler.addToolRecipe;

@Mixin(value = ToolRecipeHandler.class, remap = false)
public class ToolRecipeHandlerMixin {

	/**
	 * Fix flint tool recipes for TFG modpack
	 */
	@Inject(method = "registerFlintToolRecipes", at = @At(value = "HEAD"), remap = false, cancellable = true)
	private static void onRegisterFlintToolRecipes(CallbackInfo ci) {
		Material material = Materials.Flint;

		UnificationEntry stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		UnificationEntry toolHeadShovel = new UnificationEntry(TFGOrePrefix.toolHeadShovel, material);
		UnificationEntry toolHeadAxe = new UnificationEntry(TFGOrePrefix.toolHeadAxe, material);
		UnificationEntry toolHeadHoe = new UnificationEntry(TFGOrePrefix.toolHeadHoe, material);
		UnificationEntry toolHeadKnife = new UnificationEntry(TFGOrePrefix.toolHeadKnife, material);

		ModHandler.addShapelessRecipe(String.format("axe_%s", material), ToolItems.AXE.get(material), toolHeadAxe, stick);
		ModHandler.addShapelessRecipe(String.format("hoe_%s", material), ToolItems.HOE.get(material), toolHeadHoe, stick);
		ModHandler.addShapelessRecipe(String.format("shovel_%s", material), ToolItems.SHOVEL.get(material), toolHeadShovel, stick);
		ModHandler.addShapelessRecipe(String.format("knife_%s", material), ToolItems.KNIFE.get(material), toolHeadKnife, stick);

		ci.cancel();
	}

	/**
	 * Fix tool recipes for TFG modpack
	 */
	@Inject(method = "processTool", at = @At(value = "HEAD"), remap = false, cancellable = true)
	private static void onProcessTool(OrePrefix prefix, Material material, ToolProperty property, CallbackInfo ci) {
		UnificationEntry stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		UnificationEntry plate = new UnificationEntry(OrePrefix.plate, material);
		UnificationEntry screw = new UnificationEntry(OrePrefix.screw, material);
		UnificationEntry metalStick = new UnificationEntry(OrePrefix.stick, material);

		UnificationEntry toolHeadSword = new UnificationEntry(TFGOrePrefix.toolHeadSword, material);
		UnificationEntry toolHeadPickaxe = new UnificationEntry(TFGOrePrefix.toolHeadPickaxe, material);
		UnificationEntry toolHeadShovel = new UnificationEntry(TFGOrePrefix.toolHeadShovel, material);
		UnificationEntry toolHeadAxe = new UnificationEntry(TFGOrePrefix.toolHeadAxe, material);
		UnificationEntry toolHeadHoe = new UnificationEntry(TFGOrePrefix.toolHeadHoe, material);
		UnificationEntry toolHeadSense = new UnificationEntry(TFGOrePrefix.toolHeadSense, material);
		UnificationEntry toolHeadFile = new UnificationEntry(TFGOrePrefix.toolHeadFile, material);
		UnificationEntry toolHeadHammer = new UnificationEntry(TFGOrePrefix.toolHeadHammer, material);
		UnificationEntry toolHeadSaw = new UnificationEntry(TFGOrePrefix.toolHeadSaw, material);
		UnificationEntry toolHeadKnife = new UnificationEntry(TFGOrePrefix.toolHeadKnife, material);
		UnificationEntry toolHeadPropick = new UnificationEntry(TFGOrePrefix.toolHeadPropick, material);
		UnificationEntry toolHeadChisel = new UnificationEntry(TFGOrePrefix.toolHeadChisel, material);

		if (material.hasFlag(MaterialFlags.GENERATE_PLATE) && material != Materials.Stone) {
			addToolRecipe(material, ToolItems.MINING_HAMMER, true, "PPf", "PPS", "PPh", 'P', plate, 'S', stick);
			addToolRecipe(material, ToolItems.SPADE, false, "fPh", "PSP", " S ", 'P', plate, 'S', stick);
			addToolRecipe(material, ToolItems.WRENCH, false, "PhP", " P ", " P ", 'P', plate);
			addToolRecipe(material, TFGToolItems.TONGS, true, "F F", " S ", "K K", 'S', screw, 'K', stick, 'F', metalStick);

			ModHandler.addShapelessRecipe(String.format("saw_%s", material), ToolItems.SAW.get(material), toolHeadSaw, stick);
			ModHandler.addShapelessRecipe(String.format("pickaxe_%s", material), ToolItems.PICKAXE.get(material), toolHeadPickaxe, stick);
			ModHandler.addShapelessRecipe(String.format("sense_%s", material), ToolItems.SCYTHE.get(material), toolHeadSense, stick);
			ModHandler.addShapelessRecipe(String.format("sword_%s", material), ToolItems.SWORD.get(material), toolHeadSword, stick);
			ModHandler.addShapelessRecipe(String.format("hammer_%s", material), ToolItems.HARD_HAMMER.get(material), toolHeadHammer, stick);
			ModHandler.addShapelessRecipe(String.format("file_%s", material), ToolItems.FILE.get(material), toolHeadFile, stick);
			ModHandler.addShapelessRecipe(String.format("propick_%s", material), TFGToolItems.PROPICK.get(material), toolHeadPropick, stick);
			ModHandler.addShapelessRecipe(String.format("chisel_%s", material), TFGToolItems.CHISEL.get(material), toolHeadChisel, stick);

			ModHandler.addShapelessRecipe(String.format("axe_%s", material), ToolItems.AXE.get(material), toolHeadAxe, stick);
			ModHandler.addShapelessRecipe(String.format("hoe_%s", material), ToolItems.HOE.get(material), toolHeadHoe, stick);
			ModHandler.addShapelessRecipe(String.format("shovel_%s", material), ToolItems.SHOVEL.get(material), toolHeadShovel, stick);
			ModHandler.addShapelessRecipe(String.format("knife_%s", material), ToolItems.KNIFE.get(material), toolHeadKnife, stick);
		}

		if (material.hasFlag(MaterialFlags.GENERATE_ROD) && material != Materials.Stone) {
			UnificationEntry rod = new UnificationEntry(OrePrefix.stick, material);
			if (material.hasFlag(MaterialFlags.GENERATE_PLATE)) {
				addToolRecipe(material, ToolItems.BUTCHERY_KNIFE, false, "PPf", "PP ", "Sh ", 'P', plate, 'S', rod);
				if (material.hasFlag(MaterialFlags.GENERATE_BOLT_SCREW)) {
					addToolRecipe(material, ToolItems.WIRE_CUTTER, false, "PfP", "hPd", "STS", 'P', plate, 'T', new UnificationEntry(OrePrefix.screw, material), 'S', rod);
				}
			}

			addToolRecipe(material, ToolItems.SCREWDRIVER, true, " fS", " Sh", "W  ", 'S', rod, 'W', stick);
			addToolRecipe(material, ToolItems.CROWBAR, true, "hDS", "DSD", "SDf", 'S', rod, 'D', new UnificationEntry(OrePrefix.dye, MarkerMaterials.Color.Blue));
		}

		ci.cancel();
	}

	/**
	 * Add stone tool recipes
	 */
	@Inject(method = "registerCustomToolRecipes", at = @At(value = "TAIL"), remap = false, cancellable = true)
	private static void onRegisterCustomToolRecipes(CallbackInfo ci) {
		registerStoneToolRecipes();
	}

	private static void registerStoneToolRecipes() {
		Material material = Materials.Stone;

		UnificationEntry stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		UnificationEntry toolHeadHammer = new UnificationEntry(TFGOrePrefix.toolHeadHammer, material);
		UnificationEntry toolHeadShovel = new UnificationEntry(TFGOrePrefix.toolHeadShovel, material);
		UnificationEntry toolHeadAxe = new UnificationEntry(TFGOrePrefix.toolHeadAxe, material);
		UnificationEntry toolHeadHoe = new UnificationEntry(TFGOrePrefix.toolHeadHoe, material);
		UnificationEntry toolHeadKnife = new UnificationEntry(TFGOrePrefix.toolHeadKnife, material);

		ModHandler.addShapelessRecipe(String.format("hammer_%s", material), ToolItems.HARD_HAMMER.get(material), toolHeadHammer, stick);
		ModHandler.addShapelessRecipe(String.format("axe_%s", material), ToolItems.AXE.get(material), toolHeadAxe, stick);
		ModHandler.addShapelessRecipe(String.format("hoe_%s", material), ToolItems.HOE.get(material), toolHeadHoe, stick);
		ModHandler.addShapelessRecipe(String.format("shovel_%s", material), ToolItems.SHOVEL.get(material), toolHeadShovel, stick);
		ModHandler.addShapelessRecipe(String.format("knife_%s", material), ToolItems.KNIFE.get(material), toolHeadKnife, stick);
	}

	/**
	 * Allow to make gem axe heads in laser engraver
	 * */
    /*
    @Inject(method = "processAxeHead", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void onProcessAxeHead(OrePrefix toolPrefix, Material material, ToolProperty property, CallbackInfo ci) {
        processSimpleToolHead(toolPrefix, material, MetaItems.AXE, false, "PIh", "P  ", "f  ");

        int voltageMultiplier = IToolRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM)) {
            if (material != Materials.Stone)
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                        .input(OrePrefix.ingot, material, 3)
                        .notConsumable(MetaItems.SHAPE_EXTRUDER_AXE)
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }
        else
        {
            if (material != Materials.Flint)
                RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                        .input(OrePrefix.gem, material, 3)
                        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Purple))
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }

        ci.cancel();
    }*/

	/**
	 * Allow to make gem file heads in laser engraver
	 * */
    /*
    @Inject(method = "processFileHead", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void onProcessFileHead(OrePrefix toolPrefix, Material material, ToolProperty property, CallbackInfo ci) {
        processSimpleToolHead(toolPrefix, material, MetaItems.FILE, false," I ", " I ", " fh");

        int voltageMultiplier = IToolRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM)) {
            if (material != Materials.Stone)
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material, 3)
                    .notConsumable(MetaItems.SHAPE_EXTRUDER_FILE)
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        }
        else {
            if (material != Materials.Flint)
                RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                        .input(OrePrefix.gem, material, 3)
                        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Yellow))
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }

        ci.cancel();
    }*/

	/**
	 * Allow to make gem hammer heads in laser engraver
	 * */
    /*
    @Inject(method = "processHammerHead", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void onProcessHammerHead(OrePrefix toolPrefix, Material material, ToolProperty property, CallbackInfo ci) {
        int voltageMultiplier = IToolRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasFlag(NO_WORKING)) {
            processSimpleToolHead(toolPrefix, material, MetaItems.HARD_HAMMER, true, "II ", "IIh", "II ");

            if (!material.hasProperty(GEM)) {
                if (material != Materials.Stone)
                    RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                            .input(OrePrefix.ingot, material, 6)
                            .notConsumable(MetaItems.SHAPE_EXTRUDER_HAMMER)
                            .outputs(OreDictUnifier.get(toolPrefix, material))
                            .duration((int) material.getMass() * 2)
                            .EUt(2 * voltageMultiplier)
                            .buildAndRegister();
            }
            else {
                if (material != Materials.Flint)
                    RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                            .input(OrePrefix.gem, material, 6)
                            .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.LightGray))
                            .outputs(OreDictUnifier.get(toolPrefix, material))
                            .duration((int) material.getMass() * 2)
                            .EUt(2 * voltageMultiplier)
                            .buildAndRegister();
            }
        }

        ci.cancel();
    }*/

	/**
	 * Allow to make gem hoe heads in laser engraver
	 * */
    /*
    @Inject(method = "processHoeHead", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void onProcessHoeHead(OrePrefix toolPrefix, Material material, ToolProperty property, CallbackInfo ci) {
        processSimpleToolHead(toolPrefix, material, MetaItems.HOE, false, "PIh", "f  ");

        int voltageMultiplier = IToolRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM)) {
            if (material != Materials.Stone)
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                        .input(OrePrefix.ingot, material, 2)
                        .notConsumable(MetaItems.SHAPE_EXTRUDER_HOE)
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }
        else {
            if (material != Materials.Flint)
                RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                        .input(OrePrefix.gem, material, 2)
                        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Brown))
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }


        ci.cancel();
    }*/

	/**
	 * Allow to make gem pickaxe heads in laser engraver
	 * */
    /*
    @Inject(method = "processPickaxeHead", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void onProcessPickaxeHead(OrePrefix toolPrefix, Material material, ToolProperty property, CallbackInfo ci) {
        processSimpleToolHead(toolPrefix, material, MetaItems.PICKAXE, false, "PII", "f h");

        int voltageMultiplier = IToolRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM)) {
            if (material != Materials.Stone)
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                        .input(OrePrefix.ingot, material, 3)
                        .notConsumable(MetaItems.SHAPE_EXTRUDER_PICKAXE)
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }
        else {
            if (material != Materials.Flint)
                RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                        .input(OrePrefix.gem, material, 2)
                        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.LightBlue))
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }

        ci.cancel();
    }*/

	/**
	 * Allow to make gem saw heads in laser engraver
	 * */
    /*
    @Inject(method = "processSawHead", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void onProcessSawHead(OrePrefix toolPrefix, Material material, ToolProperty property, CallbackInfo ci) {
        processSimpleToolHead(toolPrefix, material, MetaItems.SAW, false, "PP", "fh");

        int voltageMultiplier = IToolRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM)) {
            if (material != Materials.Stone)
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                        .input(OrePrefix.ingot, material, 2)
                        .notConsumable(MetaItems.SHAPE_EXTRUDER_SAW)
                        .outputs(OreDictUnifier.get(OrePrefix.toolHeadSaw, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }
        else {
            if (material != Materials.Flint)
                RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                        .input(OrePrefix.gem, material, 2)
                        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Green))
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }

        ci.cancel();
    }*/

	/**
	 * Allow to make gem sense heads in laser engraver
	 * */
    /*
    @Inject(method = "processSenseHead", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void onProcessSenseHead(OrePrefix toolPrefix, Material material, ToolProperty property, CallbackInfo ci) {
        int voltageMultiplier = IToolRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM)) {
            if (material != Materials.Stone)
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                        .input(OrePrefix.ingot, material, 3)
                        .notConsumable(new ItemStack(Items.STICK)) // TFGModMetaItems.SHAPE_EXTRUDER_SENSE
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }
        else {
            if (material != Materials.Flint)
                RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                        .input(OrePrefix.gem, material, 2)
                        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Magenta))
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }

        ci.cancel();
    }*/

	/**
	 * Allow to make gem shovel heads in laser engraver
	 * */
    /*
    @Inject(method = "processShovelHead", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void onProcessShovelHead(OrePrefix toolPrefix, Material material, ToolProperty property, CallbackInfo ci) {
        processSimpleToolHead(toolPrefix, material, MetaItems.SHOVEL, false, "fPh");

        int voltageMultiplier = IToolRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM)) {
            if (material != Materials.Stone)
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                        .input(OrePrefix.ingot, material)
                        .notConsumable(MetaItems.SHAPE_EXTRUDER_SHOVEL)
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass())
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }
        else {
            if (material != Materials.Flint)
                RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                        .input(OrePrefix.gem, material)
                        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Pink))
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }

        ci.cancel();
    }*/

	/**
	 * Allow to make gem sword heads in laser engraver
	 * */
    /*
    @Inject(method = "processSwordHead", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void onProcessSwordHead(OrePrefix toolPrefix, Material material, ToolProperty property, CallbackInfo ci) {
        processSimpleToolHead(toolPrefix, material, MetaItems.SWORD, false, " P ", "fPh");

        int voltageMultiplier = IToolRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM)) {
            if (material != Materials.Stone)
                RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                        .input(OrePrefix.ingot, material, 2)
                        .notConsumable(MetaItems.SHAPE_EXTRUDER_SWORD)
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }
        else {
            if (material != Materials.Flint)
                RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                        .input(OrePrefix.gem, material, 2)
                        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Orange))
                        .outputs(OreDictUnifier.get(toolPrefix, material))
                        .duration((int) material.getMass() * 2)
                        .EUt(2 * voltageMultiplier)
                        .buildAndRegister();
        }

        ci.cancel();
    }*/


	/**
	 * Disable workbench recipes for File
	 * */
    /*
    @Redirect(method = "processFileHead", at = @At(value = "INVOKE", target = "Lgregtech/loaders/recipe/handlers/ToolRecipeHandler;processSimpleToolHead(Lgregtech/api/unification/ore/OrePrefix;Lgregtech/api/unification/material/Material;Lgregtech/api/items/toolitem/ToolMetaItem$MetaToolValueItem;Z[Ljava/lang/Object;)V"), remap = false)
    private static void onProcessFileHead(OrePrefix toolPrefix, Material material, ToolMetaItem.MetaToolValueItem toolItem, boolean mirrored, Object[] recipe) {}
*/
	/**
	 * Disable workbench recipes for Knife
	 * */
    /*
    @Redirect(method = "processStick", at = @At(value = "INVOKE", target = "Lgregtech/api/recipes/ModHandler;addShapedRecipe(Ljava/lang/String;Lnet/minecraft/item/ItemStack;[Ljava/lang/Object;)V", ordinal = 0), remap = false)
    private static void onProcessKnifeHead(String regName, ItemStack result, Object[] recipe) {}*/


}
