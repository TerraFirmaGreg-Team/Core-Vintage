package su.terrafirmagreg.api;


import su.terrafirmagreg.api.modules.IModuleManager;

public class TerraFirmaGregAPI {

    /**
     * Всегда будет доступен
     */
    public static Object instance;

    /**
     * Будет доступен на этапе строительства
     */
    public static IModuleManager moduleManager;
}
