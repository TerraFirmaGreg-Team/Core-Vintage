package su.terrafirmagreg.modules.soil.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.soil.feature.soiltype.spi.type.SoilType;
import su.terrafirmagreg.modules.soil.object.item.ItemSoilMud;
import su.terrafirmagreg.modules.soil.object.item.ItemSoilMudBrick;
import su.terrafirmagreg.modules.soil.object.item.ItemSoilMudWetBrick;
import su.terrafirmagreg.modules.soil.object.item.ItemSoilPile;

import java.util.Map;

public class ItemsSoil {

  public static Map<SoilType, ItemSoilPile> PILE;
  public static Map<SoilType, ItemSoilMud> MUD_BALL;
  public static Map<SoilType, ItemSoilMudBrick> MUD_BRICK;
  public static Map<SoilType, ItemSoilMudWetBrick> MUD_BRICK_WET;

  public static void onRegister(IRegistryRegistrar registry) {
    PILE = registry.addItem(SoilType.getTypes(), ItemSoilPile::new);
    MUD_BALL = registry.addItem(SoilType.getTypes(), ItemSoilMud::new);
    MUD_BRICK = registry.addItem(SoilType.getTypes(), ItemSoilMudBrick::new);
    MUD_BRICK_WET = registry.addItem(SoilType.getTypes(), ItemSoilMudWetBrick::new);


  }

}
