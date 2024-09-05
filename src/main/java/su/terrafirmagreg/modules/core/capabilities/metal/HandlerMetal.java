package su.terrafirmagreg.modules.core.capabilities.metal;

import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.oredict.OreDictionary;


import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HandlerMetal {

  //Used inside CT, set custom IMetalItem for items outside TFC
  public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>(); //Used inside CT, set custom IMetalItem for items outside TFC

  public static final Map<String, Metal.ItemType> ORE_DICT_METAL_ITEMS = new LinkedHashMap<>();

  public static void init() {
    CUSTOM_ITEMS.put(IIngredient.of(Blocks.IRON_BARS),
        () -> new ProviderMetal(Metal.WROUGHT_IRON, 25, true));

    // Register ore dict prefix values
    ORE_DICT_METAL_ITEMS.put("ingotDouble", Metal.ItemType.DOUBLE_INGOT);
    ORE_DICT_METAL_ITEMS.put("ingot", Metal.ItemType.INGOT);
    ORE_DICT_METAL_ITEMS.put("sheetDouble", Metal.ItemType.DOUBLE_SHEET);
    ORE_DICT_METAL_ITEMS.put("sheet", Metal.ItemType.SHEET);
    ORE_DICT_METAL_ITEMS.put("scrap", Metal.ItemType.SCRAP);
    ORE_DICT_METAL_ITEMS.put("dust", Metal.ItemType.DUST);
    ORE_DICT_METAL_ITEMS.put("nugget", Metal.ItemType.NUGGET);
  }

  @Nullable
  public static ICapabilityProvider getCustom(ItemStack stack) {
    for (var entry : CUSTOM_ITEMS.entrySet()) {
      if (entry.getKey().testIgnoreCount(stack)) {
        return entry.getValue().get();
      }
    }
    // Try using ore dict prefix-suffix common values (ie: ingotCopper)
    int[] ids = OreDictionary.getOreIDs(stack);
    for (int id : ids) {
      ICapabilityProvider handler = getMetalItemFromOreDict(OreDictionary.getOreName(id));
      if (handler != null) {
        return handler;
      }
    }
    return null;
  }

  @Nullable
  private static ICapabilityProvider getMetalItemFromOreDict(String oreDict) {
    for (String oreName : ORE_DICT_METAL_ITEMS.keySet()) {
      if (oreDict.startsWith(oreName)) {
        //noinspection ConstantConditions
        return TFCRegistries.METALS.getValuesCollection().stream()
            .filter(metal -> oreDict.equals(
                OreDictUtils.toString(oreName, metal.getRegistryName().getPath())))
            .findFirst()
            .map(metal -> {
              Metal.ItemType type = ORE_DICT_METAL_ITEMS.get(oreName);
              return new ProviderMetal(metal, type.getSmeltAmount(), true);
            }).orElse(null);
      }
    }
    return null;
  }
}
