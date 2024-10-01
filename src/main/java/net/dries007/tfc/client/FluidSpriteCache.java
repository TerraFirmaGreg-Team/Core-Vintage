package net.dries007.tfc.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fluids.Fluid;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;

import java.util.Map;

public class FluidSpriteCache {

  private static final Map<Fluid, TextureAtlasSprite> CACHESTILL = new Reference2ObjectOpenHashMap<>();
  private static final Map<Fluid, TextureAtlasSprite> CACHEFLOWING = new Reference2ObjectOpenHashMap<>();

  public static TextureAtlasSprite getStillSprite(Fluid fluid) {
    TextureAtlasSprite sprite = CACHESTILL.get(fluid);

    if (sprite == null) {
      sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getStill().toString());
      CACHESTILL.put(fluid, sprite);
    }

    return sprite;
  }

  public static TextureAtlasSprite getFlowingSprite(Fluid fluid) {
    TextureAtlasSprite sprite = CACHEFLOWING.get(fluid);

    if (sprite == null) {
      sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFlowing().toString());
      CACHEFLOWING.put(fluid, sprite);
    }

    return sprite;
  }

  public static void clear() {
    CACHEFLOWING.clear();
    CACHESTILL.clear();
  }
}
