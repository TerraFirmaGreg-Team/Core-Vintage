package su.terrafirmagreg.core.modules.gregtech.items;

public class TFGModMetaItem {

    public static TFGMetaItems TFGMODMetaItems;

    public static void init() {
        TFGMODMetaItems = new TFGMetaItems();
        TFGMODMetaItems.setRegistryName("meta_item");
    }
}
