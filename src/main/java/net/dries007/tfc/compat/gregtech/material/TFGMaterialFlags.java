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

    /**
     * Идентифицирует материалы, из которых можно отлить оголовье того или иного тула.
     * */
    public static final MaterialFlag TOOL_MATERIAL_CAN_BE_UNMOLDED = new MaterialFlag.Builder("tool_item_can_be_unmolded")
            .requireProps(TFGPropertyKey.HEAT)
            .build();
}
