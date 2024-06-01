package lyeoj.tfcthings.items;

import net.minecraft.item.ItemStack;

import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;


import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;

import org.jetbrains.annotations.NotNull;

public class ItemMetalBracing extends ItemTFC {

    public ItemMetalBracing() {
        this.setRegistryName("metal_bracing");
        this.setTranslationKey("metal_bracing");
        this.setCreativeTab(CreativeTabsTFC.CT_METAL);
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack itemStack) {
        return Size.SMALL;
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
        return Weight.LIGHT;
    }
}
