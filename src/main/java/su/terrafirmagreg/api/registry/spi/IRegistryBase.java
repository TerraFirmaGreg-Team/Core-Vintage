package su.terrafirmagreg.api.registry.spi;

import su.terrafirmagreg.api.network.NetworkEntityIdSupplier;
import su.terrafirmagreg.api.registry.Registry;

import net.minecraft.creativetab.CreativeTabs;

public interface IRegistryBase {

  String getModID();

  String getModuleName();

  CreativeTabs getTab();

  Registry getRegistry();

  NetworkEntityIdSupplier getNetworkEntityIdSupplier();
}
