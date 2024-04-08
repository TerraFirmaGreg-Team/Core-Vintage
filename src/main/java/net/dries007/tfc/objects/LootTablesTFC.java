package net.dries007.tfc.objects;

import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;

import net.minecraft.world.storage.loot.LootPool;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import net.dries007.tfc.ConfigTFC;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

@Mod.EventBusSubscriber(modid = MODID_TFC)
public class LootTablesTFC {

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (ConfigTFC.General.OVERRIDES.removeVanillaLoots) {
            // The pool with carrots, potatoes, and iron ingots
            remove(event, "minecraft:entities/zombie_villager", "pool1");
            remove(event, "minecraft:entities/zombie", "pool1");
            remove(event, "minecraft:entities/husk", "pool1");
        }

        // Add calamari to squid's loot table
        if ("minecraft:entities/squid".equals(event.getName().toString())) {
            event.getTable().addPool(event.getLootTableManager().getLootTableFromLocation(LootTablesAnimal.ANIMALS_SQUID).getPool("roll1"));
        }
    }

    private static void remove(LootTableLoadEvent event, String tableName, String pool) {
        if (tableName.equals(event.getName().toString())) {
            event.getTable().removePool(pool);
        }
    }

    private static void remove(LootTableLoadEvent event, String tableName, String poolName, String entry) {
        if (tableName.equals(event.getName().toString())) {
            LootPool pool = event.getTable().getPool(poolName);
            //noinspection ConstantConditions
            if (pool != null) {
                pool.removeEntry(entry);
            }
        }
    }
}
