package su.terrafirmagreg.modules.core.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockHorizontal;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.objects.blocks.BlockTorchTFC;
import net.dries007.tfc.objects.te.TETickCounter;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.data.Properties.BoolProp.LIT;
import static su.terrafirmagreg.data.Properties.DirectionProp.HORIZONTAL;

@Getter
public class BlockJackOLantern extends BaseBlockHorizontal implements IProviderTile {

  private final Carving carving;

  public BlockJackOLantern(Carving carving) {
    super(Settings.of(Material.GOURD));

    this.carving = carving;

    getSettings()
      .registryKey("core/jack_o_lantern/" + carving)
      .hardness(1f)
      .lightValue(1)
      .randomTicks()
      .size(Size.LARGE)
      .weight(Weight.HEAVY);

    setDefaultState(blockState.getBaseState()
                              .withProperty(HORIZONTAL, EnumFacing.NORTH)
                              .withProperty(LIT, Boolean.FALSE));

  }

  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta)).withProperty(LIT, meta >= 4);
  }

  public int getMetaFromState(IBlockState state) {
    return (state.getValue(LIT) ? 4 : 0) + state.getValue(HORIZONTAL).getHorizontalIndex();
  }

  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL, LIT);
  }

  @Override
  public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    return state.getValue(LIT) ? super.getLightValue(state, world, pos) : 0;
  }

  @Override
  public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
    if (world.isRemote) {
      return;
    }
    //taken from BlockTorchTFC
    var tile = TileUtils.getTile(world, pos, TETickCounter.class);
    tile.ifPresent(tileTickCounter -> {
      //last twice as long as a torch. balance this by being less bright
      if (tileTickCounter.getTicksSinceUpdate() > 2L * ConfigTFC.General.OVERRIDES.torchTime && ConfigTFC.General.OVERRIDES.torchTime > 0) {
        world.setBlockState(pos, state.withProperty(LIT, false));
        tileTickCounter.resetCounter();
      }
    });
  }

  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) && worldIn.isSideSolid(
      pos.down(), EnumFacing.UP);
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    //taken from BlockTorchTFC
    if (!worldIn.isRemote) {
      ItemStack stack = playerIn.getHeldItem(hand);
      if (BlockTorchTFC.canLight(stack)) {
        worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(LIT, true));
        var tile = TileUtils.getTile(worldIn, pos, TETickCounter.class);
        tile.ifPresent(TETickCounter::resetCounter);
      }
      worldIn.setBlockState(pos, state.withProperty(LIT, false));
    }
    return true;
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    //taken from BlockTorchTFC
    // Set the initial counter value
    var tile = TileUtils.getTile(worldIn, pos, TETickCounter.class);
    tile.ifPresent(TETickCounter::resetCounter);
    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
  }

  @Override
  public Class<TETickCounter> getTileClass() {
    return TETickCounter.class;
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TETickCounter();
  }

  @Getter
  public enum Carving implements IStringSerializable {
    NONE("none", "XXXXX", "XXXXX", "XXXXX", "XXXXX", "X   X"),
    CIRCLE("circle", "XX XX", "X   X", "     ", "X   X", "XX XX"),
    FACE("face", "XXXXX", "X X X", "XXXXX", "X   X", "XXXXX"),
    CREEPER("creeper", "XXXXX", "X X X", "XX XX", "X   X", "X X X"),
    AXE("axe", "X XXX", "    X", "     ", "    X", "X XXX"),
    HAMMER("hammer", "XXXXX", "     ", "     ", "XX XX", "XXXXX"),
    PICKAXE("pickaxe", "XXXXX", "X   X", " XXX ", "XXXXX", "XXXXX");

    private final String name;
    private final String[] craftPattern;

    Carving(String name, String... pattern) {
      this.name = name;
      this.craftPattern = pattern;
    }

  }
}
