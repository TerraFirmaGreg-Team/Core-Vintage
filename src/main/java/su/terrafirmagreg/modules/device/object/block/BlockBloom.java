package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.object.block.spi.BaseBlock;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.registry.api.provider.IProviderTile;
import su.terrafirmagreg.modules.device.object.tile.TileBloom;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.objects.items.ItemsTFC;

import org.jetbrains.annotations.Nullable;

public class BlockBloom extends BaseBlock implements IProviderTile {

  public BlockBloom() {
    super(Settings.of(Material.IRON));

    getSettings()
      .registryKey("bloom")
      .hardness(3.0f)
      .sound(SoundType.STONE)
      .harvestLevel(ToolClasses.PICKAXE, 0);
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state) {
    TileUtils.getTile(world, pos, TileBloom.class).ifPresent(tile -> tile.onBreakBlock(world, pos, state));
    super.breakBlock(world, pos, state);
  }

  @Override
  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, @Nullable EntityPlayer player, boolean willHarvest) {
    if (player != null && player.canHarvestBlock(state) && !player.isCreative()) {
      // Try to give the contents of the TE directly to the player if possible
      TileUtils.getTile(world, pos, TileBloom.class).ifPresent(tile -> {
        IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (cap != null) {
          ItemStack contents = cap.extractItem(0, 64, false);
          ItemHandlerHelper.giveItemToPlayer(player, contents);
        }
      });
    }
    //noinspection ConstantConditions
    return super.removedByPlayer(state, world, pos, player, willHarvest);
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return TileUtils.getTile(world, pos, TileBloom.class).map(tile -> {
      IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
      if (cap != null) {
        ItemStack stack = cap.extractItem(0, 1, true);
        if (!stack.isEmpty()) {
          return stack;
        }
      }
      return new ItemStack(ItemsTFC.UNREFINED_BLOOM);
    }).orElse(new ItemStack(ItemsTFC.UNREFINED_BLOOM));
  }

  @Override
  public @Nullable TileBloom createNewTileEntity(World worldIn, int meta) {
    return new TileBloom();
  }

  @Override
  public Class<TileBloom> getTileClass() {
    return TileBloom.class;
  }
}
