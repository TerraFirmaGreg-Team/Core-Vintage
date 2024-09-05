package su.terrafirmagreg.modules.rock.api.types.variant.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.data.lib.types.type.IType;
import su.terrafirmagreg.data.lib.types.variant.IVariant;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;

public interface IRockBlock extends IType<RockType>, IVariant<RockBlockVariant>, IBlockSettings {

}
