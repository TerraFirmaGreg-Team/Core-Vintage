package net.dries007.tfc.common.objects.blocks.wood.fruit;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariant;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.blocks.wood.BlockWoodSapling;
import net.dries007.tfc.common.objects.tileentities.TETickCounter;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static net.dries007.tfc.api.types.wood.variant.WoodBlockVariants.FRUIT_LEAVES;
import static net.dries007.tfc.api.types.wood.variant.WoodBlockVariants.FRUIT_TRUNK;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockFruitTreeSapling extends BlockWoodSapling {
    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockFruitTreeSapling(WoodBlockVariant variant, WoodType type) {
        super(variant, type);
        this.variant = variant;
        this.type = type;

        setTickRandomly(true);
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);

        if (!world.isRemote) {
            TETickCounter te = Helpers.getTE(world, pos, TETickCounter.class);
            if (te != null) {
                float temp = ClimateTFC.getActualTemp(world, pos);
                float rainfall = ChunkDataTFC.getRainfall(world, pos);
                long hours = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_HOUR;
                if (hours > (type.getGrowthTime() * ConfigTFC.General.FOOD.fruitTreeGrowthTimeModifier) && type.isValidForGrowth(temp, rainfall)) {
                    te.resetCounter();
                    grow(world, random, pos, state);
                }
            }
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        TETickCounter te = Helpers.getTE(worldIn, pos, TETickCounter.class);
        if (te != null) {
            te.resetCounter();
        }
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
        return true; //Only on sapling tho, so trunk still has to grow
    }

    @Override
    public void grow(World world, Random random, BlockPos blockPos, IBlockState blockState) {
        if (!world.isRemote) {
            world.setBlockState(blockPos, TFCBlocks.getWoodBlock(FRUIT_TRUNK, type).getDefaultState());
            if (world.getBlockState(blockPos.up()).getMaterial().isReplaceable()) {
                world.setBlockState(blockPos.up(),
                        TFCBlocks.getWoodBlock(FRUIT_LEAVES, type).getDefaultState()
                                .withProperty(BlockFruitTreeLeaves.HARVESTABLE, false));
            }
        }
    }

//    @SideOnly(Side.CLIENT) //TODO
//    @Override
//    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//        super.addInformation(stack, worldIn, tooltip, flagIn);
//        type.addInfo(stack, worldIn, tooltip, flagIn);
//    }

    @Override
    public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
        float temp = ClimateTFC.getActualTemp(world, pos);
        float rainfall = ChunkDataTFC.getRainfall(world, pos);
        boolean canGrow = type.isValidForGrowth(temp, rainfall);
        return canGrow ? GrowthStatus.GROWING : GrowthStatus.NOT_GROWING;
    }
}
