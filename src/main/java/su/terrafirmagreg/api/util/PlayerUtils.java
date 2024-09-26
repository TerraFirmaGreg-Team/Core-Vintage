package su.terrafirmagreg.api.util;

import net.minecraftforge.common.UsernameCache;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unused")
@UtilityClass
public class PlayerUtils {

  private static final Map<String, UUID> USERNAME = CollectionUtils.invertMap(UsernameCache.getMap());


  public static UUID getUUID(String player) {
    return USERNAME.get(player);
  }

  public static boolean hasUUID(String player) {
    return USERNAME.containsKey(player);
  }
}
