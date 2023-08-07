package net.dries007.tfc.objects.recipes.category;

import net.dries007.tfc.api.recipes.ChiselRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.rock.type.Rock;
import net.minecraft.item.EnumDyeColor;

import static net.dries007.tfc.api.types.rock.block.type.RockBlockTypes.COMMON;
import static net.dries007.tfc.api.types.rock.block.variant.RockBlockVariants.RAW;
import static net.dries007.tfc.api.types.rock.block.variant.RockBlockVariants.SMOOTH;


public class ChiselRecipes {

    public static void registerChiselRecipes() {
        var registry = TFCRegistries.CHISEL;

        // Rock smoothing
        for (Rock rock : Rock.getRockTypes()) {
            var rawRock = TFCStorage.getRockBlock(COMMON, RAW, rock);
            var smoothRock = TFCStorage.getRockBlock(COMMON, SMOOTH, rock).getDefaultState();
            registry.register(new ChiselRecipe(rawRock, smoothRock).setRegistryName("smooth_" + rock));
        }

        // Alabaster smoothing
        for (EnumDyeColor color : EnumDyeColor.values()) {
            var rawColoredAlabaster = TFCStorage.getAlabasterBlock(color.getName(), RAW);
            var smoothColoredAlabaster = TFCStorage.getAlabasterBlock(color.getName(), SMOOTH).getDefaultState();
            registry.register(new ChiselRecipe(rawColoredAlabaster, smoothColoredAlabaster).setRegistryName("smooth_" + color.getName() + "_alabaster"));
        }
        // And plain
        var rawAlabaster = TFCStorage.getAlabasterBlock("plain", RAW);
        var smoothAlabaster = TFCStorage.getAlabasterBlock("plain", SMOOTH).getDefaultState();
        registry.register(new ChiselRecipe(rawAlabaster, smoothAlabaster).setRegistryName("smooth_alabaster"));
    }
}
