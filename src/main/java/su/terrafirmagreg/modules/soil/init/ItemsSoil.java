package su.terrafirmagreg.modules.soil.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;
import su.terrafirmagreg.modules.soil.object.item.ItemSoilMud;
import su.terrafirmagreg.modules.soil.object.item.ItemSoilMudBrick;
import su.terrafirmagreg.modules.soil.object.item.ItemSoilMudWetBrick;
import su.terrafirmagreg.modules.soil.object.item.ItemSoilPile;

public final class ItemsSoil {

  public static SoilItemVariant PILE;
  public static SoilItemVariant MUD_BALL;
  public static SoilItemVariant MUD_BRICK;
  public static SoilItemVariant MUD_BRICK_WET;

  public static void onRegister(RegistryManager registry) {

    PILE = SoilItemVariant
            .builder("pile")
            .factory(ItemSoilPile::new)
            .build(registry);

    MUD_BALL = SoilItemVariant
            .builder("mud_ball")
            .factory(ItemSoilMud::new)
            .build(registry);

    MUD_BRICK = SoilItemVariant
            .builder("mud_brick")
            .factory(ItemSoilMudBrick::new)
            .build(registry);

    MUD_BRICK_WET = SoilItemVariant
            .builder("mud_brick_wet")
            .factory(ItemSoilMudWetBrick::new)
            .build(registry);
  }
}
