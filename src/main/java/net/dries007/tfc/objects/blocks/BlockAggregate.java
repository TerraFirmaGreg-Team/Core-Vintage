package net.dries007.tfc.objects.blocks;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class BlockAggregate extends BlockGravel {
	public BlockAggregate() {

		setCreativeTab(CreativeTabsTFC.ROCK_STUFFS);
		setSoundType(SoundType.SAND);
		setHardness(0.4f);
		setRegistryName(MOD_ID, "aggregate");
		setTranslationKey(MOD_ID + ".aggregate");
	}
}
