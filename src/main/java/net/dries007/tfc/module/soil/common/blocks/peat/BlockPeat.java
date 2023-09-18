package net.dries007.tfc.module.soil.common.blocks.peat;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import net.dries007.tfc.Tags;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.blocks.TFCBlock;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;


public class BlockPeat extends TFCBlock {
    public BlockPeat() {
        super(Material.GROUND);

        setRegistryName(Tags.MOD_ID, "peat");
        setTranslationKey(Tags.MOD_ID + ".peat");
        setCreativeTab(CreativeTabsTFC.SOIL);
        setSoundType(SoundType.GROUND);
        setHardness(0.6F);
        setHarvestLevel("shovel", 0);

        OreDictionaryHelper.register(this, "peat");
        Blocks.FIRE.setFireInfo(this, 5, 10);

        DirtHelper.registerSoil(this.getDefaultState().getBlock(), DirtHelper.GRAVELLIKE);
    }
}
