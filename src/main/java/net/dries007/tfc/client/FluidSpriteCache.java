package net.dries007.tfc.client;

import su.terrafirmagreg.api.util.GameUtils;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fluids.Fluid;

import java.util.HashMap;
import java.util.Map;

public class FluidSpriteCache {

  private static final Map<Fluid, TextureAtlasSprite> CACHESTILL = new HashMap<>();
  private static final Map<Fluid, TextureAtlasSprite> CACHEFLOWING = new HashMap<>();

  public static TextureAtlasSprite getStillSprite(Fluid fluid) {
    TextureAtlasSprite sprite = CACHESTILL.get(fluid);

    if (sprite == null) {
      sprite = GameUtils.getTextureMapBlocks().getAtlasSprite(fluid.getStill().toString());
      CACHESTILL.put(fluid, sprite);
    }

    return sprite;
  }

  public static TextureAtlasSprite getFlowingSprite(Fluid fluid) {
    TextureAtlasSprite sprite = CACHEFLOWING.get(fluid);

    if (sprite == null) {
      sprite = GameUtils.getTextureMapBlocks().getAtlasSprite(fluid.getFlowing().toString());
      CACHEFLOWING.put(fluid, sprite);
    }

    return sprite;
  }

  public static void clear() {
    CACHEFLOWING.clear();
    CACHESTILL.clear();
  }
}
