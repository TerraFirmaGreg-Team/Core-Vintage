package net.dries007.tfc.api.types.wood.block.variant;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WoodBlockVariant {

    private static final HashSet<WoodBlockVariant> woodBlockVariants = new HashSet<>();


    public WoodBlockVariant(@Nonnull String name) {
    }


    public static List<WoodBlockVariant> getAllWoodBlockTypes() {
        return new ArrayList<>(woodBlockVariants);
    }
}
