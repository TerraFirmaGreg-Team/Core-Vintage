/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.entity;

import net.minecraft.entity.Entity;

import javax.annotation.Nullable;
import java.util.UUID;

public interface IEntityOwnableTFC {
    @Nullable
    UUID getOwnerId();

    @Nullable
    Entity getOwner();
}
