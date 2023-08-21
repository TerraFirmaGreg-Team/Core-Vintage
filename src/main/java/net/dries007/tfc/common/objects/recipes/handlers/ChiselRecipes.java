package net.dries007.tfc.common.objects.recipes.handlers;

import net.dries007.tfc.api.recipes.ChiselRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.minecraft.item.EnumDyeColor;

import static net.dries007.tfc.api.types.rock.variant.RockBlockVariants.RAW;
import static net.dries007.tfc.api.types.rock.variant.RockBlockVariants.SMOOTH;


public class ChiselRecipes {

    public static void register() {
        var registry = TFCRegistries.CHISEL;

        // Rock smoothing
        for (RockType rockType : RockType.getRockTypes()) {
            var rawRock = TFCBlocks.getRockBlock(RAW, rockType);
            var smoothRock = TFCBlocks.getRockBlock(SMOOTH, rockType).getDefaultState();
            registry.register(new ChiselRecipe(rawRock, smoothRock).setRegistryName("smooth_" + rockType));
        }

        // Alabaster smoothing
        for (EnumDyeColor color : EnumDyeColor.values()) {
            var rawColoredAlabaster = TFCBlocks.getAlabasterBlock(color.getName(), RAW);
            var smoothColoredAlabaster = TFCBlocks.getAlabasterBlock(color.getName(), SMOOTH).getDefaultState();
            registry.register(new ChiselRecipe(rawColoredAlabaster, smoothColoredAlabaster).setRegistryName("smooth_" + color.getName() + "_alabaster"));
        }
        // And plain
        var rawAlabaster = TFCBlocks.getAlabasterBlock("plain", RAW);
        var smoothAlabaster = TFCBlocks.getAlabasterBlock("plain", SMOOTH).getDefaultState();
        registry.register(new ChiselRecipe(rawAlabaster, smoothAlabaster).setRegistryName("smooth_alabaster"));
    }
}
