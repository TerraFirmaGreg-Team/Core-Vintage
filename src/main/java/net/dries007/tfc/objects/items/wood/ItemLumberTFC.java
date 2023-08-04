package net.dries007.tfc.objects.items.wood;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.tree.Tree;
import net.dries007.tfc.api.types.wood.Wood;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemLumberTFC extends ItemTFC {
    private static final Map<Wood, ItemLumberTFC> MAP = new HashMap<>();
    public final Wood wood;

    public ItemLumberTFC(Wood wood) {
        this.wood = wood;
        if (MAP.put(wood, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        OreDictionaryHelper.register(this, "lumber");
        //noinspection ConstantConditions
        OreDictionaryHelper.register(this, "lumber", wood.getName());
    }

    public static ItemLumberTFC get(Tree wood) {
        return MAP.get(wood);
    }

    public static ItemStack get(Tree wood, int amount) {
        return new ItemStack(MAP.get(wood), amount);
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
