package net.dries007.tfc.api.types.agriculture.crop.type;

import java.util.LinkedHashSet;
import java.util.Set;

public class CropType {
    private static final Set<CropType> CROP_TYPES = new LinkedHashSet<>();

    private final String name;

    public CropType(String name) {this.name = name;}

    /**
     * Возвращает набор всех типов еды.
     *
     * @return Набор всех типов еды.
     */
    public static Set<CropType> getCropTypes() {
        return CROP_TYPES;
    }

    /**
     * Возвращает строковое представление типов еды.
     *
     * @return Строковое представление типов еды.
     */
    @Override
    public String toString() {
        return name;
    }
}
