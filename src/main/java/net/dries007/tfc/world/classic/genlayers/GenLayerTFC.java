/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.world.classic.genlayers;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.world.classic.biomes.BiomeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.genlayers.biome.*;
import net.dries007.tfc.world.classic.genlayers.datalayers.rock.GenLayerRockInit;
import net.dries007.tfc.world.classic.genlayers.datalayers.stability.GenLayerStabilityInit;
import net.dries007.tfc.world.classic.genlayers.ridge.GenLayerRidgeInitTFC;
import net.dries007.tfc.world.classic.genlayers.ridge.GenLayerRidgeMixTFC;
import net.dries007.tfc.world.classic.genlayers.ridge.GenLayerRidgeTFC;
import net.dries007.tfc.world.classic.genlayers.river.GenLayerRiverInitTFC;
import net.dries007.tfc.world.classic.genlayers.river.GenLayerRiverMixTFC;
import net.dries007.tfc.world.classic.genlayers.river.GenLayerRiverTFC;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Function;
import java.util.function.IntFunction;

public abstract class GenLayerTFC extends GenLayer {
    private static final Color[] COLORS = new Color[]{new Color(16757504), new Color(8404597), new Color(16738304), new Color(10927575), new Color(12648480), new Color(13541986), new Color(8482918), new Color(32052), new Color(16152206), new Color(21386), new Color(16743004), new Color(5453690), new Color(16748032), new Color(11741265), new Color(16041984), new Color(8329229), new Color(9677312), new Color(5845781), new Color(15809043), new Color(2305046)};
    protected final int oceanID;
    protected final int plainsID;
    protected final int highPlainsID;
    protected final int deepOceanID;
    protected final int lakeID;
    protected final int lakeshoreID;
    protected final int shoreID;
    protected final int riverID;
    protected final int riverBankID;
    protected final int riverSourceID;
    protected final int estuaryID;
    protected final int swamplandID;
    protected final int highHillsID;
    protected final int highHillsEdgeID;
    protected final int rollingHillsID;
    protected final int beachID;
    protected final int gravelBeachID;
    protected final int mountainsID;
    protected final int mountainsEdgeID;
    protected final int flatlandsID;
    protected final int fieldsID;
    protected final int meadowsID;
    protected final int bayouID;
    protected final int mangroveID;
    protected final int marshID;
    protected final int mountainRangeID;
    protected final int mountainRangeEdgeID;
    protected final int foothillsID;
    protected final int cragID;
    protected final int mesaID;
    protected final int mesaPlateauID;
    protected final int mesaBryceID;
    protected final int mesaPlateauMID;
    protected long worldGenSeed;
    protected long chunkSeed;

    public GenLayerTFC(long seed) {
        super(seed);
        this.oceanID = Biome.getIdForBiome(BiomesTFC.OCEAN);
        this.plainsID = Biome.getIdForBiome(BiomesTFC.PLAINS);
        this.highPlainsID = Biome.getIdForBiome(BiomesTFC.HIGH_PLAINS);
        this.deepOceanID = Biome.getIdForBiome(BiomesTFC.DEEP_OCEAN);
        this.lakeID = Biome.getIdForBiome(BiomesTFC.LAKE);
        this.lakeshoreID = Biome.getIdForBiome(BiomesTFC.LAKESHORE);
        this.shoreID = Biome.getIdForBiome(BiomesTFC.SHORE);
        this.riverID = Biome.getIdForBiome(BiomesTFC.RIVER);
        this.riverBankID = Biome.getIdForBiome(BiomesTFC.RIVERBANK);
        this.riverSourceID = Biome.getIdForBiome(BiomesTFC.RIVER_SOURCE);
        this.estuaryID = Biome.getIdForBiome(BiomesTFC.ESTUARY);
        this.swamplandID = Biome.getIdForBiome(BiomesTFC.SWAMPLAND);
        this.highHillsID = Biome.getIdForBiome(BiomesTFC.HIGH_HILLS);
        this.highHillsEdgeID = Biome.getIdForBiome(BiomesTFC.HIGH_HILLS_EDGE);
        this.rollingHillsID = Biome.getIdForBiome(BiomesTFC.ROLLING_HILLS);
        this.beachID = Biome.getIdForBiome(BiomesTFC.BEACH);
        this.gravelBeachID = Biome.getIdForBiome(BiomesTFC.GRAVEL_BEACH);
        this.mountainsID = Biome.getIdForBiome(BiomesTFC.MOUNTAINS);
        this.mountainsEdgeID = Biome.getIdForBiome(BiomesTFC.MOUNTAINS_EDGE);
        this.flatlandsID = Biome.getIdForBiome(BiomesTFC.FLATLANDS);
        this.fieldsID = Biome.getIdForBiome(BiomesTFC.FIELDS);
        this.meadowsID = Biome.getIdForBiome(BiomesTFC.MEADOWS);
        this.bayouID = Biome.getIdForBiome(BiomesTFC.BAYOU);
        this.mangroveID = Biome.getIdForBiome(BiomesTFC.MANGROVE);
        this.marshID = Biome.getIdForBiome(BiomesTFC.MARSH);
        this.mountainRangeID = Biome.getIdForBiome(BiomesTFC.MOUNTAIN_RANGE);
        this.mountainRangeEdgeID = Biome.getIdForBiome(BiomesTFC.MOUNTAIN_RANGE_EDGE);
        this.foothillsID = Biome.getIdForBiome(BiomesTFC.FOOTHILLS);
        this.cragID = Biome.getIdForBiome(BiomesTFC.CRAG);
        this.mesaID = Biome.getIdForBiome(BiomesTFC.MESA);
        this.mesaPlateauID = Biome.getIdForBiome(BiomesTFC.MESA_PLATEAU);
        this.mesaBryceID = Biome.getIdForBiome(BiomesTFC.MESA_BRYCE);
        this.mesaPlateauMID = Biome.getIdForBiome(BiomesTFC.MESA_PLATEAU_M);
    }

    public static GenLayerTFC[] initializeBiomes(long seed) {
        GenLayerTFC continent = new GenLayerIslandTFC(1L);
        continent = new GenLayerFuzzyZoomTFC(2000L, continent);
        continent = new GenLayerAddIslandTFC(1L, continent);
        continent = new GenLayerZoomTFC(2001L, continent);
        continent = new GenLayerAddIslandTFC(2L, continent);
        continent = new GenLayerZoomTFC(2002L, continent);
        continent = new GenLayerAddIslandTFC(3L, continent);
        continent = new GenLayerZoomTFC(2003L, continent);
        continent = new GenLayerAddIslandTFC(4L, continent);
        continent = new GenLayerDeepOcean(4L, continent);
        drawImageBiomes(1024, continent, "continent");

        GenLayerTFC biomes = new GenLayerBiomeTFC(200L, continent);
        biomes = new GenLayerLakes(200L, biomes);
        biomes = GenLayerZoomTFC.magnify(1000L, biomes, 2);
        biomes = new GenLayerBiomeEdge(1000L, biomes);

        for (int var7 = 0; var7 < 4; ++var7) {
            biomes = new GenLayerZoomTFC(1000 + var7, biomes);
            drawImage(1024, biomes, "18-" + var7 + " Zoom");
            if (var7 == 0) {
                biomes = new GenLayerAddIslandTFC(3L, (GenLayer) biomes);
            }

            if (var7 == 1) {
                biomes = new GenLayerBeachTFC(1000L, biomes);
                drawImage(1024, biomes, "18z Beach");
            }
        }

        biomes = new GenLayerZoomTFC(1001L, (GenLayerTFC) biomes);
        biomes = new GenLayerShoreTFC(1000L, biomes);
        biomes = new GenLayerLakeShore(1L, biomes);
        biomes = new GenLayerZoomTFC(1002L, biomes);
        biomes = new GenLayerZoomTFC(1003L, biomes);
        biomes = new GenLayerSmoothTFC(1000L, biomes);
        drawImageBiomes(1024, biomes, "biomes");

        GenLayerTFC riverCont = GenLayerZoomTFC.magnify(1000L, biomes, 1);
        drawImage(1024, riverCont, "9 ContinentsZoom");

        riverCont = new GenLayerRiverInitTFC(100L, riverCont);
        drawImage(1024, riverCont, "10 RiverInit");

        riverCont = GenLayerZoomTFC.magnify(1000L, riverCont, 0);
        drawImage(1024, riverCont, "11 RiverInitZoom");

        riverCont = new GenLayerRiverTFC(1L, riverCont);
        drawImage(1024, riverCont, "12 River");

        riverCont = new GenLayerSmoothTFC(1000L, riverCont);
        drawImage(1024, riverCont, "13 SmoothRiver");

        GenLayerSmoothTFC smoothContinent = new GenLayerSmoothTFC(1000L, biomes);
        drawImage(1024, smoothContinent, "Biome 19");

        GenLayerRiverMixTFC riverMix = new GenLayerRiverMixTFC(100L, smoothContinent, riverCont);
        drawImage(1024, riverMix, "Biome 20");

        GenLayerTFC mountainCont = GenLayerZoomTFC.magnify(1000L, biomes, 2);
        drawImage(1024, mountainCont, "1 ContinentsZoom Mountains");

        mountainCont = new GenLayerRidgeInitTFC(100L, mountainCont);
        drawImage(1024, mountainCont, "2 MountainInit");

        mountainCont = new GenLayerRidgeTFC(1L, mountainCont);
        drawImage(1024, mountainCont, "4 Mountains");

        mountainCont = new GenLayerSmoothTFC(1000L, mountainCont);
        drawImage(1024, mountainCont, "5 Mountain");

        GenLayerRidgeMixTFC mountainMix = new GenLayerRidgeMixTFC(100L, riverMix, mountainCont);
        drawImage(1024, mountainMix, "Biome 20 Mountains");

        GenLayerTFC finalCont = GenLayerZoomTFC.magnify(1000L, mountainMix, 2);
        drawImage(512, finalCont, "Biome 20-zoom");

        finalCont = new GenLayerSmoothTFC(1001L, finalCont);
        drawImage(512, finalCont, "Biome 21");

        riverMix.initWorldGenSeed(seed);
        mountainMix.initWorldGenSeed(seed);
        finalCont.initWorldGenSeed(seed);

        drawImage(1024, mountainMix, "Real Seed " + seed);
        return new GenLayerTFC[]{mountainMix, finalCont};
    }

    public static GenLayerTFC initializeRock(long seed, RockCategory.Layer level, int rockLayerSize) {
        GenLayerTFC layer = new GenLayerRockInit(1L, level);
        layer = new GenLayerFuzzyZoomTFC(2000L, layer);
        layer = new GenLayerZoomTFC(2001L, layer);
        layer = new GenLayerZoomTFC(2002L, layer);
        layer = new GenLayerZoomTFC(2003L, layer);
        layer = new GenLayerSmoothTFC(1000L, layer);

        for (int zoomLevel = 0; zoomLevel < rockLayerSize; ++zoomLevel) {
            layer = new GenLayerZoomTFC(1000 + zoomLevel, layer);
        }

        layer = new GenLayerSmoothTFC(1000L, layer);
        layer = new GenLayerVoronoiZoomTFC(10L, layer);
        layer.initWorldGenSeed(seed);

        drawImage(1024, layer, "rock" + level.name());

        return layer;
    }

    public static GenLayerTFC initializeStability(long seed) {
        GenLayerTFC continent = new GenLayerStabilityInit(1L + seed);
        continent = new GenLayerFuzzyZoomTFC(2000L, continent);
        continent = new GenLayerZoomTFC(2001L, continent);
        continent = new GenLayerZoomTFC(2002L, continent);
        continent = new GenLayerZoomTFC(2003L, continent);
        continent = GenLayerZoomTFC.magnify(1000L, continent, 2);
        continent = new GenLayerSmoothTFC(1000L, continent);
        continent = new GenLayerZoomTFC(1000L, continent);
        continent = new GenLayerZoomTFC(1001L, continent);
        continent = new GenLayerZoomTFC(1002L, continent);
        continent = new GenLayerZoomTFC(1003L, continent);
        continent = new GenLayerSmoothTFC(1000L, continent);
        continent = new GenLayerVoronoiZoomTFC(10L, continent);

        continent.initWorldGenSeed(seed);

        drawImage(1024, continent, "stability");
        return continent;
    }

    public static void drawImageBiomes(int size, GenLayerTFC genlayer, String name) {
        Function<Biome, Color> colorize = (x) -> x instanceof BiomeTFC ? ((BiomeTFC) x).debugColor : Color.BLACK;
        drawImage(size, genlayer, name, (i) -> colorize.apply(Biome.getBiomeForId(i)));
    }

    public static void drawImage(int size, GenLayerTFC genlayer, String name) {
        drawImage(size, genlayer, name, (i) -> COLORS[i % COLORS.length]);
    }

    public static void drawImage(int size, GenLayerTFC genlayer, String name, IntFunction<Color> gibColor) {
        if (ConfigTFC.General.DEBUG.debugWorldGenSafe) {
            if (!FMLCommonHandler.instance().getEffectiveSide().isClient()) {
                try {
                    int[] ints = genlayer.getInts(-size / 2, -size / 2, size, size);
                    BufferedImage outBitmap = new BufferedImage(size, size, 1);
                    Graphics2D graphics = (Graphics2D) outBitmap.getGraphics();
                    graphics.clearRect(0, 0, size, size);

                    for (int x = 0; x < size; ++x) {
                        for (int z = 0; z < size; ++z) {
                            int i = ints[x * size + z];
                            if (i != -1 && x != size / 2 && z != size / 2) {
                                graphics.setColor(gibColor.apply(i));
                            } else {
                                graphics.setColor(Color.WHITE);
                            }

                            graphics.drawRect(z, x, 1, 1);
                        }
                    }

                    name = "_" + name + ".png";
                    TerraFirmaCraft.getLog().info("Worldgen debug image {}", name);
                    ImageIO.write(outBitmap, "PNG", new File(name));
                } catch (Exception var10) {
                    TerraFirmaCraft.getLog().catching(var10);
                }

            }
        }
    }

    public static int validateInt(int[] array, int index) {
        return array[index];
    }

    public void initWorldGenSeed(long par1) {
        this.worldGenSeed = par1;
        if (this.parent != null) {
            this.parent.initWorldGenSeed(par1);
        }

        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
    }

    public void initChunkSeed(long par1, long par3) {
        this.chunkSeed = this.worldGenSeed;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par1;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par3;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par1;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par3;
    }

    protected int nextInt(int par1) {
        int var2 = (int) ((this.chunkSeed >> 24) % (long) par1);
        if (var2 < 0) {
            var2 += par1;
        }

        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return var2;
    }

    public boolean isWetBiome(int id) {
        return this.bayouID == id || this.mangroveID == id || this.marshID == id;
    }

    public boolean isFlatBiome(int id) {
        return this.plainsID == id || this.flatlandsID == id || this.fieldsID == id || this.meadowsID == id;
    }

    public boolean isRiverOrLakeBiome(int id) {
        return this.riverID == id || this.riverBankID == id || this.lakeID == id || this.lakeshoreID == id;
    }

    public boolean isOceanicBiome(int id) {
        return this.oceanID == id || this.deepOceanID == id || this.mangroveID == id || this.shoreID == id;
    }

    public boolean isMountainBiome(int id) {
        return this.mountainsID == id || this.mountainsEdgeID == id || this.cragID == id || this.mountainRangeID == id || this.mountainRangeEdgeID == id;
    }

    public boolean isBeachBiome(int id) {
        return this.beachID == id || this.gravelBeachID == id;
    }

    public boolean isWaterBiome(int id) {
        return this.isBeachBiome(id) || this.isOceanicBiome(id) || this.isRiverOrLakeBiome(id);
    }
}
