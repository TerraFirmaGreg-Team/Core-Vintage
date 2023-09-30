package net.dries007.tfc.module.core.objects.items;

import net.dries007.tfc.api.capability.damage.IDamageResistance;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.module.core.api.util.IArmorMaterialTFC;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemArmorTFC extends ItemArmor implements IItemSize, IDamageResistance {
    private final IArmorMaterialTFC armorMaterial;

    public ItemArmorTFC(IArmorMaterialTFC armorMaterial, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(armorMaterial.getMaterial(), renderIndexIn, equipmentSlotIn);
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
