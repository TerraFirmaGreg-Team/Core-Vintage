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
      .build();
  }

  @SubscribeEvent
  public static void onItemRemapping(final RegistryEvent.MissingMappings<Item> event) {
    DataFixUtils.builder(event)
      .logger(ModuleWood.LOGGER.getLogger())
      .comparisonRule(DataFixUtils.variantPredicate)
      .put("bookshelf", BlocksWood.BOOKSHELF.values())
      .build();
  }


}
