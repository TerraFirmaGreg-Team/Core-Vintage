package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlockContainer;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.objects.items.ItemFireStarter;
import su.terrafirmagreg.modules.device.objects.tiles.TileBellows;
import su.terrafirmagreg.modules.device.objects.tiles.TileBlastFurnace;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;


import gregtech.api.items.toolitem.ToolClasses;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.util.IBellowsConsumerBlock;
import net.dries007.tfc.objects.blocks.BlockFireBrick;
import net.dries007.tfc.objects.blocks.metal.BlockMetalSheet;
import net.dries007.tfc.objects.te.TEMetalSheet;
import net.dries007.tfc.util.block.Multiblock;

import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

import static su.terrafirmagreg.data.Properties.LIT;

@SuppressWarnings("deprecation")
public class BlockBlastFurnace extends BaseBlockContainer implements IBellowsConsumerBlock,
    IProviderTile {

  private static final Multiblock BLAST_FURNACE_CHIMNEY;

  static {
    Predicate<IBlockState> stoneMatcher = state -> state.getBlock() instanceof BlockFireBrick;
    Predicate<IBlockState> sheetMatcher = state -> {
      if (state.getBlock() instanceof BlockMetalSheet block) {
        return block.getMetal().getTier().isAtLeast(Metal.Tier.TIER_III) && block.getMetal()
            .isToolMetal();
      }
      return false;
    };
    BLAST_FURNACE_CHIMNEY = new Multiblock()
        .match(new BlockPos(0, 0, 0),
            state -> state.getBlock() == BlocksDevice.MOLTEN || state.getMaterial()
                .isReplaceable())
        .match(new BlockPos(0, 0, 1), stoneMatcher)
        .match(new BlockPos(0, 0, -1), stoneMatcher)
        .match(new BlockPos(1, 0, 0), stoneMatcher)
        .match(new BlockPos(-1, 0, 0), stoneMatcher)
        .match(new BlockPos(0, 0, -2), sheetMatcher)
        .match(new BlockPos(0, 0, -2), tile -> tile.getFace(EnumFacing.NORTH), TEMetalSheet.class)
        .match(new BlockPos(0, 0, 2), sheetMatcher)
        .match(new BlockPos(0, 0, 2), tile -> tile.getFace(EnumFacing.SOUTH), TEMetalSheet.class)
        .match(new BlockPos(2, 0, 0), sheetMatcher)
        .match(new BlockPos(2, 0, 0), tile -> tile.getFace(EnumFacing.EAST), TEMetalSheet.class)
        .match(new BlockPos(-2, 0, 0), sheetMatcher)
        .match(new BlockPos(-2, 0, 0), tile -> tile.getFace(EnumFacing.WEST), TEMetalSheet.class)
        .match(new BlockPos(-1, 0, -1), sheetMatcher)
        .match(new BlockPos(-1, 0, -1),
            tile -> tile.getFace(EnumFacing.NORTH) && tile.getFace(EnumFacing.WEST),
            TEMetalSheet.class)
        .match(new BlockPos(1, 0, -1), sheetMatcher)
        .match(new BlockPos(1, 0, -1),
            tile -> tile.getFace(EnumFacing.NORTH) && tile.getFace(EnumFacing.EAST),
            TEMetalSheet.class)
        .match(new BlockPos(-1, 0, 1), sheetMatcher)
        .match(new BlockPos(-1, 0, 1),
            tile -> tile.getFace(EnumFacing.SOUTH) && tile.getFace(EnumFacing.WEST),
            TEMetalSheet.class)
        .match(new BlockPos(1, 0, 1), sheetMatcher)
        .match(new BlockPos(1, 0, 1),
            tile -> tile.getFace(EnumFacing.SOUTH) && tile.getFace(EnumFacing.EAST),
            TEMetalSheet.class);
  }

  public BlockBlastFurnace() {
    super(Settings.of(Material.IRON));

    getSettings()
        .registryKey("device/blast_furnace")
        .hardness(2.0F)
        .resistance(2.0F);
    setHarvestLevel(ToolClasses.PICKAXE, 0);
  }

  /**
   * Structural check of the blast furnace. Any value > 0 means this blast furnace can work
   *
   * @param world the world obj to check on the structrue
   * @param pos   this block pos
   * @return [0, 5] where 0 means this blast furnace can't operate.
   */
  public static int getChimneyLevels(World world, BlockPos pos) {
    for (int i = 1; i < 6; i++) {
      BlockPos center = pos.up(i);
      if (!BLAST_FURNACE_CHIMNEY.test(world, center)) {
        return i - 1;
      }
    }
    // Maximum levels
    return 5;
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(LIT, meta == 1);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(LIT) ? 1 : 0;
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state) {
    var tile = TileUtils.getTile(world, pos, TileBlastFurnace.class);
    if (tile != null) {
      tile.onBreakBlock(world, pos, state);
    }
    super.breakBlock(world, pos, state);
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
      EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
      float hitX, float hitY, float hitZ) {
    if (!worldIn.isRemote) {
      if (!state.getValue(LIT)) {
        var tile = TileUtils.getTile(worldIn, pos, TileBlastFurnace.class);
        if (tile == null) {
          return true;
        }
        ItemStack held = playerIn.getHeldItem(hand);
        if (tile.canIgnite() && ItemFireStarter.onIgnition(held)) {
          worldIn.setBlockState(pos, state.withProperty(LIT, true));
          //tile.onIgnite();
          return true;
        }
      }
      if (!playerIn.isSneaking()) {
        GuiHandler.openGui(worldIn, pos, playerIn);
      }
    }
    return true;
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, LIT);
  }

  @Override
  public boolean canIntakeFrom(Vec3i offset, EnumFacing facing) {
    return offset.equals(TileBellows.OFFSET_LEVEL);
  }

  @Override
  public void onAirIntake(World world, BlockPos pos, int airAmount) {
    var tile = TileUtils.getTile(world, pos, TileBlastFurnace.class);
    if (tile != null) {
      tile.onAirIntake(airAmount);
    }
  }

  @Override
  public Class<? extends TileEntity> getTileEntityClass() {
    return TileBlastFurnace.class;
  }

  @Override
  public @Nullable TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileBlastFurnace();
  }
}
