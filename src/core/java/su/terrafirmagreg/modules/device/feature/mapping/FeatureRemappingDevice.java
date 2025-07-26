package su.terrafirmagreg.modules.device.feature.mapping;

import su.terrafirmagreg.api.util.DataFixUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.modules.device.ModuleDevice;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.init.ItemsDevice;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings({"unchecked"})
public class FeatureRemappingDevice extends BaseFeature {


  @SubscribeEvent
  public static void onBlockRemapping(final RegistryEvent.MissingMappings<Block> event) {
    DataFixUtils.builder(event)
      .logger(ModuleDevice.LOGGER.getLogger())
      .put("alloy_calculator", BlocksDevice.ALLOY_CALCULATOR)
      .put("bear_trap", BlocksDevice.BEAR_TRAP)
      .put("snare", BlocksDevice.SNARE)
      .put("bellows", BlocksDevice.BELLOWS)
      .put("blast_furnace", BlocksDevice.BLAST_FURNACE)
      .put("molten", BlocksDevice.MOLTEN)
      .put("bloomery", BlocksDevice.BLOOMERY)
      .put("bloom", BlocksDevice.BLOOM)
      .put("charcoal_forge", BlocksDevice.CHARCOAL_FORGE)
      .put("crucible", BlocksDevice.CRUCIBLE)
      .put("charcoal_pile", BlocksDevice.CHARCOAL_PILE)
      .put("firepit", BlocksDevice.FIRE_PIT)
      .put("pit_kiln", BlocksDevice.PIT_KILN)
      .put("cellar_wall", BlocksDevice.CELLAR_WALL)
      .put("cellar_door", BlocksDevice.CELLAR_DOOR)
      .put("cellar_shelf", BlocksDevice.CELLAR_SHELF)
      .put("smeltery_firebox", BlocksDevice.SMELTERY_FIREBOX)
      .put("smeltery_cauldron", BlocksDevice.SMELTERY_CAULDRON)
      .put("latex_extractor", BlocksDevice.LATEX_EXTRACTOR)
      .put("infected_air", BlocksDevice.INFECTED_AIR)
      .put("freeze_dryer", BlocksDevice.FREEZE_DRYER)
      .put("ice_bunker", BlocksDevice.ICE_BUNKER)
      .put("powderkeg", BlocksDevice.POWDERKEG)
      .put("thatch_bed", BlocksDevice.THATCH_BED)
      .put("greenhouse_door", BlocksDevice.GREENHOUSE_DOOR)
      .put("greenhouse_wall", BlocksDevice.GREENHOUSE_WALL)
      .put("greenhouse_roof", BlocksDevice.GREENHOUSE_ROOF)
      .put("oven", BlocksDevice.OVEN_BASE)
      .put("oven_wall", BlocksDevice.OVEN_WALL)
      .put("oven_chimney", BlocksDevice.OVEN_CHIMNEY)
      .put("log_pile", BlocksDevice.LOG_PILE)
      .put("leaf_mat", BlocksDevice.LEAF_MAT)
      .put("fridge", BlocksDevice.FRIDGE)
      .build();
  }

  @SubscribeEvent
  public static void onItemRemapping(final RegistryEvent.MissingMappings<Item> event) {
    DataFixUtils.builder(event)
      .logger(ModuleDevice.LOGGER.getLogger())
      .put("firestarter", ItemsDevice.FIRESTARTER)
      .put("leather_flask", ItemsDevice.LEATHER_FLASK)
      .put("leather_side", ItemsDevice.LEATHER_FLASK_UNFINISHED)
      .put("broken_leather_flask", ItemsDevice.LEATHER_FLASK_BROKEN)
      .put("iron_flask", ItemsDevice.METAL_FLASK)
      .put("unfinished_iron_flask", ItemsDevice.METAL_FLASK_UNFINISHED)
      .put("broken_iron_flask", ItemsDevice.METAL_FLASK_BROKEN)
      .put("wooden_bucket", ItemsDevice.WOODEN_BUCKET)

      .put("alloy_calculator", BlocksDevice.ALLOY_CALCULATOR.asItem())
      .put("bear_trap", BlocksDevice.BEAR_TRAP.asItem())
      .put("snare", BlocksDevice.SNARE.asItem())
      .put("bellows", BlocksDevice.BELLOWS.asItem())
      .put("blast_furnace", BlocksDevice.BLAST_FURNACE.asItem())
      .put("molten", BlocksDevice.MOLTEN.asItem())
      .put("bloomery", BlocksDevice.BLOOMERY.asItem())
      .put("bloom", BlocksDevice.BLOOM.asItem())
      .put("charcoal_forge", BlocksDevice.CHARCOAL_FORGE.asItem())
      .put("crucible", BlocksDevice.CRUCIBLE.asItem())
      .put("charcoal_pile", BlocksDevice.CHARCOAL_PILE.asItem())
      .put("firepit", BlocksDevice.FIRE_PIT.asItem())
      .put("pit_kiln", BlocksDevice.PIT_KILN.asItem())
      .put("cellar_wall", BlocksDevice.CELLAR_WALL.asItem())
      .put("cellar_door", BlocksDevice.CELLAR_DOOR.asItem())
      .put("cellar_shelf", BlocksDevice.CELLAR_SHELF.asItem())
      .put("smeltery_firebox", BlocksDevice.SMELTERY_FIREBOX.asItem())
      .put("smeltery_cauldron", BlocksDevice.SMELTERY_CAULDRON.asItem())
      .put("latex_extractor", BlocksDevice.LATEX_EXTRACTOR.asItem())
      .put("infected_air", BlocksDevice.INFECTED_AIR.asItem())
      .put("freeze_dryer", BlocksDevice.FREEZE_DRYER.asItem())
      .put("ice_bunker", BlocksDevice.ICE_BUNKER.asItem())
      .put("powderkeg", BlocksDevice.POWDERKEG.asItem())
      .put("thatch_bed", BlocksDevice.THATCH_BED.asItem())
      .put("greenhouse_door", BlocksDevice.GREENHOUSE_DOOR.asItem())
      .put("greenhouse_wall", BlocksDevice.GREENHOUSE_WALL.asItem())
      .put("greenhouse_roof", BlocksDevice.GREENHOUSE_ROOF.asItem())
      .put("oven", BlocksDevice.OVEN_BASE.asItem())
      .put("oven_wall", BlocksDevice.OVEN_WALL.asItem())
      .put("oven_chimney", BlocksDevice.OVEN_CHIMNEY.asItem())
      .put("log_pile", BlocksDevice.LOG_PILE.asItem())
      .put("leaf_mat", BlocksDevice.LEAF_MAT.asItem())
      .put("fridge", BlocksDevice.FRIDGE.asItem())
      .build();
  }


}
