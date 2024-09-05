package su.terrafirmagreg.modules.soil.api.types.variant.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.data.lib.types.type.IType;
import su.terrafirmagreg.data.lib.types.variant.IVariant;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

public interface ISoilBlock extends IType<SoilType>, IVariant<SoilBlockVariant>, IBlockSettings {

}
