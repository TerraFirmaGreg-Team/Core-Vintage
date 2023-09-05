package net.dries007.tfc.api.types.tree.variant.item;

import net.dries007.tfc.common.objects.items.tree.ItemTreeSeed;

import static net.dries007.tfc.api.types.tree.variant.item.TreeItemVariants.SEED;

public class TreeItemVariantHandler {
    public static void init() {
        SEED = new TreeItemVariant("seed", ItemTreeSeed::new);
    }
}
