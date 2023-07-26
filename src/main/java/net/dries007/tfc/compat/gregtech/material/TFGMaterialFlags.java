package net.dries007.tfc.compat.gregtech.material;

import gregtech.api.unification.material.info.MaterialFlag;

public class TFGMaterialFlags {
    public static final MaterialFlag GENERATE_ANVIL = new MaterialFlag.Builder("generate_anvil")
            .requireProps(TFGPropertyKey.HEAT)
            .build();
}
