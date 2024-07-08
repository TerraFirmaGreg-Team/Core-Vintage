package su.terrafirmagreg.modules.world.objects.layer.datalayers.drainage;

import su.terrafirmagreg.modules.world.classic.DataLayerClassic;
import su.terrafirmagreg.modules.world.objects.layer.GenLayerBase;
import su.terrafirmagreg.modules.world.objects.layer.GenLayerFuzzyZoom;
import su.terrafirmagreg.modules.world.objects.layer.GenLayerSmooth;
import su.terrafirmagreg.modules.world.objects.layer.GenLayerZoom;

public abstract class GenLayerDrainage extends GenLayerBase {

    public static final int MIN = DataLayerClassic.DRAINAGE_NONE.layerID;
    public static final int MAX = DataLayerClassic.DRAINAGE_VERY_GOOD.layerID;

    public GenLayerDrainage(long par1) {
        super(par1);
    }

    public static GenLayerBase initialize(long seed) {
        GenLayerBase continent = new GenLayerDrainageInit(1L);
        continent = new GenLayerAddDrainage(1L, continent);
        continent = new GenLayerFuzzyZoom(2000L, continent);
        continent = new GenLayerAddDrainage(1L, continent);
        continent = new GenLayerZoom(2001L, continent);
        continent = new GenLayerAddDrainage(2L, continent);
        continent = new GenLayerDrainageMix(88L, continent);
        continent = new GenLayerZoom(2002L, continent);
        continent = new GenLayerAddDrainage(3L, continent);
        continent = new GenLayerZoom(2003L, continent);
        continent = new GenLayerAddDrainage(4L, continent);
        continent = GenLayerZoom.magnify(1000L, continent, 2);
        continent = new GenLayerSmooth(1000L, continent);
        continent = new GenLayerDrainageMix(1000, continent);
        continent = new GenLayerZoom(1000, continent);
        continent = new GenLayerZoom(1001, continent);
        continent = new GenLayerSmooth(1000L, continent);
        continent.initWorldGenSeed(seed);
        drawImage(1024, continent, "drainage");
        return continent;
    }
}
