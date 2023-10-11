package net.dries007.tfc.module.core.init;

import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.core.ModuleCoreOld;
import net.dries007.tfc.module.core.api.capability.size.Size;
import net.dries007.tfc.module.core.api.capability.size.Weight;
import net.dries007.tfc.module.core.api.objects.item.ItemBase;
import net.dries007.tfc.module.core.api.util.fuel.Fuel;
import net.dries007.tfc.module.core.api.util.fuel.FuelManager;
import net.dries007.tfc.module.core.objects.blocks.itemblocks.ItemBlockTorch;
import net.dries007.tfc.module.core.objects.items.*;
import net.dries007.tfc.module.devices.objects.items.ItemBloom;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSnow;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.util.registry.Registry;
import su.terrafirmagreg.util.util.ModelRegistrationHelper;

public class ItemsCore {

    public static ItemMisc STRAW;
    public static ItemMisc WROUGHT_IRON_GRILL;
    public static ItemMisc GLUE;
    public static ItemBase MORTAR;
    public static ItemBase HALTER;
    public static ItemMisc STICK_BUNCH;
    public static ItemMisc STICK_BUNDLE;
    public static ItemMisc WOOD_ASH;
    public static ItemMisc GLASS_SHARD;
    public static ItemMisc ALABASTER_BRICK;
    public static ItemFireStarter FIRESTARTER;
    public static ItemCraftingTool HANDSTONE;
    public static ItemCraftingTool SPINDLE;
    public static ItemQuiver QUIVER;
    public static ItemDebug WAND;
    public static ItemBloom UNREFINED_BLOOM;
    public static ItemBloom REFINED_BLOOM;
    public static ItemMisc FIRE_CLAY;
    public static ItemWoodBucket WOODEN_BUCKET;


    public static void onRegister(Registry registry) {

        //==== ItemMisc ==============================================================================================//


        registry.registerItem(STRAW = new ItemMisc(Size.SMALL, Weight.VERY_LIGHT, "kindling", "straw"), "straw");
        registry.registerItem(WROUGHT_IRON_GRILL = new ItemMisc(Size.LARGE, Weight.HEAVY, "grill"), "wrought_iron_grill");
        registry.registerItem(GLUE = new ItemMisc(Size.TINY, Weight.LIGHT, "slimeball", "glue"), "glue");
        registry.registerItem(MORTAR = new ItemMisc(Size.TINY, Weight.VERY_LIGHT, "mortar"), "mortar");
        registry.registerItem(HALTER = new ItemMisc(Size.SMALL, Weight.LIGHT, "halter"), "halter");
        registry.registerItem(STICK_BUNCH = new ItemMisc(Size.NORMAL, Weight.LIGHT), "stick_bunch");
        registry.registerItem(STICK_BUNDLE = new ItemMisc(Size.VERY_LARGE, Weight.MEDIUM, "log_wood", "stick_bundle"), "stick_bundle");
        registry.registerItem(WOOD_ASH = new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT, "slimeball", "glue"), "wood_ash");
        registry.registerItem(GLASS_SHARD = new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT), "glass_shard");
        registry.registerItem(ALABASTER_BRICK = new ItemMisc(Size.VERY_SMALL, Weight.LIGHT), "alabaster_brick");
        registry.registerItem(FIRE_CLAY = new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT, "fire_clay", "ingotFireClay"), "ceramics/fire_clay");


        //==== ItemCraftingTool ======================================================================================//

        registry.registerItem(HANDSTONE = new ItemCraftingTool(250, Size.NORMAL, Weight.VERY_HEAVY, "handstone"), "handstone");
        registry.registerItem(SPINDLE = new ItemCraftingTool(40, Size.NORMAL, Weight.MEDIUM, "spindle"), "spindle");


        //==== Other =================================================================================================//

        registry.registerItem(FIRESTARTER = new ItemFireStarter(), ItemFireStarter.NAME);
        registry.registerItem(QUIVER = new ItemQuiver(), ItemQuiver.NAME);
        registry.registerItem(WAND = new ItemDebug(), ItemDebug.NAME);
        registry.registerItem(WOODEN_BUCKET = new ItemWoodBucket(), "wooden_bucket");
        registry.registerItem(UNREFINED_BLOOM = new ItemBloom(false), "bloom/refined");
        registry.registerItem(REFINED_BLOOM = new ItemBloom(true), "bloom/unrefined");

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerVanillaOverridesItem(RegistryEvent.Register<Item> event) {
        // Vanilla Overrides. Used for small tweaks on vanilla items, rather than replacing them outright
        ModuleCoreOld.LOGGER.info("The below warnings about unintended overrides are normal. The override is intended. ;)");
        event.getRegistry().registerAll(
                new ItemSnow(Blocks.SNOW_LAYER).setRegistryName("minecraft", "snow_layer"),
                new ItemGlassBottleTFC().setRegistryName(Items.GLASS_BOTTLE.getRegistryName()).setTranslationKey("glassBottle")
        );

        if (ConfigTFC.General.OVERRIDES.enableTorchOverride) {
            event.getRegistry().register(new ItemBlockTorch(Blocks.TORCH).setRegistryName("minecraft", "torch"));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {

            ModelRegistrationHelper.registerItemModels(
                    STRAW,
                    WROUGHT_IRON_GRILL,
                    GLUE,
                    MORTAR,
                    HALTER,
                    STICK_BUNCH,
                    STICK_BUNDLE,
                    WOOD_ASH,
                    GLASS_SHARD,
                    ALABASTER_BRICK,
                    FIRESTARTER,
                    HANDSTONE,
                    SPINDLE,
                    QUIVER,
                    WAND,
                    UNREFINED_BLOOM,
                    REFINED_BLOOM,
                    FIRE_CLAY,
                    WOODEN_BUCKET
            );
        });
    }

    public static void onPostInitialization() {
        // Coals
        FuelManager.addFuel(new Fuel(IIngredient.of("gemCoal"), 2200, 1415f, true, false));
        FuelManager.addFuel(new Fuel(IIngredient.of("gemLignite"), 2000, 1350f, true, false));

        // Charcoal
        FuelManager.addFuel(new Fuel(IIngredient.of("charcoal"), 1800, 1350f, true, true));

        // Peat
        FuelManager.addFuel(new Fuel(IIngredient.of("peat"), 2500, 680));

        // Stick Bundle
        FuelManager.addFuel(new Fuel(IIngredient.of("stickBundle"), 600, 900));
    }
}
