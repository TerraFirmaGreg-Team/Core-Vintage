package net.dries007.tfc.api.types.rock.util;

import net.dries007.tfc.api.types.rock.category.RockCategory;
import net.dries007.tfc.api.types.rock.type.RockType;

import javax.annotation.Nonnull;

public interface IRockItem {
    @Nonnull
    RockType getRockType();

    @Nonnull
    default RockCategory getRockCategory() {
        return getRockType().getRockCategory();
    }
}
