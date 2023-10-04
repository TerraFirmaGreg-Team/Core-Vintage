package su.terrafirmagreg.util.integration.crafttweaker;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import su.terrafirmagreg.util.UtilMod;
import su.terrafirmagreg.util.module.ModuleBase;

public class ModuleIntegration
        extends ModuleBase {

    public ModuleIntegration() {

        super(Integer.MAX_VALUE, UtilMod.MOD_ID);
    }

    @Override
    public void onPreInitializationEvent(FMLPreInitializationEvent event) {

        PluginDelegate.init();
    }

    @Override
    public void onPostInitializationEvent(FMLPostInitializationEvent event) {

        PluginDelegate.apply();
    }
}
