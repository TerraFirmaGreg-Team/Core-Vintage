package net.dries007.tfc.objects.items.wood;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemWoodLumber extends ItemTFC {
    private static final Map<WoodType, ItemWoodLumber> MAP = new HashMap<>();
    public final WoodType woodType;

    public ItemWoodLumber(WoodType woodType) {
        this.woodType = woodType;
        if (MAP.put(woodType, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        OreDictionaryHelper.register(this, "lumber");
        //noinspection ConstantConditions
        OreDictionaryHelper.register(this, "lumber", woodType.toString());
    }

    public static ItemWoodLumber get(WoodType woodType) {
        return MAP.get(woodType);
    }

    public static ItemStack get(WoodType woodType, int amount) {
        return new ItemStack(MAP.get(woodType), amount);
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack) {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack) {
        return Weight.VERY_LIGHT;
    }
}
