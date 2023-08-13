package net.dries007.tfc.common.objects.items;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.agriculture.crop.Crop;
import net.dries007.tfc.api.types.food.category.FoodCategories;
import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.api.types.food.type.FoodTypes;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.Powder;
import net.dries007.tfc.common.objects.blocks.BlocksTFC;
import net.dries007.tfc.common.objects.items.ceramics.*;
import net.dries007.tfc.common.objects.items.food.ItemDynamicBowlFood;
import net.dries007.tfc.common.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.common.objects.items.food.ItemSandwich;
import net.dries007.tfc.common.objects.items.itemblock.ItemBlockTorch;
import net.dries007.tfc.common.objects.items.metal.ItemMetalBucket;
import net.dries007.tfc.common.objects.items.wood.ItemWoodBucket;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSnow;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.util.Helpers.getNull;

@Mod.EventBusSubscriber(modid = MOD_ID)
@GameRegistry.ObjectHolder(MOD_ID)
public final class ItemsTFC {

    @GameRegistry.ObjectHolder("crop/product/jute")
    public static final ItemMisc JUTE = getNull();
    @GameRegistry.ObjectHolder("crop/product/jute_fiber")
    public static final ItemMisc JUTE_FIBER = getNull();
    @GameRegistry.ObjectHolder("crop/product/burlap_cloth")
    public static final ItemMisc BURLAP_CLOTH = getNull();
    @GameRegistry.ObjectHolder("animal/product/wool")
    public static final ItemMisc WOOL = getNull();
    @GameRegistry.ObjectHolder("animal/product/wool_yarn")
    public static final ItemMisc WOOL_YARN = getNull();
    @GameRegistry.ObjectHolder("animal/product/wool_cloth")
    public static final ItemMisc WOOL_CLOTH = getNull();
    @GameRegistry.ObjectHolder("animal/product/silk_cloth")
    public static final ItemMisc SILK_CLOTH = getNull();

    @GameRegistry.ObjectHolder("ceramics/unfired/fire_brick")
    public static final ItemPottery UNFIRED_FIRE_BRICK = getNull();
    @GameRegistry.ObjectHolder("ceramics/fired/fire_brick")
    public static final ItemPottery FIRED_FIRE_BRICK = getNull();
    @GameRegistry.ObjectHolder("ceramics/unfired/vessel")
    public static final ItemPottery UNFIRED_VESSEL = getNull();
    @GameRegistry.ObjectHolder("ceramics/fired/vessel")
    public static final ItemPottery FIRED_VESSEL = getNull();
    @GameRegistry.ObjectHolder("ceramics/unfired/vessel_glazed")
    public static final ItemPottery UNFIRED_VESSEL_GLAZED = getNull();
    @GameRegistry.ObjectHolder("ceramics/fired/vessel_glazed")
    public static final ItemPottery FIRED_VESSEL_GLAZED = getNull();
    @GameRegistry.ObjectHolder("ceramics/unfired/jug")
    public static final ItemPottery UNFIRED_JUG = getNull();
    @GameRegistry.ObjectHolder("ceramics/fired/jug")
    public static final ItemPottery FIRED_JUG = getNull();
    @GameRegistry.ObjectHolder("ceramics/unfired/pot")
    public static final ItemPottery UNFIRED_POT = getNull();
    @GameRegistry.ObjectHolder("ceramics/fired/pot")
    public static final ItemPottery FIRED_POT = getNull();
    @GameRegistry.ObjectHolder("ceramics/unfired/bowl")
    public static final ItemPottery UNFIRED_BOWL = getNull();
    @GameRegistry.ObjectHolder("ceramics/fired/bowl")
    public static final ItemPottery FIRED_BOWL = getNull();
    @GameRegistry.ObjectHolder("ceramics/unfired/spindle")
    public static final ItemPottery UNFIRED_SPINDLE = getNull();
    @GameRegistry.ObjectHolder("ceramics/fired/spindle")
    public static final ItemPottery FIRED_SPINDLE = getNull();
    @GameRegistry.ObjectHolder("ceramics/unfired/large_vessel")
    public static final ItemPottery UNFIRED_LARGE_VESSEL = getNull();
    @GameRegistry.ObjectHolder("ceramics/unfired/crucible")
    public static final ItemPottery UNFIRED_CRUCIBLE = getNull();

    @GameRegistry.ObjectHolder("powder/salt")
    public static final ItemPowder SALT = getNull();

    public static final ItemWoodBucket WOODEN_BUCKET = getNull();

    @GameRegistry.ObjectHolder("metal/bucket/blue_steel")
    public static final ItemMetalBucket BLUE_STEEL_BUCKET = getNull();
    @GameRegistry.ObjectHolder("metal/bucket/red_steel")
    public static final ItemMetalBucket RED_STEEL_BUCKET = getNull();

    @GameRegistry.ObjectHolder("dye/black")
    public static final ItemMisc DYE_BLACK = getNull();
    @GameRegistry.ObjectHolder("dye/blue")
    public static final ItemMisc DYE_BLUE = getNull();
    @GameRegistry.ObjectHolder("dye/brown")
    public static final ItemMisc DYE_BROWN = getNull();
    @GameRegistry.ObjectHolder("dye/white")
    public static final ItemMisc DYE_WHITE = getNull();

    @GameRegistry.ObjectHolder("ceramics/unfired/clay_brick")
    public static final ItemPottery UNFIRED_BRICK = getNull();

    @GameRegistry.ObjectHolder("ceramics/unfired/clay_flower_pot")
    public static final ItemPottery UNFIRED_FLOWER_POT = getNull();

    @GameRegistry.ObjectHolder("crop/product/jute_disc")
    public static final Item JUTE_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/jute_net")
    public static final Item JUTE_NET = getNull();
    @GameRegistry.ObjectHolder("crop/product/dirty_jute_net")
    public static final Item DIRTY_JUTE_NET = getNull();
    @GameRegistry.ObjectHolder("food/olive_paste")
    public static final Item OLIVE_PASTE = getNull();

    private static ImmutableList<Item> allSimpleItems;
    private static ImmutableList<ItemMold> allMoldItems;


    public static ImmutableList<Item> getAllSimpleItems() {
        return allSimpleItems;
    }

    public static ImmutableList<ItemMold> getAllMoldItems() {
        return allMoldItems;
    }


    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();
        Builder<Item> simpleItems = ImmutableList.builder();


        //=== Other ==================================================================================================//

        register(r, "wooden_bucket", new ItemWoodBucket(), CreativeTabsTFC.WOOD); //not a simple item, use a custom model
        //register(r, "metal/bucket/blue_steel", new ItemMetalBucket(Metal.BLUE_STEEL, Metal.ItemType.BUCKET), METAL); //not a simple item, use a custom model
        //register(r, "metal/bucket/red_steel", new ItemMetalBucket(Metal.RED_STEEL, Metal.ItemType.BUCKET), METAL); //not a simple item, use a custom model

        /*
        for (Metal.ItemType type : Metal.ItemType.values())
        {
            for (Metal metal : TFCRegistries.METALS.getValuesCollection())
            {
                if (type != Metal.ItemType.BUCKET && type.hasType(metal)) // buckets registered separately
                {
                    simpleItems.add(register(r, "metal/" + type.name().toLowerCase() + "/" + metal.getRegistryName().getPath(), Metal.ItemType.create(metal, type), METAL));
                }
            }
        }*/


        BlocksTFC.getAllNormalItemBlocks().forEach(x -> registerItemBlock(r, x));
        BlocksTFC.getAllInventoryItemBlocks().forEach(x -> registerItemBlock(r, x));

        for (Powder powder : Powder.values())
            simpleItems.add(register(r, "powder/" + powder.name().toLowerCase(), new ItemPowder(powder), CreativeTabsTFC.MISC));

        // METAL
        {
//            for (var material : GregTechAPI.materialManager.getRegistry("gregtech")) {
//                if (material.hasFlag(TFGMaterialFlags.GENERATE_ANVIL)) {
//                    simpleItems.add(register(r, "metal/anvil/" + material.getName(), new ItemMetalAnvil(material), METAL));
//                }
//
////                if (material == Materials.Iron)
////                    simpleItems.add(register(r, "metal/cladding/" + material.getName(), new ItemMetalCladding(material), METAL));
//            }
        }

        // POTTERY
        Builder<ItemMold> clayMolds = ImmutableList.builder();

        {
            for (var orePrefix : OrePrefix.values()) {
                var orePrefixExtension = (IOrePrefixExtension) orePrefix;
                if (orePrefixExtension.getHasMold()) {
                    // Not using registerPottery here because the ItemMold uses a custom ItemModelMesher, meaning it can't be in simpleItems
                    clayMolds.add(register(r, "ceramics/fired/mold/" + orePrefix.name.toLowerCase(), new ItemMold(orePrefix), CreativeTabsTFC.POTTERY));
                    simpleItems.add(register(r, "ceramics/unfired/mold/" + orePrefix.name.toLowerCase(), new ItemUnfiredMold(orePrefix), CreativeTabsTFC.POTTERY));
                }
            }


            simpleItems.add(register(r, "ceramics/unfired/large_vessel", new ItemUnfiredLargeVessel(), CreativeTabsTFC.POTTERY));
            simpleItems.add(register(r, "ceramics/unfired/crucible", new ItemPottery(Size.LARGE, Weight.VERY_HEAVY), CreativeTabsTFC.POTTERY));

            registerPottery(simpleItems, r, "ceramics/unfired/vessel", "ceramics/fired/vessel", new ItemUnfiredSmallVessel(false), new ItemSmallVessel(false));
            registerPottery(null, r, "ceramics/unfired/vessel_glazed", "ceramics/fired/vessel_glazed", new ItemUnfiredSmallVessel(true), new ItemSmallVessel(true));

            ItemPottery firedPot = new ItemPottery(Size.LARGE, Weight.LIGHT);
            registerPottery(simpleItems, r, "ceramics/unfired/pot", "ceramics/fired/pot", new ItemPottery(Size.LARGE, Weight.LIGHT), firedPot);
            OreDictionaryHelper.register(firedPot, "cooking_pot");

            ItemPottery firedBowl = new ItemPottery(Size.VERY_SMALL, Weight.VERY_LIGHT);
            registerPottery(simpleItems, r, "ceramics/unfired/bowl", "ceramics/fired/bowl", new ItemPottery(Size.VERY_SMALL, Weight.VERY_LIGHT), firedBowl);
            OreDictionaryHelper.register(firedBowl, "bowl");

            registerPottery(simpleItems, r, "ceramics/unfired/spindle", "ceramics/fired/spindle");
            registerPottery(simpleItems, r, "ceramics/unfired/fire_brick", "ceramics/fired/fire_brick");

            simpleItems.add(register(r, "ceramics/unfired/jug", new ItemPottery(), CreativeTabsTFC.POTTERY));
            register(r, "ceramics/fired/jug", new ItemJug(), CreativeTabsTFC.POTTERY);
            simpleItems.add(register(r, "ceramics/unfired/clay_brick", new ItemPottery(), CreativeTabsTFC.POTTERY));
            simpleItems.add(register(r, "ceramics/unfired/clay_flower_pot", new ItemPottery(), CreativeTabsTFC.POTTERY));

        }

        for (Crop crop : Crop.values()) {
            simpleItems.add(register(r, "crop/seeds/" + crop.name().toLowerCase(), new ItemSeedsTFC(crop), CreativeTabsTFC.FOOD));
        }

//        simpleItems.add(register(r, "crop/product/jute", new ItemMisc(Size.SMALL, Weight.VERY_LIGHT), MISC));
//        simpleItems.add(register(r, "crop/product/jute_fiber", new ItemMisc(Size.SMALL, Weight.VERY_LIGHT), MISC));
//        simpleItems.add(register(r, "crop/product/burlap_cloth", new ItemMisc(Size.SMALL, Weight.VERY_LIGHT), MISC));

        // All simple foods (not meals) just use ItemFood and are registered here
        for (FoodType foodOld : FoodType.getFoodTypes()) {
            if (foodOld.getFoodCategory() != FoodCategories.MEAL) {
                simpleItems.add(register(r, "food/" + foodOld.toString().toLowerCase(), new ItemFoodTFC(foodOld), CreativeTabsTFC.FOOD));
            }
        }
        // Complex foods that require special classes go here
        for (FoodType foodOld : new FoodType[]{FoodTypes.BARLEY_BREAD_SANDWICH, FoodTypes.CORNBREAD_SANDWICH, FoodTypes.OAT_BREAD_SANDWICH, FoodTypes.RICE_BREAD_SANDWICH, FoodTypes.RYE_BREAD_SANDWICH, FoodTypes.WHEAT_BREAD_SANDWICH}) {
            simpleItems.add(register(r, "food/" + foodOld.toString().toLowerCase(), new ItemSandwich(foodOld), CreativeTabsTFC.FOOD));
        }
        for (FoodType foodOld : new FoodType[]{FoodTypes.SOUP_GRAIN, FoodTypes.SOUP_FRUIT, FoodTypes.SOUP_VEGETABLE, FoodTypes.SOUP_MEAT, FoodTypes.SOUP_DAIRY}) {
            simpleItems.add(register(r, "food/" + foodOld.toString().toLowerCase(), new ItemDynamicBowlFood(foodOld), CreativeTabsTFC.FOOD));
        }
        for (FoodType foodOld : new FoodType[]{FoodTypes.SALAD_GRAIN, FoodTypes.SALAD_FRUIT, FoodTypes.SALAD_VEGETABLE, FoodTypes.SALAD_MEAT, FoodTypes.SALAD_DAIRY}) {
            simpleItems.add(register(r, "food/" + foodOld.toString().toLowerCase(), new ItemDynamicBowlFood(foodOld), CreativeTabsTFC.FOOD));
        }

        //olive oil production
//        simpleItems.add(register(r, "food/olive_paste", new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT), FOOD)); //not edible
//        simpleItems.add(register(r, "crop/product/jute_disc", new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT), MISC));
//        simpleItems.add(register(r, "crop/product/olive_jute_disc", new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT), FOOD)); //not edible
//        simpleItems.add(register(r, "crop/product/jute_net", new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT), MISC));
//        simpleItems.add(register(r, "crop/product/dirty_jute_net", new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT), MISC));


        // Animal Hides
        for (ItemAnimalHide.HideSize size : ItemAnimalHide.HideSize.values()) {
            for (ItemAnimalHide.HideType type : ItemAnimalHide.HideType.values()) {
                simpleItems.add(register(r, ("hide/" + type.name() + "/" + size.name()).toLowerCase(), new ItemAnimalHide(type, size), CreativeTabsTFC.MISC));
            }
        }

//        simpleItems.add(register(r, "animal/product/wool", new ItemMisc(Size.SMALL, Weight.LIGHT), MISC));
//        simpleItems.add(register(r, "animal/product/wool_yarn", new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT, "string"), MISC));
//        simpleItems.add(register(r, "animal/product/wool_cloth", new ItemMisc(Size.SMALL, Weight.LIGHT, "cloth_high_quality"), MISC));
//        simpleItems.add(register(r, "animal/product/silk_cloth", new ItemMisc(Size.SMALL, Weight.LIGHT, "cloth_high_quality"), MISC));
//
//        simpleItems.add(register(r, "dye/black", new ItemMisc(Size.TINY, Weight.LIGHT, "dye_black"), MISC));
//        simpleItems.add(register(r, "dye/blue", new ItemMisc(Size.TINY, Weight.LIGHT, "dye_blue"), MISC));
//        simpleItems.add(register(r, "dye/white", new ItemMisc(Size.TINY, Weight.LIGHT, "dye_white"), MISC));
//        simpleItems.add(register(r, "dye/brown", new ItemMisc(Size.TINY, Weight.LIGHT, "dye_brown"), MISC));

        allMoldItems = clayMolds.build();
        allSimpleItems = simpleItems.build();

        OreDictionaryHelper.init();
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerVanillaOverrides(RegistryEvent.Register<Item> event) {
        // Vanilla Overrides. Used for small tweaks on vanilla items, rather than replacing them outright
        TerraFirmaCraft.LOGGER.info("The below warnings about unintended overrides are normal. The override is intended. ;)");
        event.getRegistry().registerAll(
                new ItemSnow(Blocks.SNOW_LAYER).setRegistryName("minecraft", "snow_layer"),
                new ItemGlassBottleTFC().setRegistryName(Items.GLASS_BOTTLE.getRegistryName()).setTranslationKey("glassBottle")
        );

        if (ConfigTFC.General.OVERRIDES.enableTorchOverride) {
            event.getRegistry().register(new ItemBlockTorch(Blocks.TORCH).setRegistryName("minecraft", "torch"));
        }
    }

    private static void registerPottery(Builder<Item> items, IForgeRegistry<Item> r, String nameUnfired, String nameFired) {
        registerPottery(items, r, nameUnfired, nameFired, new ItemPottery(), new ItemPottery());
    }

    private static void registerPottery(Builder<Item> items, IForgeRegistry<Item> r, String nameUnfired, String nameFired, ItemPottery unfiredItem, ItemPottery firedItem) {
        register(r, nameFired, firedItem, CreativeTabsTFC.POTTERY);
        register(r, nameUnfired, unfiredItem, CreativeTabsTFC.POTTERY);

        if (items != null) {
            items.add(firedItem, unfiredItem);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static void registerItemBlock(IForgeRegistry<Item> r, ItemBlock item) {
        item.setRegistryName(item.getBlock().getRegistryName());
        item.setCreativeTab(item.getBlock().getCreativeTab());
        r.register(item);
    }

    private static <T extends Item> T register(IForgeRegistry<Item> r, String name, T item, CreativeTabs ct) {
        item.setRegistryName(MOD_ID, name);
        item.setTranslationKey(MOD_ID + "." + name.replace('/', '.'));
        item.setCreativeTab(ct);
        r.register(item);
        return item;
    }
}
