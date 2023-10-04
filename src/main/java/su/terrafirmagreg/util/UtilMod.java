package su.terrafirmagreg.util;

import net.minecraftforge.fml.common.Mod;

@Mod(
        modid = UtilMod.MOD_ID,
        version = UtilMod.VERSION,
        name = UtilMod.NAME,
        dependencies = UtilMod.DEPENDENCIES
)
public class UtilMod {

    public static final String MOD_ID = "athenaeum";
    public static final String VERSION = "@@VERSION@@";
    public static final String NAME = "Athenaeum";
    public static final String DEPENDENCIES = "after:crafttweaker;after:jei;";

}
