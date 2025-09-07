package su.terrafirmagreg.modules.soil.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;
import su.terrafirmagreg.modules.soil.content.item.ItemSoilMud;
import su.terrafirmagreg.modules.soil.content.item.ItemSoilMudBrick;
import su.terrafirmagreg.modules.soil.content.item.ItemSoilMudWetBrick;
import su.terrafirmagreg.modules.soil.content.item.ItemSoilPile;

import java.util.Map;

public class ItemsSoil {

  public static Map<SoilType, ItemSoilPile> PILE;
  public static Map<SoilType, ItemSoilMud> MUD_BALL;
  public static Map<SoilType, ItemSoilMudBrick> MUD_BRICK;
  public static Map<SoilType, ItemSoilMudWetBrick> MUD_BRICK_WET;

  public static void onRegister(IContentRegistrar registry) {
    PILE = registry.addItem(ItemSoilPile::new, SoilType.getTypes());
    MUD_BALL = registry.addItem(ItemSoilMud::new, SoilType.getTypes());
    MUD_BRICK = registry.addItem(ItemSoilMudBrick::new, SoilType.getTypes());
    MUD_BRICK_WET = registry.addItem(ItemSoilMudWetBrick::new, SoilType.getTypes());


  }

}
