package su.terrafirmagreg.modules.plant.api.types.variant.item;

import su.terrafirmagreg.api.base.item.spi.IItemSettings;
import su.terrafirmagreg.data.lib.types.type.IType;
import su.terrafirmagreg.data.lib.types.variant.IVariant;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;

public interface IPlantItem extends IType<PlantType>, IVariant<PlantItemVariant>, IItemSettings {


}
