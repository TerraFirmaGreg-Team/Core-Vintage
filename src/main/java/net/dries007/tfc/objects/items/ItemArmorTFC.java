package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.damage.ICapabilityDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.types.IArmorMaterialTFC;

import org.jetbrains.annotations.NotNull;

public class ItemArmorTFC extends ItemArmor implements ICapabilitySize, ICapabilityDamageResistance {

    private final IArmorMaterialTFC materialTFC;

    public ItemArmorTFC(IArmorMaterialTFC materialTFC, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialTFC.getMaterial(), renderIndexIn, equipmentSlotIn);
        this.materialTFC = materialTFC;
        setNoRepair();
    }

    @Override
    public float getCrushingModifier() {
        return materialTFC.getCrushingModifier();
    }

    @Override
    public float getPiercingModifier() {
        return materialTFC.getPiercingModifier();
    }

    @Override
    public float getSlashingModifier() {
        return materialTFC.getSlashingModifier();
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack stack) {
        return Size.LARGE; // Stored in chests
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return Weight.HEAVY; // Stacksize is already restricted to 1
    }

    @Override
    public boolean canStack(@NotNull ItemStack stack) {
        return false;
    }
}
