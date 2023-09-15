package net.dries007.tfc.common.objects.blocks;

import net.dries007.tfc.Tags;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;


public class BlockDebug extends Block {
    public BlockDebug() {
        super(Material.SPONGE);

        setCreativeTab(CreativeTabsTFC.MISC);
        setRegistryName(Tags.MOD_ID, "debug");
        setTranslationKey(Tags.MOD_ID + "." + "debug");
    }
}
