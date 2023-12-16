package com.speeedcraft.tfgmod;

import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.List;

public class TFGModMixinLateLoader implements ILateMixinLoader {

	@Override
	public List<String> getMixinConfigs() {
		final List<String> mixinList = new ArrayList<>();

		if (Loader.isModLoaded("cellars"))
			mixinList.add("mixin.tfgmod.cellars.json");

		if (Loader.isModLoaded("extraplanets"))
			mixinList.add("mixin.tfgmod.extraplanets.json");

		if (Loader.isModLoaded("firmalife"))
			mixinList.add("mixin.tfgmod.firmalife.json");

		if (Loader.isModLoaded("gregtech"))
			mixinList.add("mixin.tfgmod.gregtech.json");

		if (Loader.isModLoaded("puddles"))
			mixinList.add("mixin.tfgmod.puddles.json");

		if (Loader.isModLoaded("tfc"))
			mixinList.add("mixin.tfgmod.tfc.json");

		if (Loader.isModLoaded("tfcflorae"))
			mixinList.add("mixin.tfgmod.tfcflorae.json");

		if (Loader.isModLoaded("tfctech"))
			mixinList.add("mixin.tfgmod.tfctech.json");

		return mixinList;
	}
}
