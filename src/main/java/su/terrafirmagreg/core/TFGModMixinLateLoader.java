package su.terrafirmagreg.core;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.List;

public class TFGModMixinLateLoader implements ILateMixinLoader {

	@Override
	public List<String> getMixinConfigs() {
		final List<String> mixinList = new ArrayList<>();

		mixinList.add("mixins.tfg.json");

		return mixinList;
	}
}
