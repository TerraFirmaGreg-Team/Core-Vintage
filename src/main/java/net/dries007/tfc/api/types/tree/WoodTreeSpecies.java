package net.dries007.tfc.api.types.tree;

import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.trees.Species;
import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.compat.dynamictrees.dropcreators.DropCreatorWoodLog;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariants.SAPLING;
import static net.dries007.tfc.api.types.wood.variant.item.WoodItemVariants.SEED;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.ROOTY_DIRT_MIMIC;

public class WoodTreeSpecies extends Species {

    public WoodTreeSpecies(TreeType tree) {
        super(tree.getName(), tree);

        var sapling = TFCBlocks.getWoodBlock(SAPLING, tree.getWood());
        var seed = TFCItems.getWoodItem(SEED, tree.getWood());

        var leavesProperties = tree.getLeavesProperties();

        remDropCreator(new ResourceLocation(ModConstants.MODID, "logs"));
        addDropCreator(new DropCreatorWoodLog(tree)); // need our own because stacksize
        setSeedStack(new ItemStack(seed));
        setupStandardSeedDropping();
        setLeavesProperties(leavesProperties);
        leavesProperties.setTree(tree);
        LeavesPaging.getNextLeavesBlock(MOD_ID, leavesProperties);

        float[] map = tree.getParamMap();
        setGrowthLogicKit(tree.getGrowthLogicKit());
        setBasicGrowingParameters(map[0], map[1], (int) map[2], (int) map[3], map[4]);
        Species.REGISTRY.register(this);
        TreeRegistry.registerSaplingReplacer(sapling.getDefaultState(), this);
    }


    @Override
    public BlockRooty getRootyBlock(World world, BlockPos rootPos) {
        return ROOTY_DIRT_MIMIC;
    }

    //TFC style.
    @Override
    public boolean canBoneMeal() {
        return false;
    }
}
