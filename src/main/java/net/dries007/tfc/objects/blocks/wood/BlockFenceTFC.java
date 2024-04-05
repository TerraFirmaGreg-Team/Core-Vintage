package net.dries007.tfc.objects.blocks.wood;

import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import su.terrafirmagreg.api.util.BlockUtils;

import java.util.HashMap;
import java.util.Map;

public class BlockFenceTFC extends BlockFence {
	private static final Map<Tree, BlockFenceTFC> MAP = new HashMap<>();
	public final Tree wood;

	public BlockFenceTFC(Tree wood) {
		super(Material.WOOD, Material.WOOD.getMaterialMapColor());
		if (MAP.put(wood, this) != null) throw new IllegalStateException("There can only be one.");
		this.wood = wood;
		setHarvestLevel("axe", 0);
		setHardness(2.0F); // match vanilla
		setResistance(15.0F);
		OreDictionaryHelper.register(this, "fence", "wood");
		//noinspection ConstantConditions
		OreDictionaryHelper.register(this, "fence", "wood", wood.getRegistryName().getPath());
		BlockUtils.setFireInfo(this, 5, 20);
	}

	public static BlockFenceTFC get(Tree wood) {
		return MAP.get(wood);
	}
}
