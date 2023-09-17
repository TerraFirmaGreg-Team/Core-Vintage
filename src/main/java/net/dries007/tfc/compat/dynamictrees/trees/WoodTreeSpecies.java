package net.dries007.tfc.compat.dynamictrees.trees;

import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.dynamictrees.trees.Species;
import net.dries007.tfc.Tags;
import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.compat.dynamictrees.dropcreators.DropCreatorWoodLog;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariants.SAPLING;
import static net.dries007.tfc.api.types.wood.variant.item.WoodItemVariants.SEED;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.ROOTY_DIRT_MIMIC;

public class WoodTreeSpecies extends Species {

    public WoodTreeSpecies(ResourceLocation name, TreeType tree, LeavesProperties properties) {
        super(name, tree, properties);

        var sapling = TFCBlocks.getWoodBlock(SAPLING, tree.getWood());
        var seed = TFCItems.getWoodItem(SEED, tree.getWood());
        var map = tree.getParamMap();

        remDropCreator(new ResourceLocation(ModConstants.MODID, "logs"));
        addDropCreator(new DropCreatorWoodLog()); // need our own because stacksize
        setSeedStack(new ItemStack(seed));
        setupStandardSeedDropping();
        setGrowthLogicKit(tree.getGrowthLogicKit());
        setBasicGrowingParameters(map[0], map[1], (int) map[2], (int) map[3], map[4]);

        properties.setTree(tree);

        Species.REGISTRY.register(this);
        TreeRegistry.registerSaplingReplacer(sapling.getDefaultState(), this);
        LeavesPaging.getNextLeavesBlock(Tags.MOD_ID, properties);
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
