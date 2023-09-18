package net.dries007.tfc.common.objects.recipes.handlers;

import net.dries007.tfc.api.recipes.ChiselRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.module.rock.api.type.RockType;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.module.rock.common.RockStorage;
import net.minecraft.item.EnumDyeColor;

import static net.dries007.tfc.module.rock.api.variant.block.RockBlockVariants.RAW;
import static net.dries007.tfc.module.rock.api.variant.block.RockBlockVariants.SMOOTH;


public class ChiselRecipes {

    public static void register() {
        var registry = TFCRegistries.CHISEL;

        // Rock smoothing
        for (var type : RockType.getRockTypes()) {
            var rawRock = RockStorage.getRockBlock(RAW, type);
            var smoothRock = RockStorage.getRockBlock(SMOOTH, type).getDefaultState();
            registry.register(new ChiselRecipe(rawRock, smoothRock).setRegistryName("smooth_" + type));
        }

        // Alabaster smoothing
        for (var color : EnumDyeColor.values()) {
            var rawColoredAlabaster = TFCBlocks.getAlabasterBlock(RAW, color.getName());
            var smoothColoredAlabaster = TFCBlocks.getAlabasterBlock(SMOOTH, color.getName()).getDefaultState();
            registry.register(new ChiselRecipe(rawColoredAlabaster, smoothColoredAlabaster).setRegistryName("smooth_" + color.getName() + "_alabaster"));
        }
        // And plain
        var rawAlabaster = TFCBlocks.getAlabasterBlock(RAW, "plain");
        var smoothAlabaster = TFCBlocks.getAlabasterBlock(SMOOTH, "plain").getDefaultState();
        registry.register(new ChiselRecipe(rawAlabaster, smoothAlabaster).setRegistryName("smooth_alabaster"));
    }
}
