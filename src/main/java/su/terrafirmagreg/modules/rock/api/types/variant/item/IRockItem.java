package su.terrafirmagreg.modules.rock.api.types.variant.item;

import su.terrafirmagreg.api.base.item.spi.IItemSettings;
import su.terrafirmagreg.data.lib.types.type.IType;
import su.terrafirmagreg.data.lib.types.variant.IVariant;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;

/**
 * Интерфейс, представляющий предмет породы.
 */
public interface IRockItem extends IType<RockType>, IVariant<RockItemVariant>, IItemSettings {


}
