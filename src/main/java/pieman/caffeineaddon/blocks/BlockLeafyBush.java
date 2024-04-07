package pieman.caffeineaddon.blocks;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockBerryBush;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import pieman.caffeineaddon.init.ModBlocks;
import pieman.caffeineaddon.init.ModItems;
import pieman.caffeineaddon.util.LeafyBush;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BlockLeafyBush extends BlockBerryBush {

    public static final PropertyInteger GROWTH = PropertyInteger.create("growth", 1, 4);

    private static final AxisAlignedBB SMALL_SIZE_AABB = new AxisAlignedBB(0D, 0.0D, 0, 1D, 0.25D, 1D);
    private static final AxisAlignedBB MEDIUM_SIZE_AABB = new AxisAlignedBB(0D, 0.0D, 0, 1D, 0.5D, 1D);
    private static final AxisAlignedBB AABB_3 = new AxisAlignedBB(0D, 0.0D, 0, 1D, 0.75D, 1D);

    public BlockLeafyBush(LeafyBush bush) {
        super(bush);
        setTranslationKey(bush.getName() + "_bush");
        setRegistryName(bush.getName() + "_bush");
        setCreativeTab(CreativeTabsTFC.CT_WOOD);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(bush.getName() + "_bush"));
        setDefaultState(blockState.getBaseState().withProperty(GROWTH, 1));
    }

    @SuppressWarnings("deprecation")
    @Override
    @NotNull
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(GROWTH, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(GROWTH).intValue();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        /*if (worldIn.getBlockState(pos).getValue(FRUITING))
        {
            if (!worldIn.isRemote)
            {
                Helpers.spawnItemStack(worldIn, pos, bush.getFoodDrop());
                worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(FRUITING, false));
                TETickCounter te = Helpers.getTile(worldIn, pos, TETickCounter.class);
                if (te != null)
                {
                    te.resetCounter();
                }
            }
            return true;
        }*/
        return false;
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (!world.isRemote) {
            TETickCounter te = Helpers.getTE(world, pos, TETickCounter.class);
            if (te != null) {
                float temp = ClimateTFC.getActualTemp(world, pos);
                float rainfall = ChunkDataTFC.getRainfall(world, pos);
                long hours = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_HOUR;
                int currGrowth = world.getBlockState(pos).getValue(GROWTH);
                if (hours > bush.getGrowthTime() && bush.isValidForGrowth(temp, rainfall) && currGrowth < 4) {
                    if (bush.isHarvestMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear())) {
                        world.setBlockState(pos, world.getBlockState(pos).withProperty(GROWTH, currGrowth + 1));
                    }
                    te.resetCounter();
                }
            }
        }
    }

    @Override
    @NotNull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

        return switch (source.getBlockState(pos).getValue(GROWTH)) {
            case 1 -> SMALL_SIZE_AABB;
            case 2 -> MEDIUM_SIZE_AABB;
            case 3 -> AABB_3;
            default -> FULL_BLOCK_AABB;
        };
    }

    @Override
    @NotNull
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, GROWTH);
    }

}
