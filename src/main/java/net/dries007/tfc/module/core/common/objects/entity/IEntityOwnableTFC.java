package net.dries007.tfc.module.core.common.objects.entity;

import net.minecraft.entity.Entity;

import javax.annotation.Nullable;
import java.util.UUID;

public interface IEntityOwnableTFC {
    @Nullable
    UUID getOwnerId();

    @Nullable
    Entity getOwner();
}
