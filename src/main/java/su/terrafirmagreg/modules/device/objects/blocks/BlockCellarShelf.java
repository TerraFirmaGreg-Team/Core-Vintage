package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BlockBaseContainer;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.objects.tiles.TECellarShelf;

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


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockCellarShelf extends BlockBaseContainer implements ITEBlock {

    public BlockCellarShelf() {
        super(Material.WOOD);
        setHardness(2F);

    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing playerFacing,
                                    float hitX, float hitY, float hitZ) {

        if (!worldIn.isRemote) {
            GuiHandler.openGui(worldIn, pos, player, GuiHandler.Type.CELLAR_SHELF);
        }

        return true;

    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity entity = worldIn.getTileEntity(pos);

            if (entity instanceof TECellarShelf) {
                //((TECellarShelf)entity).setCustomName(stack.getDisplayName());
            }
        }

    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        var tile = TileUtils.getTile(worldIn, pos, TECellarShelf.class);
        if (tile != null) {
            tile.onBreakBlock(worldIn, pos, state);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TECellarShelf();
    }

    @Override
    public @NotNull EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    public @Nullable ItemBlockBase getItemBlock() {
        return new ItemBlockBase(this);
    }

    @Override
    public @NotNull String getName() {
        return "device/cellar/shelf";
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TECellarShelf.class;
    }
}
