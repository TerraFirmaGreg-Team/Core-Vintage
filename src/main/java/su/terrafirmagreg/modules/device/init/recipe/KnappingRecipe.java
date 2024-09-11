package su.terrafirmagreg.modules.device.init.recipe;

import su.terrafirmagreg.modules.device.init.ItemsDevice;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;


import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingTypes;

public class KnappingRecipe {

  public static void register(
          RegistryEvent.Register<net.dries007.tfc.api.recipes.knapping.KnappingRecipe> event) {
    event.getRegistry().registerAll(
            new KnappingRecipeSimple(
                    KnappingTypes.LEATHER, true, new ItemStack(ItemsDevice.LEATHER_SIDE),
                    "  XX ", " XXX ", "XXXXX", " XXX ", "  X  ").setRegistryName("leather_side")
    );
  }
}
