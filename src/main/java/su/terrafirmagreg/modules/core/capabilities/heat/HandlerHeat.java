package su.terrafirmagreg.modules.core.capabilities.heat;

import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.plant.api.types.type.PlantTypes;
import su.terrafirmagreg.modules.plant.init.BlocksPlant;
import su.terrafirmagreg.modules.wood.init.ItemsWood;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.eerussianguy.firmalife.registry.ItemsFL;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.blocks.BlocksTFCF;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFCF;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemsTFCF;
import net.dries007.tfc.types.TreesTFCF;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HandlerHeat {

  //Used inside CT, set custom IItemHeat for items outside TFC
  public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>();

  public static void init() {
    //register heat on vanilla egg for cooking
    CUSTOM_ITEMS.put(IIngredient.of(Items.EGG),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(Items.CLAY_BALL),
                     () -> new ProviderHeat(1, 1599));

    CUSTOM_ITEMS.put(IIngredient.of(Items.STICK),
                     () -> new ProviderHeat(1, 40));

    CUSTOM_ITEMS.put(IIngredient.of("blockClay"),
                     () -> new ProviderHeat(1, 600));

    CUSTOM_ITEMS.put(IIngredient.of("terracotta"),
                     () -> new ProviderHeat(1, 1200));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsCore.GLASS_SHARD),
                     () -> new ProviderHeat(1, 1000));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsWood.STICK_BUNCH),
                     () -> new ProviderHeat(1, 200));

    CUSTOM_ITEMS.put(IIngredient.of(Blocks.IRON_BARS),
                     () -> new ProviderHeat(Metal.WROUGHT_IRON.getSpecificHeat(),
                                            Metal.WROUGHT_IRON.getMeltTemp()));

    CUSTOM_ITEMS.put(
      IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.ARTISTS_CONK)),
      () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(
      IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.SULPHUR_SHELF)),
      () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(
      IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.TURKEY_TAIL)),
      () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(
      IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.PORCINI)),
      () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(
      IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.AMANITA)),
      () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(
                       BlocksPlant.PLANT.get(PlantTypes.BLACK_POWDERPUFF)),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(
      IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.CHANTERELLE)),
      () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(
      IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.DEATH_CAP)),
      () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(
      IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.GIANT_CLUB)),
      () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(
                       BlocksPlant.PLANT.get(PlantTypes.PARASOL_MUSHROOM)),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(
      IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.STINKHORN)),
      () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(
                       BlocksPlant.PLANT.get(PlantTypes.WEEPING_MILK_CAP)),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(
      IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.WOOD_BLEWIT)),
      () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(
      IIngredient.of(BlocksPlant.PLANT.get(PlantTypes.WOOLLY_GOMPHUS)),
      () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(
      IIngredient.of(BlockLogTFCF.get(TFCRegistries.TREES.getValue(TreesTFCF.EUCALYPTUS))),
      () -> new ProviderHeat(1, 1599));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsCore.STRAW),
                     () -> new ProviderHeat(1, 30));

    CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.TWIG),
                     () -> new ProviderHeat(1, 50));

    CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.DRIFTWOOD),
                     () -> new ProviderHeat(1, 60));

    CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.BONES),
                     () -> new ProviderHeat(1, 425));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.BLACK_TEA),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.GREEN_TEA),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.WHITE_TEA),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CANNABIS_BUD),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CANNABIS_LEAF),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.COCA_LEAF),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.OPIUM_POPPY_BULB),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.PEYOTE),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.TOBACCO_LEAF),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_COFFEA_CHERRIES),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CHAMOMILE_HEAD),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DANDELION_HEAD),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.LABRADOR_TEA_HEAD),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.SUNFLOWER_HEAD),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_BLACK_TEA),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_GREEN_TEA),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_WHITE_TEA),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_BUD),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_LEAF),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_COCA_LEAF),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_OPIUM_POPPY_BULB),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_PEYOTE),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_TOBACCO_LEAF),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CHAMOMILE_HEAD),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_DANDELION_HEAD),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_SUNFLOWER_HEAD),
                     () -> new ProviderHeat(1, 480));

    CUSTOM_ITEMS.put(IIngredient.of(ItemsFL.HONEYCOMB),
                     () -> new ProviderHeat(null, 1, 600));
  }

  @Nullable
  public static ICapabilityProvider getCustom(ItemStack stack) {
    for (var entry : CUSTOM_ITEMS.entrySet()) {
      if (entry.getKey().testIgnoreCount(stack)) {
        return entry.getValue().get();
      }
    }
    return null;
  }
}
