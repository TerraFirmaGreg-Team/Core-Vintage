package su.terrafirmagreg.api.spi.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;


import net.dries007.tfc.api.capability.size.IItemSize;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public abstract class BaseItem extends Item implements ISettingsItem {

    protected final Settings settings;

    public BaseItem(Settings settings) {

        this.settings = settings;

        setMaxStackSize(settings.maxCount);
        setMaxDamage(settings.maxDamage);
        setTranslationKey(settings.translationKey);
    }

    public BaseItem() {
        this.settings = Settings.of();

        setMaxStackSize(settings.maxCount);
        setMaxDamage(settings.maxDamage);
        setTranslationKey(settings.translationKey);
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return settings.rarity;
    }

    /**
     * This should NOT be overridden except for VERY SPECIAL cases If an item needs to not stack, i.e. small vessels, override {@link IItemSize#canStack(ItemStack)} If an item
     * needs a variable stack size, override {@link IItemSize#getWeight(ItemStack)} / {@link IItemSize#getSize(ItemStack)} and return a different value to get a different stack
     * size
     */
    @Override
    public int getItemStackLimit(@NotNull ItemStack stack) {
        return getStackSize(stack);
    }

    public boolean canStack(ItemStack stack) {
        return settings.canStack;
    }

}
