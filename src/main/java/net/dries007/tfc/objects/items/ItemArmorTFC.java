/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.items;

import su.terrafirmagreg.api.data.ArmorMaterials;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.damage.IDamageResistance;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import javax.annotation.Nonnull;

public class ItemArmorTFC extends ItemArmor implements IItemSize, IDamageResistance {

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
