package su.terrafirmagreg.modules.rock.api.types.variant.item;

import su.terrafirmagreg.modules.rock.objects.items.ItemRockBrick;
import su.terrafirmagreg.modules.rock.objects.items.ItemRockGravel;
import su.terrafirmagreg.modules.rock.objects.items.ItemRockLoose;


import static su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariants.*;

public final class RockItemVariantHandler {

    public static void init() {

        LOOSE = RockItemVariant
                .builder("loose")
                .setFactory(ItemRockLoose::new)
                .build();

        BRICK = RockItemVariant
                .builder("brick")
                .setFactory(ItemRockBrick::new)
                .build();

        GRAVEL_LAYER = RockItemVariant
                .builder("gravel_layer")
                .setFactory(ItemRockGravel::new)
                .build();
    }
}
