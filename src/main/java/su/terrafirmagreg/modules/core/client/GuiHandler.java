package su.terrafirmagreg.modules.core.client;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import org.jetbrains.annotations.Nullable;

public class GuiHandler implements IGuiHandler {

  public static void openGui(World world, BlockPos pos, EntityPlayer player) {
    player.openGui(TerraFirmaGreg.getInstance(), 0, world, pos.getX(), pos.getY(), pos.getZ());
  }

  @Override
  @Nullable
  public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    var blockPos = new BlockPos(x, y, z);
    var blockState = world.getBlockState(blockPos);

    var entity = world.getEntityByID(x);
    var item = player.getHeldItemMainhand().getItem();

    TileUtils.getTile(world, blockPos)
             .filter(tile -> tile instanceof IProviderContainer<?, ?>)
             .map(tile -> {
               IProviderContainer<?, ?> containerProvider = (IProviderContainer<?, ?>) tile;
               return containerProvider.getContainer(player.inventory, world, blockState, blockPos);
             });

    if (entity instanceof IProviderContainer<?, ?> containerProvider) {
      return containerProvider.getContainer(player.inventory, world, blockState, blockPos);
    }

    if (item instanceof IProviderContainer<?, ?> containerProvider) {
      return containerProvider.getContainer(player.inventory, world, blockState, blockPos);
    }

    return null;
  }

  @Override
  @Nullable
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    var blockPos = new BlockPos(x, y, z);
    var blockState = world.getBlockState(blockPos);

    var entity = world.getEntityByID(x);
    var item = player.getHeldItemMainhand().getItem();

    TileUtils.getTile(world, blockPos)
             .filter(tile -> tile instanceof IProviderContainer<?, ?>)
             .map(tile -> {
               IProviderContainer<?, ?> containerProvider = (IProviderContainer<?, ?>) tile;
               return containerProvider.getContainer(player.inventory, world, blockState, blockPos);
             });

    if (entity instanceof IProviderContainer<?, ?> containerProvider) {
      return containerProvider.getGuiContainer(player.inventory, world, blockState, blockPos);
    }

    if (item instanceof IProviderContainer<?, ?> containerProvider) {
      return containerProvider.getGuiContainer(player.inventory, world, blockState, blockPos);
    }
    return null;
  }


}
