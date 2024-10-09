package su.terrafirmagreg.integration.gregtech;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;

import gregtech.api.unification.material.Material;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

public class BaseMaterial
  extends Material {

  private static final AtomicInteger idCounter = new AtomicInteger(32100);

  protected BaseMaterial(@NotNull ResourceLocation resourceLocation) {
    super(resourceLocation);
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public static class Builder extends Material.Builder {

    public Builder(@NotNull String name) {
      super(idCounter.getAndIncrement(), ModUtils.resource(name));

    }
  }
}
