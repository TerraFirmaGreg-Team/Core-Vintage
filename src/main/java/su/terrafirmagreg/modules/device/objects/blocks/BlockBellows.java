package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.client.render.TESRBellows;
import su.terrafirmagreg.modules.device.objects.tiles.TEBellows;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.block.BlockHorizontal.FACING;

public class BlockBellows extends BlockBase implements ITEBlock {

    public BlockBellows() {
        super(Material.CIRCUITS, MapColor.GRAY);
        setSoundType(SoundType.WOOD);
        setHardness(2.0F);
        setResistance(2.0F);
        setHarvestLevel("axe", 0);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    @SuppressWarnings("deprecation")
    @NotNull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    @NotNull
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == state.getValue(FACING) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer playerIn,
                                    @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        TEBellows te = TileUtils.getTile(worldIn, pos, TEBellows.class);
        if (te != null) {
            return te.onRightClick();
        }
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    @NotNull
    public IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
                                            int meta, @NotNull EntityLivingBase placer) {
        if (facing.getAxis() == EnumFacing.Axis.Y) {
            if (placer.isSneaking()) {
                facing = placer.getHorizontalFacing().getOpposite();
            } else {
                facing = placer.getHorizontalFacing();
            }
        }
        return getDefaultState().withProperty(FACING, facing);
    }

    @Override
    @NotNull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@NotNull World world, @NotNull IBlockState state) {
        return new TEBellows();
    }

    @Override
    public @NotNull String getName() {
        return "device/bellows";
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TEBellows.class;
    }

    @Override
    public TileEntitySpecialRenderer<?> getTileRenderer() {
        return new TESRBellows();
    }
}
