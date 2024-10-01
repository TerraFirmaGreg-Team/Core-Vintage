package su.terrafirmagreg.modules.plant.api.types.variant.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.data.lib.types.type.IType;
import su.terrafirmagreg.data.lib.types.variant.IVariant;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;

public interface IPlantBlock extends IType<PlantType>, IVariant<PlantBlockVariant>, IBlockSettings {
}
