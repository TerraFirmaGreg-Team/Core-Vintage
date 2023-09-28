package net.dries007.tfc.common.objects.items;

import gregtech.api.unification.ore.OrePrefix;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.dries007.tfc.module.ceramic.common.items.ItemMold;
import net.dries007.tfc.module.ceramic.common.items.ItemUnfiredMold;
import net.dries007.tfc.module.core.api.item.ItemBase;
import net.dries007.tfc.module.core.common.items.*;
import net.dries007.tfc.module.devices.common.items.ItemBloom;
import net.minecraft.item.Item;

import java.util.LinkedList;
import java.util.Map;

import static net.dries007.tfc.module.core.ModuleCore.MISC_TAB;

public class TFCItems {

    //==== Item ======================================================================================================//


    public static final Map<OrePrefix, ItemMold> FIRED_MOLDS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<OrePrefix, ItemUnfiredMold> UNFIRED_MOLDS = new Object2ObjectLinkedOpenHashMap<>();

    // Предметы
    public static final LinkedList<Item> ITEMS = new LinkedList<>();

    public static ItemMisc STRAW;
    public static ItemMisc HANDSTONE;
    public static ItemMisc WROUGHT_IRON_GRILL;
    public static ItemMisc GLUE;
    public static ItemMisc SPINDLE;
    public static ItemBase MORTAR;
    public static ItemBase HALTER;
    public static ItemMisc STICK_BUNCH;
    public static ItemMisc STICK_BUNDLE;
    public static ItemMisc WOOD_ASH;
    public static ItemFireStarter FIRESTARTER;
    public static ItemMisc GLASS_SHARD;
    public static ItemMisc ALABASTER_BRICK;
    public static ItemQuiver QUIVER;
    public static ItemDebug WAND;
    public static ItemBloom UNREFINED_BLOOM;
    public static ItemBloom REFINED_BLOOM;
    public static ItemMisc FIRE_CLAY;

    private TFCItems() {
    }


    public static void preInit() {

        //==== Molds =================================================================================================//

        for (var orePrefix : OrePrefix.values()) {
            var orePrefixExtension = (IOrePrefixExtension) orePrefix;
            if (orePrefixExtension.getHasMold()) {
                if (UNFIRED_MOLDS.put(orePrefix, new ItemUnfiredMold(orePrefix)) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s", orePrefix));

                if (FIRED_MOLDS.put(orePrefix, new ItemMold(orePrefix)) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s", orePrefix));
            }
        }


        //==== ItemMisc ==============================================================================================//

        ITEMS.add(STRAW = new ItemMisc("straw", Size.SMALL, Weight.VERY_LIGHT, "kindling", "straw"));
        ITEMS.add(WROUGHT_IRON_GRILL = new ItemMisc("wrought_iron_grill", Size.LARGE, Weight.HEAVY, "grill"));
        ITEMS.add(GLUE = new ItemMisc("glue", Size.TINY, Weight.LIGHT, "slimeball", "glue"));
        ITEMS.add(MORTAR = new ItemMisc("mortar", Size.TINY, Weight.VERY_LIGHT, "mortar"));
        ITEMS.add(HALTER = new ItemMisc("halter", Size.SMALL, Weight.LIGHT, "halter"));
        ITEMS.add(STICK_BUNCH = new ItemMisc("stick_bunch", Size.NORMAL, Weight.LIGHT, MISC_TAB));
        ITEMS.add(STICK_BUNDLE = new ItemMisc("stick_bundle", Size.VERY_LARGE, Weight.MEDIUM, MISC_TAB, "log_wood", "stick_bundle"));
        ITEMS.add(WOOD_ASH = new ItemMisc("wood_ash", Size.VERY_SMALL, Weight.VERY_LIGHT, MISC_TAB, "slimeball", "glue"));
        ITEMS.add(GLASS_SHARD = new ItemMisc("glass_shard", Size.VERY_SMALL, Weight.VERY_LIGHT));
        ITEMS.add(ALABASTER_BRICK = new ItemMisc("alabaster_brick", Size.VERY_SMALL, Weight.LIGHT));
        ITEMS.add(FIRE_CLAY = new ItemMisc("ceramics/fire_clay", Size.VERY_SMALL, Weight.VERY_LIGHT, "fire_clay", "ingotFireClay"));


        //==== ItemCraftingTool ======================================================================================//

        ITEMS.add(HANDSTONE = new ItemCraftingTool("handstone", 250, Size.NORMAL, Weight.VERY_HEAVY, "handstone"));
        ITEMS.add(SPINDLE = new ItemCraftingTool("spindle", 40, Size.NORMAL, Weight.MEDIUM, "spindle"));


        //==== Other =================================================================================================//

        ITEMS.add(FIRESTARTER = new ItemFireStarter());
        ITEMS.add(QUIVER = new ItemQuiver());
        ITEMS.add(WAND = new ItemDebug());
        ITEMS.add(UNREFINED_BLOOM = new ItemBloom(false));
        ITEMS.add(REFINED_BLOOM = new ItemBloom(true));


    }

}
