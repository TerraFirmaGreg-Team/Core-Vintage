package net.dries007.tfc.common.objects.recipes.handlers;

import net.dries007.tfc.module.core.StorageCore;
import net.dries007.tfc.module.core.api.util.EnumColor;
import net.dries007.tfc.module.core.init.RegistryCore;
import net.dries007.tfc.module.rock.StorageRock;
import net.dries007.tfc.module.rock.api.recipes.RecipeRockChisel;
import net.dries007.tfc.module.rock.api.types.type.RockType;

import static net.dries007.tfc.module.core.api.util.EnumColor.COLORLESS;
import static net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariants.RAW;
import static net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariants.SMOOTH;


public class ChiselRecipes {

    public static void register() {
        var registry = RegistryCore.CHISEL;

        // Rock smoothing
        for (var type : RockType.getRockTypes()) {
            var rawRock = StorageRock.getRockBlock(RockBlockVariants.RAW, type);
            var smoothRock = StorageRock.getRockBlock(RockBlockVariants.SMOOTH, type).getDefaultState();
            registry.register(new RecipeRockChisel(rawRock, smoothRock).setRegistryName("smooth_" + type));
        }

        // Alabaster smoothing
        for (var color : EnumColor.values()) {
            var rawColoredAlabaster = StorageCore.getAlabasterBlock(RockBlockVariants.RAW, color);
            var smoothColoredAlabaster = StorageCore.getAlabasterBlock(RockBlockVariants.SMOOTH, color).getDefaultState();
            registry.register(new RecipeRockChisel(rawColoredAlabaster, smoothColoredAlabaster).setRegistryName("smooth_" + color.getName() + "_alabaster"));
        }
        // And plain
        var rawAlabaster = StorageCore.getAlabasterBlock(RockBlockVariants.RAW, EnumColor.COLORLESS);
        var smoothAlabaster = StorageCore.getAlabasterBlock(RockBlockVariants.SMOOTH, EnumColor.COLORLESS).getDefaultState();
        registry.register(new RecipeRockChisel(rawAlabaster, smoothAlabaster).setRegistryName("smooth_alabaster"));
    }
}
