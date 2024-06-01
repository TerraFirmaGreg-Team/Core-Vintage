package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlockContainer;
import su.terrafirmagreg.api.spi.tile.provider.ITileProvider;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.objects.tiles.TileCellarShelf;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class BlockCellarShelf extends BaseBlockContainer implements ITileProvider {

    public BlockCellarShelf() {
        super(Settings.of(Material.WOOD));

        getSettings()
                .registryKey("device/cellar/shelf")
                .hardness(2F)
                .nonOpaque();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing playerFacing, float hitX, float hitY,
                                    float hitZ) {
        if (!worldIn.isRemote) {
            GuiHandler.openGui(worldIn, pos, player);
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            var tile = TileUtils.getTile(world, pos, TileCellarShelf.class);
            //tile.setCustomName(stack.getDisplayName());
        }

    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        var tile = TileUtils.getTile(world, pos, TileCellarShelf.class);
        if (tile != null) {
            tile.onBreakBlock(world, pos, state);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public @Nullable TileEntity createNewTileEntity(World world, int i) {
        return new TileCellarShelf();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileCellarShelf.class;
    }
}
