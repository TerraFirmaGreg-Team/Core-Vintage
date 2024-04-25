package net.dries007.tfc.objects.items.rock;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ItemBrickTFC extends ItemTFC implements IRockObject {

    private static final Map<Rock, ItemBrickTFC> MAP = new HashMap<>();
    private final Rock rock;

    public ItemBrickTFC(Rock rock) {
        this.rock = rock;
        if (MAP.put(rock, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        OreDictionaryHelper.register(this, "brick");
        OreDictionaryHelper.register(this, "brick", rock.getRockCategory());
    }

    public static ItemBrickTFC get(Rock ore) {
        return MAP.get(ore);
    }

    public static ItemStack get(Rock ore, int amount) {
        return new ItemStack(MAP.get(ore), amount);
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return Size.SMALL; // Stored everywhere
    }

    @Override
    public @NotNull Weight getWeight(ItemStack stack) {
        return Weight.LIGHT; // Stacksize = 32
    }

    @NotNull
    @Override
    public Rock getRock(ItemStack stack) {
        return rock;
    }

    @NotNull
    @Override
    public RockCategory getRockCategory(ItemStack stack) {
        return rock.getRockCategory();
    }
}
