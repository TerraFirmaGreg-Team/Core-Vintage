package su.terrafirmagreg.modules.device.feature.mapping;

import su.terrafirmagreg.datafix.mapping.Remapping;
import su.terrafirmagreg.framework.manager.feature.spi.FeatureBase;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.init.ItemsDevice;
import su.terrafirmagreg.modules.device.init.SoundsDevice;

public class RemappingDevice extends FeatureBase {

  static {
    Remapping.put(Remapping.BLOCK_MAP, m -> {
      m.put("alloy_calculator", BlocksDevice.ALLOY_CALCULATOR);
      m.put("bear_trap", BlocksDevice.BEAR_TRAP);
      m.put("snare", BlocksDevice.SNARE);
      m.put("bellows", BlocksDevice.BELLOWS);
      m.put("blast_furnace", BlocksDevice.BLAST_FURNACE);
      m.put("molten", BlocksDevice.MOLTEN);
      m.put("bloomery", BlocksDevice.BLOOMERY);
      m.put("bloom", BlocksDevice.BLOOM);
      m.put("charcoal_forge", BlocksDevice.CHARCOAL_FORGE);
      m.put("crucible", BlocksDevice.CRUCIBLE);
      m.put("charcoal_pile", BlocksDevice.CHARCOAL_PILE);
      m.put("firepit", BlocksDevice.FIRE_PIT);
      m.put("pit_kiln", BlocksDevice.PIT_KILN);
      m.put("cellar_wall", BlocksDevice.CELLAR_WALL);
      m.put("cellar_door", BlocksDevice.CELLAR_DOOR);
      m.put("cellar_shelf", BlocksDevice.CELLAR_SHELF);
      m.put("smeltery_firebox", BlocksDevice.SMELTERY_FIREBOX);
      m.put("smeltery_cauldron", BlocksDevice.SMELTERY_CAULDRON);
      m.put("latex_extractor", BlocksDevice.LATEX_EXTRACTOR);
      m.put("infected_air", BlocksDevice.INFECTED_AIR);
      m.put("freeze_dryer", BlocksDevice.FREEZE_DRYER);
      m.put("ice_bunker", BlocksDevice.ICE_BUNKER);
      m.put("powderkeg", BlocksDevice.POWDERKEG);
    });

    Remapping.put(Remapping.ITEM_MAP, m -> {
      m.put("firestarter", ItemsDevice.FIRESTARTER);
      m.put("leather_flask", ItemsDevice.LEATHER_FLASK);
      m.put("leather_side", ItemsDevice.LEATHER_FLASK_UNFINISHED);
      m.put("broken_leather_flask", ItemsDevice.LEATHER_FLASK_BROKEN);
      m.put("iron_flask", ItemsDevice.METAL_FLASK);
      m.put("unfinished_iron_flask", ItemsDevice.METAL_FLASK_UNFINISHED);
      m.put("broken_iron_flask", ItemsDevice.METAL_FLASK_BROKEN);
    });

    Remapping.put(Remapping.SOUND_MAP, m -> {
      m.put("item.flaskbreak", SoundsDevice.FLASK_BREAK);
      m.put("item.firestarter", SoundsDevice.FIRE_STARTER);
      m.put("bellows.blow.air", SoundsDevice.BELLOWS_BLOW_AIR);
    });
  }
}
