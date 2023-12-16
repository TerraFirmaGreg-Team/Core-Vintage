package com.buuz135.hotornot.config;

import net.minecraft.item.ItemStack;

public class HotLists
{
    public static boolean isRemoved(ItemStack stack)
    {
        String regName = stack.getItem().getRegistryName().toString();
        for (String s : HotConfig.ITEM_REMOVALS)
        {
            if (regName.equals(s))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isHot(ItemStack stack)
    {
        String regName = stack.getItem().getRegistryName().toString();
        for (String s : HotConfig.HOT_ITEM_ADDITIONS)
        {
            if (regName.equals(s))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isCold(ItemStack stack)
    {
        String regName = stack.getItem().getRegistryName().toString();
        for (String s : HotConfig.COLD_ITEM_ADDITIONS)
        {
            if (regName.equals(s))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isGaseous(ItemStack stack)
    {
        String regName = stack.getItem().getRegistryName().toString();
        for (String s : HotConfig.GASEOUS_ITEM_ADDITIONS)
        {
            if (regName.equals(s))
            {
                return true;
            }
        }
        return false;
    }
}