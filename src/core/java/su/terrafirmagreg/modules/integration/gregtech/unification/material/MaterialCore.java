package su.terrafirmagreg.modules.integration.gregtech.unification.material;


import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

import gregtech.api.unification.material.Material;

public class MaterialCore extends Material {

  private static final AtomicInteger idCounter = new AtomicInteger(32100);

  protected MaterialCore(@NotNull ResourceLocation resourceLocation) {
    super(resourceLocation);
  }

  public static MaterialCore.Builder builder(String name) {
    return new MaterialCore.Builder(name);
  }

  public static class Builder extends Material.Builder {

    public Builder(@NotNull String name) {
      super(idCounter.getAndIncrement(), ModUtils.resource(name));

    }
  }

}

