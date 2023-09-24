package net.dries007.tfc.api.util;

import net.dries007.tfc.Tags;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static net.minecraft.item.Item.getItemFromBlock;


public class TFCCreativeTab extends CreativeTabs {
    private final Item item;

    public TFCCreativeTab(String label, Block block) {
        this(label, getItemFromBlock(block));
    }

    public TFCCreativeTab(String label, String icon) {
        super(Tags.MOD_ID + "." + label);
        this.item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(icon));
    }

    public TFCCreativeTab(String label, Item item) {
        super(Tags.MOD_ID + "." + label);
        this.item = item;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public ItemStack createIcon() {
        //noinspection ConstantConditions
        var stack = new ItemStack(item);
        if (!stack.isEmpty()) {
            // Food stacks shouldn't rot in creative tabs, and these are created on demand instead of beforehand and cached
            CapabilityFood.setStackNonDecaying(stack);
            return stack;
        }
        TerraFirmaCraft.LOGGER.error("[Please inform developers] No icon stack for creative tab {}", getTabLabel());
        return new ItemStack(Items.STICK);
    }
}

