package su.terrafirmagreg.modules.device.data.recipe;

import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import su.terrafirmagreg.modules.device.data.ItemsDevice;

public class KnappingRecipeAdd {


	public static void register(RegistryEvent.Register<KnappingRecipe> event) {
		event.getRegistry().registerAll(
				new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(ItemsDevice.LEATHER_SIDE),
						"  XX ", " XXX ", "XXXXX", " XXX ", "  X  ").setRegistryName("leather_side")
		);
	}
}
