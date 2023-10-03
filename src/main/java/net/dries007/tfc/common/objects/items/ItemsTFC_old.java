package net.dries007.tfc.common.objects.items;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.dries007.tfc.Tags;
import net.dries007.tfc.module.animal.objects.items.ItemAnimalHide;
import net.dries007.tfc.module.ceramic.objects.items.ItemPottery;
import net.dries007.tfc.module.core.objects.items.ItemMetalBucket;
import net.dries007.tfc.module.core.objects.items.ItemMisc;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static net.dries007.tfc.module.ceramic.ModuleCeramic.POTTERY_TAB;
import static net.dries007.tfc.module.core.ModuleCore.MISC_TAB;
import static net.dries007.tfc.module.core.api.util.Helpers.getNull;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
@GameRegistry.ObjectHolder(Tags.MOD_ID)
public final class ItemsTFC_old {

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

    @GameRegistry.ObjectHolder("metal/bucket/blue_steel")
    public static final ItemMetalBucket BLUE_STEEL_BUCKET = getNull();
    @GameRegistry.ObjectHolder("metal/bucket/red_steel")
    public static final ItemMetalBucket RED_STEEL_BUCKET = getNull();
    @GameRegistry.ObjectHolder("crop/product/jute_disc")
    public static final Item JUTE_DISC = getNull();
    @GameRegistry.ObjectHolder("crop/product/jute_net")
    public static final Item JUTE_NET = getNull();
    @GameRegistry.ObjectHolder("crop/product/dirty_jute_net")
    public static final Item DIRTY_JUTE_NET = getNull();
    @GameRegistry.ObjectHolder("food/olive_paste")
    public static final Item OLIVE_PASTE = getNull();

    private static ImmutableList<Item> allSimpleItems;


    public static ImmutableList<Item> getAllSimpleItems() {
        return allSimpleItems;
    }


    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();
        Builder<Item> simpleItems = ImmutableList.builder();

        //==== Other ==================================================================================================//

//        // POTTERY
//        {
//            simpleItems.add(register(r, "ceramics/unfired/large_vessel", new ItemUnfiredLargeVessel(), POTTERY_TAB));
//            simpleItems.add(register(r, "ceramics/unfired/crucible", new ItemPottery(Size.LARGE, Weight.VERY_HEAVY), POTTERY_TAB));
//            simpleItems.add(register(r, "ceramics/unfired/clay_brick", new ItemPottery(), POTTERY_TAB));
//            simpleItems.add(register(r, "ceramics/unfired/clay_flower_pot", new ItemPottery(), POTTERY_TAB));
//            simpleItems.add(register(r, "ceramics/unfired/jug", new ItemPottery(), POTTERY_TAB));
//            register(r, "ceramics/fired/jug", new ItemJug(), POTTERY_TAB);
//
//            registerPottery(simpleItems, r, "ceramics/unfired/vessel", "ceramics/fired/vessel", new ItemUnfiredSmallVessel(false), new ItemSmallVessel(false));
//            registerPottery(null, r, "ceramics/unfired/vessel_glazed", "ceramics/fired/vessel_glazed", new ItemUnfiredSmallVessel(true), new ItemSmallVessel(true));
//            registerPottery(simpleItems, r, "ceramics/unfired/pot", "ceramics/fired/pot", new ItemPottery(Size.LARGE, Weight.LIGHT), new ItemPottery(Size.LARGE, Weight.LIGHT));
//            registerPottery(simpleItems, r, "ceramics/unfired/bowl", "ceramics/fired/bowl", new ItemPottery(Size.VERY_SMALL, Weight.VERY_LIGHT), new ItemPottery(Size.VERY_SMALL, Weight.VERY_LIGHT));
//            registerPottery(simpleItems, r, "ceramics/unfired/spindle", "ceramics/fired/spindle");
//
//
//        }


//        simpleItems.add(register(r, "crop/product/jute", new ItemMisc(Size.SMALL, Weight.VERY_LIGHT), MISC));
//        simpleItems.add(register(r, "crop/product/jute_fiber", new ItemMisc(Size.SMALL, Weight.VERY_LIGHT), MISC));
//        simpleItems.add(register(r, "crop/product/burlap_cloth", new ItemMisc(Size.SMALL, Weight.VERY_LIGHT), MISC));

        // All simple foods (not meals) just use ItemFood and are registered here
//        for (FoodType foodOld : FoodType.getFoodType()) {
//            if (foodOld.getFoodCategory() != FoodCategories.MEAL) {
//                simpleItems.add(register(r, "food/" + foodOld.toString().toLowerCase(), new ItemFoodIngredient(foodOld), CreativeTabsTFC.FOOD));
//            }
//        }
//        // Complex foods that require special classes go here
//        for (FoodType foodOld : new FoodType[]{FoodTypes.BARLEY_BREAD_SANDWICH, FoodTypes.CORNBREAD_SANDWICH, FoodTypes.OAT_BREAD_SANDWICH, FoodTypes.RICE_BREAD_SANDWICH, FoodTypes.RYE_BREAD_SANDWICH, FoodTypes.WHEAT_BREAD_SANDWICH}) {
//            simpleItems.add(register(r, "food/" + foodOld.toString().toLowerCase(), new ItemSandwich(foodOld), CreativeTabsTFC.FOOD));
//        }
//        for (FoodType foodOld : new FoodType[]{FoodTypes.SOUP_GRAIN, FoodTypes.SOUP_FRUIT, FoodTypes.SOUP_VEGETABLE, FoodTypes.SOUP_MEAT, FoodTypes.SOUP_DAIRY}) {
//            simpleItems.add(register(r, "food/" + foodOld.toString().toLowerCase(), new ItemDynamicBowlFood(foodOld), CreativeTabsTFC.FOOD));
//        }
//        for (FoodType foodOld : new FoodType[]{FoodTypes.SALAD_GRAIN, FoodTypes.SALAD_FRUIT, FoodTypes.SALAD_VEGETABLE, FoodTypes.SALAD_MEAT, FoodTypes.SALAD_DAIRY}) {
//            simpleItems.add(register(r, "food/" + foodOld.toString().toLowerCase(), new ItemDynamicBowlFood(foodOld), CreativeTabsTFC.FOOD));
//        }

        //olive oil production
//        simpleItems.add(register(r, "food/olive_paste", new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT), FOOD)); //not edible
//        simpleItems.add(register(r, "crop/product/jute_disc", new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT), MISC));
//        simpleItems.add(register(r, "crop/product/olive_jute_disc", new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT), FOOD)); //not edible
//        simpleItems.add(register(r, "crop/product/jute_net", new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT), MISC));
//        simpleItems.add(register(r, "crop/product/dirty_jute_net", new ItemMisc(Size.VERY_SMALL, Weight.VERY_LIGHT), MISC));


        // Animal Hides
        for (ItemAnimalHide.HideSize size : ItemAnimalHide.HideSize.values()) {
            for (ItemAnimalHide.HideType type : ItemAnimalHide.HideType.values()) {
                simpleItems.add(register(r, ("hide/" + type.name() + "/" + size.name()).toLowerCase(), new ItemAnimalHide(type, size), MISC_TAB));
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

        allSimpleItems = simpleItems.build();
    }

    private static void registerPottery(Builder<Item> items, IForgeRegistry<Item> r, String nameUnfired, String nameFired) {
        registerPottery(items, r, nameUnfired, nameFired, new ItemPottery(), new ItemPottery());
    }

    private static void registerPottery(Builder<Item> items, IForgeRegistry<Item> r, String nameUnfired, String nameFired, ItemPottery unfiredItem, ItemPottery firedItem) {
        register(r, nameFired, firedItem, POTTERY_TAB);
        register(r, nameUnfired, unfiredItem, POTTERY_TAB);

        if (items != null) {
            items.add(firedItem, unfiredItem);
        }
    }

    private static <T extends Item> T register(IForgeRegistry<Item> r, String name, T item, CreativeTabs ct) {
        item.setRegistryName(Tags.MOD_ID, name);
        item.setTranslationKey(Tags.MOD_ID + "." + name.replace('/', '.'));
        item.setCreativeTab(ct);
        r.register(item);
        return item;
    }
}
