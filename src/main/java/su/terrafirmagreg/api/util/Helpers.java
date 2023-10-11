package su.terrafirmagreg.api.util;

import com.google.common.base.Joiner;
import net.minecraft.util.ResourceLocation;

import static su.terrafirmagreg.api.Tags.MOD_ID;

public class Helpers {

    private static final Joiner JOINER_DOT = Joiner.on('.');

    public static ResourceLocation getID(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static String getEnumName(Enum<?> anEnum) {
        return JOINER_DOT.join(MOD_ID, "enum", anEnum.getDeclaringClass().getSimpleName(), anEnum).toLowerCase();
    }
}
