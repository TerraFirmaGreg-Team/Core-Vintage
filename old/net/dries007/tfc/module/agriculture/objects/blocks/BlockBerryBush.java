package net.dries007.tfc.module.agriculture.objects.blocks;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.agriculture.api.types.bush.IBushBlock;
import net.dries007.tfc.module.agriculture.api.types.bush.type.BushType;
import net.dries007.tfc.module.agriculture.api.util.IGrowingPlant;
import net.dries007.tfc.module.core.api.objects.block.itemblocks.ItemBlockBase;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.core.objects.tiles.TETickCounter;
import net.dries007.tfc.util.DamageSourcesTFC;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockBerryBush extends BlockBush implements IGrowingPlant, IBushBlock {
    public static final PropertyBool FRUITING = PropertyBool.create("fruiting");

    private static final AxisAlignedBB SMALL_SIZE_AABB = new AxisAlignedBB(0D, 0.0D, 0, 1D, 0.25D, 1D);
    private static final AxisAlignedBB MEDIUM_SIZE_AABB = new AxisAlignedBB(0D, 0.0D, 0, 1D, 0.5D, 1D);

    private final BushType type;

    public BlockBerryBush(BushType type) {
        this.type = type;

        setSoundType(SoundType.PLANT);
        setHardness(1.0F);
        setDefaultState(blockState.getBaseState().withProperty(FRUITING, false));

        Blocks.FIRE.setFireInfo(this, 30, 60);
    }

    @Nonnull
    public BushType getType() {
        return type;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockBase(this);
    }


    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FRUITING, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FRUITING) ? 1 : 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return type.getSize() == Size.LARGE;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return switch (type.getSize()) {
            case SMALL -> SMALL_SIZE_AABB;
            case MEDIUM -> MEDIUM_SIZE_AABB;
            default -> Block.FULL_BLOCK_AABB;
        };
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        if (type.getSize() == Size.LARGE && face == EnumFacing.UP)
            return BlockFaceShape.SOLID;
        else
            return BlockFaceShape.UNDEFINED;
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (!world.isRemote) {
            TETickCounter te = Helpers.getTE(world, pos, TETickCounter.class);
            if (te != null) {
                float temp = ClimateTFC.getActualTemp(world, pos);
                float rainfall = ChunkDataTFC.getRainfall(world, pos);
                long hours = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_HOUR;
                if (hours > (type.getGrowthTime() * ConfigTFC.General.FOOD.berryBushGrowthTimeModifier) && type.isValidForGrowth(temp, rainfall)) {
                    if (type.isHarvestMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear())) {
                        //Fruiting
                        world.setBlockState(pos, world.getBlockState(pos).withProperty(FRUITING, true));
                    }
                    te.resetCounter();
                }
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if (!canStay(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        TETickCounter tile = Helpers.getTE(worldIn, pos, TETickCounter.class);
        if (tile != null) {
            tile.resetCounter();
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, @Nonnull BlockPos pos) {
        if (super.canPlaceBlockAt(worldIn, pos)) {
            return canStay(worldIn, pos);
        }
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.getBlockState(pos).getValue(FRUITING)) {
            if (!worldIn.isRemote) {
                ItemHandlerHelper.giveItemToPlayer(playerIn, type.getFoodDrop());
                worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(FRUITING, false));
                TETickCounter te = Helpers.getTE(worldIn, pos, TETickCounter.class);
                if (te != null) {
                    te.resetCounter();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!(entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).isCreative())) {
            // Entity motion is reduced (like leaves).
            entityIn.motionX *= ConfigTFC.General.MISC.berryBushMovementModifier;
            if (entityIn.motionY < 0) {
                entityIn.motionY *= ConfigTFC.General.MISC.berryBushMovementModifier;
            }
            entityIn.motionZ *= ConfigTFC.General.MISC.berryBushMovementModifier;
            if (type.isSpiky() && entityIn instanceof EntityLivingBase) {
                entityIn.attackEntityFrom(DamageSourcesTFC.BERRYBUSH, 1.0F);
            }
        }
    }

    @Override
    @Nonnull
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FRUITING);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TETickCounter();
    }


    private boolean canStay(IBlockAccess world, BlockPos pos) {
        IBlockState below = world.getBlockState(pos.down());
        if (type.getSize() == Size.LARGE && below.getBlock() instanceof BlockBerryBush && ((BlockBerryBush) below.getBlock()).type == this.type) {
            return Helpers.isGrowableSoil(world.getBlockState(pos.down(2))); // Only stack once
        }
        return Helpers.isGrowableSoil(below);
    }

    @Override
    public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
        float temp = ClimateTFC.getActualTemp(world, pos);
        float rainfall = ChunkDataTFC.getRainfall(world, pos);
        boolean canGrow = type.isValidForGrowth(temp, rainfall);
        if (state.getValue(FRUITING)) {
            return GrowthStatus.FULLY_GROWN;
        } else if (canGrow && type.isHarvestMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear())) {
            return GrowthStatus.GROWING;
        }
        return canGrow ? GrowthStatus.CAN_GROW : GrowthStatus.NOT_GROWING;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {

        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(
                        getResourceLocation(),
                        this.getPropertyString(state.getProperties()));
            }
        });

        for (IBlockState state : this.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
                    this.getMetaFromState(state), new ModelResourceLocation(getResourceLocation(), "normal"));
        }
    }
}
