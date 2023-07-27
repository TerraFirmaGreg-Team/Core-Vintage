package net.dries007.tfc.compat.gregtech.material;

import gregtech.api.unification.material.info.MaterialFlag;

public class TFGMaterialFlags {
    /**
     * Идентифицирует материалы, которые должны иметь наковальни.
     * */
    public static final MaterialFlag GENERATE_ANVIL = new MaterialFlag.Builder("generate_anvil")
            .requireProps(TFGPropertyKey.HEAT)
            .build();

    /**
     * Идентифицирует материалы, которые не должны иметь расширенных рецептов.
     * */
    public static final MaterialFlag UNUSABLE = new MaterialFlag.Builder("unusable")
            .requireProps(TFGPropertyKey.HEAT)
            .build();
}
