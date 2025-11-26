package su.terrafirmagreg.modules.wood.feature.mapping;

import su.terrafirmagreg.api.util.DataFixUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.modules.wood.ModuleWood;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatureRemappingWood extends BaseFeature {


  @SubscribeEvent
  public static void onBlockRemapping(final RegistryEvent.MissingMappings<Block> event) {
    DataFixUtils.builder(event)
      .logger(ModuleWood.LOGGER.getLogger())
      .comparisonRule(DataFixUtils.variantPredicate)
      .put("log", BlocksWood.LOG.values())
      .put("leaves", BlocksWood.LEAVES.values())
      .put("sapling", BlocksWood.SAPLING.values())
      .put("planks", BlocksWood.PLANKS.values())
      .put("bookshelf", BlocksWood.BOOKSHELF.values())
      .put("door", BlocksWood.DOOR.values())
      .put("trapdoor", BlocksWood.TRAPDOOR.values())
      .put("fence", BlocksWood.FENCE.values())
      .put("fence_gate", BlocksWood.FENCE_GATE.values())
      .put("fence_log", BlocksWood.FENCE.values())
      .put("fence_gate_log", BlocksWood.FENCE_GATE.values())
      .put("button", BlocksWood.BUTTON.values())
      .put("pressure_plate", BlocksWood.PRESSURE_PLATE.values())
      .put("tool_rack", BlocksWood.TOOL_RACK.values())
      .put("support", BlocksWood.SUPPORT.values())
      .put("workbench", BlocksWood.WORKBENCH.values())
      .put("chest_trap", BlocksWood.CHEST_TRAPPED.values())
      .put("chest", BlocksWood.CHEST.values())
      .put("loom", BlocksWood.LOOM.values())
      .put("barrel", BlocksWood.BARREL.values())
      .put("ladder", BlocksWood.LADDER.values())
      .build();
  }

  @SubscribeEvent
  public static void onItemRemapping(final RegistryEvent.MissingMappings<Item> event) {
    DataFixUtils.builder(event)
      .logger(ModuleWood.LOGGER.getLogger())
      .comparisonRule(DataFixUtils.variantPredicate)
      .put("bookshelf", BlocksWood.BOOKSHELF.values())
      .put("workbench", BlocksWood.WORKBENCH.values())
      .put("pressure_plate", BlocksWood.PRESSURE_PLATE.values())
      .put("button", BlocksWood.BUTTON.values())
      .put("fence", BlocksWood.FENCE.values())
      .put("fence_gate", BlocksWood.FENCE_GATE.values())
      .put("fence_log", BlocksWood.FENCE.values())
      .put("fence_gate_log", BlocksWood.FENCE_GATE.values())
      .put("door", BlocksWood.DOOR.values())
      .put("trapdoor", BlocksWood.TRAPDOOR.values())
      .put("chest", BlocksWood.CHEST.values())
      .put("chest_trap", BlocksWood.CHEST_TRAPPED.values())
      .put("support", BlocksWood.SUPPORT.values())
      .build();
  }


}
