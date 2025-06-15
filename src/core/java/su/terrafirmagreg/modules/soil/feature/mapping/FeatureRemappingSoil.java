package su.terrafirmagreg.modules.soil.feature.mapping;

import su.terrafirmagreg.api.util.DataFixUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.modules.soil.ModuleSoil;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class FeatureRemappingSoil extends BaseFeature {


  @SubscribeEvent
  public static void onBlockRemapping(final RegistryEvent.MissingMappings<Block> event) {
    DataFixUtils.remap(event, ModuleSoil.LOGGER.getLogger(), new Object2ObjectOpenHashMap<>() {{
      put("peat", () -> BlocksSoil.PEAT_GRASS);
      put("peat_grass", () -> BlocksSoil.PEAT_GRASS);

    }});
  }

  @SubscribeEvent
  public static void onItemRemapping(final RegistryEvent.MissingMappings<Item> event) {
    DataFixUtils.remap(event, ModuleSoil.LOGGER.getLogger(), new Object2ObjectOpenHashMap<>() {{
      put("peat", () -> BlocksSoil.PEAT_GRASS.asItem());
      put("peat_grass", () -> BlocksSoil.PEAT_GRASS.asItem());

    }});
  }


}
