package su.terrafirmagreg.framework.manager.plugin.spi.gregtech.unification.material;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;

import gregtech.api.unification.material.Material;

import java.util.concurrent.atomic.AtomicInteger;

public class BuilderMaterial extends Material.Builder {

  private static final AtomicInteger idCounter = new AtomicInteger(32100);

  public BuilderMaterial(String name) {
    this(ModUtils.resource(name));

  }

  public BuilderMaterial(ResourceLocation resourceLocation) {
    super(idCounter.getAndIncrement(), resourceLocation);

  }

}
