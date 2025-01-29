package net.dries007.tfc.objects.items;

import su.terrafirmagreg.api.data.ArmorMaterials;
import su.terrafirmagreg.modules.core.capabilities.damage.ICapabilityDamageResistance;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import javax.annotation.Nonnull;

public class ItemArmorTFC extends ItemArmor implements ICapabilitySize, ICapabilityDamageResistance {

  private final ArmorMaterials armorMaterial;

  public ItemArmorTFC(ArmorMaterials armorMaterial, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
    super(armorMaterial.getArmorMaterial(), renderIndexIn, equipmentSlotIn);
    this.armorMaterial = armorMaterial;
    setNoRepair();
  }

  @Override
  public float getCrushingModifier() {
    return armorMaterial.getCrushingModifier();
  }

  @Override
  public float getPiercingModifier() {
    return armorMaterial.getPiercingModifier();
  }

  @Override
  public float getSlashingModifier() {
    return armorMaterial.getSlashingModifier();
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack stack) {
    return Size.LARGE; // Stored in chests
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack stack) {
    return Weight.HEAVY; // Stacksize is already restricted to 1
  }

  @Override
  public boolean canStack(@Nonnull ItemStack stack) {
    return false;
  }
}
