package net.dries007.tfc.common.objects.blocks;

import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.common.objects.blocks.itemblocks.ItemBlockTFC;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

import javax.annotation.Nullable;

public class TFCBlock extends Block implements IItemProvider {
    public TFCBlock(Material materialIn) {
        super(materialIn);
    }

    public TFCBlock(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }
}
