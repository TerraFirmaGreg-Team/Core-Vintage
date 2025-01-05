package su.terrafirmagreg.proxy;

import su.terrafirmagreg.api.util.GameUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy {

  @NotNull
  @Override
  public IThreadListener getThreadListener(MessageContext context) {
    if (context.side.isClient()) {
      return GameUtils.getMinecraft();
    } else {
      return context.getServerHandler().player.server;
    }
  }

  @Override
  @Nullable
  public EntityPlayer getPlayer(MessageContext context) {
    if (context.side.isClient()) {
      return GameUtils.getPlayer();
    } else {
      return context.getServerHandler().player;
    }
  }

  @Override
  @Nullable
  public World getWorld(MessageContext context) {
    if (context.side.isClient()) {
      return GameUtils.getWorld();
    } else {
      return context.getServerHandler().player.getEntityWorld();
    }
  }
}
