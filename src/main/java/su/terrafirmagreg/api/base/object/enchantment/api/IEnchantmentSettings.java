package su.terrafirmagreg.api.base.object.enchantment.api;

import su.terrafirmagreg.api.base.object.enchantment.api.IEnchantmentSettings.Settings;
import su.terrafirmagreg.api.library.IBaseSettings;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import lombok.Getter;

public interface IEnchantmentSettings extends IBaseSettings<Settings> {

  @Getter
  class Settings extends BaseSettings<Settings> {

    Enchantment.Rarity rarity;
    EnumEnchantmentType type;
    EntityEquipmentSlot[] slots;

    protected Settings() {

    }

    public static Settings of() {
      return new Settings();
    }

    public Settings rarityIn(Enchantment.Rarity rarityIn) {
      this.rarity = rarityIn;
      return this;
    }

    public Settings type(EnumEnchantmentType typeIn) {
      this.type = typeIn;
      return this;
    }

    public Settings slots(EntityEquipmentSlot... slotsIn) {
      this.slots = slotsIn;
      return this;
    }

  }
}
