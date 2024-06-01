package su.terrafirmagreg.api.spi.item;

import su.terrafirmagreg.api.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.api.capabilities.size.spi.Size;
import su.terrafirmagreg.api.capabilities.size.spi.Weight;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;


import lombok.Getter;

@Getter
public abstract class BaseItem extends Item implements IItemSettings {

    protected final Settings settings;

    public BaseItem() {
        this.settings = Settings.of();
    }

    public IRarity getForgeRarity(ItemStack stack) {
        return getSettings().getRarity();
    }

    @Override
    public String getTranslationKey() {
        return getSettings().getTranslationKey() == null ? super.getTranslationKey() : "item." + getSettings().getTranslationKey();
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
     * This should NOT be overridden except for VERY SPECIAL cases If an item needs to not stack, i.e. small vessels, override {@link ICapabilitySize#canStack(ItemStack)} If an
     * item needs a variable stack size, override {@link ICapabilitySize#getWeight(ItemStack)} / {@link ICapabilitySize#getSize(ItemStack)} and return a different value to get a
     * different stack size
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
