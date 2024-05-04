package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;
import su.terrafirmagreg.api.spi.block.BaseBlockContainer;
import su.terrafirmagreg.api.spi.tile.ITileBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.objects.tiles.TileIceBunker;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class BlockIceBunker extends BaseBlockContainer implements ITileBlock {

    public BlockIceBunker() {
        super(Settings.of()
                .material(Material.WOOD)
                .hardness(2F));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing playerFacing, float hitX, float hitY,
                                    float hitZ) {
        if (!worldIn.isRemote) {
            GuiHandler.openGui(worldIn, pos, player, GuiHandler.Type.ICE_BUNKER);
        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

        var te = TileUtils.getTile(worldIn, pos, TileIceBunker.class);
        if (te != null) {
            InventoryHelper.dropInventoryItems(worldIn, pos, te);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            var te = TileUtils.getTile(worldIn, pos, TileIceBunker.class);
            //te.setCustomName(stack.getDisplayName());
        }

    }

    @Override
    public @Nullable TileEntity createNewTileEntity(World world, int i) {
        return new TileIceBunker();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public String getName() {
        return "device/ice_bunker";
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileIceBunker.class;
    }
}
