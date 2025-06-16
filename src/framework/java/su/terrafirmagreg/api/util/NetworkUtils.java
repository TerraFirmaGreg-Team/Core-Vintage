package su.terrafirmagreg.api.util;

import su.terrafirmagreg.TerraFirmaGreg;

import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;


@UtilityClass
public final class NetworkUtils {


  public static final int DEFAULT_RANGE = 64;

  public static boolean isValidChannel(String channel) {
    Objects.requireNonNull(channel);
    ModUtils.isValidIdentifier(channel);

    if (channel.length() > 20) {
      throw new RuntimeException("Channel name '" + channel + "' is too long for Forge. Maximum length supported is 20 characters.");
    }
    return true;
  }

  public static boolean isValidChannel(ResourceLocation channel) {

    return isValidChannel(channel.toString());
  }

  public static <T> void queueTask(MessageContext context, Supplier<T> supplier, Consumer<T> consumer) {

    queueTask(context, () -> consumer.accept(supplier.get()));
  }

  public static void queueTask(MessageContext context, Runnable task) {
    final IThreadListener target = TerraFirmaGreg.PROXY.getThreadListener(context);

    addScheduledTask(target, task);
  }

  public static void addScheduledTask(IThreadListener target, Runnable task) {
    if (target == null) {
      return;
    }
    if (target.isCallingFromMinecraftThread()) {
      task.run();
    } else {
      target.addScheduledTask(task);
    }
  }
}
