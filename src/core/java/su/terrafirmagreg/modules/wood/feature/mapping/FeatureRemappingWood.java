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
      .put("bookshelf", BlocksWood.BOOKSHELF.values())
      .put("workbench", BlocksWood.WORKBENCH.values())
      .put("pressure_plate", BlocksWood.PRESSURE_PLATE.values())
      .put("button", BlocksWood.BUTTON.values())
      .put("fence", BlocksWood.FENCE.values())
      .put("fence_gate", BlocksWood.FENCE_GATE.values())
      .put("fence_log", BlocksWood.FENCE.values())
      .put("fence_gate_log", BlocksWood.FENCE_GATE.values())
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
      .build();
  }


}
