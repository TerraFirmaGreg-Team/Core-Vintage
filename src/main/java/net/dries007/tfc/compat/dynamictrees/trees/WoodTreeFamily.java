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
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.blocks.wood.BlockWoodBranchBasic;
import net.dries007.tfc.common.objects.blocks.wood.BlockWoodBranchThick;
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
import static net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariants.*;
import static net.dries007.tfc.api.types.wood.variant.item.WoodItemVariants.SEED;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.blockRootyDirt;

public class WoodTreeFamily extends TreeFamily {

    public static final ArrayList<WoodTreeFamily> TREES = new ArrayList<>();
    private final WoodType type;
    public boolean hasConiferVariants = false;
    private boolean thick = false;

    public WoodTreeFamily(WoodType type) {
        super(TerraFirmaCraft.identifier(type.toString()));

        this.type = type;

        setCommonSpecies(new WoodTreeSpecies(this, type));
        setPrimitiveLog(TFCBlocks.getWoodBlock(LOG, type).getDefaultState());

        //setDynamicBranch(createBranch());

        TREES.add(this);
    }

    public WoodType getType() {
        return type;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public int getWoodColor() {
        return type.getColor();
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
        var stack = new ItemStack(TFCBlocks.getWoodBlock(LOG, type), qty);
        stack.setCount(MathHelper.clamp(qty, 0, 64));
        return stack;
    }


    @Override
    public BlockBranch createBranch() {
        var branchName = String.format("wood/branch/%s", getName().getPath());
        return isThick() ? new BlockWoodBranchThick(branchName) : new BlockWoodBranchBasic(branchName);
    }


    public static class WoodTreeSpecies extends Species {

        public static final Map<WoodType, Species> SPECIES = new HashMap<>();

        public WoodTreeSpecies(WoodTreeFamily treeFamily, WoodType type) {
            super(treeFamily.getName(), treeFamily);

            var leaves = TFCBlocks.getWoodBlock(LEAVES, type);
            var sapling = TFCBlocks.getWoodBlock(SAPLING, type);
            var seed = TFCItems.getWoodItem(SEED, type);

            var leavesProperties = new LeavesProperties(leaves.getDefaultState(), type.getCellKit());

            remDropCreator(new ResourceLocation(ModConstants.MODID, "logs"));
            addDropCreator(new DropCreatorWoodLog(treeFamily)); // need our own because stacksize
            setSeedStack(new ItemStack(seed));
            setupStandardSeedDropping();
            setLeavesProperties(leavesProperties);
            leavesProperties.setTree(treeFamily);
            LeavesPaging.getNextLeavesBlock(MOD_ID, leavesProperties);

            float[] map = type.getParamMap();
            setGrowthLogicKit(type.getGrowthLogicKit()).
                    setBasicGrowingParameters(map[0], map[1], (int) map[2], (int) map[3], map[4]);

            SPECIES.put(type, this);
            Species.REGISTRY.register(this);
            TreeRegistry.registerSaplingReplacer(sapling.getDefaultState(), this);
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
