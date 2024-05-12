package su.terrafirmagreg.api.spi.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;


import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import lombok.Getter;

@Getter
public abstract class BaseItem extends Item implements ISettingsItem {

    protected final Settings settings;

    public BaseItem() {
        this.settings = Settings.of();
    }

    public IRarity getForgeRarity(ItemStack stack) {
        return getSettings().getRarity();
    }

    @Override
    public String getTranslationKey() {
        return "item." + getSettings().getTranslationKey();
    }

    @Override
    public int getMaxDamage() {
        return getSettings().getMaxDamage();
    }

    @Override
    public int getItemStackLimit() {
        return getSettings().getMaxCount();
    }

    /**
     * This should NOT be overridden except for VERY SPECIAL cases If an item needs to not stack, i.e. small vessels, override {@link IItemSize#canStack(ItemStack)} If an item
     * needs a variable stack size, override {@link IItemSize#getWeight(ItemStack)} / {@link IItemSize#getSize(ItemStack)} and return a different value to get a different stack
     * size
     */
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getStackSize(stack);
    }

    @Override
    public Size getSize(ItemStack stack) {
        return getSettings().getSize();
    }

    @Override
    public Weight getWeight(ItemStack stack) {
        return getSettings().getWeight();
    }

    @Override
    public boolean canStack(ItemStack stack) {
        return getSettings().isCanStack();
    }

}
