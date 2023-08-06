package net.dries007.tfc.objects.recipes.category;

import net.dries007.tfc.api.recipes.ChiselRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.rock.Rock;
import net.minecraft.item.EnumDyeColor;

import static net.dries007.tfc.api.types.rock.RockType.ORDINARY;
import static net.dries007.tfc.api.types.rock.RockVariant.RAW;
import static net.dries007.tfc.api.types.rock.RockVariant.SMOOTH;

public class ChiselRecipes {

    public static void registerChiselRecipes() {
        var registry = TFCRegistries.CHISEL;

        // Rock smoothing
        for (Rock rock : Rock.values()) {
            var rawRock = TFCStorage.getRockBlock(ORDINARY, RAW, rock);
            var smoothRock = TFCStorage.getRockBlock(ORDINARY, SMOOTH, rock).getDefaultState();
            registry.register(new ChiselRecipe(rawRock, smoothRock).setRegistryName("smooth_" + rock.getName()));
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
