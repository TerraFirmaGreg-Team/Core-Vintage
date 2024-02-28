package pieman.caffeineaddon.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import pieman.caffeineaddon.blocks.TEDryingMat;
import pieman.caffeineaddon.client.TESRDryingMat;

import static su.terrafirmagreg.api.lib.Constants.MODID_CAFFEINEADDON;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerItemRenderer(Item item, int metadata, String id) {
		ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(item.getRegistryName(), id));
	}

	@Override
	public <E extends Enum<?>> void registerMetaItemRenderer(Item item, Class<E> variants, String id) {
		for (int i = 0; i < variants.getEnumConstants().length; i++) {
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(MODID_CAFFEINEADDON + ":" + variants.getEnumConstants()[i] + "_" + item.getRegistryName()
			                                                                                                                                                     .toString()
			                                                                                                                                                     .substring(MODID_CAFFEINEADDON.length() + 1), "inventory"));
		}
	}

	@Override
	public void preinit() {
		super.preinit();
	}

	@Override
	public void init() {
		super.init();
		ClientRegistry.bindTileEntitySpecialRenderer(TEDryingMat.class, new TESRDryingMat());
	}

}
