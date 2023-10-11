package su.terrafirmagreg.api.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static su.terrafirmagreg.api.Tags.MOD_ID;

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

            return stack;
        }
        return new ItemStack(Items.STICK);
    }
}
