package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlockContainer;
import su.terrafirmagreg.api.spi.tile.provider.ITileProvider;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.objects.tiles.TileAlloyCalculator;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.data.Blockstates.HORIZONTAL;

@SuppressWarnings("deprecation")
public class BlockAlloyCalculator extends BaseBlockContainer implements ITileProvider {

    private static final AxisAlignedBB BOUNDS_NS = new AxisAlignedBB(3 / 16d, 0, 5 / 16d, 13 / 16d, 4 / 16d, 11 / 16d);
    private static final AxisAlignedBB BOUNDS_WE = new AxisAlignedBB(5 / 16d, 0, 3 / 16d, 11 / 16d, 4 / 16d, 13 / 16d);

    public BlockAlloyCalculator() {
        super(Settings.of(Material.IRON));

        getSettings()
                .registryKey("device/alloy_calculator")
                .nonCube();

        setDefaultState(getBlockState().getBaseState()
                .withProperty(HORIZONTAL, EnumFacing.NORTH));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HORIZONTAL);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        GuiHandler.openGui(worldIn, pos, playerIn);
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(HORIZONTAL, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.byIndex(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(HORIZONTAL, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HORIZONTAL).getIndex();
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(HORIZONTAL, rot.rotate(state.getValue(HORIZONTAL)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(HORIZONTAL)));
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(HORIZONTAL).getAxis() == EnumFacing.Axis.Z ? BOUNDS_NS : BOUNDS_WE;
    }

    @Override
    public @Nullable TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileAlloyCalculator();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileAlloyCalculator.class;
    }

}
