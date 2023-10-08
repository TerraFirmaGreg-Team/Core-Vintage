package net.dries007.tfc.module.wood.plugin.dynamictrees.client;

import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.blocks.BlockSurfaceRoot;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.wood.plugin.dynamictrees.blocks.BlockTreeBranchThick;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Copied from DT, uses TFC asset naming standard for manual branch models.
 * Would have only overridden 1 method, but it's private
 */
@SideOnly(Side.CLIENT)
public class ModelHelperTFC extends ModelHelper {
    /**
     * Registers models associated with the tree.
     * At the moment this only deals with {@link BlockBranch} blocks
     *
     * @param tree
     */
    public static void regModel(TreeFamily tree) {

        var blockBranch = tree.getDynamicBranch();
        var modelLocation = getBranchModelResourceLocation(blockBranch);

        ModelHelper.setGenericStateMapper(blockBranch, modelLocation);
        if (blockBranch instanceof BlockTreeBranchThick) {
            ModelHelper.setGenericStateMapper(((BlockTreeBranchThick) blockBranch).otherBlock, modelLocation);
            Item item = Item.getItemFromBlock(((BlockTreeBranchThick) blockBranch).otherBlock);
            ModelHelper.regModel(item, 0, blockBranch.getRegistryName());
        }

        BlockSurfaceRoot surfaceRoot = tree.getSurfaceRoots();
        if (surfaceRoot != null) {
            ModelLoader.setCustomStateMapper(surfaceRoot, new StateMap.Builder().ignore(surfaceRoot.getIgnorableProperties()).build());
        }

        if (blockBranch != Blocks.AIR) {
            ModelHelper.regModel(Item.getItemFromBlock(blockBranch));
        }

        ModelHelper.regModel(tree.getCommonSpecies().getSeed());
    }


    private static ModelResourceLocation getBranchModelResourceLocation(BlockBranch blockBranch) {
        var family = blockBranch.getFamily().getName().getPath();
        var resloc = Helpers.getID("wood/branch/" + family);
        return new ModelResourceLocation(resloc, null);
    }

}
