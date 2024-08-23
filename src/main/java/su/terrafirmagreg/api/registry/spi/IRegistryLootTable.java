package su.terrafirmagreg.api.registry.spi;

import su.terrafirmagreg.api.lib.LootBuilder;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;


import java.util.List;

public interface IRegistryLootTable
        extends IRegistryBase {

    /**
     * Registers a loot table with the loot table list. This needs to be called before a loot table can be used.
     *
     * @param name The name of the loot table to use.
     * @return A ResourceLocation pointing to the table.
     */
    default ResourceLocation lootTable(String name) {

        return LootTableList.register(new ResourceLocation(this.getModID(), name));
    }

    default <T extends LootFunction> void lootFunction(LootFunction.Serializer<? extends T> serializer) {

        LootFunctionManager.registerFunction(serializer);
    }

    /**
     * Creates a new loot entry that will be added to the loot pools when a world is loaded.
     *
     * @param location The loot table to add the loot to. You can use {@link LootTableList} for convenience.
     * @param name     The name of the entry being added.
     * @param pool     The name of the pool to add the entry to. This pool must already exist.
     * @param weight   The weight of the entry.
     * @param item     The item to add.
     * @param meta     The metadata for the loot.
     * @param amount   The amount of the item to set.
     * @return A builder object. It can be used to fine tune the loot entry.
     */
    default LootBuilder loot(ResourceLocation location, String name, String pool, int weight, Item item, int meta, int amount) {

        return this.loot(location, name, pool, weight, item, meta, amount, amount);
    }

    /**
     * Creates a new loot entry that will be added to the loot pools when a world is loaded.
     *
     * @param location The loot table to add the loot to. You can use {@link LootTableList} for convenience.
     * @param name     The name of the entry being added.
     * @param pool     The name of the pool to add the entry to. This pool must already exist.
     * @param weight   The weight of the entry.
     * @param item     The item to add.
     * @param meta     The metadata for the loot.
     * @param min      The smallest item size.
     * @param max      The largest item size.
     * @return A builder object. It can be used to fine tune the loot entry.
     */
    default LootBuilder loot(ResourceLocation location, String name, String pool, int weight, Item item, int meta, int min, int max) {

        final LootBuilder loot = this.loot(location, name, pool, weight, item, meta);
        loot.addFunction(new SetCount(new LootCondition[0], new RandomValueRange(min, max)));
        return loot;
    }

    /**
     * Creates a new loot entry that will be added to the loot pools when a world is loaded.
     *
     * @param location The loot table to add the loot to. You can use {@link LootTableList} for convenience.
     * @param name     The name of the entry being added.
     * @param pool     The name of the pool to add the entry to. This pool must already exist.
     * @param weight   The weight of the entry.
     * @param item     The item to add.
     * @param meta     The metadata for the loot.
     * @return A builder object. It can be used to fine tune the loot entry.
     */
    default LootBuilder loot(ResourceLocation location, String name, String pool, int weight, Item item, int meta) {

        final LootBuilder loot = this.loot(location, name, pool, weight, item);
        loot.addFunction(new SetMetadata(new LootCondition[0], new RandomValueRange(meta, meta)));
        return loot;
    }

    /**
     * Creates a new loot entry that will be added to the loot pools when a world is loaded.
     *
     * @param location The loot table to add the loot to. You can use {@link LootTableList} for convenience.
     * @param name     The name of the entry being added.
     * @param pool     The name of the pool to add the entry to. This pool must already exist.
     * @param weight   The weight of the entry.
     * @param item     The item to add.
     * @return A builder object. It can be used to fine tune the loot entry.
     */
    default LootBuilder loot(ResourceLocation location, String name, String pool, int weight, Item item) {

        return this.loot(location, new LootBuilder(this.getModID() + ":" + name, pool, weight, item));
    }

    /**
     * Creates a new loot entry that will be added to the loot pools when a world is loaded.
     *
     * @param location   The loot table to add the loot to. You can use {@link LootTableList} for convenience.
     * @param name       The name of the entry being added.
     * @param pool       The name of the pool to add the entry to. This pool must already exist.
     * @param weight     The weight of the entry.
     * @param quality    The quality of the entry. Quality is an optional value which modifies the weight of an entry based on the player's luck level. totalWeight = weight +
     *                   (quality * luck)
     * @param item       The item to add.
     * @param conditions A list of loot conditions.
     * @param functions  A list of loot functions.
     * @return A builder object. It can be used to fine tune the loot entry.
     */
    default LootBuilder loot(ResourceLocation location, String name, String pool, int weight, int quality, Item item, List<LootCondition> conditions,
                             List<LootFunction> functions) {

        return this.loot(location, new LootBuilder(this.getModID() + ":" + name, pool, weight, quality, item, conditions, functions));
    }

    /**
     * Creates a new loot entry that will be added to the loot pools when a world is loaded.
     *
     * @param location The loot table to add the loot to. You can use {@link LootTableList} for convenience.
     * @param builder  The loot builder to add.
     * @return A builder object. It can be used to fine tune the loot entry.
     */
    default LootBuilder loot(ResourceLocation location, LootBuilder builder) {

        this.getRegistry().getLootTable().put(location, builder);
        return builder;
    }
}
