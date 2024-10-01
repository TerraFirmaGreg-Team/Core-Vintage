package su.terrafirmagreg.modules.core.object.block;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.object.tile.TileIngotPile;
import su.terrafirmagreg.modules.device.client.render.TESRIngotPile;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import com.google.common.collect.ImmutableMap;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.metal.ItemMetal;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class BlockIngotPile extends BaseBlock implements IProviderTile {

  private static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0, 0, 0, 1, 0.125, 1);

  public BlockIngotPile() {
    super(Settings.of(Material.IRON));

    getSettings()
      .registryKey("core/ingot_pile")
      .hardness(3.0F)
      .resistance(10.0F)
      .nonFullCube()
      .nonOpaque()
      .noItemBlock()
      .harvestLevel(ToolClasses.PICKAXE, 0);

  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return TileUtils.getTile(source, pos, TileIngotPile.class).map(tile -> {
      double y = tile.getCount() / 64f;
      return new AxisAlignedBB(0d, 0d, 0d, 1d, y, 1d);
    }).orElseGet(() -> DEFAULT_AABB); // Default is here for the default state bounding box query (comes from world#mayPlace)

  }

  @SideOnly(Side.CLIENT)
  @Override
  public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
    return TileUtils.getTile(worldIn, pos, TileIngotPile.class).map(tile -> {
      double y = 0.125 * (tile.getCount() / 8.0);
      return new AxisAlignedBB(0d, 0d, 0d, 1d, y, 1d);
    }).orElseGet(() -> new AxisAlignedBB(0d, 0d, 0d, 1d, 1, 1d));
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!collapseDown(worldIn, pos) && !worldIn.isSideSolid(pos.down(), EnumFacing.UP)) {
      worldIn.setBlockToAir(pos);
    }
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, TileIngotPile.class).ifPresent(TileIngotPile::onBreakBlock);
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!playerIn.isSneaking()) {
      var tile = TileUtils.getTile(worldIn, pos, TileIngotPile.class);
      if (tile.isPresent()) {
        BlockPos posTop = pos;
        IBlockState stateTop;
        do {
          posTop = posTop.up();
          stateTop = worldIn.getBlockState(posTop);
          if (stateTop.getBlock() != BlocksCore.INGOT_PILE && tile.isPresent()) {
            return removeIngot(worldIn, playerIn, tile.get());
          } else {
            tile = TileUtils.getTile(worldIn, posTop, TileIngotPile.class);
            if (tile.isPresent()) {
              if (tile.get().getCount() < 64) {
                return removeIngot(worldIn, playerIn, tile.get());
              }
            }
          }
        } while (posTop.getY() <= 256);
        return false;
      }
    }
    return true;
  }


  @Override
  public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    return TileUtils.getTile(world, pos, TileIngotPile.class)
                    .map(tile -> tile.getCount() == 64)
                    .orElse(false);
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return TileUtils.getTile(world, pos, TileIngotPile.class)
                    .map(tile -> new ItemStack(ItemMetal.get(tile.getMetal(), Metal.ItemType.INGOT)))
                    .orElse(ItemStack.EMPTY);
  }

  private boolean removeIngot(World worldIn, EntityPlayer playerIn, TileIngotPile tile) {
    if (worldIn.isRemote) {
      tile.setCount(tile.getCount() - 1);
      if (tile.getCount() <= 0) {
        worldIn.setBlockState(tile.getPos(), Blocks.AIR.getDefaultState());
      }
      ItemHandlerHelper.giveItemToPlayer(playerIn, new ItemStack(ItemMetal.get(tile.getMetal(), Metal.ItemType.INGOT)));
    }
    return true;
  }

  private boolean collapseDown(World world, BlockPos pos) {
    IBlockState stateDown = world.getBlockState(pos.down());
    if (stateDown.getBlock() == BlocksCore.INGOT_PILE) {return false;}
    TileUtils.getTile(world, pos.down(), TileIngotPile.class)
             .filter(tile -> tile.getCount() < 64)
             .ifPresent(tile -> {
               TileUtils.getTile(world, pos, TileIngotPile.class).ifPresent(tileUp -> {
                 if (tile.getCount() + tileUp.getCount() <= 64) {
                   tile.setCount(tile.getCount() + tileUp.getCount());
                   world.setBlockToAir(pos);
                 } else {
                   tile.setCount(64);
                   tileUp.setCount(tile.getCount() + tileUp.getCount() - 64);
                 }
               });
             });
    return true;
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return TileUtils.getTile(worldIn, pos, TileIngotPile.class)
                    .filter(tile -> tile.getCount() == 64 && face == EnumFacing.UP)
                    .map(tile -> BlockFaceShape.SOLID)
                    .orElse(BlockFaceShape.UNDEFINED);
  }

  @Override
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return TileUtils.getTile(worldIn, pos, TileIngotPile.class).map(tile -> {
      double y = 0.125 * (tile.getCount() / 8.0);
      return new AxisAlignedBB(0d, 0d, 0d, 1d, y, 1d);
    }).orElse(new AxisAlignedBB(0d, 0d, 0d, 1d, 1, 1d));

  }

  @Override
  public @Nullable TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileIngotPile();
  }

  @Override
  public Class<TileIngotPile> getTileClass() {
    return TileIngotPile.class;
  }

  @SideOnly(Side.CLIENT)
  public @Nullable TileEntitySpecialRenderer<?> getTileRenderer() {
    return new TESRIngotPile();
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IStateMapper getStateMapper() {
    return blockIn -> ImmutableMap.of(this.getDefaultState(),
                                      new ModelResourceLocation(ModUtils.resource("empty").toString()));
  }
}
