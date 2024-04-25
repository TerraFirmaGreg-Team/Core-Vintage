package net.sharkbark.cellars.items;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;

import org.jetbrains.annotations.NotNull;

import static net.dries007.tfc.objects.CreativeTabsTFC.CT_METAL;

public class ItemToolHead extends ItemBase implements IMetalItem {

    private final Metal metal;

    public ItemToolHead(Metal metal, String name, String oreName) {
        super(name);
        this.metal = metal;

        setCreativeTab(CT_METAL);
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack arg0) {
        return Size.NORMAL;
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
        return Weight.MEDIUM;
    }

    @Override
    public Metal getMetal(ItemStack itemStack) {
        return metal;
    }

    @Override
    public int getSmeltAmount(ItemStack itemStack) {
        return 100;
    }
}
