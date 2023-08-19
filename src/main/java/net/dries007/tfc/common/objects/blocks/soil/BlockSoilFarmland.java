package net.dries007.tfc.common.objects.blocks.soil;

import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.soil.ISoilBlock;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariant;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariants;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.blocks.BlocksTFC_old;
import net.dries007.tfc.common.objects.blocks.crop.BlockCropGrowing;
import net.dries007.tfc.common.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.dries007.tfc.api.types.soil.variant.SoilBlockVariants.*;
import static net.dries007.tfc.api.types.soil.variant.SoilBlockVariants.DRY_GRASS;
import static net.dries007.tfc.common.objects.blocks.crop.BlockCropGrowing.WILD;

public class BlockSoilFarmland extends Block implements ISoilBlock {
    public static final int MAX_MOISTURE = 15;
    public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, MAX_MOISTURE);
    public static final int[] TINT = new int[]{
            0xffffffff,
            0xfff7f7f7,
            0xffefefef,
            0xffe7e7e7,
            0xffdfdfdf,
            0xffd7d7d7,
            0xffcfcfcf,
            0xffc7c7c7,
            0xffbfbfbf,
            0xffb7b7b7,
            0xffafafaf,
            0xffa7a7a7,
            0xff9f9f9f,
            0xff979797,
            0xff8f8f8f,
            0xff878787,
    };
    private static final AxisAlignedBB FARMLAND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
    private static final AxisAlignedBB FLIPPED_AABB = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);

    private final SoilBlockVariant variant;
    private final SoilType type;

    public BlockSoilFarmland(SoilBlockVariant variant, SoilType type) {
        super(Material.GROUND);

        if (variant.canFall())
            FallingBlockManager.registerFallable(this, variant.getFallingSpecification());

        this.variant = variant;
        this.type = type;

        this.useNeighborBrightness = true;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.EARTH);
        setSoundType(SoundType.GROUND);
        setHardness(2.0F);
        setHarvestLevel("shovel", 0);
        setDefaultState(blockState.getBaseState()
                .withProperty(MOISTURE, 1)); // 1 is default so it doesn't instantly turn back to dirt
        setTickRandomly(true);
        setLightOpacity(255);

        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    protected static void turnToDirt(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof ISoilBlock) {
            var soil = ((ISoilBlock) block).getType();

            world.setBlockState(pos, TFCStorage.getSoilBlock(SoilBlockVariants.DIRT, soil).getDefaultState());
            AxisAlignedBB axisalignedbb = FLIPPED_AABB.offset(pos);
            for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb)) {
                double d0 = Math.min(axisalignedbb.maxY - axisalignedbb.minY, axisalignedbb.maxY - entity.getEntityBoundingBox().minY);
                entity.setPositionAndUpdate(entity.posX, entity.posY + d0 + 0.001D, entity.posZ);
            }
        }
    }

    @Nonnull
    @Override
    public SoilBlockVariant getBlockVariant() {
        return variant;
    }

    @Nonnull
    @Override
    public SoilType getType() {
        return type;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(MOISTURE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(MOISTURE);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(@Nonnull IBlockState state) {
        return false;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return FARMLAND_AABB;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(@Nonnull IBlockAccess worldIn, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EnumFacing face) {
        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(@Nonnull IBlockState state) {
        return false;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, @Nonnull Random rand) {
        int current = state.getValue(MOISTURE);
        int target = world.isRainingAt(pos.up()) ? MAX_MOISTURE : getWaterScore(world, pos);

        if (current < target) {
            if (current < MAX_MOISTURE) world.setBlockState(pos, state.withProperty(MOISTURE, current + 1), 2);
        } else if (current > target || target == 0) {
            if (current > 0) world.setBlockState(pos, state.withProperty(MOISTURE, current - 1), 2);
            else if (!hasCrops(world, pos)) turnToDirt(world, pos);
        }

        super.updateTick(world, pos, state, rand);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isSideSolid(@Nonnull IBlockState baseState, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        return (side != EnumFacing.DOWN && side != EnumFacing.UP);
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, @Nonnull RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player) {
        return new ItemStack(this);
    }


    @Override
    public boolean canSustainPlant(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing direction, @Nonnull IPlantable plantable) {
        int beachDistance = 2;

        if (plantable instanceof BlockPlantTFC) {
            switch (((BlockPlantTFC) plantable).getEnumType()) {
                case CLAY -> {
                    return variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || variant == CLAY || variant == CLAY_GRASS;
                }
                case DESERT_CLAY -> {
                    return variant == CLAY || variant == CLAY_GRASS || BlocksTFC_old.isSand(state);
                }
                case DRY_CLAY -> {
                    return variant == DIRT || variant == DRY_GRASS ||
                            variant == CLAY || variant == CLAY_GRASS || BlocksTFC_old.isSand(state);
                }
                case DRY -> {
                    return variant == DIRT || variant == DRY_GRASS || BlocksTFC_old.isSand(state);
                }
                case FRESH_WATER -> {
                    return variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || BlocksTFC_old.isGravel(state);
                }
                case SALT_WATER -> {
                    return variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || BlocksTFC_old.isSand(state) || BlocksTFC_old.isGravel(state);
                }
                case FRESH_BEACH -> {
                    boolean flag = false;
                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                        for (int i = 1; i <= beachDistance; i++) {
                            if (BlocksTFC_old.isFreshWaterOrIce(world.getBlockState(pos.offset(facing, i)))) {
                                flag = true;
                                break;
                            }
                        }
                    }
                    return (variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || BlocksTFC_old.isSand(state)) && flag;
                }
                case SALT_BEACH -> {
                    boolean flag = false;
                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                        for (int i = 1; i <= beachDistance; i++)
                            if (BlocksTFC_old.isSaltWater(world.getBlockState(pos.offset(facing, i)))) {
                                flag = true;
                            }
                    }
                    return (variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || BlocksTFC_old.isSand(state)) && flag;
                }
            }
        }
        else if (plantable instanceof BlockCropGrowing) {
            IBlockState cropState = world.getBlockState(pos.up());
            if (cropState.getBlock() instanceof BlockCropGrowing) {
                boolean isWild = cropState.getValue(WILD);
                if (isWild) {
                    if (variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || variant == CLAY_GRASS) {
                        return true;
                    }
                }
                return variant == FARMLAND;
            }
        }

        switch (plantable.getPlantType(world, pos.offset(direction))) {
            case Plains -> {
                return variant == DIRT || variant == GRASS ||
                        variant == FARMLAND || variant == DRY_GRASS ||
                        variant == CLAY || variant == CLAY_GRASS;
            }
            case Crop -> {
                return variant == FARMLAND;
            }
            case Desert -> {
                return BlocksTFC_old.isSand(state);
            }
            case Cave -> {
                return true;
            }
            case Water, Nether -> {
                return false;
            }
            case Beach -> {
                boolean flag = false;
                for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                    for (int i = 1; i <= beachDistance; i++)
                        if (BlocksTFC_old.isWater(world.getBlockState(pos.offset(facing, i)))) {
                            flag = true;
                        }
                }
                return (variant == DIRT || variant == GRASS ||
                        variant == DRY_GRASS || BlocksTFC_old.isSand(state)) && flag;
            }
        }

        return false;
    }

    public int getWaterScore(IBlockAccess world, BlockPos pos) {
        final int hRange = 7;
        float score = 0;
        for (BlockPos.MutableBlockPos i : BlockPos.getAllInBoxMutable(pos.add(-hRange, -1, -hRange), pos.add(hRange, 2, hRange))) {
            BlockPos diff = i.subtract(pos);
            float hDist = MathHelper.sqrt(diff.getX() * diff.getX() + diff.getZ() * diff.getZ());
            if (hDist > hRange) continue;
            if (world.getBlockState(i).getMaterial() != Material.WATER) continue;
            score += ((hRange - hDist) / (float) hRange);
        }
        return score > 1 ? MAX_MOISTURE : Math.round(score * MAX_MOISTURE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World world, BlockPos pos, @Nonnull Block block, BlockPos fromPos) {
        if (fromPos.getY() == pos.getY() + 1) {
            IBlockState up = world.getBlockState(fromPos);
            if (up.isSideSolid(world, fromPos, EnumFacing.DOWN) && FallingBlockManager.getSpecification(up) == null) {
                turnToDirt(world, pos);
            }
        }
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return Item.getItemFromBlock(TFCStorage.getSoilBlock(SoilBlockVariants.DIRT, type));
    }

    private boolean hasCrops(World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return block instanceof IPlantable && canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, EnumFacing.UP, (IPlantable) block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(@Nonnull IBlockState blockState, @Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos, EnumFacing side) {
        switch (side) {
            case UP:
                return true;
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
                Block block = iblockstate.getBlock();
                if (iblockstate.isOpaqueCube()) return false;
                if (block instanceof BlockFarmland || block instanceof BlockGrassPath) return false;
            default:
                return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
        }
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, MOISTURE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(getResourceLocation(), "soiltype=" + type.toString());
            }

        });


        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                this.getMetaFromState(this.getBlockState().getBaseState()),
                new ModelResourceLocation(getResourceLocation(), "soiltype=" + type.toString()));
    }

}
