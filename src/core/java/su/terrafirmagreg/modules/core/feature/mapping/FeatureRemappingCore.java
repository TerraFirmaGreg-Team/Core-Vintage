package su.terrafirmagreg.modules.core.feature.mapping;

import su.terrafirmagreg.api.util.DataFixUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
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

@SuppressWarnings({"unchecked"})
public class FeatureRemappingCore extends BaseFeature {

  @SubscribeEvent
  public static void onBlockRemapping(final RegistryEvent.MissingMappings<Block> event) {
    DataFixUtils.builder(event)
      .logger(ModuleCore.LOGGER.getLogger())
      .put("debug", BlocksCore.DEBUG)
      .put("puddle", BlocksCore.PUDDLE)
      .put("fire_bricks", BlocksCore.FIRE_BRICKS)
      .put("thatch", BlocksCore.THATCH)
      .put("aggregate", BlocksCore.AGGREGATE)
      .build();
  }

  @SubscribeEvent
  public static void onItemRemapping(final RegistryEvent.MissingMappings<Item> event) {
    DataFixUtils.builder(event)
      .logger(ModuleCore.LOGGER.getLogger())
      .put("wand", ItemsCore.DEBUG_WAND)
      .put("wood_ash", ItemsCore.WOOD_ASH)
      .put("straw", ItemsCore.STRAW)
      .put("glass_shard", ItemsCore.GLASS_SHARD)
      .put("ice_shard", ItemsCore.ICE_SHARD)
      .put("packed_ice_shard", ItemsCore.ICE_SHARD)
      .put("sea_ice_shard", ItemsCore.ICE_SHARD)
      .put("mortar", ItemsCore.MORTAR)
      .put("glue", ItemsCore.GLUE)

      .put("debug", BlocksCore.DEBUG.asItem())
      .put("puddle", BlocksCore.PUDDLE.asItem())
      .put("fire_bricks", BlocksCore.FIRE_BRICKS.asItem())
      .put("thatch", BlocksCore.THATCH.asItem())
      .put("aggregate", BlocksCore.AGGREGATE.asItem())
      .build();
  }

  @SubscribeEvent
  public static void onEntityRemapping(final RegistryEvent.MissingMappings<EntityEntry> event) {
    DataFixUtils.builder(event)
      .logger(ModuleCore.LOGGER.getLogger())
      .put("sitblock", EntitiesCore.SIT_BLOCK)
      .build();
  }

  @SubscribeEvent
  public static void onEffectRemapping(final RegistryEvent.MissingMappings<Potion> event) {
    DataFixUtils.builder(event)
      .logger(ModuleCore.LOGGER.getLogger())
      .put("cool", EffectsCore.HYPOTHERMIA)
      .put("warm", EffectsCore.HYPERTHERMIA)
      .put("overburdened", EffectsCore.OVERBURDENED)
      .put("thirst", EffectsCore.THIRST)
      .put("parasites", EffectsCore.PARASITES)
      .put("swarm", EffectsCore.SWARM)
      .build();
  }
}
