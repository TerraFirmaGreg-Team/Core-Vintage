package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.api.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.api.capabilities.size.spi.Size;
import su.terrafirmagreg.api.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;


import org.jetbrains.annotations.NotNull;

public class BlockFireBrick extends Block implements ICapabilitySize {

    public BlockFireBrick() {
        super(Material.ROCK);
        setSoundType(SoundType.STONE);
        setHardness(1.0F);
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return Size.SMALL; // Stored everywhere
    }

    @Override
    public @NotNull Weight getWeight(ItemStack stack) {
        return Weight.LIGHT; // Stacksize = 32
    }
}
