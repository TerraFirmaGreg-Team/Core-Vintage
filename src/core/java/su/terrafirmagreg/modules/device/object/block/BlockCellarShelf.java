package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.object.block.spi.BaseBlockContainer;
import su.terrafirmagreg.framework.network.spi.GuiHandler;
import su.terrafirmagreg.modules.device.object.tile.TileCellarShelf;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

public class BlockCellarShelf extends BaseBlockContainer {

  public BlockCellarShelf() {
    super(Settings.of(Material.WOOD));

    getSettings()
      .registryKey("cellar/shelf")
      .hardness(2F)
      .nonOpaque();
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing playerFacing, float hitX, float hitY, float hitZ) {
    if (!worldIn.isRemote) {
      GuiHandler.openGui(worldIn, pos, player);
    }
    return true;
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
  public Class<TileCellarShelf> getTileClass() {
    return TileCellarShelf.class;
  }
}
