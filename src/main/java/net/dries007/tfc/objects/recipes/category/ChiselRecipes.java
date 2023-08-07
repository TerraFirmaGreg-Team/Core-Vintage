package net.dries007.tfc.objects.recipes.category;

import net.dries007.tfc.api.recipes.ChiselRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.rock.block.type.RockBlockTypes;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariants;
import net.dries007.tfc.api.types.rock.type.Rock;
import net.minecraft.item.EnumDyeColor;


public class ChiselRecipes {

    public static void registerChiselRecipes() {
        var registry = TFCRegistries.CHISEL;

        // Rock smoothing
        for (Rock rock : Rock.getRockTypes()) {
            var rawRock = TFCStorage.getRockBlock(RockBlockTypes.COMMON, RockBlockVariants.RAW, rock);
            var smoothRock = TFCStorage.getRockBlock(RockBlockTypes.COMMON, RockBlockVariants.SMOOTH, rock).getDefaultState();
            registry.register(new ChiselRecipe(rawRock, smoothRock).setRegistryName("smooth_" + rock));
        }

        // Alabaster smoothing
        for (EnumDyeColor color : EnumDyeColor.values()) {
            var rawColoredAlabaster = TFCStorage.getAlabasterBlock(color.getName(), RockBlockVariants.RAW);
            var smoothColoredAlabaster = TFCStorage.getAlabasterBlock(color.getName(), RockBlockVariants.SMOOTH).getDefaultState();
            registry.register(new ChiselRecipe(rawColoredAlabaster, smoothColoredAlabaster).setRegistryName("smooth_" + color.getName() + "_alabaster"));
        }
        // And plain
        var rawAlabaster = TFCStorage.getAlabasterBlock("plain", RockBlockVariants.RAW);
        var smoothAlabaster = TFCStorage.getAlabasterBlock("plain", RockBlockVariants.SMOOTH).getDefaultState();
        registry.register(new ChiselRecipe(rawAlabaster, smoothAlabaster).setRegistryName("smooth_alabaster"));
    }
}
