package net.dries007.tfc.module.wood.plugin.dynamictrees.trees;

import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.api.IGenFeature;
import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenConiferTopper;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import net.dries007.tfc.Tags;
import net.dries007.tfc.module.wood.StorageWood;
import net.dries007.tfc.module.wood.api.types.variant.block.WoodBlockVariants;
import net.dries007.tfc.module.wood.init.BlocksWood;
import net.dries007.tfc.module.wood.plugin.dynamictrees.dropcreators.DropCreatorWoodLog;
import net.dries007.tfc.module.wood.plugin.dynamictrees.items.ItemWoodSeed;
import net.dries007.tfc.module.wood.tree.type.TreeType;
import net.dries007.tfc.module.wood.tree.type.TreeTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WoodTreeSpecies extends Species {

    private final TreeType tree;

    public WoodTreeSpecies(ResourceLocation name, TreeType tree, LeavesProperties properties) {
        super(name, tree, properties);
        this.tree = tree;

        var sapling = StorageWood.getWoodBlock(WoodBlockVariants.SAPLING, tree.getWood());
        var map = tree.getParamMap();

        remDropCreator(new ResourceLocation(ModConstants.MODID, "logs"));
        addDropCreator(new DropCreatorWoodLog()); // need our own because stacksize
        setSeedStack(new ItemStack(new ItemWoodSeed(tree)));
        setupStandardSeedDropping();
        setGrowthLogicKit(tree.getGrowthLogicKit());
        setBasicGrowingParameters(map[0], map[1], (int) map[2], (int) map[3], map[4]);
        setSoilLongevity(tree.getSoilLongevity());

        if (tree.getModule() != null)
            for (IGenFeature module : tree.getModule()) addGenFeature(module);

        if (tree.isConifer()) {
            new FeatureGenConiferTopper(properties);
            tree.hasConiferVariants = tree.isConifer();
        }

        if (tree == TreeTypes.ACACIA_TREE) addAcceptableSoils(DirtHelper.HARDCLAYLIKE); // match base DT


        properties.setTree(tree);

        Species.REGISTRY.register(this);
        //TreeRegistry.registerSaplingReplacer(sapling.getDefaultState(), this);
        LeavesPaging.getNextLeavesBlock(Tags.MOD_ID, properties);
    }

    @Override
    public float getEnergy(World world, BlockPos pos) {
        long day = world.getWorldTime() / 24000L;
        int month = (int) day / 30; // Change the hashs every in-game month

        return super.getEnergy(world, pos) * biomeSuitability(world, pos) + (CoordUtils.coordHashCode(pos.up(month), 3) % 5); // Vary the height energy by a psuedorandom hash function
    }


    @Override
    public BlockRooty getRootyBlock(World world, BlockPos rootPos) {
        return BlocksWood.ROOTY_DIRT_MIMIC;
    }

    @Override
    public boolean isThick() {
        return tree.isThick();
    }

    //TFC style.
    @Override
    public boolean canBoneMeal() {
        return false;
    }
}
