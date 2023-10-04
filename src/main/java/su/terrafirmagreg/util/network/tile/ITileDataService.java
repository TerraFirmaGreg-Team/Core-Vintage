package su.terrafirmagreg.util.network.tile;

import su.terrafirmagreg.util.network.tile.spi.ITileData;
import su.terrafirmagreg.util.network.tile.spi.TileEntityDataContainerBase;

import javax.annotation.Nullable;

public interface ITileDataService {

    int getServiceId();

    @Nullable
    TileDataTracker getTracker(TileEntityDataContainerBase tile);

    void register(TileEntityDataContainerBase tile, ITileData[] data);

    void update();
}
