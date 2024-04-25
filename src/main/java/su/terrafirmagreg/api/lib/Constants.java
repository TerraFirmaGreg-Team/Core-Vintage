package su.terrafirmagreg.api.lib;

import su.terrafirmagreg.Tags;
import su.terrafirmagreg.api.lib.json.LowercaseEnumTypeAdapterFactory;
import su.terrafirmagreg.api.lib.json.ResourceLocationJson;

import net.minecraft.util.ResourceLocation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dries007.tfc.api.capability.damage.DamageResistance;
import net.dries007.tfc.objects.entity.animal.AnimalFood;
import net.dries007.tfc.util.json.AnimalFoodJson;
import net.dries007.tfc.util.json.DamageResistanceJson;
import net.dries007.tfc.util.json.VeinTypeJson;
import net.dries007.tfc.world.classic.worldgen.vein.VeinType;

public final class Constants {

    public static final String MOD_ID = Tags.MOD_ID;
    public static final String MOD_NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "required:forge@[14.23.5.2847,);after:jei;after:gregtech;after:top;after:tfc;";
    public static final String SERVER_PROXY = "su.terrafirmagreg.proxy.ServerProxy";
    public static final String CLIENT_PROXY = "su.terrafirmagreg.proxy.ClientProxy";
    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocationJson())
            .registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory())
            .registerTypeAdapter(VeinType.class, new VeinTypeJson())
            .registerTypeAdapter(DamageResistance.class, new DamageResistanceJson())
            .registerTypeAdapter(AnimalFood.class, new AnimalFoodJson())
            .create();

    public static final String
            MODID_TFC = "tfc",
            MODID_TFCF = "tfcflorae",
            MODID_TFCTECH = "tfctech",
            MODID_HORSEPOWER = "horsepower",
            MODID_CAFFEINEADDON = "ca",
            MODID_TIME4TFC = "time4tfc",
            MODID_OSA = "oversizediteminstoragearea",
            MODID_CELLARS = "cellars",
            MODID_TFCTHINGS = "tfcthings",
            MODID_HOTORNOT = "hotornot",
            MODID_FL = "firmalife",
            MODID_AGEDDRINKS = "aged_drinks",
            MODID_TFCAMBIENTAL = "tfcambiental",
            MODID_DDD = "deathdairydespair",
            MODID_FF = "floraefixes",
            MODID_TFCFARMING = "tfcfarming",
            MODID_TFCPASSINGDAYS = "tfcpassingdays",
            MODID_TFCTOWERHEAT = "tfctowerheat";

    private Constants() {
        throw new IllegalAccessError("Utility class");
    }

}
