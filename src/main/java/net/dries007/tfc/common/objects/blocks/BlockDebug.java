package net.dries007.tfc.common.objects.blocks;

import net.dries007.tfc.Tags;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

import javax.annotation.Nullable;


public class BlockDebug extends TFCBlock {
    public BlockDebug() {
        super(Material.SPONGE);

        setCreativeTab(CreativeTabsTFC.MISC);
        setRegistryName(Tags.MOD_ID, "debug");
        setTranslationKey(Tags.MOD_ID + "." + "debug");
    }
}
