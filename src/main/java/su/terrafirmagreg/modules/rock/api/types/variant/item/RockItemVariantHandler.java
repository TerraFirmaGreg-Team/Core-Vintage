package su.terrafirmagreg.modules.rock.api.types.variant.item;

import su.terrafirmagreg.modules.rock.objects.items.*;

public class RockItemVariantHandler {

    public static void init() {

        RockItemVariants.LOOSE = new RockItemVariant.Builder("loose")
                .setFactory(ItemRockLoose::new)
                .build();

        RockItemVariants.BRICK = new RockItemVariant.Builder("brick")
                .setFactory(ItemRockBrick::new)
                .build();

        RockItemVariants.GRAVEL_LAYER = new RockItemVariant.Builder("gravel_layer")
                .setFactory(ItemRockGravel::new)
                .build();
    }
}
