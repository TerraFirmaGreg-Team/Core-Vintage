package su.terrafirmagreg.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IProxy {

  @NotNull
  IThreadListener getThreadListener(MessageContext context);

  @Nullable
  EntityPlayer getPlayer(MessageContext context);

  @Nullable
  World getWorld(MessageContext context);
}
