package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockContainer;
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

public class BlockCellarShelf extends BaseBlockContainer {

  public BlockCellarShelf() {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .registryKey("cellar/shelf")
      .tile(TileCellarShelf.class)
      .renderType(EnumBlockRenderType.MODEL)
      .hardness(2F)
      .nonOpaque()
    );
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing playerFacing, float hitX, float hitY, float hitZ) {
    if (!worldIn.isRemote) {
      GuiHandler.openGui(worldIn, pos, player);
    }
    return true;
  }

  @Override
  public TileEntity createNewTileEntity(World world, int i) {
    return new TileCellarShelf();
  }
}
