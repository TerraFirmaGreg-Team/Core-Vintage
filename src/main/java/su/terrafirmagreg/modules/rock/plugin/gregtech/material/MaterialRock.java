package su.terrafirmagreg.modules.rock.plugin.gregtech.material;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;


import gregtech.api.unification.material.Material;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

public class MaterialRock extends Material {

    private static final AtomicInteger idCounter = new AtomicInteger(32100);

    protected MaterialRock(@NotNull ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public static class Builder extends Material.Builder {

        public Builder(@NotNull String name) {
            super(idCounter.getAndIncrement(), ModUtils.resource(name));

        }
    }
}
