package net.dries007.tfc.compat.dynamictrees.trees;

import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.compat.dynamictrees.dropcreators.DropCreatorWoodLog;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.tree.variant.block.TreeBlockVariants.*;
import static net.dries007.tfc.api.types.tree.variant.item.TreeItemVariants.SEED;
import static net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariants.LOG;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.blockRootyDirt;

public class WoodTreeFamily extends TreeFamily {

    public static final ArrayList<WoodTreeFamily> TREES = new ArrayList<>();
    private final TreeType tree;
    public boolean hasConiferVariants = false;
    private boolean thick = false;

    public WoodTreeFamily(TreeType tree) {
        super(TerraFirmaCraft.identifier(tree.toString()));

        this.tree = tree;

        setCommonSpecies(new WoodTreeSpecies(this, tree));
        setPrimitiveLog(TFCBlocks.getWoodBlock(LOG, tree.getWood()).getDefaultState());

        var branch = TFCBlocks.getTreeBlock(BRANCH, tree);
        var branch_thick = TFCBlocks.getTreeBlock(BRANCH_THICK, tree);
        setDynamicBranch((BlockBranch) (isThick() ? branch_thick : branch));

        TREES.add(this);
    }

    public TreeType getTree() {
        return tree;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public int getWoodColor() {
        return tree.getWood().getColor();
    }

    @Override
    public boolean isThick() {
        return thick;
    }

    public void setThick(boolean thick) {
        this.thick = thick;
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        var stack = new ItemStack(TFCBlocks.getWoodBlock(LOG, tree.getWood()), qty);
        stack.setCount(MathHelper.clamp(qty, 0, 64));
        return stack;
    }


    public static class WoodTreeSpecies extends Species {

        public static final Map<TreeType, Species> SPECIES = new HashMap<>();

        private final TreeType tree;

        public WoodTreeSpecies(WoodTreeFamily treeFamily, TreeType tree) {
            super(treeFamily.getName(), treeFamily);

            this.tree = tree;

            var leaves = TFCBlocks.getTreeBlock(LEAVES, tree);
            var sapling = TFCBlocks.getTreeBlock(SAPLING, tree);
            var seed = TFCItems.getTreeItem(SEED, tree);

            var leavesProperties = new LeavesProperties(leaves.getDefaultState(), tree.getCellKit());

            remDropCreator(new ResourceLocation(ModConstants.MODID, "logs"));
            addDropCreator(new DropCreatorWoodLog(treeFamily)); // need our own because stacksize
            setSeedStack(new ItemStack(seed));
            setupStandardSeedDropping();
            setLeavesProperties(leavesProperties);
            leavesProperties.setTree(treeFamily);
            LeavesPaging.getNextLeavesBlock(MOD_ID, leavesProperties);

            float[] map = tree.getParamMap();
            setGrowthLogicKit(tree.getGrowthLogicKit()).
                    setBasicGrowingParameters(map[0], map[1], (int) map[2], (int) map[3], map[4]);

            SPECIES.put(tree, this);
            Species.REGISTRY.register(this);
            TreeRegistry.registerSaplingReplacer(sapling.getDefaultState(), this);
        }

        public TreeType getTree() {
            return tree;
        }

        @Override

        public BlockRooty getRootyBlock(World world, BlockPos rootPos) {
            return blockRootyDirt;
        }

        public float getSignalEnergy() {
            return signalEnergy;
        }

        //TFC style.
        @Override
        public boolean canBoneMeal() {
            return false;
        }
    }
}
