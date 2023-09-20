package net.dries007.tfc.module.plant.api.variant.block;

import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.module.plant.api.type.PlantType;

public interface IPlantBlock extends IHasModel, IItemProvider {
    PlantEnumVariant getBlockVariant();

    PlantType getType();
}
