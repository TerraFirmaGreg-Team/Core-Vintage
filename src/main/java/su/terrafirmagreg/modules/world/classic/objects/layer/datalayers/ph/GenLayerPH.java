package su.terrafirmagreg.modules.world.classic.objects.layer.datalayers.ph;

import su.terrafirmagreg.modules.world.classic.DataLayerClassic;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerBase;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerFuzzyZoom;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerSmooth;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerZoom;

public abstract class GenLayerPH extends GenLayerBase {

    public static final int MIN = DataLayerClassic.PH_ACID_HIGH.layerID;
    public static final int MAX = DataLayerClassic.PH_ALKALINE_HIGH.layerID;

    public GenLayerPH(long par1) {
        super(par1);
    }

    public static GenLayerBase initializePH(long seed) {
        GenLayerBase continent = new GenLayerPHInit(1L);
        continent = new GenLayerAddPH(1L, continent);
        continent = new GenLayerFuzzyZoom(2000L, continent);
        continent = new GenLayerAddPH(1L, continent);
        continent = new GenLayerZoom(2001L, continent);
        continent = new GenLayerAddPH(2L, continent);
        continent = new GenLayerPHMix(88L, continent);
        continent = new GenLayerZoom(2002L, continent);
        continent = new GenLayerAddPH(3L, continent);
        continent = new GenLayerZoom(2003L, continent);
        continent = new GenLayerAddPH(4L, continent);
        continent = GenLayerZoom.magnify(1000L, continent, 2);
        continent = new GenLayerSmooth(1000L, continent);
        continent = new GenLayerPHMix(1000, continent);
        continent = new GenLayerZoom(1000, continent);
        continent = new GenLayerZoom(1000 + 1, continent);
        continent = new GenLayerSmooth(1000L, continent);
        continent.initWorldGenSeed(seed);
        return continent;
    }
}
