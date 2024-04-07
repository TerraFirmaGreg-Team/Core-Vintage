package su.terrafirmagreg.modules.device.data.recipe;

import su.terrafirmagreg.modules.device.data.ItemsDevice;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;

import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingType;

public class KnappingRecipeAdd {

    public static void register(RegistryEvent.Register<KnappingRecipe> event) {
        event.getRegistry().registerAll(
                new KnappingRecipeSimple(
                        KnappingType.LEATHER, true, new ItemStack(ItemsDevice.LEATHER_SIDE),
                        "  XX ", " XXX ", "XXXXX", " XXX ", "  X  ").setRegistryName("leather_side")
        );
    }
}
