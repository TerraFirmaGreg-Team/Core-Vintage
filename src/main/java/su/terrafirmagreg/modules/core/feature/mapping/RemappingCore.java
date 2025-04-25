package su.terrafirmagreg.modules.core.feature.mapping;

import su.terrafirmagreg.datafix.mapping.Remapping;
import su.terrafirmagreg.framework.manager.feature.spi.FeatureBase;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.EffectsCore;
import su.terrafirmagreg.modules.core.init.EntitiesCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;

public class RemappingCore extends FeatureBase {

  static {
    Remapping.put(Remapping.BLOCK_MAP, m -> {
      m.put("debug", BlocksCore.DEBUG);
      m.put("puddle", BlocksCore.PUDDLE);
      m.put("fire_bricks", BlocksCore.FIRE_BRICKS);
      m.put("thatch", BlocksCore.THATCH);
    });

    Remapping.put(Remapping.ITEM_MAP, m -> {
      m.put("wand", ItemsCore.DEBUG_WAND);
      m.put("wood_ash", ItemsCore.WOOD_ASH);
      m.put("straw", ItemsCore.STRAW);
      m.put("glass_shard", ItemsCore.GLASS_SHARD);
      m.put("ice_shard", ItemsCore.ICE_SHARD);
      m.put("packed_ice_shard", ItemsCore.ICE_SHARD);
      m.put("sea_ice_shard", ItemsCore.ICE_SHARD);
    });

    Remapping.put(Remapping.ENTITY_MAP, m -> {
      m.put("sitblock", EntitiesCore.SIT_BLOCK);
    });

    Remapping.put(Remapping.EFFECT_MAP, m -> {
      m.put("cool", EffectsCore.HYPOTHERMIA);
      m.put("warm", EffectsCore.HYPERTHERMIA);
      m.put("overburdened", EffectsCore.OVERBURDENED);
      m.put("thirst", EffectsCore.THIRST);
      m.put("parasites", EffectsCore.PARASITES);
      m.put("swarm", EffectsCore.SWARM);
    });
  }
}
