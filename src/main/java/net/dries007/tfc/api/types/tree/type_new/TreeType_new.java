package net.dries007.tfc.api.types.tree.type_new;

import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.tree.WoodTreeSpecies;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.compat.dynamictrees.blocks.BlockTreeBranch;
import net.dries007.tfc.compat.dynamictrees.blocks.BlockTreeBranchThick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariants.LOG;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.BLOCKS;
import static net.dries007.tfc.common.objects.items.TFCItems.ITEMS;

public class TreeType_new extends TreeFamily {

    public static final ArrayList<TreeType_new> TREES = new ArrayList<>();
    public boolean hasConiferVariants = false;
    private boolean thick = false;

    private TreeType_new(Builder builder) {
        super(builder.name);

        setCommonSpecies(new WoodTreeSpecies(this));
        setPrimitiveLog(TFCBlocks.getWoodBlock(LOG, tree.getWood()).getDefaultState());
        setDynamicBranch(isThick() ? new BlockTreeBranchThick(tree.getWood()) : new BlockTreeBranch(tree.getWood()));

        TREES.add(this);

        this.getRegisterableBlocks(BLOCKS);
        this.getRegisterableItems(ITEMS);
        BLOCKS.addAll(LeavesPaging.getLeavesMapForModId(MOD_ID).values());

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


    public static class Builder {

        //Common Species
        private final boolean speciesCreateSeed = true;
        private ResourceLocation name;
        private WoodType wood;
        private float minTemp, maxTemp;
        private float minRain, maxRain;
        private float[] paramMap;
        //Drops
        private IBlockState primitiveLeavesBlockState;
        private IBlockState primitiveLogBlockState;
        private ItemStack stickItemStack;
        //Leaves
        private ILeavesProperties dynamicLeavesProperties;
        private ResourceLocation dynamicLeavesCellKit;

        public Builder setName(ResourceLocation name) {
            this.name = name;
            return this;
        }

        public Builder setName(String path) {
            return setName(TerraFirmaCraft.identifier(path));
        }

        public Builder setWoodType(WoodType wood) {
            this.wood = wood;
            return this;
        }

        public Builder setTemp(float minTemp, float maxTemp) {
            this.minTemp = minTemp;
            this.maxTemp = maxTemp;
            return this;
        }

        public Builder setRain(float minRain, float maxRain) {
            this.minRain = minRain;
            this.maxRain = maxRain;
            return this;
        }

        public Builder setParamMap(float tapering, float energy, int upProbability, int lowestBranchHeight, float growthRate) {
            this.paramMap = new float[]{};
            return this;
        }

        public Builder setPrimitiveLeaves(IBlockState primLeaves) {
            primitiveLeavesBlockState = primLeaves;
            return this;
        }

        public Builder setPrimitiveLog(IBlockState primLog) {
            primitiveLogBlockState = primLog;
            return this;
        }

        public Builder setStick(ItemStack stick) {
            this.stickItemStack = stick;
            return this;
        }


        // Метод для построения объекта WoodType
        public TreeType_new build() {
            return new TreeType_new(this);
        }
    }
}
