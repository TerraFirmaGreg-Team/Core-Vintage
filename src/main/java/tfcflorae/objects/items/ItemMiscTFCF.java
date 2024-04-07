package tfcflorae.objects.items;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")
//public class ItemMiscTFCF extends ItemMisc
public class ItemMiscTFCF extends ItemTFCF implements IItemSize {

    private final Size size;
    private final Weight weight;

    public ItemMiscTFCF(Size size, Weight weight, Object... oreNameParts) {
        this(size, weight);

        for (Object obj : oreNameParts) {
            if (obj instanceof Object[])
                OreDictionaryHelper.register(this, (Object[]) obj);
            else
                OreDictionaryHelper.register(this, obj);
        }
    }

    public ItemMiscTFCF(Size size, Weight weight) {
        this.size = size;
        this.weight = weight;
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack stack) {
        return size;
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return weight;
    }
}
