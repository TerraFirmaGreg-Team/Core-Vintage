package su.terrafirmagreg.api.spi.item;

import su.terrafirmagreg.api.registry.IAutoReg;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

public interface ISettingsItem extends IAutoReg {

    default IRarity getForgeRarity(ItemStack stack) {
        return getSettings().rarity;
    }

    default Settings getSettings() {
        return Settings.of();
    }

    class Settings {

        Size size;
        Weight weight;
        boolean canStack;
        int maxCount;
        int maxDamage;
        CreativeTabs tab;
        EnumRarity rarity;
        String translationKey;

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

        public Settings maxCount(int count) {
            maxCount = count;
            return this;
        }

        public Settings maxDamage(int damage) {
            maxDamage = damage;
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
