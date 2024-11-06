package su.terrafirmagreg.core;

import su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipeMeasurable;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipeSplitting;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeStone;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemsTFC;
import tfcflorae.api.knapping.KnappingTypes;

import static gregtech.api.recipes.RecipeMaps.MACERATOR_RECIPES;
import static net.dries007.tfc.util.forge.ForgeRule.HIT_LAST;
import static net.dries007.tfc.util.forge.ForgeRule.HIT_SECOND_LAST;
import static net.dries007.tfc.util.forge.ForgeRule.HIT_THIRD_LAST;
import static net.dries007.tfc.util.forge.ForgeRule.PUNCH_LAST;
import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.core.util.TFGModUtils.TFG_OREPREFIX_REGISTRY;

public class Recipes {

  public static void register() {
    fixStoneToolsRecipes();
    //fixFlintToolsRecipes(); // TODO

    registerKnappingRecipes();
    registerAnvilRecipes();
  }

  private static void fixStoneToolsRecipes() {
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadSword, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadPickaxe, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadSense, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadFile, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadPropick, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadChisel, Materials.Stone));
  }

  private static void fixFlintToolsRecipes() {
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadSword, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadPickaxe, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadSense, Materials.Stone));
    // GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadFile, Materials.Stone));
    // GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadSaw, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadPropick, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadChisel, Materials.Stone));
  }

  private static void registerAnvilRecipes() {

    IForgeRegistry<AnvilRecipe> r = TFCRegistries.ANVIL;

    // Bloom works
    r.registerAll(
      new AnvilRecipeMeasurable(new ResourceLocation(MOD_ID, "refining_bloom"), IIngredient.of(ItemsTFC.UNREFINED_BLOOM), new ItemStack(ItemsTFC.REFINED_BLOOM), Metal.Tier.TIER_II, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST),
      new AnvilRecipeSplitting(new ResourceLocation(MOD_ID, "splitting_bloom"), IIngredient.of(ItemsTFC.REFINED_BLOOM), new ItemStack(ItemsTFC.REFINED_BLOOM), 144, Metal.Tier.TIER_II, PUNCH_LAST),
      new AnvilRecipe(new ResourceLocation(MOD_ID, "iron_bloom"), x -> {
        if (x.getItem() == ItemsTFC.REFINED_BLOOM) {
          IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
          if (cap instanceof IForgeableMeasurableMetal) {
            return ((IForgeableMeasurableMetal) cap).getMetal() == Metal.WROUGHT_IRON && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 144;
          }
        }
        return false;
      }, OreDictUnifier.get(OrePrefix.ingot, Materials.WroughtIron), Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST)
    );

  }

  private static void registerKnappingRecipes() {
    IForgeRegistry<KnappingRecipe> r = TFCRegistries.KNAPPING;

    TFG_OREPREFIX_REGISTRY.forEach(s -> {

      // This covers all stone -> single tool head recipes
      r.register(new KnappingRecipeStone(KnappingType.STONE, rockIn -> OreDictUnifier.get(s.getOrePrefix(), Materials.Stone), s.getStoneKnappingRecipe()).setRegistryName(MOD_ID,
                                                                                                                                                                          s.getOrePrefix()
                                                                                                                                                                           .name()
                                                                                                                                                                           .toLowerCase()
                                                                                                                                                                          + "_stone_head"));

      // This covers all flint -> single tool head recipes
      if (s.getOrePrefix() != TFGOrePrefix.toolHeadHammer) {
        r.register(new KnappingRecipeSimple(KnappingTypes.FLINT, true, OreDictUnifier.get(s.getOrePrefix(), Materials.Flint), s.getStoneKnappingRecipe()).setRegistryName(MOD_ID,
                                                                                                                                                                          s.getOrePrefix()
                                                                                                                                                                           .name()
                                                                                                                                                                           .toLowerCase()
                                                                                                                                                                          + "_flint_head"));
      }
    });

    // these recipes cover all cases where multiple stone and flint items can be made
    // recipes are already mirror checked
    r.registerAll(
      new KnappingRecipeStone(KnappingType.STONE, rockIn -> OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, Materials.Stone, 2), "X  X ", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName(MOD_ID, "stone_knife_head_1"),
      new KnappingRecipeStone(KnappingType.STONE, rockIn -> OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, Materials.Stone, 2), "X   X", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName(MOD_ID, "stone_knife_head_2"),
      new KnappingRecipeStone(KnappingType.STONE, rockIn -> OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, Materials.Stone, 2), " X X ", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName(MOD_ID, "stone_knife_head_3"),
      new KnappingRecipeStone(KnappingType.STONE, rockIn -> OreDictUnifier.get(TFGOrePrefix.toolHeadHoe, Materials.Stone, 2), "XXXXX", "XX   ", "     ", "XXXXX", "XX   ").setRegistryName(MOD_ID, "stone_hoe_head_1"),
      new KnappingRecipeStone(KnappingType.STONE, rockIn -> OreDictUnifier.get(TFGOrePrefix.toolHeadHoe, Materials.Stone, 2), "XXXXX", "XX   ", "     ", "XXXXX", "   XX").setRegistryName(MOD_ID, "stone_hoe_head_2"),

      new KnappingRecipeSimple(KnappingTypes.FLINT, true, OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, Materials.Flint, 2), "X  X ", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName(MOD_ID, "flint_knife_head_1"),
      new KnappingRecipeSimple(KnappingTypes.FLINT, true, OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, Materials.Flint, 2), "X   X", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName(MOD_ID, "flint_knife_head_2"),
      new KnappingRecipeSimple(KnappingTypes.FLINT, true, OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, Materials.Flint, 2), " X X ", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName(MOD_ID, "flint_knife_head_3"),
      new KnappingRecipeSimple(KnappingTypes.FLINT, true, OreDictUnifier.get(TFGOrePrefix.toolHeadHoe, Materials.Flint, 2), "XXXXX", "XX   ", "     ", "XXXXX", "XX   ").setRegistryName(MOD_ID, "flint_hoe_head_1"),
      new KnappingRecipeSimple(KnappingTypes.FLINT, true, OreDictUnifier.get(TFGOrePrefix.toolHeadHoe, Materials.Flint, 2), "XXXXX", "XX   ", "     ", "XXXXX", "   XX").setRegistryName(MOD_ID, "flint_hoe_head_2")
    );
  }
}
