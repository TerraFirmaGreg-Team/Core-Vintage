package su.terrafirmagreg.api.registry.provider;

import org.jetbrains.annotations.Nullable;

public interface IProviderMultiItemBlock {

  boolean getHasItemSubtypes();

  default int getMaxItemDamage() {
    return 0;
  }

  @Nullable
  String getTranslationKey(int meta);

}
