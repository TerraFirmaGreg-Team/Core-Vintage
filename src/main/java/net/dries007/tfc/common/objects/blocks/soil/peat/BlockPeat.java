package net.dries007.tfc.common.objects.blocks.soil.peat;

import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class BlockPeat extends Block {
    public BlockPeat(Material material) {
        super(material);

        setRegistryName(MOD_ID, "peat");
        setTranslationKey(MOD_ID + ".peat");
        setCreativeTab(CreativeTabsTFC.EARTH);
        setSoundType(SoundType.GROUND);
        setHardness(0.6F);
        setHarvestLevel("shovel", 0);

        OreDictionaryHelper.register(this, "peat");
        Blocks.FIRE.setFireInfo(this, 5, 10);
    }
}
