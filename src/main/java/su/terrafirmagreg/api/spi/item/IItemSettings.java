package su.terrafirmagreg.api.spi.item;

import su.terrafirmagreg.api.registry.provider.IAutoRegProvider;
import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public interface IItemSettings extends IAutoRegProvider {

    Settings getSettings();

    // Override Item methods

    default int getItemStackLimit(@NotNull ItemStack stack) {
        return getStackSize(stack);
    }

    default IRarity getForgeRarity(ItemStack stack) {
        return getSettings().getRarity();
    }

    default String getTranslationKey() {
        return "item." + getSettings().getTranslationKey();
    }

    default int getMaxDamage() {
        return getSettings().getMaxDamage();
    }

    default int getItemStackLimit() {
        return getSettings().getMaxCount();
    }

    // Override IOreDictProvider methods

    default void onRegisterOreDict() {
        if (!getSettings().getOreDict().isEmpty()) {
            for (var ore : getSettings().getOreDict()) {
                if (ore != null) OreDictUtils.register(getItem(), ore);
            }
            getSettings().getOreDict().clear();
        }
    }

    default Item getItem() {
        return (Item) this;
    }

    // Override IAutoRegProvider methods

    default String getRegistryKey() {
        return getSettings().getRegistryKey();
    }

    // Override IItemSize methods

    default Size getSize(ItemStack stack) {
        return getSettings().getSize();
    }

    default Weight getWeight(ItemStack stack) {
        return getSettings().getWeight();
    }

    default boolean canStack(ItemStack stack) {
        return getSettings().isCanStack();
    }

    @Getter
    class Settings {

        protected List<Object[]> oreDict = new ArrayList<>();

        protected String registryKey;
        protected String translationKey;

        protected Size size;
        protected Weight weight;
        protected boolean canStack;
        protected int maxCount;
        protected int maxDamage;
        protected CreativeTabs tab;
        protected EnumRarity rarity;

        private Settings() {
            this.maxCount = 64;
            this.maxDamage = 0;
            this.rarity = EnumRarity.COMMON;

            this.size = Size.SMALL;
            this.weight = Weight.LIGHT;
            this.canStack = true;
        }

        public static Settings of() {
            return new Settings();
        }

        public Settings registryKey(String registryKey) {
            this.registryKey = registryKey;
            return this;
        }

        public Settings maxCount(int maxCount) {
            this.maxCount = maxCount;
            return this;
        }

        public Settings maxDamage(int damage) {
            this.maxDamage = damage;
            return this;
        }

        public Settings creativeTab(CreativeTabs tab) {
            this.tab = tab;
            return this;
        }

        public Settings rarity(EnumRarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public Settings translationKey(String translationKey) {
            this.translationKey = translationKey;
            return this;
        }

        public Settings addOreDict(Object... oreDict) {
            this.oreDict.add(oreDict);
            return this;
        }

        public Settings weight(Weight weight) {
            this.weight = weight;
            return this;
        }

        public Settings size(Size size) {
            this.size = size;
            return this;
        }

        public Settings notCanStack() {
            this.canStack = false;
            return this;
        }
    }
}
