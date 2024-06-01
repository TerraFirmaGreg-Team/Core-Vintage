package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlockHorizontal;
import su.terrafirmagreg.api.spi.tile.provider.ITileProvider;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.objects.tiles.TileElectricForge;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import gregtech.api.items.toolitem.ToolClasses;


import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;


import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.data.Blockstates.LIT;

public class BlockElectricForge extends BaseBlockHorizontal implements ITileProvider {

    public BlockElectricForge() {
        super(Settings.of(Material.IRON));

        getSettings()
                .registryKey("device/electric_forge")
                .soundType(SoundType.METAL)
                .hardness(4.0F)
                .size(Size.LARGE)
                .weight(Weight.MEDIUM)
                .renderLayer(BlockRenderLayer.CUTOUT_MIPPED)
                .nonCanStack();
        setHarvestLevel(ToolClasses.PICKAXE, 0);
        setDefaultState(getBlockState().getBaseState()
                .withProperty(LIT, false)
                .withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.byHorizontalIndex(meta % 4))
                .withProperty(LIT, meta >= 4);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(LIT) ? 4 : 0);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!player.isSneaking()) {
            if (!world.isRemote) {
                GuiHandler.openGui(world, pos, player, GuiHandler.Type.ELECTRIC_FORGE);
            }
            return true;
        }
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, LIT);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileElectricForge();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileElectricForge.class;
    }

}
