/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@ParametersAreNonnullByDefault
public class BlockFireBrick extends Block implements IItemSize {
	public BlockFireBrick() {
		super(Material.ROCK);

		setSoundType(SoundType.STONE);
		setHardness(1.0F);

		setCreativeTab(CreativeTabsTFC.MISC);
		setRegistryName(MOD_ID, "fire_bricks");
		setTranslationKey(MOD_ID + ".fire_bricks");
	}

	@Nonnull
	@Override
	public Size getSize(ItemStack stack) {
		return Size.SMALL; // Stored everywhere
	}

	@Nonnull
	@Override
	public Weight getWeight(ItemStack stack) {
		return Weight.LIGHT; // Stacksize = 32
	}
}
