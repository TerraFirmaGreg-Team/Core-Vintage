package net.dries007.tfc.api.types;

import net.minecraft.item.ItemArmor;

import su.terrafirmagreg.api.capabilities.damage.spi.DamageType;


import org.jetbrains.annotations.NotNull;

/**
 * @see net.dries007.tfc.objects.ArmorMaterialTFC
 */
public interface IArmorMaterialTFC {

    /**
     * Returns the crushing modifier this armor has
     *
     * @return float value with the modifier. To check how damage calculation is done, see {@link DamageType}
     */
    float getCrushingModifier();

    /**
     * Returns the crushing modifier this armor has
     *
     * @return float value with the modifier. To check how damage calculation is done, see {@link DamageType}
     */
    float getPiercingModifier();

    /**
     * Returns the crushing modifier this armor has
     *
     * @return float value with the modifier. To check how damage calculation is done, see {@link DamageType}
     */
    float getSlashingModifier();

    /**
     * The enum(register one using forge {@link net.minecraftforge.common.util.EnumHelper}) of this armor
     *
     * @return Vanilla "extended" ArmorMaterial
     */
    @NotNull
    ItemArmor.ArmorMaterial getMaterial();
}
