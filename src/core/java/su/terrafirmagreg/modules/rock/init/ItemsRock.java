package su.terrafirmagreg.modules.rock.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.content.item.ItemRockBrick;
import su.terrafirmagreg.modules.rock.content.item.ItemRockGravel;
import su.terrafirmagreg.modules.rock.content.item.ItemRockLoose;

import java.util.Map;

public class ItemsRock {

  public static Map<RockType, ItemRockLoose> LOOSE;
  public static Map<RockType, ItemRockBrick> BRICK;
  public static Map<RockType, ItemRockGravel> GRAVEL_LAYER;


  public static void onRegister(IContentRegistrar registrar) {

    LOOSE = registrar.addItem("loose", ItemRockLoose::new, RockType.getTypes());
    BRICK = registrar.addItem("brick", ItemRockBrick::new, RockType.getTypes());
    GRAVEL_LAYER = registrar.addItem("gravel_layer", ItemRockGravel::new, RockType.getTypes());
  }
}
