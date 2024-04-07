package su.terrafirmagreg.api.spi.block;

import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

import net.dries007.tfc.api.capability.size.IItemSize;

import org.jetbrains.annotations.Nullable;

public abstract class BlockBase extends Block implements IAutoReg, IItemSize {

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
