package su.terrafirmagreg.util.integration.crafttweaker;

import su.terrafirmagreg.util.integration.IIntegrationPluginHandler;
import su.terrafirmagreg.util.module.ModuleBase;

import javax.annotation.Nullable;

public class IntegrationPluginHandler
        implements IIntegrationPluginHandler {

    @Override
    public void execute(String plugin) throws Exception {

        PluginDelegate.registerZenClass(Class.forName(plugin));
    }

    @Nullable
    @Override
    public Class<? extends ModuleBase> getModuleClass() {

        return ModuleIntegration.class;
    }
}
