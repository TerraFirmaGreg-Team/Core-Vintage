package su.terrafirmagreg.framework.manager.registry.base.enchantment.api;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.enchantment.api.IEnchantmentEntry.Settings;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import lombok.Getter;

public interface IEnchantmentEntry extends IRegistryEntry<Settings, Enchantment> {

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
