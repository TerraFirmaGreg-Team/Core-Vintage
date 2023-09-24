package net.dries007.tfc.module.core.common.objects.recipes.handlers;

import net.dries007.tfc.api.recipes.ChiselRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.util.EnumColor;
import net.dries007.tfc.module.core.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.module.rock.StorageRock;
import net.dries007.tfc.module.rock.api.type.RockType;

import static net.dries007.tfc.api.util.EnumColor.COLORLESS;
import static net.dries007.tfc.module.rock.api.variant.block.RockBlockVariants.RAW;
import static net.dries007.tfc.module.rock.api.variant.block.RockBlockVariants.SMOOTH;


public class ChiselRecipes {

    public static void register() {
        var registry = TFCRegistries.CHISEL;

        // Rock smoothing
        for (var type : RockType.getRockTypes()) {
            var rawRock = StorageRock.getRockBlock(RAW, type);
            var smoothRock = StorageRock.getRockBlock(SMOOTH, type).getDefaultState();
            registry.register(new ChiselRecipe(rawRock, smoothRock).setRegistryName("smooth_" + type));
        }

        // Alabaster smoothing
        for (var color : EnumColor.values()) {
            var rawColoredAlabaster = TFCBlocks.getAlabasterBlock(RAW, color);
            var smoothColoredAlabaster = TFCBlocks.getAlabasterBlock(SMOOTH, color).getDefaultState();
            registry.register(new ChiselRecipe(rawColoredAlabaster, smoothColoredAlabaster).setRegistryName("smooth_" + color.getName() + "_alabaster"));
        }
        // And plain
        var rawAlabaster = TFCBlocks.getAlabasterBlock(RAW, COLORLESS);
        var smoothAlabaster = TFCBlocks.getAlabasterBlock(SMOOTH, COLORLESS).getDefaultState();
        registry.register(new ChiselRecipe(rawAlabaster, smoothAlabaster).setRegistryName("smooth_alabaster"));
    }
}
