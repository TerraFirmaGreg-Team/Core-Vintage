package su.terrafirmagreg.api.network.tile;

import su.terrafirmagreg.api.network.tile.spi.ITileData;
import su.terrafirmagreg.api.network.tile.spi.TileEntityDataContainerBase;

import org.jetbrains.annotations.Nullable;

public interface ITileDataService {

    int getServiceId();

    @Nullable
    TileDataTracker getTracker(TileEntityDataContainerBase tile);

    void register(TileEntityDataContainerBase tile, ITileData[] data);

    void update();
}
