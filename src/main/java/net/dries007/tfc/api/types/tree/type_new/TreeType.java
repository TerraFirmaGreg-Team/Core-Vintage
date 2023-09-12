package net.dries007.tfc.api.types.tree.type_new;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.cells.ICellKit;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKits;
import com.ferreusveritas.dynamictrees.growthlogic.IGrowthLogicKit;
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

import javax.annotation.Nonnull;
import java.util.ArrayList;

import static com.ferreusveritas.dynamictrees.ModConstants.MODID;
import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariants.LEAVES;
import static net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariants.LOG;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.BLOCKS;
import static net.dries007.tfc.common.objects.items.TFCItems.ITEMS;

public class TreeType extends TreeFamily {

    public static final ArrayList<TreeType> TREES = new ArrayList<>();
    private final WoodType wood;
    private final float[] paramMap;
    private final IGrowthLogicKit logicMap;
    private final ResourceLocation name;

    private final LeavesProperties leavesProperties;
    public boolean hasConiferVariants = false;
    private boolean thick = false;

    private TreeType(Builder builder) {
        super(builder.name);

        this.name = builder.name;
        this.wood = builder.wood;
        this.paramMap = builder.paramMap;
        this.logicMap = builder.logicMap;
        this.leavesProperties = new LeavesProperties(builder.primitiveLeaves, builder.cellKit);

        setCommonSpecies(new WoodTreeSpecies(this));
        setPrimitiveLog(builder.primitiveLog);
        setDynamicBranch(isThick() ? new BlockTreeBranchThick(wood) : new BlockTreeBranch(wood));

        TREES.add(this);

        this.getRegisterableBlocks(BLOCKS);
        this.getRegisterableItems(ITEMS);
        BLOCKS.addAll(LeavesPaging.getLeavesMapForModId(MOD_ID).values());

    }

    @Override
    public void createSpecies() {
        //setCommonSpecies(new WoodTreeSpecies(this));
        //setCommonSpecies(speciesCreator != null ? speciesCreator.create(this) : new Species(name, this, dynamicLeavesProperties));
    }

//    @Override
//    public BlockBranch createBranch() {
//        return isThick() ? new BlockTreeBranchThick(wood) : new BlockTreeBranch(wood);
//    }

    @Nonnull
    public WoodType getWood() {
        return wood;
    }

    public float[] getParamMap() {
        return paramMap;
    }

    public IGrowthLogicKit getGrowthLogicKit() {
        return logicMap;
    }

    public LeavesProperties getLeavesProperties() {
        return leavesProperties;
    }


    @Override
    public boolean isThick() {
        return thick;
    }

    public void setThick(boolean thick) {
        this.thick = thick;
    }

    public static class Builder {

        //Common Species
        private final boolean speciesCreateSeed = true;
        private final ResourceLocation name;
        //Leaves
        private final IGrowthLogicKit logicMap;
        private WoodType wood;
        private float minTemp, maxTemp;
        private float minRain, maxRain;
        private float[] paramMap;
        //Drops
        private IBlockState primitiveLeaves;
        private IBlockState primitiveLog;
        private ItemStack stickItemStack;
        private ICellKit cellKit;

        public Builder(@Nonnull String name) {
            this.name = TerraFirmaCraft.identifier(name);
            this.logicMap = GrowthLogicKits.nullLogic;
        }

        public Builder setWoodType(WoodType wood) {
            this.wood = wood;
            setPrimitiveLeaves(TFCBlocks.getWoodBlock(LEAVES, wood).getDefaultState());
            setPrimitiveLog(TFCBlocks.getWoodBlock(LOG, wood).getDefaultState());
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
            this.paramMap = new float[]{tapering, energy, upProbability, lowestBranchHeight, growthRate};
            return this;
        }

        public Builder setPrimitiveLeaves(IBlockState primLeaves) {
            this.primitiveLeaves = primLeaves;
            return this;
        }

        public Builder setPrimitiveLog(IBlockState primLog) {
            this.primitiveLog = primLog;
            return this;
        }

        public Builder setStick(ItemStack stick) {
            this.stickItemStack = stick;
            return this;
        }

        public Builder setCellKit(ResourceLocation kit) {
            cellKit = TreeRegistry.findCellKit(kit);
            return this;
        }

        public Builder setCellKit(String modid, String kit) {
            setCellKit(new ResourceLocation(modid, kit));
            return this;
        }

        public Builder setCellKit(String kit) {
            setCellKit(new ResourceLocation(MODID, kit));
            return this;
        }


        // Метод для построения объекта WoodType
        public TreeType build() {
            return new TreeType(this);
        }
    }
}
