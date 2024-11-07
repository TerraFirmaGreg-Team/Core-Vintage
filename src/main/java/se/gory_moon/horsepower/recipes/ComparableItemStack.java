package se.gory_moon.horsepower.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import se.gory_moon.horsepower.util.Utils;

public class ComparableItemStack
{
    private final ItemStack stack;

    public ComparableItemStack(ItemStack stack)
    {
        this.stack = stack;
    }

    @Override
    public int hashCode()
    {
        return Utils.getItemStackHashCode(stack);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof ComparableItemStack)) return false;

        ComparableItemStack that = (ComparableItemStack) o;

        return this.hashCode() == that.hashCode() &&
            (that.stack.getMetadata() == OreDictionary.WILDCARD_VALUE ? stack.getItem() == that.stack.getItem() : stack.isItemEqual(that.stack));
    }

    @Override
    public String toString()
    {
        return stack.toString();
    }
}