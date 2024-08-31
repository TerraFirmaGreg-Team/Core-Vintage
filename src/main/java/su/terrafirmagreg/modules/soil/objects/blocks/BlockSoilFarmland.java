package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.modules.core.features.falling.FallingBlockManager;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import gregtech.api.items.toolitem.ToolClasses;

import lombok.Getter;

import java.util.Random;

@Getter
@SuppressWarnings("deprecation")
public class BlockSoilFarmland extends BlockFarmland implements ISoilBlock, IProviderBlockColor {

    public static final int[] TINT = new int[] {
            0xffffffff,
            0xffe7e7e7,
            0xffd7d7d7,
            0xffc7c7c7,
            0xffb7b7b7,
            0xffa7a7a7,
            0xff979797,
            0xff878787,
    };
    private static final AxisAlignedBB FLIPPED_AABB = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);

    protected final Settings settings;
    private final SoilBlockVariant variant;
    private final SoilType type;

    public BlockSoilFarmland(SoilBlockVariant variant, SoilType type) {

        this.variant = variant;
        this.type = type;
        this.useNeighborBrightness = true;
        this.settings = Settings.of(Material.GROUND)
                .ignoresProperties(MOISTURE)
                .sound(SoundType.GROUND)
                .hardness(2.0F)
                .harvestLevel(ToolClasses.SHOVEL, 0)
                .fallable(this, variant.getSpecification())
                .oreDict(variant)
                .oreDict(variant, type);

        setDefaultState(getBlockState().getBaseState()
                .withProperty(MOISTURE, 1)); // 1 is default so it doesn't instantly turn back to dirt
    }

    protected static void turnToDirt(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof ISoilBlock soilBlockVariant) {
            var soil = soilBlockVariant.getType();

            world.setBlockState(pos, BlocksSoil.DIRT.get(soil).getDefaultState());
            AxisAlignedBB axisalignedbb = FLIPPED_AABB.offset(pos);
            for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb)) {
                double d0 = Math.min(axisalignedbb.maxY - axisalignedbb.minY, axisalignedbb.maxY - entity.getEntityBoundingBox().minY);
                entity.setPositionAndUpdate(entity.posX, entity.posY + d0 + 0.001D, entity.posZ);
            }
        }
    }

    @Override
    public boolean isSideSolid(IBlockState baseState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return (side != EnumFacing.DOWN && side != EnumFacing.UP);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        int beachDistance = 2;

        //        if (plantable instanceof BlockPlantTFC) {
        //            switch (((BlockPlantTFC) plantable).getEnumType()) {
        //                case EnumType.CLAY -> {
        //                    return variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                            variant == SoilBlockVariants.DRY_GRASS || variant == SoilBlockVariants.CLAY || variant == SoilBlockVariants.CLAY_GRASS;
        //                }
        //                case EnumType.DESERT_CLAY -> {
        //                    return variant == SoilBlockVariants.CLAY || variant == SoilBlockVariants.CLAY_GRASS || Helpers.isSand(state);
        //                }
        //                case EnumType.DRY_CLAY -> {
        //                    return variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.DRY_GRASS ||
        //                            variant == SoilBlockVariants.CLAY || variant == SoilBlockVariants.CLAY_GRASS || Helpers.isSand(state);
        //                }
        //                case EnumType.DRY -> {
        //                    return variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.DRY_GRASS || Helpers.isSand(state);
        //                }
        //                case EnumType.FRESH_WATER -> {
        //                    return variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                            variant == SoilBlockVariants.DRY_GRASS || Helpers.isGravel(state);
        //                }
        //                case EnumType.SALT_WATER -> {
        //                    return variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                            variant == SoilBlockVariants.DRY_GRASS || Helpers.isSand(state) || Helpers.isGravel(state);
        //                }
        //                case EnumType.FRESH_BEACH -> {
        //                    boolean flag = false;
        //                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
        //                        for (int i = 1; i <= beachDistance; i++) {
        //                            if (Helpers.isFreshWaterOrIce(world.getBlockState(pos.offset(facing, i)))) {
        //                                flag = true;
        //                                break;
        //                            }
        //                        }
        //                    }
        //                    return (variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                            variant == SoilBlockVariants.DRY_GRASS || Helpers.isSand(state)) && flag;
        //                }
        //                case EnumType.SALT_BEACH -> {
        //                    boolean flag = false;
        //                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
        //                        for (int i = 1; i <= beachDistance; i++)
        //                            if (Helpers.isSaltWater(world.getBlockState(pos.offset(facing, i)))) {
        //                                flag = true;
        //                            }
        //                    }
        //                    return (variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                            variant == SoilBlockVariants.DRY_GRASS || Helpers.isSand(state)) && flag;
        //                }
        //            }
        //        } else if (plantable instanceof BlockCropGrowing) {
        //            IBlockState cropState = world.getBlockState(pos.up());
        //            if (cropState.get() instanceof BlockCropGrowing) {
        //                boolean isWild = cropState.getValue(BlockCropGrowing.WILD);
        //                if (isWild) {
        //                    if (variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                            variant == SoilBlockVariants.DRY_GRASS || variant == SoilBlockVariants.CLAY_GRASS) {
        //                        return true;
        //                    }
        //                }
        //                return variant == SoilBlockVariants.FARMLAND;
        //            }
        //        }
        //
        //        switch (plantable.getPlantType(world, pos.offset(direction))) {
        //            case EnumPlantType.Plains -> {
        //                return variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                        variant == SoilBlockVariants.FARMLAND || variant == SoilBlockVariants.DRY_GRASS ||
        //                        variant == SoilBlockVariants.CLAY || variant == SoilBlockVariants.CLAY_GRASS;
        //            }
        //            case EnumPlantType.Crop -> {
        //                return variant == SoilBlockVariants.FARMLAND;
        //            }
        //            case EnumPlantType.Desert -> {
        //                return Helpers.isSand(state);
        //            }
        //            case EnumPlantType.Cave -> {
        //                return true;
        //            }
        //            case EnumPlantType.Water, EnumPlantType.Nether -> {
        //                return false;
        //            }
        //            case EnumPlantType.Beach -> {
        //                boolean flag = false;
        //                for (EnumFacing facing : EnumFacing.HORIZONTALS) {
        //                    for (int i = 1; i <= beachDistance; i++)
        //                        if (Helpers.isWater(world.getBlockState(pos.offset(facing, i)))) {
        //                            flag = true;
        //                        }
        //                }
        //                return (variant == SoilBlockVariants.DIRT || variant == SoilBlockVariants.GRASS ||
        //                        variant == SoilBlockVariants.DRY_GRASS || Helpers.isSand(state)) && flag;
        //            }
        //        }

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

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemsSoil.PILE.get(getType());
    }

    @Override
    @SideOnly(Side.CLIENT)
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
    public IBlockColor getBlockColor() {
        return (s, w, p, i) -> BlockSoilFarmland.TINT[s.getValue(BlockSoilFarmland.MOISTURE)];
    }

}
