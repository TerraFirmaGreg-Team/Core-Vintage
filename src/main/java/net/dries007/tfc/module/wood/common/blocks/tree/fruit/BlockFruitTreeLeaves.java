//package net.dries007.tfc.common.objects.blocks.tree.fruit;
//
//import mcp.MethodsReturnNonnullByDefault;
//import net.dries007.tfc.module.core.submodule.wood.api.type.WoodType;
//import net.dries007.tfc.module.core.submodule.wood.api.variant.block.WoodBlockVariant;
//import net.dries007.tfc.module.core.submodule.wood.api.variant.block.WoodBlockVariants;
//import net.dries007.tfc.api.util.IGrowingPlant;
//import net.dries007.tfc.common.objects.blocks.TFCBlocks;
//import net.dries007.tfc.module.core.submodule.wood.common.blocks.BlockWoodLeaves;
//import net.dries007.tfc.module.core.common.tiles.TETickCounter;
//import net.dries007.tfc.config.ConfigTFC;
//import net.dries007.tfc.util.Helpers;
//import net.dries007.tfc.util.calendar.CalendarTFC;
//import net.dries007.tfc.util.calendar.ICalendar;
//import net.minecraft.block.Block;
//import net.minecraft.block.properties.PropertyBool;
//import net.minecraft.block.properties.PropertyEnum;
//import net.minecraft.block.state.BlockStateContainer;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.renderer.block.statemap.StateMap;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.IStringSerializable;
//import net.minecraft.util.NonNullList;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.World;
//import net.minecraftforge.client.model.ModelLoader;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//import net.minecraftforge.items.ItemHandlerHelper;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import javax.annotation.ParametersAreNonnullByDefault;
//import java.util.*;
//
//@MethodsReturnNonnullByDefault
//@ParametersAreNonnullByDefault
//public class BlockFruitTreeLeaves extends BlockWoodLeaves implements IGrowingPlant {
//    public static final PropertyEnum<EnumLeafState> LEAF_STATE = PropertyEnum.create("state", BlockFruitTreeLeaves.EnumLeafState.class);
//    public static final PropertyBool HARVESTABLE = PropertyBool.create("harvestable");
//    private final WoodBlockVariant variant;
//    private final WoodType type;
//
//    public BlockFruitTreeLeaves(WoodBlockVariant variant, WoodType type) {
//        super(variant, type);
//        this.variant = variant;
//        this.type = type;
//
//        setDefaultState(blockState.getBaseState()
//                .withProperty(DECAYABLE, false)
//                .withProperty(LEAF_STATE, EnumLeafState.NORMAL)
//                .withProperty(HARVESTABLE, false));
//    }
//
//    @Override
//    @Nonnull
//    public IBlockState getStateFromMeta(int meta) {
//        return getDefaultState()
//                .withProperty(HARVESTABLE, meta > 3)
//                .withProperty(LEAF_STATE, EnumLeafState.valueOf(meta & 0b11));
//    }
//
//    @Override
//    public int getMetaFromState(IBlockState state) {
//        return state.getValue(LEAF_STATE).ordinal() + (state.getValue(HARVESTABLE) ? 4 : 0);
//    }
//
//    @Override
//    public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
//        if (!world.isRemote) {
//            if (state.getValue(HARVESTABLE) && type.isHarvestMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear())) {
//                TETickCounter te = Helpers.getTE(world, pos, TETickCounter.class);
//                if (te != null) {
//                    long hours = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_HOUR;
//                    if (hours > (type.getGrowthTime() * ConfigTFC.General.FOOD.fruitTreeGrowthTimeModifier)) {
//                        world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.FRUIT));
//                        te.resetCounter();
//                    }
//                }
//            } else if (type.isFlowerMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear())) {
//                if (state.getValue(LEAF_STATE) != EnumLeafState.FLOWERING) {
//                    world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.FLOWERING));
//                }
//            } else {
//                if (state.getValue(LEAF_STATE) != EnumLeafState.NORMAL) {
//                    world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.NORMAL));
//                }
//            }
//            doLeafDecay(world, pos, state);
//        }
//    }
//
//    @Override
//    @SuppressWarnings("deprecation")
//    public void neighborChanged(IBlockState state, World world, BlockPos pos, @Nullable Block blockIn, @Nullable BlockPos fromPos) {
//        doLeafDecay(world, pos, state);
//    }
//
//    @Override
//    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
//        TETickCounter tile = Helpers.getTE(worldIn, pos, TETickCounter.class);
//        if (tile != null) {
//            tile.resetCounter();
//        }
//    }
//
//    @Override
//    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//        if (worldIn.getBlockState(pos).getValue(LEAF_STATE) == EnumLeafState.FRUIT) {
//            if (!worldIn.isRemote) {
//                ItemHandlerHelper.giveItemToPlayer(playerIn, type.getFoodDrop());
//                worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(LEAF_STATE, EnumLeafState.NORMAL));
//                TETickCounter te = Helpers.getTE(worldIn, pos, TETickCounter.class);
//                if (te != null) {
//                    te.resetCounter();
//                }
//            }
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    @Nonnull
//    public BlockStateContainer createBlockState() {
//        return new BlockStateContainer(this, DECAYABLE, LEAF_STATE, HARVESTABLE);
//    }
//
//    @Override
//    public boolean hasTileEntity(IBlockState state) {
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public TileEntity createTileEntity(World world, IBlockState state) {
//        return new TETickCounter();
//    }
//
//
//    @Override
//    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
//        // Stops dropping oak saplings inherited from BlockLeaves
//        drops.clear();
//    }
//
//    private void doLeafDecay(World world, BlockPos pos, IBlockState state) {
//        // TFC Leaf Decay, modified for fruit trees
//        if (world.isRemote)
//            return;
//
//
//        Set<BlockPos> paths = new HashSet<>();
//        Set<BlockPos> evaluated = new HashSet<>(); // Leaves that everything was evaluated so no need to do it again
//        List<BlockPos> pathsToAdd; // New Leaves that needs evaluation
//        BlockPos.MutableBlockPos pos1 = new BlockPos.MutableBlockPos(pos);
//        IBlockState state1;
//        paths.add(pos); // Center block
//
//        for (int i = 0; i < type.getMaxDecayDistance(); i++) {
//            pathsToAdd = new ArrayList<>();
//            for (BlockPos p1 : paths) {
//                for (EnumFacing face : EnumFacing.values()) {
//                    pos1.setPos(p1).move(face);
//                    if (evaluated.contains(pos1) || !world.isBlockLoaded(pos1))
//                        continue;
//                    state1 = world.getBlockState(pos1);
//                    if (state1.getBlock() == TFCBlocks.getWoodBlock(WoodBlockVariants.FRUIT_TRUNK, type)
//                            || state1.getBlock() == TFCBlocks.getWoodBlock(WoodBlockVariants.FRUIT_BRANCH, type))
//                        return;
//                    if (state1.getBlock() == this)
//                        pathsToAdd.add(pos1.toImmutable());
//                }
//                evaluated.add(p1); // Evaluated
//            }
//            paths.addAll(pathsToAdd);
//            paths.removeAll(evaluated);
//        }
//
//        world.setBlockToAir(pos);
//    }
//
//    @Override
//    public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
//        if (world.getBlockState(pos).getValue(LEAF_STATE) == EnumLeafState.FRUIT) {
//            return GrowthStatus.FULLY_GROWN;
//        } else if (!state.getValue(HARVESTABLE) && type.isHarvestMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear())) {
//            return GrowthStatus.GROWING;
//        }
//        return GrowthStatus.NOT_GROWING;
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void onModelRegister() {
//        ModelLoader.setCustomStateMapper(this, new StateMap.Builder()
//                .ignore(BlockFruitTreeLeaves.DECAYABLE)
//                .ignore(BlockFruitTreeLeaves.HARVESTABLE).build());
//    }
//
//    /**
//     * Enum state for blockstate
//     * Used to render the correct texture of this leaf block
//     */
//    public enum EnumLeafState implements IStringSerializable {
//        NORMAL, FLOWERING, FRUIT;
//
//        private static final EnumLeafState[] VALUES = values();
//
//        @Nonnull
//        public static EnumLeafState valueOf(int index) {
//            return index < 0 || index > VALUES.length ? NORMAL : VALUES[index];
//        }
//
//        @Override
//        public String getName() {
//            return this.name().toLowerCase();
//        }
//    }
//}
