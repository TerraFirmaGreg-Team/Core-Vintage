package net.dries007.tfc.module.core.api.util;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.module.core.ModuleCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static net.dries007.tfc.Tags.MOD_ID;

public class CreativeTabBase extends CreativeTabs {
    public final ResourceLocation iconResourceLocation;

    public CreativeTabBase(String label, String icon) {
        super(MOD_ID + "." + label);
        iconResourceLocation = new ResourceLocation(icon);
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public ItemStack createIcon() {
        //noinspection ConstantConditions
        var stack = new ItemStack(ForgeRegistries.ITEMS.getValue(iconResourceLocation));
        if (!stack.isEmpty()) {
            // Food stacks shouldn't rot in creative tabs, and these are created on demand instead of beforehand and cached
            CapabilityFood.setStackNonDecaying(stack);
            return stack;
        }
        ModuleCore.LOGGER.error("[Please inform developers] No icon stack for creative tab {}", getTabLabel());
        return new ItemStack(Items.STICK);
    }
}
