package su.terrafirmagreg.api.spi.entity;

import net.minecraft.entity.Entity;


import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface IEntityOwnable {

    @Nullable
    UUID getOwnerId();

    @Nullable
    Entity getOwner();
}
