package net.dries007.tfc.api.types.rock;

import net.dries007.tfc.api.types.rock.category.RockCategory;
import net.dries007.tfc.api.types.rock.type.Rock;

import javax.annotation.Nonnull;

public interface IRockItem {
    @Nonnull
    Rock getRock();

    @Nonnull
    default RockCategory getRockCategory() {
        return getRock().getRockCategory();
    }
}
