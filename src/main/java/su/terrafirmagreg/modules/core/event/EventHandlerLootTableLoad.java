package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.api.util.LootUtils;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;

import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerLootTableLoad {

  @SubscribeEvent
  public static void on(LootTableLoadEvent event) {
    // The pool with carrots, potatoes, and iron ingots
    LootUtils.remove(event, "minecraft:entities/zombie_villager", "pool1");
    LootUtils.remove(event, "minecraft:entities/zombie", "pool1");
    LootUtils.remove(event, "minecraft:entities/husk", "pool1");

    // Add calamari to squid's loot table
    LootUtils.add(event, "minecraft:entities/squid", LootTablesAnimal.ANIMALS_SQUID, "roll1");
  }
}
