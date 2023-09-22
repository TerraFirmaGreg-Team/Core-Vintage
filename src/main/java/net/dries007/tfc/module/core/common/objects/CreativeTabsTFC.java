package net.dries007.tfc.module.core.common.objects;

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

    public static final CreativeTabs ROCK = new TFCCreativeTab("rock", "tfc:rock.raw.shale");
    public static final CreativeTabs SOIL = new TFCCreativeTab("soil", "tfc:soil.grass.silt");
    public static final CreativeTabs WOOD = new TFCCreativeTab("wood", "tfc:wood.planks.pine");
    public static final CreativeTabs CROP = new TFCCreativeTab("crop", "tfc:crop.seed.rice");
    public static final CreativeTabs METAL = new TFCCreativeTab("metal", "tfc:metal/anvil/red_steel");
    public static final CreativeTabs POTTERY = new TFCCreativeTab("pottery", "tfc:ceramics/fired/mold/ingot");
    public static final CreativeTabs FOOD = new TFCCreativeTab("food", "tfc:food/green_apple");
    public static final CreativeTabs MISC = new TFCCreativeTab("misc", "tfc:wand");
    public static final CreativeTabs FLORA = new TFCCreativeTab("flora", "tfc:plants/cactus/barrel_cactus");

    private static class TFCCreativeTab extends CreativeTabs {
        private final ResourceLocation iconResourceLocation;

        private TFCCreativeTab(String label, String icon) {
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
