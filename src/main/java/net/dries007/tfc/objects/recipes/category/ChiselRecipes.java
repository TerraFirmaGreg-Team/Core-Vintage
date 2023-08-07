package net.dries007.tfc.objects.recipes.category;

import net.dries007.tfc.api.recipes.ChiselRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.rock.block.type.RockBlockTypes;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariant;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariants;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.minecraft.item.EnumDyeColor;


public class ChiselRecipes {

    public static void registerChiselRecipes() {
        var registry = TFCRegistries.CHISEL;

        // Rock smoothing
        for (RockType rockType : RockType.getRockTypes()) {
            var rawRock = TFCStorage.getRockBlock(RockBlockTypes.Common, RockBlockVariants.Raw, rockType);
            var smoothRock = TFCStorage.getRockBlock(RockBlockTypes.Common, RockBlockVariants.Smooth, rockType).getDefaultState();
            registry.register(new ChiselRecipe(rawRock, smoothRock).setRegistryName("smooth_" + rockType));
        }

        // Alabaster smoothing
        for (EnumDyeColor color : EnumDyeColor.values()) {
            var rawColoredAlabaster = TFCStorage.getAlabasterBlock(color.getName(), RockBlockVariants.Raw);
            var smoothColoredAlabaster = TFCStorage.getAlabasterBlock(color.getName(), RockBlockVariants.Smooth).getDefaultState();
            registry.register(new ChiselRecipe(rawColoredAlabaster, smoothColoredAlabaster).setRegistryName("smooth_" + color.getName() + "_alabaster"));
        }
        // And plain
        var rawAlabaster = TFCStorage.getAlabasterBlock("plain", RockBlockVariants.Raw);
        var smoothAlabaster = TFCStorage.getAlabasterBlock("plain", RockBlockVariants.Smooth).getDefaultState();
        registry.register(new ChiselRecipe(rawAlabaster, smoothAlabaster).setRegistryName("smooth_alabaster"));
    }
}
