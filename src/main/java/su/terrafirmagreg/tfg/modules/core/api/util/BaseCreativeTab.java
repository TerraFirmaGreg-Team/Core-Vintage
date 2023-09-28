package su.terrafirmagreg.tfg.modules.core.api.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.tfg.modules.core.ModuleCore;
import su.terrafirmagreg.tfg.Tags;

import javax.annotation.Nonnull;


public class BaseCreativeTab extends CreativeTabs {
    private final ResourceLocation iconResourceLocation;

    public BaseCreativeTab(String label, String icon) {
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
//                CapabilityFood.setStackNonDecaying(stack);
            return stack;
        }
        ModuleCore.LOGGER.error("[Please inform developers] No icon stack for creative tab {}", getTabLabel());
        return new ItemStack(Items.STICK);
    }
}
