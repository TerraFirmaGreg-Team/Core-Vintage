package net.dries007.tfc.common.objects.blocks;

import net.dries007.tfc.Tags;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;


public class BlockAggregate extends BlockGravel {
    public BlockAggregate() {


        setSoundType(SoundType.SAND);
        setHardness(0.4f);

        setCreativeTab(CreativeTabsTFC.ROCK);
        setRegistryName(Tags.MOD_ID, "aggregate");
        setTranslationKey(Tags.MOD_ID + ".aggregate");
    }
}
