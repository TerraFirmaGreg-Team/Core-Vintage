package com.speeedcraft.tfgmod.mixins.tfc;

import net.dries007.tfc.api.recipes.WeldingRecipe;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.types.DefaultRecipes;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.skills.SmithingSkill;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(value = DefaultRecipes.class, remap = false)
public interface IDefaultRecipesInvoker {
	@Invoker
	static void invokeAddAnvil(IForgeRegistry<AnvilRecipe> registry, Metal.ItemType inputType, Metal.ItemType outputType, boolean onlyToolMetals, @Nullable SmithingSkill.Type skillType, ForgeRule... rules) {
		throw new AssertionError();
	}

	@Invoker
	static void invokeAddAnvil(IForgeRegistry<AnvilRecipe> registry, ResourceLocation inputMetalLoc, ResourceLocation outputMetalLoc, @Nullable SmithingSkill.Type skillType) {
		throw new AssertionError();
	}

	@Invoker
	static void invokeAddAnvil(IForgeRegistry<AnvilRecipe> registry, String recipeName, Metal.ItemType inputType, ResourceLocation inputMetalRes, ItemStack output, Metal.Tier tier, @Nullable SmithingSkill.Type skillType, ForgeRule... rules) {
		throw new AssertionError();
	}

	@Invoker
	static void invokeAddWelding(IForgeRegistry<WeldingRecipe> registry, Metal.ItemType inputType, Metal.ItemType outputType, SmithingSkill.Type skillType) {
		throw new AssertionError();
	}

	@Invoker
	static void invokeAddWelding(IForgeRegistry<WeldingRecipe> registry, Metal.ItemType inputType1, Metal.ItemType inputType2, Metal.ItemType outputType, boolean onlyToolMetals, SmithingSkill.Type skillType) {
		throw new AssertionError();
	}

	@Invoker
	static void invokeAddWelding(IForgeRegistry<WeldingRecipe> registry, ResourceLocation input1Loc, ResourceLocation input2Loc, ResourceLocation outputLoc) {
		throw new AssertionError();
	}
}
