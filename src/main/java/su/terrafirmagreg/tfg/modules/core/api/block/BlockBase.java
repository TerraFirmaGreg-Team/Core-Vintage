package su.terrafirmagreg.tfg.modules.core.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import su.terrafirmagreg.tfg.modules.core.api.block.itemblocks.ItemBlockBase;
import su.terrafirmagreg.tfg.modules.core.api.capability.size.IItemSize;
import su.terrafirmagreg.tfg.modules.core.api.capability.size.Size;
import su.terrafirmagreg.tfg.modules.core.api.capability.size.Weight;
import su.terrafirmagreg.tfg.modules.core.api.util.IItemProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static su.terrafirmagreg.tfg.modules.core.api.capability.size.Size.TINY;
import static su.terrafirmagreg.tfg.modules.core.api.capability.size.Weight.VERY_LIGHT;

public abstract class BlockBase extends Block implements IItemProvider, IItemSize {

    public BlockBase(Material materialIn) {
        super(materialIn);
    }

    public BlockBase(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Nullable
    @Override
    public ItemBlockBase getItemBlock() {
        return new ItemBlockBase(this);
    }


    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return TINY;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return VERY_LIGHT;
    }
}
