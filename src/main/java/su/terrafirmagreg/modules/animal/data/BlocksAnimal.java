package su.terrafirmagreg.modules.animal.data;


import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.animal.objects.blocks.BlockNestBox;


public final class BlocksAnimal {

	public static BlockNestBox NEST_BOX;

	public static void onRegister(RegistryManager registry) {
		NEST_BOX = registry.registerBlock(new BlockNestBox());
	}

}
