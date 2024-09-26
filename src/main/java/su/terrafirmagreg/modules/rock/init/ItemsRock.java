package su.terrafirmagreg.modules.rock.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;
import su.terrafirmagreg.modules.rock.object.item.ItemRockBrick;
import su.terrafirmagreg.modules.rock.object.item.ItemRockGravel;
import su.terrafirmagreg.modules.rock.object.item.ItemRockLoose;

public final class ItemsRock {

  public static RockItemVariant LOOSE;
  public static RockItemVariant BRICK;
  public static RockItemVariant GRAVEL_LAYER;

  public static void onRegister(RegistryManager registry) {

    LOOSE = RockItemVariant
      .builder("loose")
      .setFactory(ItemRockLoose::new)
      .build(registry);

    BRICK = RockItemVariant
      .builder("brick")
      .setFactory(ItemRockBrick::new)
      .build(registry);

    GRAVEL_LAYER = RockItemVariant
      .builder("gravel_layer")
      .setFactory(ItemRockGravel::new)
      .build(registry);

  }
}
