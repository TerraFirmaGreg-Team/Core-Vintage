package net.dries007.tfc.objects.blocks;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDebug extends Block {
    public BlockDebug() {
        super(Material.SPONGE);

        this.setCreativeTab(CreativeTabsTFC.MISC);
        this.setRegistryName(MOD_ID, "debug");
        this.setTranslationKey(MOD_ID + "." + "debug");
    }
}
