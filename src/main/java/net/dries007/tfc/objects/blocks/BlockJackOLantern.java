package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlockHorizontal;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
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
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.te.TETickCounter;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.LIT;
import static su.terrafirmagreg.api.data.Properties.DirectionProp.HORIZONTAL;

@MethodsReturnNonnullByDefault

public class BlockJackOLantern extends BaseBlockHorizontal implements ICapabilitySize {

  private final Carving carving;

  public BlockJackOLantern(Carving carving) {
    super(Settings.of(Material.GOURD));
    setDefaultState(blockState.getBaseState().withProperty(HORIZONTAL, EnumFacing.NORTH).withProperty(LIT, false));
    setTickRandomly(true);
    setCreativeTab(CreativeTabsTFC.CT_MISC);
    setHardness(1f);
    setLightLevel(0.75f);
    setTickRandomly(true);
    this.carving = carving;
  }

  @Override
  public @NotNull Weight getWeight(ItemStack stack) {
    return Weight.HEAVY;
  }

  @Override
  public @NotNull Size getSize(ItemStack stack) {
    return Size.LARGE;
  }

  @SuppressWarnings("deprecation")
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta)).withProperty(LIT, meta >= 4);
  }

  public int getMetaFromState(IBlockState state) {
    return (state.getValue(LIT) ? 4 : 0) + state.getValue(HORIZONTAL).getHorizontalIndex();
  }

  @SuppressWarnings("deprecation")
  public IBlockState withRotation(IBlockState state, Rotation rot) {
    return state.withProperty(HORIZONTAL, rot.rotate(state.getValue(HORIZONTAL)));
  }

  @SuppressWarnings("deprecation")
  public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
    return state.withRotation(mirrorIn.toRotation(state.getValue(HORIZONTAL)));
  }

  public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
    if (worldIn.isRemote) {
      return;
    }
    //taken from BlockTorchTFC
    //last twice as long as a torch. balance this by being less bright
    TileUtils.getTile(worldIn, pos, TETickCounter.class)
             .filter(tile -> tile.getTicksSinceUpdate() > (2L * ConfigTFC.General.OVERRIDES.torchTime) && ConfigTFC.General.OVERRIDES.torchTime > 0)
             .ifPresent(tile -> {
               worldIn.setBlockState(pos, state.withProperty(LIT, false));
               tile.resetCounter();
             });
  }

  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return worldIn.getBlockState(pos)
                  .getBlock()
                  .isReplaceable(worldIn, pos) && worldIn.isSideSolid(pos.down(), EnumFacing.UP);
  }

  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    //taken from BlockTorchTFC
    if (!worldIn.isRemote) {
      ItemStack stack = playerIn.getHeldItem(hand);
      TileUtils.getTile(worldIn, pos, TETickCounter.class).ifPresent(tile -> {
        if (BlockTorchTFC.canLight(stack)) {
          worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(LIT, true));
          tile.resetCounter();
        } else {
          worldIn.setBlockState(pos, state.withProperty(LIT, false));
        }
      });
    }
    return true;
  }

  @SuppressWarnings("deprecation")
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return this.getDefaultState().withProperty(HORIZONTAL, placer.getHorizontalFacing().getOpposite());
  }

  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    //taken from BlockTorchTFC
    // Set the initial counter value
    TileUtils.getTile(worldIn, pos, TETickCounter.class).ifPresent(TETickCounter::resetCounter);
    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
  }

  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL, LIT);
  }

  public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    return state.getValue(LIT) ? super.getLightValue(state, world, pos) : 0;
  }

  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TETickCounter();
  }

  public Carving getCarving() {
    return carving;
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
