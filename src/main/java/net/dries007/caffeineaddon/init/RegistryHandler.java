package net.dries007.caffeineaddon.init;

import su.terrafirmagreg.api.registry.provider.IProviderModel;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.api.data.Reference.MODID_CAFFEINEADDON;

@EventBusSubscriber(modid = MODID_CAFFEINEADDON)
public class RegistryHandler {

  @SubscribeEvent
  public static void onItemRegister(RegistryEvent.Register<Item> event) {
    event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
  }

  @SubscribeEvent
  public static void onModelRegister(ModelRegistryEvent event) {
    for (Item item : ModItems.ITEMS) {
      if (item instanceof IProviderModel customModel) {
        customModel.onModelRegister();
      }
    }
  }

}
