package su.terrafirmagreg.api.base.block;

import su.terrafirmagreg.api.base.tile.BaseTileHorse;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.EntityUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

public abstract class BaseBlockHorse extends BaseBlock implements IProviderTile {


  public BaseBlockHorse(Settings settings) {
    super(settings);

    getSettings()
      .nonFullCube()
      .nonOpaque()
      .size(Size.LARGE)
      .weight(Weight.HEAVY);
  }

  public static ItemStack createItemStack(BaseBlock table, int amount, ItemStack blockItem) {

    return new ItemStack(table, amount);
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    var stack = playerIn.getHeldItem(hand);
    var tile = TileUtils.getTile(worldIn, pos, BaseTileHorse.class);
    return tile.map(baseTileHorse -> {

      final int x = pos.getX();
      final int y = pos.getY();
      final int z = pos.getZ();
      EntityCreature creature = null;
      var clazzes = EntityUtils.getCreatureClasses();
      search:
      for (Class<? extends Entity> clazz : clazzes) {
        var entitiesWithinAABB = worldIn.getEntitiesWithinAABB(clazz, new AxisAlignedBB(
          (double) x - 7.0D, (double) y - 7.0D, (double) z - 7.0D,
          (double) x + 7.0D, (double) y + 7.0D, (double) z + 7.0D)
        );

        for (Object entity : entitiesWithinAABB) {
          if (entity instanceof EntityCreature entityCreature) {
            if ((entityCreature.getLeashed() && entityCreature.getLeashHolder() == playerIn)) {
              creature = entityCreature;
              break search;
            }
          }
        }
      }

      if (((OreDictUtils.contains(stack, "lead") && creature != null) || creature != null)) {
        if (!baseTileHorse.hasWorker()) {
          creature.clearLeashed(true, false);
          baseTileHorse.setWorker(creature);
          return true;
        }
        return false;
      }

      if (!stack.isEmpty()) {
        return false;
      }
      baseTileHorse.setWorkerToPlayer(playerIn);
      baseTileHorse.markDirty();
      return true;
    }).orElse(false);
  }

  @Override
  public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
    super.onBlockHarvested(worldIn, pos, state, player);

    if (!player.isCreative() && !worldIn.isRemote) {
      var tile = TileUtils.getTile(worldIn, pos, BaseTileHorse.class);
      tile.ifPresent(tileHorse -> {
        InventoryHelper.dropInventoryItems(worldIn, pos, tileHorse.getInventory());
        if (tileHorse.hasWorker()) {
          StackUtils.spawnItemStack(worldIn, pos, new ItemStack(Items.LEAD));
        }
      });
    }
  }

  @Override
  public boolean removedByPlayer(@NotNull IBlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull EntityPlayer player, boolean willHarvest) {
    // we pull up a few calls to this point in time because we still have the TE here
    // the execution otherwise is equivalent to vanilla order
    this.onPlayerDestroy(world, pos, state);
    onBlockHarvested(world, pos, state, player);
    if (willHarvest) {
      TileUtils.getTile(world, pos).ifPresent(tile -> {
        this.harvestBlock(world, player, pos, state, tile, player.getHeldItemMainhand());
      });
    }

    world.setBlockToAir(pos);
    // return false to prevent the above called functions to be called again
    return false;
  }


}
