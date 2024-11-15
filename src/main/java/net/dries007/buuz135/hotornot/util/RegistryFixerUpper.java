package net.dries007.buuz135.hotornot.util;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.buuz135.hotornot.object.item.ItemMetalTongs;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;

import static su.terrafirmagreg.api.data.Reference.MODID_HOTORNOT;

// TODO remove at some point? Only needed to transition to the new names
@EventBusSubscriber(modid = MODID_HOTORNOT)
public final class RegistryFixerUpper {

  @SubscribeEvent
  public static void onMissingMapping(final MissingMappings<Item> event) {

    nextMapping:
    for (final Mapping<Item> itemMapping : event.getMappings()) {
      final String oldItemName = itemMapping.key.getPath();

      for (final Metal metal : TFCRegistries.METALS.getValuesCollection()) {
        // We only made tongs for tool metals
        if (!metal.isToolMetal()) {
          continue;
        }

        // Found the metal type the old item was
        if (oldItemName.equals(metal + "_tongs")) {
          itemMapping.remap(ItemMetalTongs.get(metal));
          continue nextMapping;
        }
      }
    }
  }
}
