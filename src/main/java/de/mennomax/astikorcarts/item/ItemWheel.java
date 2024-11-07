package de.mennomax.astikorcarts.item;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import de.mennomax.astikorcarts.AstikorCarts;
import de.mennomax.astikorcarts.init.ModCreativeTabs;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.ItemTFC;

public class ItemWheel extends ItemTFC
{
    public ItemWheel()
    {
        this.setRegistryName(AstikorCarts.MODID, "wheel");
        this.setUnlocalizedName(this.getRegistryName().toString());
        this.setCreativeTab(ModCreativeTabs.astikor);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack itemStack)
    {
        return Size.NORMAL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack itemStack)
    {
        return Weight.HEAVY;
    }
}