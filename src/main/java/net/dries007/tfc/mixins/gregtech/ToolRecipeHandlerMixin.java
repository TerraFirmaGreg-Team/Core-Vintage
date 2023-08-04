package net.dries007.tfc.mixins.gregtech;

import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.ToolProperty;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.ToolItems;
import gregtech.loaders.recipe.handlers.ToolRecipeHandler;
import net.dries007.tfc.compat.gregtech.items.tools.TFGToolItems;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ToolRecipeHandler.class, remap = false)
public class ToolRecipeHandlerMixin {

	/**
	 * Отключение инструментов из FLINT.
	 */
	@Inject(method = "registerFlintToolRecipes", at = @At(value = "HEAD"), remap = false, cancellable = true)
	private static void onRegisterFlintToolRecipes(CallbackInfo ci) {
		ci.cancel();
	}

	// Spade?
	// Mining Hammer?
	// Wrench?

	/**
	 * Исправление рецепта пилы.
	 */
	@Redirect(method = "processTool", at = @At(value = "INVOKE", target = "Lgregtech/loaders/recipe/handlers/ToolRecipeHandler;addToolRecipe(Lgregtech/api/unification/material/Material;Lgregtech/api/items/toolitem/IGTTool;Z[Ljava/lang/Object;)V", ordinal = 2), remap = false)
	private static void onCreateSawRecipe(Material material, IGTTool tool, boolean mirrored, Object[] recipe) {
		var stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		var toolHead = new UnificationEntry(TFGOrePrefix.toolHeadSaw, material);

		ModHandler.addShapelessRecipe(String.format("saw_%s", material), ToolItems.SAW.get(material), toolHead, stick);
	}

	/**
	 * Исправление рецепта пилы.
	 */
	@Redirect(method = "processTool", at = @At(value = "INVOKE", target = "Lgregtech/loaders/recipe/handlers/ToolRecipeHandler;addToolRecipe(Lgregtech/api/unification/material/Material;Lgregtech/api/items/toolitem/IGTTool;Z[Ljava/lang/Object;)V", ordinal = 3), remap = false)
	private static void onCreateAxeRecipe(Material material, IGTTool tool, boolean mirrored, Object[] recipe) {
		var stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		var toolHead = new UnificationEntry(TFGOrePrefix.toolHeadAxe, material);

		ModHandler.addShapelessRecipe(String.format("axe_%s", material), ToolItems.AXE.get(material), toolHead, stick);
	}

	/**
	 * Исправление рецепта мотыги.
	 */
	@Redirect(method = "processTool", at = @At(value = "INVOKE", target = "Lgregtech/loaders/recipe/handlers/ToolRecipeHandler;addToolRecipe(Lgregtech/api/unification/material/Material;Lgregtech/api/items/toolitem/IGTTool;Z[Ljava/lang/Object;)V", ordinal = 4), remap = false)
	private static void onCreateHoeRecipe(Material material, IGTTool tool, boolean mirrored, Object[] recipe) {
		var stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		var toolHead = new UnificationEntry(TFGOrePrefix.toolHeadHoe, material);

		ModHandler.addShapelessRecipe(String.format("hoe_%s", material), ToolItems.HOE.get(material), toolHead, stick);
	}

	/**
	 * Исправление рецепта кирки.
	 */
	@Redirect(method = "processTool", at = @At(value = "INVOKE", target = "Lgregtech/loaders/recipe/handlers/ToolRecipeHandler;addToolRecipe(Lgregtech/api/unification/material/Material;Lgregtech/api/items/toolitem/IGTTool;Z[Ljava/lang/Object;)V", ordinal = 5), remap = false)
	private static void onCreatePickaxeRecipe(Material material, IGTTool tool, boolean mirrored, Object[] recipe) {
		var stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		var toolHead = new UnificationEntry(TFGOrePrefix.toolHeadPickaxe, material);

		ModHandler.addShapelessRecipe(String.format("pickaxe_%s", material), ToolItems.PICKAXE.get(material), toolHead, stick);
	}

	/**
	 * Исправление рецепта косы.
	 */
	@Redirect(method = "processTool", at = @At(value = "INVOKE", target = "Lgregtech/loaders/recipe/handlers/ToolRecipeHandler;addToolRecipe(Lgregtech/api/unification/material/Material;Lgregtech/api/items/toolitem/IGTTool;Z[Ljava/lang/Object;)V", ordinal = 6), remap = false)
	private static void onCreateSenseRecipe(Material material, IGTTool tool, boolean mirrored, Object[] recipe) {
		var stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		var toolHead = new UnificationEntry(TFGOrePrefix.toolHeadSense, material);

		ModHandler.addShapelessRecipe(String.format("sense_%s", material), ToolItems.SCYTHE.get(material), toolHead, stick);
	}

	/**
	 * Исправление рецепта лопаты.
	 */
	@Redirect(method = "processTool", at = @At(value = "INVOKE", target = "Lgregtech/loaders/recipe/handlers/ToolRecipeHandler;addToolRecipe(Lgregtech/api/unification/material/Material;Lgregtech/api/items/toolitem/IGTTool;Z[Ljava/lang/Object;)V", ordinal = 7), remap = false)
	private static void onCreateShovelRecipe(Material material, IGTTool tool, boolean mirrored, Object[] recipe) {
		var stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		var toolHead = new UnificationEntry(TFGOrePrefix.toolHeadShovel, material);

		ModHandler.addShapelessRecipe(String.format("shovel_%s", material), ToolItems.SHOVEL.get(material), toolHead, stick);
	}

	/**
	 * Исправление рецепта меча.
	 */
	@Redirect(method = "processTool", at = @At(value = "INVOKE", target = "Lgregtech/loaders/recipe/handlers/ToolRecipeHandler;addToolRecipe(Lgregtech/api/unification/material/Material;Lgregtech/api/items/toolitem/IGTTool;Z[Ljava/lang/Object;)V", ordinal = 8), remap = false)
	private static void onCreateSwordRecipe(Material material, IGTTool tool, boolean mirrored, Object[] recipe) {
		var stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		var toolHead = new UnificationEntry(TFGOrePrefix.toolHeadSword, material);

		ModHandler.addShapelessRecipe(String.format("sword_%s", material), ToolItems.SWORD.get(material), toolHead, stick);
	}

	/**
	 * Исправление рецепта молота.
	 */
	@Redirect(method = "processTool", at = @At(value = "INVOKE", target = "Lgregtech/loaders/recipe/handlers/ToolRecipeHandler;addToolRecipe(Lgregtech/api/unification/material/Material;Lgregtech/api/items/toolitem/IGTTool;Z[Ljava/lang/Object;)V", ordinal = 9), remap = false)
	private static void onCreateHammerRecipe(Material material, IGTTool tool, boolean mirrored, Object[] recipe) {
		var stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		var toolHead = new UnificationEntry(TFGOrePrefix.toolHeadHammer, material);

		ModHandler.addShapelessRecipe(String.format("hammer_%s", material), ToolItems.HARD_HAMMER.get(material), toolHead, stick);
	}

	/**
	 * Исправление рецепта напильника.
	 */
	@Redirect(method = "processTool", at = @At(value = "INVOKE", target = "Lgregtech/loaders/recipe/handlers/ToolRecipeHandler;addToolRecipe(Lgregtech/api/unification/material/Material;Lgregtech/api/items/toolitem/IGTTool;Z[Ljava/lang/Object;)V", ordinal = 10), remap = false)
	private static void onCreateFileRecipe(Material material, IGTTool tool, boolean mirrored, Object[] recipe) {
		var stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		var toolHead = new UnificationEntry(TFGOrePrefix.toolHeadFile, material);

		ModHandler.addShapelessRecipe(String.format("file_%s", material), ToolItems.FILE.get(material), toolHead, stick);
	}

	/**
	 * Исправление рецепта ножа.
	 */
	@Redirect(method = "processTool", at = @At(value = "INVOKE", target = "Lgregtech/loaders/recipe/handlers/ToolRecipeHandler;addToolRecipe(Lgregtech/api/unification/material/Material;Lgregtech/api/items/toolitem/IGTTool;Z[Ljava/lang/Object;)V", ordinal = 11), remap = false)
	private static void onCreateKnifeRecipe(Material material, IGTTool tool, boolean mirrored, Object[] recipe) {
		var stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		var toolHead = new UnificationEntry(TFGOrePrefix.toolHeadKnife, material);

		ModHandler.addShapelessRecipe(String.format("knife_%s", material), ToolItems.KNIFE.get(material), toolHead, stick);
	}

	/**
	 * Добавление рецептов новым инструментам.
	 */

	@Inject(method = "processTool", at = @At(value = "INVOKE", target = "Lgregtech/loaders/recipe/handlers/ToolRecipeHandler;addToolRecipe(Lgregtech/api/unification/material/Material;Lgregtech/api/items/toolitem/IGTTool;Z[Ljava/lang/Object;)V", ordinal = 12), remap = false)
	private static void onProcessTool(OrePrefix prefix, Material material, ToolProperty property, CallbackInfo ci) {
		var stick = new UnificationEntry(OrePrefix.stick, Materials.Wood);
		var toolHeadPropick = new UnificationEntry(TFGOrePrefix.toolHeadPropick, material);
		var toolHeadChisel = new UnificationEntry(TFGOrePrefix.toolHeadChisel, material);

		ModHandler.addShapelessRecipe(String.format("propick_%s", material), TFGToolItems.PROPICK.get(material), toolHeadPropick, stick);
		ModHandler.addShapelessRecipe(String.format("chisel_%s", material), TFGToolItems.CHISEL.get(material), toolHeadChisel, stick);

	}
}
