package tfcflorae.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import static su.terrafirmagreg.Constants.MODID_TFCF;

@ZenRegister
@ZenClass("mods.tfcflorae.knapping")
public class KnappingTFCF {
	@ZenMethod
	public static void add(String type, String name, IItemStack output, String... pattern) {
		KnappingType knappingType = KnappingHelperTFCF.getType(type);
		if (knappingType != null) {
			KnappingHelperTFCF.addRecipe(new ResourceLocation(MODID_TFCF, name), knappingType, output, pattern);
		} else CraftTweakerAPI.logError("Error Wrong Type name entered!");
	}

	@ZenMethod
	public static void remove(String type, IItemStack output) {
		KnappingType knappingType = KnappingHelperTFCF.getType(type);
		if (knappingType != null) {
			KnappingHelperTFCF.removeRecipe(output, knappingType);
		} else CraftTweakerAPI.logError("Error Wrong Type name entered!");
	}

	@ZenMethod
	public static void remove(String registryName) {
		KnappingHelperTFCF.removeRecipe(registryName);
	}
}
