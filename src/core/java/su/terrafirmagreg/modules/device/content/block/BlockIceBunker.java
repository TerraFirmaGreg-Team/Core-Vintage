package su.terrafirmagreg.modules.device.content.block;

import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockContainer;
import su.terrafirmagreg.modules.device.content.tile.TileIceBunker;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

public class BlockIceBunker extends BaseBlockContainer {

  public BlockIceBunker() {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .tile(TileIceBunker.class)
      .renderType(EnumBlockRenderType.MODEL)
      .hardness(2F)
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
  public @Nullable TileIceBunker createNewTileEntity(World world, int i) {
    return new TileIceBunker();
  }

}
