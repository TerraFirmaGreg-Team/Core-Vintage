package net.dries007.tfc.test.items;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.objects.items.*;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.dries007.tfc.objects.items.rock.ItemRockBrick;
import net.dries007.tfc.objects.items.wood.ItemWoodBoat;
import net.dries007.tfc.objects.items.wood.ItemWoodLumber;
import net.minecraft.item.Item;

import static net.dries007.tfc.api.registries.TFCStorage.*;
import static net.dries007.tfc.objects.CreativeTabsTFC.WOOD;

public class TFCItems {

    public static ItemMisc STRAW;
    public static Item HANDSTONE;
    public static Item WROUGHT_IRON_GRILL;
    public static Item GLUE;
    public static Item SPINDLE;
    public static ItemTFC MORTAR;
    public static ItemTFC HALTER;
    public static Item STICK_BUNCH;
    public static Item STICK_BUNDLE;
    public static Item FIRE_CLAY;
    public static Item WOOD_ASH;
    public static Item FIRESTARTER;
    public static Item GLASS_SHARD;
    public static Item ALABASTER_BRICK;
    public static ItemQuiver QUIVER;
    public static ItemDebug WAND;
    public static ItemBloom UNREFINED_BLOOM;
    public static ItemBloom REFINED_BLOOM;

    private TFCItems() {}


    public static void preInit() {


        //=== ItemRock ===============================================================================================//

        for (var rockType : RockType.getRockTypes()) {
            if (BRICK_ITEMS.put(rockType, new ItemRockBrick(rockType)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s", rockType));

            if (ROCK_ITEMS.put(rockType, new ItemRock(rockType)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s", rockType));
        }

        //=== ItemWood ===============================================================================================//

        for (var woodType : WoodType.getWoodTypes()) {
            if (LUMBER_ITEMS.put(woodType, new ItemWoodLumber(woodType)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s", woodType));

            if (BOAT_ITEMS.put(woodType, new ItemWoodBoat(woodType)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s", woodType));
        }

        //=== ItemMisc ===============================================================================================//

        ITEM.add(STRAW = new ItemMisc("straw", Size.SMALL, Weight.VERY_LIGHT, "kindling", "straw"));
        ITEM.add(WROUGHT_IRON_GRILL = new ItemMisc("wrought_iron_grill", Size.LARGE, Weight.HEAVY, "grill"));
        ITEM.add(GLUE = new ItemMisc("glue", Size.TINY, Weight.LIGHT, "slimeball", "glue"));
        ITEM.add(MORTAR = new ItemMisc("mortar", Size.TINY, Weight.VERY_LIGHT, "mortar"));
        ITEM.add(HALTER = new ItemMisc("halter", Size.SMALL, Weight.LIGHT, "halter"));
        ITEM.add(STICK_BUNCH = new ItemMisc("stick_bunch", Size.NORMAL, Weight.LIGHT, WOOD));
        ITEM.add(STICK_BUNDLE = new ItemMisc("stick_bundle", Size.VERY_LARGE, Weight.MEDIUM, WOOD, "log_wood", "stick_bundle"));
        ITEM.add(FIRE_CLAY = new ItemMisc("ceramics/fire_clay", Size.VERY_SMALL, Weight.VERY_LIGHT, "fire_clay"));
        ITEM.add(WOOD_ASH = new ItemMisc("wood_ash", Size.VERY_SMALL, Weight.VERY_LIGHT, WOOD, "slimeball", "glue"));
        ITEM.add(GLASS_SHARD = new ItemMisc("glass_shard", Size.VERY_SMALL, Weight.VERY_LIGHT));
        ITEM.add(ALABASTER_BRICK = new ItemMisc("alabaster_brick", Size.VERY_SMALL, Weight.LIGHT));


        //=== ItemCraftingTool =======================================================================================//

        ITEM.add(HANDSTONE = new ItemCraftingTool("handstone", 250, Size.NORMAL, Weight.VERY_HEAVY, "handstone"));
        ITEM.add(SPINDLE = new ItemCraftingTool("spindle", 40, Size.NORMAL, Weight.MEDIUM, "spindle"));


        //=== Other ==================================================================================================//

        ITEM.add(FIRESTARTER = new ItemFireStarter());
        ITEM.add(QUIVER = new ItemQuiver());
        ITEM.add(WAND = new ItemDebug());
        ITEM.add(UNREFINED_BLOOM = new ItemBloom(false));
        ITEM.add(REFINED_BLOOM = new ItemBloom(true));
    }
}
