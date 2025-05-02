package su.terrafirmagreg.api.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.event.LootTableLoadEvent;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public final class LootUtils {


  public static void removeLoot(LootTableLoadEvent event, String tableName, String poolName) {
    if (isTableNameMatching(event, tableName)) {
      event.getTable().removePool(poolName);
    }
  }

  public static void removeLoot(LootTableLoadEvent event, String tableName, String poolName, String entryName) {
    if (isTableNameMatching(event, tableName)) {
      LootPool pool = event.getTable().getPool(poolName);
      //noinspection ConstantConditions
      if (pool != null) {
        pool.removeEntry(entryName);
      }
    }
  }

  public static void addLoot(LootTableLoadEvent event, String tableName, ResourceLocation resourceLocation, String poolName) {
    if (isTableNameMatching(event, tableName)) {
      addLoot(event.getTable(), poolName, resourceLocation, event.getLootTableManager());
    }
  }

  public static boolean isTableNameMatching(LootTableLoadEvent event, String tableName) {
    return tableName.equals(event.getName().toString());
  }

  public static void addLoot(LootTable lootTable, String poolName, ResourceLocation resourceLocation, LootTableManager lootTableManager) {
    LootTable tableFromLocation = lootTableManager.getLootTableFromLocation(resourceLocation);
    LootPool pool = tableFromLocation.getPool(poolName);
    //noinspection ConstantConditions
    if (pool != null) {
      lootTable.addPool(pool);
    }
  }

  public static ResourceLocation addLoot(ResourceLocation identifier) {
    return LootTableList.register(identifier);
  }

  public static <T extends LootFunction> void addLootFunction(LootFunction.Serializer<? extends T> serializer) {

    LootFunctionManager.registerFunction(serializer);
  }

}
