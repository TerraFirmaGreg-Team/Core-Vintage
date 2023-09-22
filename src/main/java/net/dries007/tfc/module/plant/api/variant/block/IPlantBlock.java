package net.dries007.tfc.module.plant.api.variant.block;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.module.plant.api.type.PlantType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public interface IPlantBlock extends IHasModel, IItemProvider {
    PlantEnumVariant getBlockVariant();

    PlantType getType();

    /**
     * Возвращает расположение в реестре для данного деревянного блока.
     *
     * @return расположение в реестре
     */
    @Nonnull
    default String getName() {
        return String.format("plant.%s.%s", getBlockVariant(), getType());
    }

    /**
     * Возвращает расположение ресурса для данного деревянного блока.
     *
     * @return расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaCraft.getID(String.format("plant/%s", getBlockVariant()));
    }
}
