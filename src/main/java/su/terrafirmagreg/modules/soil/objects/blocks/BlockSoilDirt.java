package su.terrafirmagreg.modules.soil.objects.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariants;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import java.util.Random;

public class BlockSoilDirt extends BlockSoil {

	public BlockSoilDirt(SoilBlockVariant blockVariant, SoilType type) {
		super(blockVariant, type);

		//DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
	}

	@NotNull
	@Override
	public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
		return ItemsSoil.getItem(SoilItemVariants.PILE, getType());
	}
}
