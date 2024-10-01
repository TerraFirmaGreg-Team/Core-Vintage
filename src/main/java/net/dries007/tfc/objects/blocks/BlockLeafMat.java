package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import com.eerussianguy.firmalife.recipe.DryingRecipe;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.te.TELeafMat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@MethodsReturnNonnullByDefault
public class BlockLeafMat extends Block implements ICapabilitySize {

  public static final AxisAlignedBB MAT_SHAPE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);

  public BlockLeafMat() {
    super(Material.PLANTS, MapColor.GREEN);
    setHardness(1.0F);
    setResistance(1.0F);
    setLightOpacity(0);
    setSoundType(SoundType.PLANT);
    setTickRandomly(true);
  }

  @Override
  public @NotNull Weight getWeight(@NotNull ItemStack stack) {
    return Weight.LIGHT;
  }

  @Override
  public @NotNull Size getSize(@NotNull ItemStack stack) {
    return Size.SMALL;
  }

  @Override
  @SuppressWarnings("deprecation")
  @NotNull
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  @SuppressWarnings("deprecation")
  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return MAT_SHAPE;
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @Override
  public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
    if (!worldIn.isRainingAt(pos.up())) {return;}
    TileUtils.getTile(worldIn, pos, TELeafMat.class).ifPresent(TELeafMat::rain);

  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state) {
    TileUtils.getTile(world, pos, TELeafMat.class).ifPresent(tile -> tile.onBreakBlock(world, pos, state));
    super.breakBlock(world, pos, state);
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    if (world.isRemote) {return true;}
    ItemStack held = player.getHeldItem(hand);
    if (held.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
      return false;
    }
    TileUtils.getTile(world, pos, TELeafMat.class).map(tile -> {
      IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
      if (inventory != null) {
        ItemStack tryStack = new ItemStack(held.getItem(), 1);
        if (DryingRecipe.get(tryStack) != null && inventory.getStackInSlot(0).isEmpty()) {
          ItemStack leftover = inventory.insertItem(0, held.splitStack(1), false);
          ItemHandlerHelper.giveItemToPlayer(player, leftover);
          tile.start();
          tile.markForSync();
          return true;
        }
        if (held.isEmpty() && player.isSneaking()) {
          ItemStack takeStack = inventory.extractItem(0, 1, false);
          StackUtils.spawnItemStack(world, pos, takeStack);
          tile.deleteSlot();
          tile.clear();
          tile.markForSync();
        }
      }
      return true;
    });
    return true;


  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TELeafMat();
  }
}
