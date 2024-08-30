package su.terrafirmagreg.modules.world.classic.objects.layer.datalayers.tree;

import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.world.classic.DataLayerClassic;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerBase;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerFuzzyZoom;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerSmooth;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerVoronoiZoom;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerZoom;

import net.minecraftforge.fml.common.FMLCommonHandler;


import net.dries007.tfc.TerraFirmaCraft;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.IntFunction;

public abstract class GenRiverLayer extends GenLayerBase {

    private static boolean shouldDraw;

    public GenRiverLayer(long par1) {
        super(par1);
    }

    public static GenLayerBase initialize(long par0, DataLayerClassic[] regions) {
        GenLayerBase layer = new GenLayerTreeInit(1L + par0, regions);
        layer = new GenLayerFuzzyZoom(2000L, layer);
        layer = new GenLayerZoom(2001L, layer);
        layer = new GenLayerZoom(2002L, layer);
        layer = new GenLayerZoom(2003L, layer);
        layer = new GenLayerSmooth(1000L, layer);

        for (int zoomLevel = 0; zoomLevel < 4; ++zoomLevel) {
            layer = new GenLayerZoom(1000 + zoomLevel, layer);
            drawImage(1024, layer, "River " + (7 + zoomLevel));
        }

        GenLayerSmooth smoothedLayer = new GenLayerSmooth(1000L, layer);
        GenLayerVoronoiZoom voronoiLayer = new GenLayerVoronoiZoom(10L, smoothedLayer);
        shouldDraw = true;
        drawImage(1024, layer, "River Final");
        shouldDraw = false;
        voronoiLayer.initWorldGenSeed(par0);
        return voronoiLayer;
    }

    public static void drawImage(int size, GenLayerBase genlayer, String name, IntFunction<Color> gibColor) {
        if (ConfigCore.MISC.DEBUG.debugWorldGenSafe) {
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
