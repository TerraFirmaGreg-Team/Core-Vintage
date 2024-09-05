package su.terrafirmagreg.api.registry.provider;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;

public interface IProviderAutoReg
    extends IProviderOreDict, ICapabilitySize {

  default String getRegistryKey() {
    throw new IllegalArgumentException("Must override");
  }

}
