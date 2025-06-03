package su.terrafirmagreg.modules.core.feature.mapping;

import su.terrafirmagreg.api.util.DataFixUtils;
import su.terrafirmagreg.framework.manager.feature.spi.FeatureBase;
import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.EffectsCore;
import su.terrafirmagreg.modules.core.init.EntitiesCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class FeatureRemappingCore extends FeatureBase {

  @SubscribeEvent
  public static void onBlockRemapping(final RegistryEvent.MissingMappings<Block> event) {
    DataFixUtils.remap(event, ModuleCore.LOGGER.getLogger(), new Object2ObjectOpenHashMap<>() {{
      put("debug", () -> BlocksCore.DEBUG);
      put("puddle", () -> BlocksCore.PUDDLE);
      put("fire_bricks", () -> BlocksCore.FIRE_BRICKS);
      put("thatch", () -> BlocksCore.THATCH);
      put("aggregate", () -> BlocksCore.AGGREGATE);
    }});
  }

  @SubscribeEvent
  public static void onItemRemapping(final RegistryEvent.MissingMappings<Item> event) {
    DataFixUtils.remap(event, ModuleCore.LOGGER.getLogger(), new Object2ObjectOpenHashMap<>() {{
      put("wand", () -> ItemsCore.DEBUG_WAND);
      put("wood_ash", () -> ItemsCore.WOOD_ASH);
      put("straw", () -> ItemsCore.STRAW);
      put("glass_shard", () -> ItemsCore.GLASS_SHARD);
      put("ice_shard", () -> ItemsCore.ICE_SHARD);
      put("packed_ice_shard", () -> ItemsCore.ICE_SHARD);
      put("sea_ice_shard", () -> ItemsCore.ICE_SHARD);

      put("debug", () -> BlocksCore.DEBUG.asItem());
      put("puddle", () -> BlocksCore.PUDDLE.asItem());
      put("fire_bricks", () -> BlocksCore.FIRE_BRICKS.asItem());
      put("thatch", () -> BlocksCore.THATCH.asItem());
      put("aggregate", () -> BlocksCore.AGGREGATE.asItem());
    }});
  }

  @SubscribeEvent
  public static void onEntityRemapping(final RegistryEvent.MissingMappings<EntityEntry> event) {
    DataFixUtils.remap(event, ModuleCore.LOGGER.getLogger(), new Object2ObjectOpenHashMap<>() {{
      put("sitblock", () -> EntitiesCore.SIT_BLOCK);
    }});
  }

  @SubscribeEvent
  public static void onEffectRemapping(final RegistryEvent.MissingMappings<Potion> event) {
    DataFixUtils.remap(event, ModuleCore.LOGGER.getLogger(), new Object2ObjectOpenHashMap<>() {{
      put("cool", () -> EffectsCore.HYPOTHERMIA);
      put("warm", () -> EffectsCore.HYPERTHERMIA);
      put("overburdened", () -> EffectsCore.OVERBURDENED);
      put("thirst", () -> EffectsCore.THIRST);
      put("parasites", () -> EffectsCore.PARASITES);
      put("swarm", () -> EffectsCore.SWARM);
    }});
  }
}
