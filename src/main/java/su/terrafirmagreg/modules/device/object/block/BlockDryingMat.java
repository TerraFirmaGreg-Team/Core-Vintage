package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockContainer;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.client.render.TESRDryingMat;
import su.terrafirmagreg.modules.device.object.tile.TileDryingMat;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockDryingMat extends BaseBlockContainer {

  public static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

  public BlockDryingMat() {
    super(Settings.of(Material.GRASS, MapColor.YELLOW));

    getSettings()
      .registryKey("device/drying_mat")
      .hardness(0.5f)
      .randomTicks()
      .nonCube()
      .sound(SoundType.PLANT);
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return AABB;
  }

  @Override
  public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
    if (worldIn.isRemote) {return;}
    TileUtils.getTile(worldIn, pos, TileDryingMat.class)
             .filter(tile -> worldIn.isRainingAt(pos.up()))
             .ifPresent(TileDryingMat::resetCounter);
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!canPlaceBlockAt(worldIn, pos)) {
      this.breakBlock(worldIn, pos, state);
      worldIn.setBlockToAir(pos);
    }
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return worldIn.getBlockState(pos.down()).isTopSolid();
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!hand.equals(EnumHand.MAIN_HAND)) {return false;}
    TileUtils.getTile(world, pos, TileDryingMat.class).ifPresent(tile -> {
      if (!world.isRemote) {
        if (player.isSneaking()) {
          ItemStack stack = tile.getStack();
          if (stack.isEmpty()) {
            ItemStack is = player.getHeldItem(hand);
            if (tile.isItemValid(TileDryingMat.SLOT, is)) {
              tile.setStack(is.copy());
              is.setCount(0);
            }
          } else {
            ItemHandlerHelper.giveItemToPlayer(player, stack);
            tile.setStack(ItemStack.EMPTY);
          }
          tile.setAndUpdateSlots(TileDryingMat.SLOT);
        } else {
          GuiHandler.openGui(world, pos, player);
        }
      }
    });
    world.notifyBlockUpdate(pos, state, state, 3);
    return true;


  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    // Set the initial counter value
    TileUtils.getTile(worldIn, pos, TileDryingMat.class).ifPresent(TileDryingMat::resetCounter);
    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, TileDryingMat.class).ifPresent(tile -> tile.onBreakBlock(worldIn, pos, state));
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public @Nullable TileDryingMat createNewTileEntity(World worldIn, int meta) {
    return new TileDryingMat();
  }

  @Override
  public Class<TileDryingMat> getTileClass() {
    return TileDryingMat.class;
  }

  @Override
  public @Nullable TileEntitySpecialRenderer<?> getTileRenderer() {
    return new TESRDryingMat();
  }
}
