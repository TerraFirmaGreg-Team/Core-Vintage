package net.dries007.tfc.objects.blocks.soil;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class BlockSoilPeat extends Block {
	public BlockSoilPeat(Material material) {
		super(material);

		setSoundType(SoundType.GROUND);
		setHardness(0.6F);
		setHarvestLevel("shovel", 0);

		setCreativeTab(CreativeTabsTFC.EARTH);
		setRegistryName(MOD_ID, "peat");
		setTranslationKey(MOD_ID + ".peat");

		OreDictionaryHelper.register(this, "peat");
		Blocks.FIRE.setFireInfo(this, 5, 10);
	}
}
