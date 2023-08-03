package net.dries007.tfc.objects.blocks.soil;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class BlockSoilPeat extends Block {
    public BlockSoilPeat(Material material) {
        super(material);

        this.setCreativeTab(CreativeTabsTFC.EARTH);
        this.setSoundType(SoundType.GROUND);
        this.setHardness(0.6F);
        this.setHarvestLevel("shovel", 0);
        this.setRegistryName(MOD_ID, "peat");
        this.setTranslationKey(MOD_ID + ".peat");

        OreDictionaryHelper.register(this, "peat");
        Blocks.FIRE.setFireInfo(this, 5, 10);
    }
}
