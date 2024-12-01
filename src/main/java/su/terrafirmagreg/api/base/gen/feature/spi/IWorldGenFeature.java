package su.terrafirmagreg.api.base.gen.feature.spi;

import su.terrafirmagreg.api.util.CollectionUtils;

import net.minecraftforge.fml.common.IWorldGenerator;

public interface IWorldGenFeature
  extends IWorldGenerator {

  boolean isAllowed(int dimensionId);

  default boolean isAllowedDimension(int dimensionId, int[] whitelist, int[] blacklist) {

    if (whitelist.length > 0) {
      return CollectionUtils.containsInt(whitelist, dimensionId);

    } else if (blacklist.length > 0) {
      return !CollectionUtils.containsInt(blacklist, dimensionId);
    }

    return true;
  }
}
