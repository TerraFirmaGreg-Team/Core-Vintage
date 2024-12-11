package su.terrafirmagreg.modules.agriculture.api.types.berrybush.spi;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.modules.agriculture.api.types.berrybush.BerryBushType;

import net.dries007.tfc.api.util.IGrowingPlant;


public interface IBerryBushBlock extends IType<BerryBushType>, IBlockSettings, IGrowingPlant {


}
