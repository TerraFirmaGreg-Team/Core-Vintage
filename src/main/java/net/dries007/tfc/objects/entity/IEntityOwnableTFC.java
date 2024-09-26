package net.dries007.tfc.objects.entity;

import net.minecraft.entity.Entity;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface IEntityOwnableTFC {

  @Nullable
  UUID getOwnerId();

  @Nullable
  Entity getOwner();
}
