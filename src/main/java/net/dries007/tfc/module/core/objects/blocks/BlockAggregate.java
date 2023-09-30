package net.dries007.tfc.module.core.objects.blocks;

import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.module.core.api.objects.block.itemblocks.ItemBlockBase;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;

import javax.annotation.Nullable;


public class BlockAggregate extends BlockGravel implements IItemProvider {

    public static final String NAME = "aggregate";

    public BlockAggregate() {

        setSoundType(SoundType.SAND);
        setHardness(0.4f);
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockBase(this);
    }
}
