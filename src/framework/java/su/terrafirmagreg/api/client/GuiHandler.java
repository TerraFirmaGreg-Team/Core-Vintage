package su.terrafirmagreg.api.client;

import su.terrafirmagreg.framework.Framework;
import su.terrafirmagreg.framework.manager.content.provider.IProviderContainer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import org.jetbrains.annotations.Nullable;

public class GuiHandler implements IGuiHandler {

  public static void enableGui() {
    NetworkRegistry.INSTANCE.registerGuiHandler(Framework.modId, new GuiHandler());
  }

  public static void openGui(World world, BlockPos pos, EntityPlayer player) {
    player.openGui(Framework.modId, 0, world, pos.getX(), pos.getY(), pos.getZ());
  }

  @Override
  @Nullable
  public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    var blockPos = new BlockPos(x, y, z);
    var blockState = world.getBlockState(blockPos);

    var entity = world.getEntityByID(x);
    var item = player.getHeldItemMainhand().getItem();
    var tile = world.getTileEntity(blockPos);

    if (tile instanceof IProviderContainer<?, ?> provider) {
      return provider.getContainer(player.inventory, world, blockState, blockPos);
    }

    if (entity instanceof IProviderContainer<?, ?> provider) {
      return provider.getContainer(player.inventory, world, blockState, blockPos);
    }

    if (item instanceof IProviderContainer<?, ?> provider) {
      return provider.getContainer(player.inventory, world, blockState, blockPos);
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
    var tile = world.getTileEntity(blockPos);

    if (tile instanceof IProviderContainer<?, ?> provider) {
      return provider.getGuiContainer(player.inventory, world, blockState, blockPos);
    }

    if (entity instanceof IProviderContainer<?, ?> provider) {
      return provider.getGuiContainer(player.inventory, world, blockState, blockPos);
    }

    if (item instanceof IProviderContainer<?, ?> provider) {
      return provider.getGuiContainer(player.inventory, world, blockState, blockPos);
    }
    return null;
  }


}
