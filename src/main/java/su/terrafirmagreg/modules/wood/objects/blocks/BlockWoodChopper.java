package su.terrafirmagreg.modules.wood.objects.blocks;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import se.gory_moon.horsepower.blocks.BlockFiller;
import se.gory_moon.horsepower.client.model.modelvariants.ChopperModels;
import se.gory_moon.horsepower.lib.Constants;
import se.gory_moon.horsepower.tileentity.TileEntityChopper;
import se.gory_moon.horsepower.tileentity.TileEntityHPBase;
import se.gory_moon.horsepower.util.Localization;
import se.gory_moon.horsepower.util.color.Colors;

import java.util.Arrays;
import java.util.List;

public class BlockWoodChopper extends BlockWoodChoppingBase {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", Arrays.asList(EnumFacing.HORIZONTALS));
    public static final PropertyEnum<ChopperModels> PART = PropertyEnum.create("part", ChopperModels.class);
    private static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D);

    public BlockWoodChopper() {
        super();
        setHardness(5.0F);
        setResistance(5.0F);
        setRegistryName(Constants.CHOPPER_BLOCK);
        setTranslationKey(Constants.CHOPPER_BLOCK);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.byIndex(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return getDefaultState().withProperty(FACING, enumfacing).withProperty(PART, ChopperModels.BASE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public boolean hasCustomBreakingProgress(IBlockState state) {
        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return COLLISION_AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return COLLISION_AABB;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            EnumFacing enumfacing = state.getValue(FACING);
            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing)
                    .withProperty(PART, ChopperModels.BASE), 2);
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{PART, FACING}, new IUnlistedProperty[]{SIDE_TEXTURE, TOP_TEXTURE});
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add(Localization.ITEM.HORSE_CHOPPING.SIZE.translate(Colors.WHITE.toString(), Colors.LIGHTGRAY.toString()));
        tooltip.add(Localization.ITEM.HORSE_CHOPPING.LOCATION.translate());
        tooltip.add(Localization.ITEM.HORSE_CHOPPING.USE.translate());
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        if (!((World) world).isRemote && pos.up().equals(neighbor) && !(world.getBlockState(neighbor)
                .getBlock() instanceof BlockFiller)) {
            ((World) world).setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite())
                .withProperty(PART, ChopperModels.BASE);
    }

    public void onBlockPlacedBy(@NotNull World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        TileEntityHPBase tile = getTileEntity(worldIn, pos);
        if (tile == null)
            return;
        tile.setForward(placer.getHorizontalFacing().getOpposite());
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void emptiedOutput(World world, BlockPos pos) {
    }

    @NotNull
    @Override
    public Class<?> getTileClass() {
        return TileEntityChopper.class;
    }
}
