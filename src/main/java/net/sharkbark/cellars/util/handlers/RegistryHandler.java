package net.sharkbark.cellars.util.handlers;

import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Metal.ItemType;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.metal.ItemMetal;
import net.dries007.tfc.types.DefaultMetals;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.skills.SmithingSkill;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.sharkbark.cellars.init.ModItems;

import static su.terrafirmagreg.api.lib.Constants.MODID_CELLARS;

@Mod.EventBusSubscriber(modid = MODID_CELLARS)
public class RegistryHandler {


	@SubscribeEvent
	public static void registerAnvilRecipes(RegistryEvent.Register<AnvilRecipe> event) {
		ForgeRule[] iceSawRules = new ForgeRule[]{ForgeRule.HIT_LAST, ForgeRule.UPSET_SECOND_LAST, ForgeRule.DRAW_NOT_LAST};
		event.getRegistry().registerAll(
				new AnvilRecipe(new ResourceLocation(MODID_CELLARS, "bronze_ice_saw"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BRONZE, ItemType.DOUBLE_INGOT))),
						new ItemStack(ModItems.BRONZE_ICE_SAW_HEAD), Metal.BRONZE.getTier(), SmithingSkill.Type.TOOLS, iceSawRules),
				new AnvilRecipe(new ResourceLocation(MODID_CELLARS, "bismuth_bronze_ice_saw"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BISMUTH_BRONZE, ItemType.DOUBLE_INGOT))),
						new ItemStack(ModItems.BISMUTH_BRONZE_ICE_SAW_HEAD), Metal.BISMUTH_BRONZE.getTier(), SmithingSkill.Type.TOOLS, iceSawRules),
				new AnvilRecipe(new ResourceLocation(MODID_CELLARS, "black_bronze_ice_saw"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BLACK_BRONZE, ItemType.DOUBLE_INGOT))),
						new ItemStack(ModItems.BLACK_BRONZE_ICE_SAW_HEAD), Metal.BLACK_BRONZE.getTier(), SmithingSkill.Type.TOOLS, iceSawRules),
				new AnvilRecipe(new ResourceLocation(MODID_CELLARS, "wrought_iron_ice_saw"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.WROUGHT_IRON, ItemType.DOUBLE_INGOT))),
						new ItemStack(ModItems.WROUGHT_IRON_ICE_SAW_HEAD), Metal.WROUGHT_IRON.getTier(), SmithingSkill.Type.TOOLS, iceSawRules),
				new AnvilRecipe(new ResourceLocation(MODID_CELLARS, "steel_ice_saw"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.STEEL, ItemType.DOUBLE_INGOT))),
						new ItemStack(ModItems.STEEL_ICE_SAW_HEAD), Metal.STEEL.getTier(), SmithingSkill.Type.TOOLS, iceSawRules),
				new AnvilRecipe(new ResourceLocation(MODID_CELLARS, "black_steel_ice_saw"), IIngredient.of(new ItemStack(ItemMetal.get(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL), ItemType.DOUBLE_INGOT))),
						new ItemStack(ModItems.BLACK_STEEL_ICE_SAW_HEAD), TFCRegistries.METALS
						.getValue(DefaultMetals.BLACK_STEEL)
						.getTier(), SmithingSkill.Type.TOOLS, iceSawRules),
				new AnvilRecipe(new ResourceLocation(MODID_CELLARS, "red_steel_ice_saw"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.RED_STEEL, ItemType.DOUBLE_INGOT))),
						new ItemStack(ModItems.RED_STEEL_ICE_SAW_HEAD), Metal.RED_STEEL.getTier(), SmithingSkill.Type.TOOLS, iceSawRules),
				new AnvilRecipe(new ResourceLocation(MODID_CELLARS, "blue_steel_ice_saw"), IIngredient.of(new ItemStack(ItemMetal.get(Metal.BLUE_STEEL, ItemType.DOUBLE_INGOT))),
						new ItemStack(ModItems.BLUE_STEEL_ICE_SAW_HEAD), Metal.BLUE_STEEL.getTier(), SmithingSkill.Type.TOOLS, iceSawRules)
		);
	}

}
