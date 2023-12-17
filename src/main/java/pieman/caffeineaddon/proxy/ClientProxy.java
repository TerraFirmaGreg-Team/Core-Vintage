package pieman.caffeineaddon.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import pieman.caffeineaddon.Reference;
import pieman.caffeineaddon.blocks.TEDryingMat;
import pieman.caffeineaddon.client.TESRDryingMat;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderer(Item item, int metadata, String id) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @Override
    public <E extends Enum<?>> void registerMetaItemRenderer(Item item, Class<E> variants, String id) {
        for (int i = 0; i < variants.getEnumConstants().length; i++) {
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(Reference.MOD_ID + ":" + variants.getEnumConstants()[i] + "_" + item.getRegistryName().toString().substring(Reference.MOD_ID.length() + 1), "inventory"));
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
