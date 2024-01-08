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

	public GenLayerTFC(long seed) {
		super(seed);
		this.oceanID = Biome.getIdForBiome(BiomesTFC.OCEAN);
		this.plainsID = Biome.getIdForBiome(BiomesTFC.PLAINS);
		this.highPlainsID = Biome.getIdForBiome(BiomesTFC.HIGH_PLAINS);
		this.deepOceanID = Biome.getIdForBiome(BiomesTFC.DEEP_OCEAN);
		this.lakeID = Biome.getIdForBiome(BiomesTFC.LAKE);
		this.riverID = Biome.getIdForBiome(BiomesTFC.RIVER);
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
		this.cragID = Biome.getIdForBiome(BiomesTFC.CRAG);
		this.mesaID = Biome.getIdForBiome(BiomesTFC.MESA);
		this.mesaPlateauID = Biome.getIdForBiome(BiomesTFC.MESA_PLATEAU);
		this.mesaBryceID = Biome.getIdForBiome(BiomesTFC.MESA_BRYCE);
		this.mesaPlateauMID = Biome.getIdForBiome(BiomesTFC.MESA_PLATEAU_M);
	}

	public static GenLayerTFC[] initializeBiomes(long seed) {
		// Continent generator
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
		// At this point, the output of continent only contains PLAINS, OCEAN and DEEP OCEAN.
		drawImageBiomes(1024, continent, "continent");

		// Create Biomes
		GenLayerTFC biomes = new GenLayerBiomeTFC(200L, continent);
		biomes = new GenLayerLakes(200L, biomes);
		biomes = GenLayerZoomTFC.magnify(1000L, biomes, 2);
		biomes = new GenLayerBiomeEdge(1000L, biomes);
		biomes = new GenLayerZoomTFC(1000L, biomes);
		biomes = new GenLayerAddIslandTFC(3L, biomes);
		biomes = new GenLayerZoomTFC(1001L, biomes);
		biomes = new GenLayerShoreTFC(1000L, biomes);
		biomes = new GenLayerZoomTFC(1002L, biomes);
		biomes = new GenLayerZoomTFC(1003L, biomes);
		biomes = new GenLayerSmoothTFC(1000L, biomes);
		// Now we have a full on biome map
		drawImageBiomes(1024, biomes, "biomes");

		// Create Rivers
		GenLayerTFC rivers = GenLayerZoomTFC.magnify(1000L, continent, 2);
		rivers = new GenLayerRiverInitTFC(100L, rivers);
		rivers = GenLayerZoomTFC.magnify(1000L, rivers, 6);
		rivers = new GenLayerRiverTFC(1L, rivers);
		rivers = new GenLayerSmoothTFC(1000L, rivers);
		// Rivers should only have plains or rivers.
		drawImageBiomes(1024, rivers, "rivers");

		// Mix the biomes and rivers
		GenLayerRiverMixTFC riverMix = new GenLayerRiverMixTFC(100L, biomes, rivers);
		riverMix.initWorldGenSeed(seed);
		drawImageBiomes(1024, riverMix, "mixed");

		GenLayerTFC zoomed = GenLayerZoomTFC.magnify(1000L, riverMix, 2);
		zoomed = new GenLayerSmoothTFC(1001L, zoomed);
		zoomed.initWorldGenSeed(seed);
		drawImageBiomes(1024, zoomed, "zoomed");

		return new GenLayerTFC[]{riverMix, zoomed};
	}

	public static GenLayerTFC initializeRock(long seed, RockCategory.Layer level, int rockLayerSize) {
		GenLayerTFC layer = new GenLayerRockInit(1L, level);
		layer = new GenLayerFuzzyZoomTFC(2000L, layer);
		layer = new GenLayerZoomTFC(2001L, layer);
		layer = new GenLayerZoomTFC(2002L, layer);
		layer = new GenLayerZoomTFC(2003L, layer);
		layer = new GenLayerSmoothTFC(1000L, layer);

		for (int zoomLevel = 0; zoomLevel < rockLayerSize; ++zoomLevel) {
			layer = new GenLayerZoomTFC((long) (1000 + zoomLevel), layer);
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
		if (!ConfigTFC.General.DEBUG.debugWorldGenSafe) return;
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) return;
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
