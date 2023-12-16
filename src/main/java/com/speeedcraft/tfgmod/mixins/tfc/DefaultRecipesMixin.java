package com.speeedcraft.tfgmod.mixins.tfc;

import net.dries007.tfc.api.recipes.WeldingRecipe;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.types.DefaultRecipes;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dries007.tfc.api.types.Metal.ItemType.*;
import static net.dries007.tfc.types.DefaultMetals.*;
import static net.dries007.tfc.util.forge.ForgeRule.*;
import static net.dries007.tfc.util.skills.SmithingSkill.Type.*;

@Mixin(value = DefaultRecipes.class, remap = false)
public final class DefaultRecipesMixin {

	@Inject(method = "onRegisterAnvilRecipeEvent", at = @At(value = "HEAD"), remap = false, cancellable = true)
	private static void onRegisterAnvilRecipeEvent(RegistryEvent.Register<AnvilRecipe> event, CallbackInfo cir) {
		IForgeRegistry<AnvilRecipe> r = event.getRegistry();

		// Misc
		// addAnvil(r, DOUBLE_INGOT, SHEET, false, GENERAL, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST);
		IDefaultRecipesInvoker.invokeAddAnvil(r, DOUBLE_SHEET, TUYERE, true, GENERAL, BEND_LAST, BEND_SECOND_LAST);
		IDefaultRecipesInvoker.invokeAddAnvil(r, INGOT, LAMP, false, GENERAL, BEND_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
		IDefaultRecipesInvoker.invokeAddAnvil(r, SHEET, TRAPDOOR, false, GENERAL, BEND_LAST, DRAW_SECOND_LAST, DRAW_THIRD_LAST);

		// Tools
		// addAnvil(r, INGOT, PICK_HEAD, true, TOOLS, PUNCH_LAST, BEND_NOT_LAST, DRAW_NOT_LAST);
		// addAnvil(r, INGOT, SHOVEL_HEAD, true, TOOLS, PUNCH_LAST, HIT_NOT_LAST);
		// addAnvil(r, INGOT, AXE_HEAD, true, TOOLS, PUNCH_LAST, HIT_SECOND_LAST, UPSET_THIRD_LAST);
		// addAnvil(r, INGOT, HOE_HEAD, true, TOOLS, PUNCH_LAST, HIT_NOT_LAST, BEND_NOT_LAST);
		// addAnvil(r, INGOT, HAMMER_HEAD, true, TOOLS, PUNCH_LAST, SHRINK_NOT_LAST);
//        IDefaultRecipesInvoker.invokeAddAnvil(r, INGOT, PROPICK_HEAD, true, TOOLS, PUNCH_LAST, DRAW_NOT_LAST, BEND_NOT_LAST);
		// addAnvil(r, INGOT, SAW_BLADE, true, TOOLS, HIT_LAST, HIT_SECOND_LAST);
		// addAnvil(r, DOUBLE_INGOT, SWORD_BLADE, true, WEAPONS, HIT_LAST, BEND_SECOND_LAST, BEND_THIRD_LAST);
		IDefaultRecipesInvoker.invokeAddAnvil(r, DOUBLE_INGOT, MACE_HEAD, true, WEAPONS, HIT_LAST, SHRINK_NOT_LAST, BEND_NOT_LAST);
		// addAnvil(r, INGOT, SCYTHE_BLADE, true, WEAPONS, HIT_LAST, DRAW_SECOND_LAST, BEND_THIRD_LAST);
		// addAnvil(r, INGOT, KNIFE_BLADE, true, WEAPONS, HIT_LAST, DRAW_SECOND_LAST, DRAW_THIRD_LAST);
		IDefaultRecipesInvoker.invokeAddAnvil(r, INGOT, JAVELIN_HEAD, true, WEAPONS, HIT_LAST, HIT_SECOND_LAST, DRAW_THIRD_LAST);
//        IDefaultRecipesInvoker.invokeAddAnvil(r, INGOT, CHISEL_HEAD, true, TOOLS, HIT_LAST, HIT_NOT_LAST, DRAW_NOT_LAST);

		// Armor
		IDefaultRecipesInvoker.invokeAddAnvil(r, DOUBLE_SHEET, UNFINISHED_HELMET, true, ARMOR, HIT_LAST, BEND_SECOND_LAST, BEND_THIRD_LAST);
		IDefaultRecipesInvoker.invokeAddAnvil(r, DOUBLE_SHEET, UNFINISHED_CHESTPLATE, true, ARMOR, HIT_LAST, HIT_SECOND_LAST, UPSET_THIRD_LAST);
		IDefaultRecipesInvoker.invokeAddAnvil(r, DOUBLE_SHEET, UNFINISHED_GREAVES, true, ARMOR, BEND_ANY, DRAW_ANY, HIT_ANY);
		IDefaultRecipesInvoker.invokeAddAnvil(r, SHEET, UNFINISHED_BOOTS, true, ARMOR, BEND_LAST, BEND_SECOND_LAST, SHRINK_THIRD_LAST);

		// Blooms
		// r.register(new AnvilRecipeMeasurable(new ResourceLocation(MOD_ID, "refining_bloom"), IIngredient.of(ItemsTFC.UNREFINED_BLOOM), new ItemStack(ItemsTFC.REFINED_BLOOM), Metal.Tier.TIER_II, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
		// r.register(new AnvilRecipeSplitting(new ResourceLocation(MOD_ID, "splitting_bloom"), IIngredient.of(ItemsTFC.REFINED_BLOOM), new ItemStack(ItemsTFC.REFINED_BLOOM), 144, Metal.Tier.TIER_II, PUNCH_LAST));
		// r.register(new AnvilRecipe(new ResourceLocation(MOD_ID, "iron_bloom"), x -> {
		//     if (x.getItem() == ItemsTFC.REFINED_BLOOM)
		//     {
		//         IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
		//         if (cap instanceof IForgeableMeasurableMetal)
		//         {
		//             return ((IForgeableMeasurableMetal) cap).getMetal() == Metal.WROUGHT_IRON && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 144;
		//         }
		//     }
		//     return false;
		// }, new ItemStack(ItemMetal.get(Metal.WROUGHT_IRON, INGOT)), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));

		// Shields
		IDefaultRecipesInvoker.invokeAddAnvil(r, DOUBLE_SHEET, SHIELD, true, ARMOR, UPSET_LAST, BEND_SECOND_LAST, BEND_THIRD_LAST);

		// Steel Working
		IDefaultRecipesInvoker.invokeAddAnvil(r, PIG_IRON, HIGH_CARBON_STEEL, null);
		IDefaultRecipesInvoker.invokeAddAnvil(r, HIGH_CARBON_STEEL, STEEL, null);
		IDefaultRecipesInvoker.invokeAddAnvil(r, HIGH_CARBON_BLACK_STEEL, BLACK_STEEL, null);
		IDefaultRecipesInvoker.invokeAddAnvil(r, HIGH_CARBON_BLUE_STEEL, BLUE_STEEL, null);
		IDefaultRecipesInvoker.invokeAddAnvil(r, HIGH_CARBON_RED_STEEL, RED_STEEL, null);
		// addAnvil(r, "brass_mechanisms", INGOT, BRASS, new ItemStack(ItemsTFC.BRASS_MECHANISMS, 2), Metal.Tier.TIER_II, GENERAL, PUNCH_LAST, HIT_SECOND_LAST, PUNCH_THIRD_LAST);

		// Misc
		// addAnvil(r, "iron_bars", SHEET, WROUGHT_IRON, new ItemStack(Blocks.IRON_BARS, 8), Metal.Tier.TIER_III, GENERAL, UPSET_LAST, PUNCH_SECOND_LAST, PUNCH_THIRD_LAST);
		// addAnvil(r, "iron_bars_double", DOUBLE_SHEET, WROUGHT_IRON, new ItemStack(Blocks.IRON_BARS, 16), Metal.Tier.TIER_III, GENERAL, UPSET_LAST, PUNCH_SECOND_LAST, PUNCH_THIRD_LAST);
		// addAnvil(r, "iron_door", SHEET, WROUGHT_IRON, new ItemStack(Items.IRON_DOOR), Metal.Tier.TIER_III, GENERAL, HIT_LAST, DRAW_NOT_LAST, PUNCH_NOT_LAST);
		// addAnvil(r, "red_steel_bucket", SHEET, RED_STEEL, new ItemStack(ItemMetal.get(Metal.RED_STEEL, BUCKET)), Metal.Tier.TIER_VI, GENERAL, BEND_LAST, BEND_SECOND_LAST, BEND_THIRD_LAST);
		// addAnvil(r, "blue_steel_bucket", SHEET, BLUE_STEEL, new ItemStack(ItemMetal.get(Metal.BLUE_STEEL, BUCKET)), Metal.Tier.TIER_VI, GENERAL, BEND_LAST, BEND_SECOND_LAST, BEND_THIRD_LAST);
		// addAnvil(r, "wrought_iron_grill", DOUBLE_SHEET, WROUGHT_IRON, new ItemStack(ItemsTFC.WROUGHT_IRON_GRILL), Metal.Tier.TIER_III, GENERAL, DRAW_ANY, PUNCH_LAST, PUNCH_NOT_LAST);

		// Rods, because they produce 2
		// Arrays.asList(WROUGHT_IRON, STEEL, GOLD).forEach(metal -> {
		//    Metal metalObj = TFCRegistries.METALS.getValue(metal);
		//    // noinspection ConstantConditions
		//    addAnvil(r, metal.getPath() + "_rod", INGOT, metal, new ItemStack(ItemMetal.get(metalObj, ROD), 2), metalObj.getTier().previous(), GENERAL, DRAW_LAST, DRAW_NOT_LAST, PUNCH_NOT_LAST);
		// });

		cir.cancel();
	}

	@Inject(method = "onRegisterWeldingRecipeEvent", at = @At(value = "HEAD"), remap = false, cancellable = true)
	private static void onRegisterWeldingRecipeEvent(RegistryEvent.Register<WeldingRecipe> event, CallbackInfo cir) {
		IForgeRegistry<WeldingRecipe> r = event.getRegistry();

		// Basic Parts
		IDefaultRecipesInvoker.invokeAddWelding(r, INGOT, DOUBLE_INGOT, null);
		// IDefaultRecipesInvoker.invokeAddWelding(r, SHEET, DOUBLE_SHEET, null);

		// Armor
		IDefaultRecipesInvoker.invokeAddWelding(r, UNFINISHED_HELMET, SHEET, HELMET, true, ARMOR);
		IDefaultRecipesInvoker.invokeAddWelding(r, UNFINISHED_CHESTPLATE, DOUBLE_SHEET, CHESTPLATE, true, ARMOR);
		IDefaultRecipesInvoker.invokeAddWelding(r, UNFINISHED_GREAVES, SHEET, GREAVES, true, ARMOR);
		IDefaultRecipesInvoker.invokeAddWelding(r, UNFINISHED_BOOTS, SHEET, BOOTS, true, ARMOR);

		// Steel Welding
		IDefaultRecipesInvoker.invokeAddWelding(r, WEAK_STEEL, PIG_IRON, HIGH_CARBON_BLACK_STEEL);
		IDefaultRecipesInvoker.invokeAddWelding(r, WEAK_BLUE_STEEL, BLACK_STEEL, HIGH_CARBON_BLUE_STEEL);
		IDefaultRecipesInvoker.invokeAddWelding(r, WEAK_RED_STEEL, BLACK_STEEL, HIGH_CARBON_RED_STEEL);

		// Special Recipes
		// IDefaultRecipesInvoker.invokeAddWelding(r, KNIFE_BLADE, KNIFE_BLADE, SHEARS, true, TOOLS);

		cir.cancel();
	}
}
