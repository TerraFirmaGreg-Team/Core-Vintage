package net.dries007.tfc.api.types.tree.variant.block;

import net.dries007.tfc.common.objects.blocks.tree.BlockTreeBranch;
import net.dries007.tfc.common.objects.blocks.tree.BlockTreeBranchThick;
import net.dries007.tfc.common.objects.blocks.tree.BlockTreeLeaves;
import net.dries007.tfc.common.objects.blocks.tree.BlockTreeSapling;

import static net.dries007.tfc.api.types.tree.variant.block.TreeBlockVariants.*;

public class TreeBlockVariantHandler {
    public static void init() {

        BRANCH = new TreeBlockVariant("branch", BlockTreeBranch::new);
        BRANCH_THICK = new TreeBlockVariant("branch_thick", BlockTreeBranchThick::new);
        LEAVES = new TreeBlockVariant("leaves", BlockTreeLeaves::new);
        SAPLING = new TreeBlockVariant("sapling", BlockTreeSapling::new);

//        FRUIT_LEAVES = new WoodBlockVariant("fruit_leaves", BlockFruitTreeLeaves::new);
//        FRUIT_SAPLING = new WoodBlockVariant("fruit_sapling", BlockFruitTreeSapling::new);
//        FRUIT_TRUNK = new WoodBlockVariant("fruit_sapling", BlockFruitTreeTrunk::new);
//        FRUIT_BRANCH = new WoodBlockVariant("fruit_sapling", BlockFruitTreeBranch::new);

        // POTTED_SAPLING = new WoodBlockVariant("potted_sapling");
    }
}
