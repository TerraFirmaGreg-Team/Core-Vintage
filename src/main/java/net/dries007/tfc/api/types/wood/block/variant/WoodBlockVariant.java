package net.dries007.tfc.api.types.wood.block.variant;

import net.dries007.tfc.api.types.wood.util.IWoodBlock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WoodBlockVariant {

	private static final HashSet<WoodBlockVariant> woodBlockVariants = new HashSet<>();


	public WoodBlockVariant(IWoodBlock woodBlock) {
	}


	public static List<WoodBlockVariant> getAllWoodBlockTypes() {
		return new ArrayList<>(woodBlockVariants);
	}
}
