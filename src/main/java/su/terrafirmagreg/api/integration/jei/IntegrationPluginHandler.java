package su.terrafirmagreg.api.integration.jei;

import mezz.jei.api.IModPlugin;
import su.terrafirmagreg.api.integration.IIntegrationPluginHandler;

public class IntegrationPluginHandler implements IIntegrationPluginHandler {

	@Override
	public void execute(String pluginClass) throws Exception {

		IModPlugin plugin = (IModPlugin) Class.forName(pluginClass).newInstance();
		PluginDelegate.registerPlugin(plugin);
	}
}
