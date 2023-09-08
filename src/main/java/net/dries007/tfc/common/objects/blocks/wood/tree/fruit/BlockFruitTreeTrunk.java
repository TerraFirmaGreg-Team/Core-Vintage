//package net.dries007.tfc.common.objects.blocks.tree.fruit;
//
//import net.dries007.tfc.api.types.wood.IWoodBlock;
//import net.dries007.tfc.api.types.wood.type.WoodType;
//import net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariant;
//import net.dries007.tfc.api.util.IGrowingPlant;
//import net.dries007.tfc.client.util.CustomStateMap;
//import net.dries007.tfc.common.objects.CreativeTabsTFC;
//import net.dries007.tfc.common.objects.blocks.TFCBlocks;
//import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
//import net.dries007.tfc.common.objects.tileentities.TETickCounter;
//import net.dries007.tfc.config.ConfigTFC;
//import net.dries007.tfc.util.Helpers;
//import net.dries007.tfc.util.calendar.ICalendar;
//import net.dries007.tfc.util.climate.ClimateTFC;
//import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
//import net.minecraft.block.Block;
//import net.minecraft.block.SoundType;
//import net.minecraft.block.material.Material;
//import net.minecraft.block.properties.PropertyBool;
//import net.minecraft.block.state.BlockFaceShape;
//import net.minecraft.block.state.BlockStateContainer;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.ItemBlock;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.RayTraceResult;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.World;
//import net.minecraftforge.client.model.ModelLoader;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import javax.annotation.ParametersAreNonnullByDefault;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Random;
//
//import static net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariants.*;
//
//@ParametersAreNonnullByDefault
//public class BlockFruitTreeTrunk extends Block implements IGrowingPlant, IWoodBlock {
//    /* Connection sides (used if there's a branch on facing) */
//    public static final PropertyBool NORTH = PropertyBool.create("north");
//    public static final PropertyBool SOUTH = PropertyBool.create("south");
//    public static final PropertyBool EAST = PropertyBool.create("east");
//    public static final PropertyBool WEST = PropertyBool.create("west");
//    private static final AxisAlignedBB TRUNK_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 1.0D, 0.6875D);
//    private static final AxisAlignedBB CONNECTION_N_AABB = new AxisAlignedBB(0.3125D, 0.375D, 0.0D, 0.0D, 0.625D, 0.3125D);
//    private static final AxisAlignedBB CONNECTION_S_AABB = new AxisAlignedBB(0.3125D, 0.375D, 0.6875D, 0.0D, 0.625D, 1.0D);
//    private static final AxisAlignedBB CONNECTION_W_AABB = new AxisAlignedBB(0.0D, 0.375D, 0.3125D, 0.3125D, 0.625D, 0.6875D);
//    private static final AxisAlignedBB CONNECTION_E_AABB = new AxisAlignedBB(0.6875D, 0.375D, 0.3125D, 1.0D, 0.625D, 0.6875D);
//    private final WoodBlockVariant variant;
//    private final WoodType type;
//
//    public BlockFruitTreeTrunk(WoodBlockVariant variant, WoodType type) {
//        super(Material.WOOD, Material.WOOD.getMaterialMapColor());
//
//        this.variant = variant;
//        this.type = type;
//
//        setRegistryName(getRegistryLocation());
//        setTranslationKey(getTranslationName());
//        setCreativeTab(CreativeTabsTFC.WOOD);
//
//        setHardness(2.0F);
//        setTickRandomly(true);
//        setHarvestLevel("axe", 0);
//        setSoundType(SoundType.WOOD);
//        setDefaultState(blockState.getBaseState()
//                .withProperty(NORTH, false)
//                .withProperty(SOUTH, false)
//                .withProperty(EAST, false)
//                .withProperty(WEST, false));
//
//        Blocks.FIRE.setFireInfo(this, 5, 20);
//    }
//
//    @Override
//    public WoodBlockVariant getBlockVariant() {
//        return variant;
//    }
//
//    @Override
//    public WoodType getTree() {
//        return type;
//    }
//
//    @Nullable
//    @Override
//    public ItemBlock getItemBlock() {
//        return new ItemBlockTFC(this);
//    }
//
//    @Override
//    public int getMetaFromState(IBlockState state) {
//        return 0;
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    @Nonnull
//    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos) {
//        for (EnumFacing face : EnumFacing.HORIZONTALS) {
//            if (worldIn.getBlockState(pos.offset(face)).getBlock() == TFCBlocks.getWoodBlock(FRUIT_BRANCH, type)) {
//                if (face == EnumFacing.NORTH) {
//                    state = state.withProperty(NORTH, true);
//                } else if (face == EnumFacing.SOUTH) {
//                    state = state.withProperty(SOUTH, true);
//                } else if (face == EnumFacing.EAST) {
//                    state = state.withProperty(EAST, true);
//                } else {
//                    state = state.withProperty(WEST, true);
//                }
//            }
//        }
//        return state;
//    }
//
//    @Override
//    @SuppressWarnings("deprecation")
//    public boolean isFullCube(IBlockState state) {
//        return false;
//    }
//
//    @Override
//    @SuppressWarnings("deprecation")
//    @Nonnull
//    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
//        state = getActualState(state, source, pos);
//        AxisAlignedBB finalAABB = TRUNK_AABB;
//        if (state.getValue(NORTH)) {
//            finalAABB = finalAABB.union(CONNECTION_N_AABB);
//        }
//        if (state.getValue(SOUTH)) {
//            finalAABB = finalAABB.union(CONNECTION_S_AABB);
//        }
//        if (state.getValue(WEST)) {
//            finalAABB = finalAABB.union(CONNECTION_W_AABB);
//        }
//        if (state.getValue(EAST)) {
//            finalAABB = finalAABB.union(CONNECTION_E_AABB);
//        }
//        return finalAABB;
//    }
//
//    @Override
//    @Nonnull
//    @SuppressWarnings("deprecation")
//    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
//        return BlockFaceShape.UNDEFINED;
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public void addCollisionBoxToList(IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB entityBox, @Nonnull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
//        addCollisionBoxToList(pos, entityBox, collidingBoxes, TRUNK_AABB);
//    }
//
//    @Override
//    @SuppressWarnings("deprecation")
//    public boolean isOpaqueCube(IBlockState state) {
//        return false;
//    }
//
//    @Override
//    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
//        super.updateTick(worldIn, pos, state, random);
//        if (!worldIn.isRemote) {
//            // Attempt to grow
//            float temp = ClimateTFC.getActualTemp(worldIn, pos);
//            float rainfall = ChunkDataTFC.getRainfall(worldIn, pos);
//            TETickCounter te = Helpers.getTE(worldIn, pos, TETickCounter.class);
//            if (te != null) {
//                long hours = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_HOUR;
//                if (hours > (type.getGrowthTime() * ConfigTFC.General.FOOD.fruitTreeGrowthTimeModifier) && type.isValidForGrowth(temp, rainfall)) {
//                    te.resetCounter();
//                    if (worldIn.getBlockState(pos.up()).getBlock() != this) //If the above block is a trunk, this one don't need to do anything
//                    {
//                        if (this.getTrunkHeight(worldIn, pos) < 4) {
//                            BlockPos missingLeaf = getMissingLeaf(worldIn, pos);
//                            if (missingLeaf != null) {
//                                //Missing leaf, spawn that first
//                                if (worldIn.getBlockState(missingLeaf).getMaterial().isReplaceable()) {
//                                    worldIn.setBlockState(missingLeaf, TFCBlocks.getWoodBlock(FRUIT_LEAVES, type).getDefaultState()
//                                            .withProperty(BlockFruitTreeLeaves.HARVESTABLE, false));
//                                }
//                            } else {
//                                //Time to grow one trunk
//                                worldIn.setBlockState(pos.up(), this.getDefaultState());
//                                //Set all remaining leaf blocks to air
//                                levelUpLeaves(worldIn, pos, false);
//                            }
//                        } else {
//                            //Starting with top
//                            if (worldIn.getBlockState(pos.up()).getBlock() != TFCBlocks.getWoodBlock(FRUIT_BRANCH, type)) {
//                                BlockPos missingLeaf = getMissingLeaf(worldIn, pos);
//                                if (missingLeaf != null) {
//                                    //There is a missing leaf
//                                    if (worldIn.getBlockState(missingLeaf).getMaterial().isReplaceable()) {
//                                        worldIn.setBlockState(missingLeaf, TFCBlocks.getWoodBlock(FRUIT_LEAVES, type).getDefaultState()
//                                                .withProperty(BlockFruitTreeLeaves.HARVESTABLE, true));
//                                    }
//                                    return; //Done what we needed to do on this stage
//                                } else {
//                                    worldIn.setBlockState(pos.up(), TFCBlocks.getWoodBlock(FRUIT_BRANCH, type).getDefaultState());
//                                    levelUpLeaves(worldIn, pos, true);
//                                    return; //Done what we needed to do on this stage
//                                }
//                            } else {
//                                BlockPos missingLeaf = getMissingLeaf(worldIn, pos.up());
//                                if (missingLeaf != null) {
//                                    //There is a missing leaf
//                                    if (worldIn.getBlockState(missingLeaf).getMaterial().isReplaceable()) {
//                                        worldIn.setBlockState(missingLeaf, TFCBlocks.getWoodBlock(FRUIT_LEAVES, type).getDefaultState()
//                                                .withProperty(BlockFruitTreeLeaves.HARVESTABLE, true));
//                                    }
//                                    return; //Done what we needed to do on this stage
//                                }
//                                //is there a missing leaf in diagonals?
//                                missingLeaf = getMissingLeaf(worldIn, pos.up(), EnumFacing.NORTH);
//                                if (missingLeaf != null) {
//                                    if (worldIn.getBlockState(missingLeaf).getMaterial().isReplaceable()) {
//                                        worldIn.setBlockState(missingLeaf, TFCBlocks.getWoodBlock(FRUIT_LEAVES, type).getDefaultState()
//                                                .withProperty(BlockFruitTreeLeaves.HARVESTABLE, true));
//                                    }
//                                    return; //Done what we needed to do on this stage
//                                }
//                                missingLeaf = getMissingLeaf(worldIn, pos.up(), EnumFacing.SOUTH);
//                                if (missingLeaf != null) {
//                                    if (worldIn.getBlockState(missingLeaf).getMaterial().isReplaceable()) {
//                                        worldIn.setBlockState(missingLeaf, TFCBlocks.getWoodBlock(FRUIT_LEAVES, type).getDefaultState()
//                                                .withProperty(BlockFruitTreeLeaves.HARVESTABLE, true));
//                                    }
//                                    return; //Done what we needed to do on this stage
//                                }
//                            }
//                            List<EnumFacing> shuffledList = Arrays.asList(EnumFacing.HORIZONTALS.clone()); //if we don't clone, the original array shuffes!
//                            Collections.shuffle(shuffledList, new Random(pos.toLong()));
//                            for (EnumFacing branchFacing : shuffledList) {
//                                BlockPos branchPos = pos.offset(branchFacing);
//                                if (worldIn.getBlockState(branchPos).getBlock() != TFCBlocks.getWoodBlock(FRUIT_BRANCH, type)) {
//                                    if (worldIn.getBlockState(branchPos).getMaterial().isReplaceable()) {
//                                        worldIn.setBlockState(branchPos, TFCBlocks.getWoodBlock(FRUIT_BRANCH, type).getDefaultState());
//                                    }
//                                    return; //Done what we needed to do on this stage
//                                }
//                                BlockPos missingLeaf = getMissingLeaf(worldIn, branchPos, branchFacing);
//                                if (missingLeaf != null) {
//                                    if (worldIn.getBlockState(missingLeaf).getMaterial().isReplaceable()) {
//                                        worldIn.setBlockState(missingLeaf, TFCBlocks.getWoodBlock(FRUIT_LEAVES, type).getDefaultState()
//                                                .withProperty(BlockFruitTreeLeaves.HARVESTABLE, true));
//                                        return; //Done what we needed to do on this stage
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    @SuppressWarnings("deprecation")
//    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
//        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
//        if (!(worldIn.getBlockState(pos.down()).getBlock() instanceof BlockFruitTreeTrunk) && !TFCBlocks.isGrowableSoil(worldIn.getBlockState(pos.down()))) {
//            worldIn.destroyBlock(pos, true);
//        }
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
//    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
//        super.breakBlock(worldIn, pos, state);
//        if (!worldIn.isRemote) {
//            //Destroy this tree upwards starting from this block
//            //Not needed to destroy leaves, they will decay by itself
//            Block branch = TFCBlocks.getWoodBlock(FRUIT_BRANCH, type);
//            if (worldIn.getBlockState(pos.up()).getBlock() == this || worldIn.getBlockState(pos.up()).getBlock() == branch) {
//                worldIn.destroyBlock(pos.up(), true);
//            }
//            if (worldIn.getBlockState(pos.north()).getBlock() == branch) {
//                worldIn.destroyBlock(pos.up(), true);
//            }
//            if (worldIn.getBlockState(pos.south()).getBlock() == branch) {
//                worldIn.destroyBlock(pos.up(), true);
//            }
//            if (worldIn.getBlockState(pos.west()).getBlock() == branch) {
//                worldIn.destroyBlock(pos.up(), true);
//            }
//            if (worldIn.getBlockState(pos.east()).getBlock() == branch) {
//                worldIn.destroyBlock(pos.up(), true);
//            }
//        }
//    }
//
//    @Override
//    @Nonnull
//    public BlockStateContainer createBlockState() {
//        return new BlockStateContainer(this, NORTH, SOUTH, EAST, WEST);
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
//    @Override
//    @Nonnull
//    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
//        return new ItemStack(TFCBlocks.getWoodBlock(FRUIT_SAPLING, type));
//    }
//
//    private BlockPos getMissingLeaf(World world, BlockPos branchPos, EnumFacing branchFacing) {
//        //Helper method, gets a missing leaf block position that connects to this branch
//
//        //the same facing has priority
//        if (world.getBlockState(branchPos.offset(branchFacing)).getBlock() != TFCBlocks.getWoodBlock(FRUIT_LEAVES, type)) {
//            return branchPos.offset(branchFacing);
//        }
//
//        //The rest is shuffled
//        List<BlockPos> positions = Arrays.asList(branchPos.offset(branchFacing.rotateY()),
//                branchPos.offset(branchFacing.rotateY().getOpposite()),
//                branchPos.offset(branchFacing).offset(branchFacing.rotateY()),
//                branchPos.offset(branchFacing).offset(branchFacing.rotateY().getOpposite())
//        );
//        Collections.shuffle(positions);
//        for (BlockPos pos : positions) {
//            if (world.getBlockState(pos).getBlock() != TFCBlocks.getWoodBlock(FRUIT_LEAVES, type)) {
//                return pos;
//            }
//        }
//        return null; //All leaves are in position
//    }
//
//    @Nullable
//    private BlockPos getMissingLeaf(World world, BlockPos pos) {
//        //Helper method, checks if all 5 leafs blocks(horizontals + above) has a missing block leaf
//
//        //Above first
//        if (world.getBlockState(pos.offset(EnumFacing.UP)).getBlock() != TFCBlocks.getWoodBlock(FRUIT_LEAVES, type)) {
//            return pos.offset(EnumFacing.UP);
//        }
//        List<EnumFacing> shuffledList = Arrays.asList(EnumFacing.HORIZONTALS.clone()); //if i don't clone, the original array shuffes!
//        Collections.shuffle(shuffledList);
//        for (EnumFacing facing : shuffledList) {
//            if (world.getBlockState(pos.offset(facing)).getBlock() != TFCBlocks.getWoodBlock(FRUIT_LEAVES, type)) {
//                return pos.offset(facing);
//            }
//        }
//        return null;
//    }
//
//    private void levelUpLeaves(World world, BlockPos oldCenter, boolean hasvestable) {
//        //Remove the old ones
//        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
//            if (world.getBlockState(oldCenter.offset(facing)).getBlock() == TFCBlocks.getWoodBlock(FRUIT_LEAVES, type)) {
//                world.setBlockToAir(oldCenter.offset(facing));
//            }
//        }
//        BlockPos missingLeaf;
//        //noinspection ConstantConditions
//        do {
//            //Respawn leafs in the new height
//            missingLeaf = getMissingLeaf(world, oldCenter.up());
//            if (missingLeaf != null && world.getBlockState(missingLeaf).getMaterial().isReplaceable()) {
//                world.setBlockState(missingLeaf, TFCBlocks.getWoodBlock(FRUIT_LEAVES, type).getDefaultState()
//                        .withProperty(BlockFruitTreeLeaves.HARVESTABLE, hasvestable));
//            } else {
//                //Found a block(ceiling?) that prevents this tree to grow leafs, aborting
//                break;
//            }
//        } while (missingLeaf != null);
//    }
//
//    private int getTrunkHeight(World world, BlockPos pos) {
//        for (int i = 1; i < 4; i++) {
//            if (world.getBlockState(pos.down(i)).getBlock() != this) return i;
//        }
//        return 4;
//    }
//
//    @Override
//    public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
//        float temp = ClimateTFC.getActualTemp(world, pos);
//        float rainfall = ChunkDataTFC.getRainfall(world, pos);
//        boolean canGrow = type.isValidForGrowth(temp, rainfall);
//        if (canGrow) {
//            return GrowthStatus.GROWING;
//        }
//        return GrowthStatus.NOT_GROWING;
//    }
//
//    @Override
//    public void onModelRegister() {
//        ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customPath(getRegistryLocation()).build());
//    }
//}
