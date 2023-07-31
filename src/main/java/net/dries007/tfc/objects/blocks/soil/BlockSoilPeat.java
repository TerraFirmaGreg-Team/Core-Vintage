/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks.soil;

import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class BlockSoilPeat extends Block {
	public BlockSoilPeat(Material material) {
		super(material);

		this.setSoundType(SoundType.GROUND);
		this.setHardness(0.6F);
		this.setHarvestLevel("shovel", 0);

		OreDictionaryHelper.register(this, "peat");
		Blocks.FIRE.setFireInfo(this, 5, 10);
	}
}
