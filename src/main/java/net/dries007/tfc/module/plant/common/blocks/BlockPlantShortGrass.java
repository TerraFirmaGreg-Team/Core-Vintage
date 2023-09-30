package net.dries007.tfc.module.plant.common.blocks;

import net.dries007.tfc.module.core.init.ItemsCore;
import net.dries007.tfc.module.plant.api.types.type.PlantType;
import net.dries007.tfc.module.plant.api.types.variant.block.PlantEnumVariant;
import net.dries007.tfc.util.Constants;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class BlockPlantShortGrass extends BlockPlant implements IShearable {
    private static final AxisAlignedBB GRASS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
    private static final AxisAlignedBB SHORTER_GRASS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.5D, 0.875D);
    private static final AxisAlignedBB SHORT_GRASS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.75D, 0.875D);
    private static final AxisAlignedBB SHORTEST_GRASS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.25D, 0.875D);

    private final PlantType plant;

    public BlockPlantShortGrass(PlantEnumVariant plantBlockVariant, PlantType plant) {
        super(plantBlockVariant, plant);

        this.plant = plant;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS) {
            spawnAsEntity(worldIn, pos, new ItemStack(this, 1));
        } else if (!worldIn.isRemote && stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1 || stack.getItem().getHarvestLevel(stack, "scythe", player, state) != -1) {
            if (Constants.RNG.nextDouble() <= (state.getValue(AGE) + 1) / 4.0D) //+25% change for each age
            {
                spawnAsEntity(worldIn, pos, new ItemStack(ItemsCore.STRAW, 1));
            }
        }
        super.harvestBlock(worldIn, player, pos, state, te, stack);
    }

    @Override
    @Nonnull
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XZ;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isAreaLoaded(pos, 1)) return;

        if (plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) && plant.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
            int j = state.getValue(AGE);

            if (rand.nextDouble() < getGrowthRate(worldIn, pos) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
                if (j < 3) {
                    worldIn.setBlockState(pos, state.withProperty(AGE, j + 1));
                }
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
            }
        } else if (!plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) || !plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
            int j = state.getValue(AGE);

            if (rand.nextDouble() < getGrowthRate(worldIn, pos) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
                if (j > 0) {
                    worldIn.setBlockState(pos, state.withProperty(AGE, j - 1));
                } else {
                    worldIn.setBlockToAir(pos);
                }
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
            }
        }

        checkAndDropBlock(worldIn, pos, state);
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return switch (state.getValue(AGE)) {
            case 0 -> SHORTEST_GRASS_AABB.offset(state.getOffset(source, pos));
            case 1 -> SHORTER_GRASS_AABB.offset(state.getOffset(source, pos));
            case 2 -> SHORT_GRASS_AABB.offset(state.getOffset(source, pos));
            default -> GRASS_AABB.offset(state.getOffset(source, pos));
        };
    }

    @Override
    @Nonnull
    protected BlockStateContainer createPlantBlockState() {
        return new BlockStateContainer(this, AGE, growthStageProperty, DAYPERIOD);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return 1 + random.nextInt(fortune * 2 + 1);
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(this, 1);
    }

    @Override
    @Nonnull
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1);
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    @Nonnull
    public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this, 1));
    }
}
