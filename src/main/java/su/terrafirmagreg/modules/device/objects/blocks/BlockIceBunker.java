package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BlockBaseContainer;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.objects.tiles.TEIceBunker;

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


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockIceBunker extends BlockBaseContainer implements ITEBlock {

    public BlockIceBunker() {
        super(Material.WOOD);

        setHardness(2F);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing playerFacing,
                                    float hitX, float hitY, float hitZ) {

        if (!worldIn.isRemote) {
            GuiHandler.openGui(worldIn, pos, player, GuiHandler.Type.ICE_BUNKER);
        }

        return true;

    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TEIceBunker tile = (TEIceBunker) worldIn.getTileEntity(pos);
        InventoryHelper.dropInventoryItems(worldIn, pos, tile);
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity entity = worldIn.getTileEntity(pos);

            if (entity instanceof TEIceBunker) {
                //((TECellarShelf)entity).setCustomName(stack.getDisplayName());
            }
        }

    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TEIceBunker();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public @NotNull String getName() {
        return "device/ice_bunker";
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TEIceBunker.class;
    }
}
