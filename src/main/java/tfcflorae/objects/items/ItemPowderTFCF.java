package tfcflorae.objects.items;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import tfcflorae.objects.PowderTFCF;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

@SuppressWarnings("WeakerAccess")

public class ItemPowderTFCF extends ItemTFCF {

    private static final EnumMap<PowderTFCF, ItemPowderTFCF> MAP = new EnumMap<>(PowderTFCF.class);
    private final PowderTFCF powder;

    public ItemPowderTFCF(PowderTFCF powder) {
        this.powder = powder;
        if (MAP.put(powder, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        OreDictionaryHelper.register(this, "dust", powder);
    }

    public static ItemPowderTFCF get(PowderTFCF powder) {
        return MAP.get(powder);
    }

    public static ItemStack get(PowderTFCF powder, int amount) {
        return new ItemStack(MAP.get(powder), amount);
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return Size.SMALL;
    }

    @Override
    public @NotNull Weight getWeight(ItemStack stack) {
        return Weight.VERY_LIGHT;
    }

    @NotNull
    public PowderTFCF getPowder() {
        return powder;
    }
}
