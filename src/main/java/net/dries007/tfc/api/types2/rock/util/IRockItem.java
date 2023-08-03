package net.dries007.tfc.api.types2.rock.util;

import javax.annotation.Nonnull;
import net.dries007.tfc.api.types2.rock.RockCategory;
import net.dries007.tfc.api.types2.rock.RockType;

public interface IRockItem {
    @Nonnull
    RockType getRockType();

    @Nonnull
    default RockCategory getRockCategory() {
        return getRockType().getRockCategory();
    }
}
