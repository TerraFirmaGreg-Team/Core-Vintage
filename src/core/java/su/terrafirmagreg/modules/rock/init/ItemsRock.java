package su.terrafirmagreg.modules.rock.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.object.item.ItemRockBrick;
import su.terrafirmagreg.modules.rock.object.item.ItemRockGravel;
import su.terrafirmagreg.modules.rock.object.item.ItemRockLoose;

import java.util.Map;

public class ItemsRock {

  public static Map<RockType, ItemRockLoose> LOOSE;
  public static Map<RockType, ItemRockBrick> BRICK;
  public static Map<RockType, ItemRockGravel> GRAVEL_LAYER;


  public static void onRegister(IRegistryRegistrar registry) {

  }
}
