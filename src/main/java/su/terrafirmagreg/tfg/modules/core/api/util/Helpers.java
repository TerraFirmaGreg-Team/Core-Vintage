package su.terrafirmagreg.tfg.modules.core.api.util;

import net.minecraft.util.ResourceLocation;

import static su.terrafirmagreg.tfg.Tags.MOD_ID;


public class Helpers {

    public static ResourceLocation getID(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
