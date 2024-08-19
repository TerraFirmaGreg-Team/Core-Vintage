package su.terrafirmagreg.api.registry;

import net.minecraft.creativetab.CreativeTabs;

public interface IRegistryManager {

    String getModID();

    CreativeTabs getTab();

    Registry getRegistry();

}
