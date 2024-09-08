package su.terrafirmagreg.modules.soil.api.types.variant.item;

import su.terrafirmagreg.api.base.item.spi.IItemSettings;
import su.terrafirmagreg.data.lib.types.type.IType;
import su.terrafirmagreg.data.lib.types.variant.IVariant;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

public interface ISoilItem extends IType<SoilType>, IVariant<SoilItemVariant>, IItemSettings {


}
