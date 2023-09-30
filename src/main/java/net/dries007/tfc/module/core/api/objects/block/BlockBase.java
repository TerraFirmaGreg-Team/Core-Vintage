package net.dries007.tfc.module.core.api.objects.block;

import net.dries007.tfc.module.core.api.util.IItemProvider;
import net.dries007.tfc.module.core.api.objects.block.itemblocks.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

import javax.annotation.Nullable;

public abstract class BlockBase extends Block implements IItemProvider {
    public BlockBase(Material materialIn) {
        super(materialIn);
    }

    public BlockBase(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockBase(this);
    }
}
