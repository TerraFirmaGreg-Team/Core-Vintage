package net.dries007.tfc.common.objects;

import net.dries007.tfc.Tags;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;


public final class CreativeTabsTFC {


    public static final CreativeTabs POTTERY_TAB = new TFCCreativeTab("pottery", "tfc:ceramics/fired/mold/ingot");
    public static final CreativeTabs FOOD_TAB = new TFCCreativeTab("food", "tfc:food/green_apple");
    public static final CreativeTabs MISC_TAB = new TFCCreativeTab("misc", "tfc:wand");


    public static class TFCCreativeTab extends CreativeTabs {
        private final ResourceLocation iconResourceLocation;

        public TFCCreativeTab(String label, String icon) {
            super(Tags.MOD_ID + "." + label);
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
            TerraFirmaCraft.LOGGER.error("[Please inform developers] No icon stack for creative tab {}", getTabLabel());
            return new ItemStack(Items.STICK);
        }
    }
}
