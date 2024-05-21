package pieman.caffeineaddon.init;

import su.terrafirmagreg.api.registry.provider.IModelProvider;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;


import pieman.caffeineaddon.CaffeineAddon;
import pieman.caffeineaddon.blocks.TEDryingMat;
import pieman.caffeineaddon.client.GUIHandler;

import static su.terrafirmagreg.api.data.Constants.MODID_CAFFEINEADDON;

@EventBusSubscriber(modid = MODID_CAFFEINEADDON)
public class RegistryHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
        event.getRegistry().registerAll(ModItems.ITEMBLOCKS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for (Item item : ModItems.ITEMS) {
            if (item instanceof IModelProvider) {
                ((IModelProvider) item).onModelRegister();
            }
        }
        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof IModelProvider customModel) {
                customModel.onModelRegister();
            }
        }
    }

    public static void preInitRegistries() {
    }

    public static void initRegistries() {
        GameRegistry.registerTileEntity(TEDryingMat.class, MODID_CAFFEINEADDON + ":drying_mat");
        NetworkRegistry.INSTANCE.registerGuiHandler(CaffeineAddon.instance, new GUIHandler());
    }

    public static void postInitRegistries() {
    }

}
