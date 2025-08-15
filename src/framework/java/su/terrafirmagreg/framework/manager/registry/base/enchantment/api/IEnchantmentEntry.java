package su.terrafirmagreg.framework.manager.registry.base.enchantment.api;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.enchantment.api.IEnchantmentEntry.EnchantmentSettings;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface IEnchantmentEntry extends IRegistryEntry<EnchantmentSettings, Enchantment> {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  class EnchantmentSettings extends RegistrySettings<EnchantmentSettings> {

    protected Enchantment.Rarity rarity;
    protected EnumEnchantmentType type;
    protected EntityEquipmentSlot[] slots;

    public static EnchantmentSettings of() {
      return new EnchantmentSettings();
    }

    public EnchantmentSettings rarityIn(Enchantment.Rarity rarityIn) {
      this.rarity = rarityIn;
      return this.self();
    }

    public EnchantmentSettings type(EnumEnchantmentType typeIn) {
      this.type = typeIn;
      return this.self();
    }

    public EnchantmentSettings slots(EntityEquipmentSlot... slotsIn) {
      this.slots = slotsIn;
      return this.self();
    }

  }
}
