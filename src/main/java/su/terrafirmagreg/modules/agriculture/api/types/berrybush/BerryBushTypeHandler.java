package su.terrafirmagreg.modules.agriculture.api.types.berrybush;

import su.terrafirmagreg.api.data.enums.EnumBushSize;
import su.terrafirmagreg.api.library.MCDate.Month;

import net.dries007.eerussianguy.firmalife.init.FoodFL;
import net.dries007.eerussianguy.firmalife.registry.ItemsFL;
import net.dries007.tfc.objects.items.ItemsTFCF;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.objects.items.food.ItemFoodTFCF;
import net.dries007.tfc.util.agriculture.Food;


public class BerryBushTypeHandler {

  public static void init() {
    BerryBushTypes.BLACKBERRY = BerryBushType
      .builder("blackberry")
      .fruit(() -> ItemFoodTFC.get(Food.BLACKBERRY))
      .harvest(Month.MAY, 4)
      .temp(7f, 20f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.LARGE)
      .spiky(true)
      .build();

    BerryBushTypes.BLUEBERRY = BerryBushType
      .builder("blueberry")
      .fruit(() -> ItemFoodTFC.get(Food.BLUEBERRY))
      .harvest(Month.JUNE, 3)
      .temp(7f, 25f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.LARGE)
      .spiky(false)
      .build();

    BerryBushTypes.BUNCH_BERRY = BerryBushType
      .builder("bunch_berry")
      .fruit(() -> ItemFoodTFC.get(Food.BUNCH_BERRY))
      .harvest(Month.JUNE, 3)
      .temp(15f, 30f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.SMALL)
      .spiky(false)
      .build();

    BerryBushTypes.CLOUD_BERRY = BerryBushType
      .builder("cloud_berry")
      .fruit(() -> ItemFoodTFC.get(Food.CLOUD_BERRY))
      .harvest(Month.JUNE, 2)
      .temp(3f, 17f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.MEDIUM)
      .spiky(false)
      .build();

    BerryBushTypes.CRANBERRY = BerryBushType
      .builder("cranberry")
      .fruit(() -> ItemFoodTFC.get(Food.CRANBERRY))
      .harvest(Month.AUGUST, 3)
      .temp(1f, 19f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.MEDIUM)
      .spiky(false)
      .build();

    BerryBushTypes.ELDERBERRY = BerryBushType
      .builder("elderberry")
      .fruit(() -> ItemFoodTFC.get(Food.ELDERBERRY))
      .harvest(Month.JULY, 2)
      .temp(10f, 29f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.LARGE)
      .spiky(false)
      .build();

    BerryBushTypes.GOOSEBERRY = BerryBushType
      .builder("gooseberry")
      .fruit(() -> ItemFoodTFC.get(Food.GOOSEBERRY))
      .harvest(Month.MARCH, 4)
      .temp(5f, 25f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.MEDIUM)
      .spiky(false)
      .build();

    BerryBushTypes.RASPBERRY = BerryBushType
      .builder("raspberry")
      .fruit(() -> ItemFoodTFC.get(Food.RASPBERRY))
      .harvest(Month.JUNE, 2)
      .temp(5f, 20f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.LARGE)
      .spiky(true)
      .build();

    BerryBushTypes.SNOW_BERRY = BerryBushType
      .builder("snow_berry")
      .fruit(() -> ItemFoodTFC.get(Food.SNOW_BERRY))
      .harvest(Month.JULY, 2)
      .temp(-5f, 18f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.SMALL)
      .spiky(false)
      .build();

    BerryBushTypes.STRAWBERRY = BerryBushType
      .builder("strawberry")
      .fruit(() -> ItemFoodTFC.get(Food.STRAWBERRY))
      .harvest(Month.MARCH, 3)
      .temp(5f, 28f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.SMALL)
      .spiky(false)
      .build();

    BerryBushTypes.WINTERGREEN_BERRY = BerryBushType
      .builder("wintergreen_berry")
      .fruit(() -> ItemFoodTFC.get(Food.WINTERGREEN_BERRY))
      .harvest(Month.AUGUST, 2)
      .temp(-5f, 17f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.SMALL)
      .spiky(false)
      .build();

    BerryBushTypes.TEA = BerryBushType
      .builder("tea")
      .fruit(() -> ItemFoodTFC.get(Food.TEA))
      .harvest(Month.MAY, 4)
      .temp(5f, 35f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.LARGE)
      .spiky(true)
      .build();

    BerryBushTypes.PINEAPPLE = BerryBushType
      .builder("pineapple")
      .fruit(() -> ItemsFL.getFood(FoodFL.PINEAPPLE))
      .harvest(Month.JANUARY, 12)
      .temp(-5f, 17f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.LARGE)
      .spiky(true)
      .build();

    BerryBushTypes.ALLSPICE = BerryBushType
      .builder("allspice")
      .fruit(() -> ItemFoodTFCF.get(ItemsTFCF.ALLSPICE))
      .harvest(Month.AUGUST, 2)
      .temp(0f, 35f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.LARGE)
      .spiky(false)
      .build();

    BerryBushTypes.CLOVE = BerryBushType
      .builder("clove")
      .fruit(() -> ItemFoodTFCF.get(ItemsTFCF.CLOVE))
      .harvest(Month.AUGUST, 2)
      .temp(0f, 35f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.LARGE)
      .spiky(false)
      .build();

    BerryBushTypes.CURRY_LEAF = BerryBushType
      .builder("curry_leaf")
      .fruit(() -> ItemFoodTFCF.get(ItemsTFCF.CURRY_LEAF))
      .harvest(Month.AUGUST, 2)
      .temp(5f, 45f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.LARGE)
      .spiky(false)
      .build();

    BerryBushTypes.STAR_ANISE = BerryBushType
      .builder("star_anise")
      .fruit(() -> ItemFoodTFCF.get(ItemsTFCF.STAR_ANISE))
      .harvest(Month.AUGUST, 2)
      .temp(0f, 25f)
      .rain(100f, 400f)
      .growthTime(0.8f)
      .size(EnumBushSize.LARGE)
      .spiky(false)
      .build();
  }
}
