package net.dries007.tfc.world.classic.genlayers.datalayers.tree;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.world.classic.DataLayer;
import net.dries007.tfc.world.classic.genlayers.*;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.IntFunction;

public abstract class GenRiverLayer extends GenLayerTFC {
	private static boolean shouldDraw;

	public GenRiverLayer(long par1) {
		super(par1);
	}

	public static GenLayerTFC initialize(long par0, DataLayer[] regions) {
		GenLayerTFC layer = new GenLayerTreeInit(1L + par0, regions);
		layer = new GenLayerFuzzyZoomTFC(2000L, layer);
		layer = new GenLayerZoomTFC(2001L, layer);
		layer = new GenLayerZoomTFC(2002L, layer);
		layer = new GenLayerZoomTFC(2003L, layer);
		layer = new GenLayerSmoothTFC(1000L, layer);

		for (int zoomLevel = 0; zoomLevel < 4; ++zoomLevel) {
			layer = new GenLayerZoomTFC(1000 + zoomLevel, layer);
			drawImage(1024, layer, "River " + (7 + zoomLevel));
		}

		GenLayerSmoothTFC smoothedLayer = new GenLayerSmoothTFC(1000L, layer);
		GenLayerVoronoiZoomTFC voronoiLayer = new GenLayerVoronoiZoomTFC(10L, smoothedLayer);
		shouldDraw = true;
		drawImage(1024, layer, "River Final");
		shouldDraw = false;
		voronoiLayer.initWorldGenSeed(par0);
		return voronoiLayer;
	}

	public static void drawImage(int size, GenLayerTFC genlayer, String name, IntFunction<Color> gibColor) {
		if (ConfigTFC.General.DEBUG.debugWorldGenSafe) {
			if (!FMLCommonHandler.instance().getEffectiveSide().isClient()) {
				if (shouldDraw) {
					try {
						int[] ints = genlayer.getInts(0, 0, size, size);
						BufferedImage outBitmap = new BufferedImage(size, size, 1);
						Graphics2D graphics = (Graphics2D) outBitmap.getGraphics();
						graphics.clearRect(0, 0, size, size);

						for (int x = 0; x < size; ++x) {
							for (int z = 0; z < size; ++z) {
								int id = ints[x * size + z];
								int color = (id * 8 << 16) + (id * 8 << 8) + id * 8;
								graphics.setColor(Color.getColor("", color));
								graphics.drawRect(x, z, 1, 1);
							}
						}

						name = "_" + name + ".png";
						TerraFirmaCraft.getLog().info("Worldgen debug image {}", name);
						ImageIO.write(outBitmap, "PNG", new File(name));
					} catch (Exception var11) {
						TerraFirmaCraft.getLog().catching(var11);
					}

				}
			}
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

	public abstract int[] getInts(int var1, int var2, int var3, int var4);
}
