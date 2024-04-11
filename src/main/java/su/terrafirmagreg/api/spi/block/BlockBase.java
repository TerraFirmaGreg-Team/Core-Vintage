package su.terrafirmagreg.api.spi.block;

import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;


import org.jetbrains.annotations.Nullable;

public abstract class BlockBase extends Block implements IAutoReg {

    public BlockBase(Material materialIn) {
        super(materialIn);
    }

    public BlockBase(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Override
    public @Nullable ItemBlockBase getItemBlock() {
        return new ItemBlockBase(this);
    }
}
