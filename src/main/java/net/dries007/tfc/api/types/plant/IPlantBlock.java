package net.dries007.tfc.api.types.plant;

import net.dries007.tfc.api.types.plant.type.PlantType;
import net.dries007.tfc.api.types.plant.variant.block.PlantBlockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;

public interface IPlantBlock extends IHasModel, IItemProvider {
    PlantBlockVariant getBlockVariant();

    PlantType getType();
}
