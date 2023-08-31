package net.dries007.tfc.compat.dynamictrees.trees;

import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariants.LOG;
import static net.dries007.tfc.api.types.wood.variant.item.WoodItemVariants.SEED;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.blockRootyDirt;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.leafMap;

public class WoodTreeFamily extends TreeFamily {

    public static final ArrayList<WoodTreeFamily> TREES = new ArrayList<>();
    private final WoodType type;
    public boolean hasConiferVariants = false;
    private boolean thick = false;

    public WoodTreeFamily(WoodType type) {
        super(TerraFirmaCraft.identifier(type.toString()));

        this.type = type;

        setCommonSpecies(new WoodTreeSpecies(this, leafMap.get(type)));
        setPrimitiveLog(TFCBlocks.getWoodBlock(LOG, type).getDefaultState());

        leafMap.get(type).setTree(this);
    }

    public WoodType getType() {
        return type;
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
        String branchName = "branch/" + getName().getPath();
        return isThick() ? new BlockWoodBranchThick(branchName) : new BlockWoodBranchBasic(branchName);
    }


    public static class WoodTreeSpecies extends Species {

        public static final Map<WoodType, Species> SPECIES = new HashMap<>();

        public WoodTreeSpecies(WoodTreeFamily treeFamily, LeavesProperties prop) {
            super(treeFamily.getName(), treeFamily, prop);

            remDropCreator(new ResourceLocation(ModConstants.MODID, "logs"));
            addDropCreator(new DropCreatorWoodLog(treeFamily)); // need our own because stacksize
            setLeavesProperties(prop);
            setSeedStack(new ItemStack(TFCItems.getWoodItem(SEED, treeFamily.getType())));
            setupStandardSeedDropping();
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
