package net.dries007.tfc.module.soil.common.blocks;

import net.dries007.tfc.module.soil.api.variant.block.ISoilBlock;
import net.dries007.tfc.module.soil.api.variant.item.SoilItemVariants;
import net.dries007.tfc.module.soil.common.SoilStorage;
import net.dries007.tfc.module.soil.api.type.SoilType;
import net.dries007.tfc.module.soil.api.variant.block.SoilBlockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.module.crop.common.blocks.BlockCropGrowing;
import net.dries007.tfc.common.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.block.SoundType;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static net.dries007.tfc.module.crop.common.blocks.BlockCropGrowing.WILD;
import static net.dries007.tfc.module.soil.api.variant.block.SoilBlockVariants.*;

@ParametersAreNonnullByDefault
public class BlockSoilFarmland extends BlockFarmland implements ISoilBlock {
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
    private static final AxisAlignedBB FLIPPED_AABB = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);

    private final SoilBlockVariant variant;
    private final SoilType type;

    public BlockSoilFarmland(SoilBlockVariant variant, SoilType type) {

        if (variant.canFall())
            FallingBlockManager.registerFallable(this, variant.getFallingSpecification());

        this.variant = variant;
        this.type = type;

        this.useNeighborBrightness = true;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.SOIL);
        setSoundType(SoundType.GROUND);
        setHardness(2.0F);
        setHarvestLevel("shovel", 0);
        setDefaultState(blockState.getBaseState().withProperty(MOISTURE, 1)); // 1 is default so it doesn't instantly turn back to dirt

        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }


    protected static void turnToDirt(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof ISoilBlock) {
            var soil = ((ISoilBlock) block).getType();

            world.setBlockState(pos, SoilStorage.getSoilBlock(DIRT, soil).getDefaultState());
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


    @Override
    @SuppressWarnings("deprecation")
    public boolean isSideSolid(IBlockState baseState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return (side != EnumFacing.DOWN && side != EnumFacing.UP);
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this);
    }


    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        int beachDistance = 2;

        if (plantable instanceof BlockPlantTFC) {
            switch (((BlockPlantTFC) plantable).getEnumType()) {
                case CLAY -> {
                    return variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || variant == CLAY || variant == CLAY_GRASS;
                }
                case DESERT_CLAY -> {
                    return variant == CLAY || variant == CLAY_GRASS || TFCBlocks.isSand(state);
                }
                case DRY_CLAY -> {
                    return variant == DIRT || variant == DRY_GRASS ||
                            variant == CLAY || variant == CLAY_GRASS || TFCBlocks.isSand(state);
                }
                case DRY -> {
                    return variant == DIRT || variant == DRY_GRASS || TFCBlocks.isSand(state);
                }
                case FRESH_WATER -> {
                    return variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || TFCBlocks.isGravel(state);
                }
                case SALT_WATER -> {
                    return variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || TFCBlocks.isSand(state) || TFCBlocks.isGravel(state);
                }
                case FRESH_BEACH -> {
                    boolean flag = false;
                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                        for (int i = 1; i <= beachDistance; i++) {
                            if (TFCBlocks.isFreshWaterOrIce(world.getBlockState(pos.offset(facing, i)))) {
                                flag = true;
                                break;
                            }
                        }
                    }
                    return (variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || TFCBlocks.isSand(state)) && flag;
                }
                case SALT_BEACH -> {
                    boolean flag = false;
                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                        for (int i = 1; i <= beachDistance; i++)
                            if (TFCBlocks.isSaltWater(world.getBlockState(pos.offset(facing, i)))) {
                                flag = true;
                            }
                    }
                    return (variant == DIRT || variant == GRASS ||
                            variant == DRY_GRASS || TFCBlocks.isSand(state)) && flag;
                }
            }
        } else if (plantable instanceof BlockCropGrowing) {
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
                return TFCBlocks.isSand(state);
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
                        if (TFCBlocks.isWater(world.getBlockState(pos.offset(facing, i)))) {
                            flag = true;
                        }
                }
                return (variant == DIRT || variant == GRASS ||
                        variant == DRY_GRASS || TFCBlocks.isSand(state)) && flag;
            }
        }

        return false;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (fromPos.getY() == pos.getY() + 1) {
            IBlockState up = world.getBlockState(fromPos);
            if (up.isSideSolid(world, fromPos, EnumFacing.DOWN) && FallingBlockManager.getSpecification(up) == null) {
                turnToDirt(world, pos);
            }
        }
    }

    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return SoilStorage.getSoilItem(SoilItemVariants.PILE, type);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
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

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(getResourceLocation(), "soiltype=" + type.toString());
            }

        });


        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                this.getMetaFromState(this.getBlockState().getBaseState()),
                new ModelResourceLocation(getResourceLocation(), "soiltype=" + type.toString()));
    }

}
