package su.terrafirmagreg.modules.world.classic.objects.layer;

import su.terrafirmagreg.api.base.biome.BaseBiome;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.rock.api.types.category.RockCategory;
import su.terrafirmagreg.modules.world.classic.init.BiomesWorld;
import su.terrafirmagreg.modules.world.classic.objects.layer.biome.GenLayerAddIsland;
import su.terrafirmagreg.modules.world.classic.objects.layer.biome.GenLayerBiome;
import su.terrafirmagreg.modules.world.classic.objects.layer.biome.GenLayerBiomeEdge;
import su.terrafirmagreg.modules.world.classic.objects.layer.biome.GenLayerDeepOcean;
import su.terrafirmagreg.modules.world.classic.objects.layer.biome.GenLayerIsland;
import su.terrafirmagreg.modules.world.classic.objects.layer.biome.GenLayerLakes;
import su.terrafirmagreg.modules.world.classic.objects.layer.biome.GenLayerShore;
import su.terrafirmagreg.modules.world.classic.objects.layer.datalayers.rock.GenLayerRockInit;
import su.terrafirmagreg.modules.world.classic.objects.layer.datalayers.soil.GenLayerSoilInit;
import su.terrafirmagreg.modules.world.classic.objects.layer.datalayers.stability.GenLayerStabilityInit;
import su.terrafirmagreg.modules.world.classic.objects.layer.river.GenLayerRiver;
import su.terrafirmagreg.modules.world.classic.objects.layer.river.GenLayerRiverInit;
import su.terrafirmagreg.modules.world.classic.objects.layer.river.GenLayerRiverMix;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.fml.common.FMLCommonHandler;


import net.dries007.tfc.TerraFirmaCraft;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Function;
import java.util.function.IntFunction;

public abstract class GenLayerBase extends GenLayer {

  // Distinct colors for debug map gen
  private static final Color[] COLORS = new Color[]{
          new Color(0xFFB300),    // Vivid Yellow
          new Color(0x803E75),    // Strong Purple
          new Color(0xFF6800),    // Vivid Orange
          new Color(0xA6BDD7),    // Very Light Blue
          new Color(0xC10020),    // Vivid Red
          new Color(0xCEA262),    // Grayish Yellow
          new Color(0x817066),    // Medium Gray
          new Color(0x007D34),    // Vivid Green
          new Color(0xF6768E),    // Strong Purplish Pink
          new Color(0x00538A),    // Strong Blue
          new Color(0xFF7A5C),    // Strong Yellowish Pink
          new Color(0x53377A),    // Strong Violet
          new Color(0xFF8E00),    // Vivid Orange Yellow
          new Color(0xB32851),    // Strong Purplish Red
          new Color(0xF4C800),    // Vivid Greenish Yellow
          new Color(0x7F180D),    // Strong Reddish Brown
          new Color(0x93AA00),    // Vivid Yellowish Green
          new Color(0x593315),    // Deep Yellowish Brown
          new Color(0xF13A13),    // Vivid Reddish Orange
          new Color(0x232C16),    // Dark Olive Green
  };
  // Doing this lookup only once is quite a bit faster.
  protected final int oceanID;
  protected final int plainsID;
  protected final int highPlainsID;
  protected final int deepOceanID;
  protected final int lakeID;
  protected final int riverID;
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
  protected final int cragID;
  protected final int mesaID;
  protected final int mesaPlateauID;
  protected final int mesaBryceID;
  protected final int mesaPlateauMID;
  protected long worldGenSeed;
  protected long chunkSeed;

  public GenLayerBase(long seed) {
    super(seed);
    this.oceanID = Biome.getIdForBiome(BiomesWorld.OCEAN);
    this.plainsID = Biome.getIdForBiome(BiomesWorld.PLAINS);
    this.highPlainsID = Biome.getIdForBiome(BiomesWorld.HIGH_PLAINS);
    this.deepOceanID = Biome.getIdForBiome(BiomesWorld.DEEP_OCEAN);
    this.lakeID = Biome.getIdForBiome(BiomesWorld.LAKE);
    this.riverID = Biome.getIdForBiome(BiomesWorld.RIVER);
    this.swamplandID = Biome.getIdForBiome(BiomesWorld.SWAMPLAND);
    this.highHillsID = Biome.getIdForBiome(BiomesWorld.HIGH_HILLS);
    this.highHillsEdgeID = Biome.getIdForBiome(BiomesWorld.HIGH_HILLS_EDGE);
    this.rollingHillsID = Biome.getIdForBiome(BiomesWorld.ROLLING_HILLS);
    this.beachID = Biome.getIdForBiome(BiomesWorld.BEACH);
    this.gravelBeachID = Biome.getIdForBiome(BiomesWorld.GRAVEL_BEACH);
    this.mountainsID = Biome.getIdForBiome(BiomesWorld.MOUNTAINS);
    this.mountainsEdgeID = Biome.getIdForBiome(BiomesWorld.MOUNTAINS_EDGE);
    this.flatlandsID = Biome.getIdForBiome(BiomesWorld.FLATLANDS);
    this.fieldsID = Biome.getIdForBiome(BiomesWorld.FIELDS);
    this.meadowsID = Biome.getIdForBiome(BiomesWorld.MEADOWS);
    this.bayouID = Biome.getIdForBiome(BiomesWorld.BAYOU);
    this.mangroveID = Biome.getIdForBiome(BiomesWorld.MANGROVE);
    this.marshID = Biome.getIdForBiome(BiomesWorld.MARSH);
    this.cragID = Biome.getIdForBiome(BiomesWorld.CRAG);
    this.mesaID = Biome.getIdForBiome(BiomesWorld.MESA);
    this.mesaPlateauID = Biome.getIdForBiome(BiomesWorld.MESA_PLATEAU);
    this.mesaBryceID = Biome.getIdForBiome(BiomesWorld.MESA_BRYCE);
    this.mesaPlateauMID = Biome.getIdForBiome(BiomesWorld.MESA_PLATEAU_M);
  }

  public static GenLayerBase[] initializeBiomes(long seed) {
    // Continent generator
    GenLayerBase continent = new GenLayerIsland(1L);
    continent = new GenLayerFuzzyZoom(2000L, continent);
    continent = new GenLayerAddIsland(1L, continent);
    continent = new GenLayerZoom(2001L, continent);
    continent = new GenLayerAddIsland(2L, continent);
    continent = new GenLayerZoom(2002L, continent);
    continent = new GenLayerAddIsland(3L, continent);
    continent = new GenLayerZoom(2003L, continent);
    continent = new GenLayerAddIsland(4L, continent);
    continent = new GenLayerDeepOcean(4L, continent);
    // At this point, the output of continent only contains PLAINS, OCEAN and DEEP OCEAN.
    drawImageBiomes(1024, continent, "continent");

    // Create Biomes
    GenLayerBase biomes = new GenLayerBiome(200L, continent);
    biomes = new GenLayerLakes(200L, biomes);
    biomes = GenLayerZoom.magnify(1000L, biomes, 2);
    biomes = new GenLayerBiomeEdge(1000L, biomes);
    biomes = new GenLayerZoom(1000L, biomes);
    biomes = new GenLayerAddIsland(3L, biomes);
    biomes = new GenLayerZoom(1001L, biomes);
    biomes = new GenLayerShore(1000L, biomes);
    biomes = new GenLayerZoom(1002L, biomes);
    biomes = new GenLayerZoom(1003L, biomes);
    biomes = new GenLayerSmooth(1000L, biomes);
    // Now we have a full on biome map
    drawImageBiomes(1024, biomes, "biomes");

    // Create Rivers
    GenLayerBase rivers = GenLayerZoom.magnify(1000L, continent, 2);
    rivers = new GenLayerRiverInit(100L, rivers);
    rivers = GenLayerZoom.magnify(1000L, rivers, 6);
    rivers = new GenLayerRiver(1L, rivers);
    rivers = new GenLayerSmooth(1000L, rivers);
    // Rivers should only have plains or rivers.
    drawImageBiomes(1024, rivers, "rivers");

    // Mix the biomes and rivers
    GenLayerRiverMix riverMix = new GenLayerRiverMix(100L, biomes, rivers);
    riverMix.initWorldGenSeed(seed);
    drawImageBiomes(1024, riverMix, "mixed");

    GenLayerBase zoomed = GenLayerZoom.magnify(1000L, riverMix, 2);
    zoomed = new GenLayerSmooth(1001L, zoomed);
    zoomed.initWorldGenSeed(seed);
    drawImageBiomes(1024, zoomed, "zoomed");

    return new GenLayerBase[]{riverMix, zoomed};
  }

  public static void drawImageBiomes(int size, GenLayerBase genlayer, String name) {
    Function<Biome, Color> colorize = (x) -> x instanceof BaseBiome baseBiome
            ? baseBiome.getSettings().getDebugColour() : Color.BLACK;
    drawImage(size, genlayer, name, (i) -> colorize.apply(Biome.getBiomeForId(i)));
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

  public static void drawImage(int size, GenLayerBase genlayer, String name,
          IntFunction<Color> gibColor) {
    if (!ConfigCore.MISC.DEBUG.debugWorldGenSafe) {
      return;
    }
    if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
      return;
    }
    try {
      int[] ints = genlayer.getInts(-size / 2, -size / 2, size, size);
      BufferedImage outBitmap = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
      Graphics2D graphics = (Graphics2D) outBitmap.getGraphics();
      graphics.clearRect(0, 0, size, size);
      for (int x = 0; x < size; x++) {
        for (int z = 0; z < size; z++) {
          int i = ints[x * size + z];
          if (i == -1 || x == size / 2 || z == size / 2) {
            graphics.setColor(Color.WHITE);
          } else {
            graphics.setColor(gibColor.apply(i));
          }
          //noinspection SuspiciousNameCombination
          graphics.drawRect(z, x, 1, 1);
        }
      }
      name = "_" + name + ".png";
      TerraFirmaCraft.getLog().info("Worldgen debug image {}", name);
      ImageIO.write(outBitmap, "PNG", new File(name));
    } catch (Exception e) {
      TerraFirmaCraft.getLog().catching(e);
    }
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

  public static GenLayerBase initializeRock(long seed, RockCategory.Layer level,
          int rockLayerSize) {
    GenLayerBase layer = new GenLayerRockInit(1L, level);
    layer = new GenLayerFuzzyZoom(2000L, layer);
    layer = new GenLayerZoom(2001L, layer);
    layer = new GenLayerZoom(2002L, layer);
    layer = new GenLayerZoom(2003L, layer);
    layer = new GenLayerSmooth(1000L, layer);

    for (int zoomLevel = 0; zoomLevel < rockLayerSize; ++zoomLevel) {
      layer = new GenLayerZoom(1000 + zoomLevel, layer);
    }

    layer = new GenLayerSmooth(1000L, layer);
    layer = new GenLayerVoronoiZoom(10L, layer);
    layer.initWorldGenSeed(seed);
    drawImage(1024, layer, "rock" + level.name());
    return layer;
  }

  public static void drawImage(int size, GenLayerBase genlayer, String name) {
    drawImage(size, genlayer, name, (i) -> COLORS[i % COLORS.length]);
  }

  public static GenLayerBase initializeSoil(long seed, int soilLayerSize) {
    GenLayerBase layer = new GenLayerSoilInit(1L);
    layer = new GenLayerFuzzyZoom(2000L, layer);
    layer = new GenLayerZoom(2001L, layer);
    layer = new GenLayerZoom(2002L, layer);
    layer = new GenLayerZoom(2003L, layer);
    layer = new GenLayerSmooth(1000L, layer);

    for (int zoomLevel = 0; zoomLevel < soilLayerSize; ++zoomLevel) {
      layer = new GenLayerZoom(1000 + zoomLevel, layer);
    }

    layer = new GenLayerSmooth(1000L, layer);
    layer = new GenLayerVoronoiZoom(10L, layer);
    layer.initWorldGenSeed(seed);
    drawImage(1024, layer, "soil");
    return layer;
  }

  public static GenLayerBase initializeStability(long seed) {
    GenLayerBase continent = new GenLayerStabilityInit(1L + seed);
    continent = new GenLayerFuzzyZoom(2000L, continent);
    continent = new GenLayerZoom(2001L, continent);
    continent = new GenLayerZoom(2002L, continent);
    continent = new GenLayerZoom(2003L, continent);
    continent = GenLayerZoom.magnify(1000L, continent, 2);
    continent = new GenLayerSmooth(1000L, continent);
    continent = new GenLayerZoom(1000L, continent);
    continent = new GenLayerZoom(1001L, continent);
    continent = new GenLayerZoom(1002L, continent);
    continent = new GenLayerZoom(1003L, continent);
    continent = new GenLayerSmooth(1000L, continent);
    continent = new GenLayerVoronoiZoom(10L, continent);
    continent.initWorldGenSeed(seed);
    drawImage(1024, continent, "stability");
    return continent;
  }

  public boolean isOceanicBiome(int id) {
    return this.oceanID == id || this.deepOceanID == id || this.mangroveID == id;
  }

  public boolean isMountainBiome(int id) {
    return this.mountainsID == id || this.mountainsEdgeID == id || this.cragID == id;
  }

  public boolean isBeachBiome(int id) {
    return this.beachID == id || this.gravelBeachID == id;
  }
}
