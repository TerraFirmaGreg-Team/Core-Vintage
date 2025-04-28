package su.terrafirmagreg.api.base.client.gui;

import su.terrafirmagreg.framework.manager.registry.api.provider.IProviderContainer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import org.jetbrains.annotations.Nullable;

public class GuiHandler implements IGuiHandler {

  private static String namespace;

  public GuiHandler(String namespace) {
    GuiHandler.namespace = namespace;
    NetworkRegistry.INSTANCE.registerGuiHandler(namespace, this);
  }

  public static IGuiHandler of(String modId) {
    return new GuiHandler(modId);
  }

  public static void openGui(World world, BlockPos pos, EntityPlayer player) {
    player.openGui(namespace, 0, world, pos.getX(), pos.getY(), pos.getZ());
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
