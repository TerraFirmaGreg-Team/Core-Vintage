package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockHorizontal;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.object.tile.TileElectricForge;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.Properties.BoolProp.LIT;
import static su.terrafirmagreg.data.Properties.DirectionProp.HORIZONTAL;

public class BlockElectricForge extends BaseBlockHorizontal implements IProviderTile {

  public BlockElectricForge() {
    super(Settings.of(Material.IRON));

    getSettings()
      .registryKey("device/electric_forge")
      .sound(SoundType.METAL)
      .hardness(4.0F)
      .size(Size.LARGE)
      .weight(Weight.MEDIUM)
      .renderLayer(BlockRenderLayer.CUTOUT_MIPPED)
      .nonCanStack();
    setHarvestLevel(ToolClasses.PICKAXE, 0);
    setDefaultState(blockState.getBaseState()
                              .withProperty(LIT, false)
                              .withProperty(HORIZONTAL, EnumFacing.NORTH));
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState()
               .withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta % 4))
               .withProperty(LIT, meta >= 4);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(HORIZONTAL).getHorizontalIndex() + (state.getValue(LIT) ? 4 : 0);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL, LIT);
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                  EnumHand hand, EnumFacing side, float hitX,
                                  float hitY, float hitZ) {
    if (!player.isSneaking()) {
      if (!world.isRemote) {
        GuiHandler.openGui(world, pos, player);
      }
      return true;
    }
    return false;
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileElectricForge();
  }

  @Override
  public Class<TileElectricForge> getTileClass() {
    return TileElectricForge.class;
  }

}
