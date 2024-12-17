/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.api.capability.metal;

import su.terrafirmagreg.api.data.Reference;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import gregtech.api.unification.OreDictUnifier;
import net.dries007.tfc.api.capability.DumbStorage;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static gregtech.api.unification.ore.OrePrefix.bolt;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.dustImpure;
import static gregtech.api.unification.ore.OrePrefix.dustSmall;
import static gregtech.api.unification.ore.OrePrefix.dustTiny;
import static gregtech.api.unification.ore.OrePrefix.ingot;
import static gregtech.api.unification.ore.OrePrefix.nugget;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.api.unification.ore.OrePrefix.plateDouble;
import static gregtech.api.unification.ore.OrePrefix.screw;
import static gregtech.api.unification.ore.OrePrefix.stick;
import static gregtech.api.unification.ore.OrePrefix.stickLong;
import static gregtech.api.unification.ore.OrePrefix.toolHeadBuzzSaw;
import static gregtech.api.unification.ore.OrePrefix.toolHeadChainsaw;
import static gregtech.api.unification.ore.OrePrefix.toolHeadDrill;
import static gregtech.api.unification.ore.OrePrefix.toolHeadScrewdriver;
import static gregtech.api.unification.ore.OrePrefix.toolHeadWrench;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.ingotDouble;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.toolHeadAxe;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.toolHeadChisel;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.toolHeadFile;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.toolHeadHammer;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.toolHeadHoe;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.toolHeadKnife;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.toolHeadPickaxe;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.toolHeadPropick;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.toolHeadSaw;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.toolHeadSense;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.toolHeadShovel;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.toolHeadSword;

public final class CapabilityMetalItem {

  public static final ResourceLocation KEY = new ResourceLocation(Reference.TFC, "metal_object");
  public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_METAL_ITEMS = new HashMap<>(); //Used inside CT, set custom IMetalItem for items outside TFC
  public static final Map<gregtech.api.unification.ore.OrePrefix, Integer> ORE_DICT_METAL_ITEMS = new LinkedHashMap<>();
  @CapabilityInject(IMetalItem.class)
  public static Capability<IMetalItem> METAL_OBJECT_CAPABILITY;

  public static void preInit() {
    CapabilityManager.INSTANCE.register(IMetalItem.class, new DumbStorage<>(), MetalItemHandler::new);

  }

  public static void init() {
    CUSTOM_METAL_ITEMS.put(IIngredient.of(Blocks.IRON_BARS), () -> new MetalItemHandler(Metal.WROUGHT_IRON, 25, true));

    // Register ore dict prefix values
    ORE_DICT_METAL_ITEMS.put(nugget, 16);
    ORE_DICT_METAL_ITEMS.put(ingot, 144);
    ORE_DICT_METAL_ITEMS.put(ingotDouble, 288);
    ORE_DICT_METAL_ITEMS.put(plate, 144);
    ORE_DICT_METAL_ITEMS.put(plateDouble, 288);
    ORE_DICT_METAL_ITEMS.put(dustTiny, 16); //7
    ORE_DICT_METAL_ITEMS.put(dustSmall, 36); //17
    ORE_DICT_METAL_ITEMS.put(dustImpure, 120); //17
    ORE_DICT_METAL_ITEMS.put(dust, 144);
    ORE_DICT_METAL_ITEMS.put(stick, 72);
    ORE_DICT_METAL_ITEMS.put(stickLong, 144);
    ORE_DICT_METAL_ITEMS.put(bolt, 36);
    ORE_DICT_METAL_ITEMS.put(screw, 36);

//    ORE_DICT_METAL_ITEMS.put(TFGOrePrefix.ingotTriple, 432);
//    ORE_DICT_METAL_ITEMS.put(TFGOrePrefix.ingotHex, 864);
    ORE_DICT_METAL_ITEMS.put(toolHeadDrill, 576);
    ORE_DICT_METAL_ITEMS.put(toolHeadChainsaw, 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadWrench, 576);
    ORE_DICT_METAL_ITEMS.put(toolHeadBuzzSaw, 576);
    ORE_DICT_METAL_ITEMS.put(toolHeadScrewdriver, 144);
    ORE_DICT_METAL_ITEMS.put(toolHeadSword, 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadPickaxe, 432);
    ORE_DICT_METAL_ITEMS.put(toolHeadShovel, 144);
    ORE_DICT_METAL_ITEMS.put(toolHeadAxe, 432);
    ORE_DICT_METAL_ITEMS.put(toolHeadHoe, 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadSense, 432);
    ORE_DICT_METAL_ITEMS.put(toolHeadFile, 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadHammer, 864);
    ORE_DICT_METAL_ITEMS.put(toolHeadSaw, 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadKnife, 144);
    ORE_DICT_METAL_ITEMS.put(toolHeadPropick, 432);
    ORE_DICT_METAL_ITEMS.put(toolHeadChisel, 288);
  }

  /**
   * Gets the IMetalItem instance from an itemstack, either via capability or via interface
   *
   * @param stack The stack
   * @return The IMetalItem if it exists, or null if it doesn't
   */
  @Nullable
  public static IMetalItem getMetalItem(ItemStack stack) {
    if (!stack.isEmpty()) {
      IMetalItem metal = stack.getCapability(METAL_OBJECT_CAPABILITY, null);
      if (metal != null) {
        return metal;
      } else if (stack.getItem() instanceof IMetalItem metalItem) {
        return metalItem;
      } else if (stack.getItem() instanceof ItemBlock itemBlock && itemBlock.getBlock() instanceof IMetalItem metalItem) {
        return metalItem;
      }
    }
    return null;
  }

  @Nullable
  public static ICapabilityProvider getCustomMetalItem(ItemStack stack) {
    if (!stack.isEmpty()) {
      Set<IIngredient<ItemStack>> itemItemSet = CUSTOM_METAL_ITEMS.keySet();
      for (IIngredient<ItemStack> ingredient : itemItemSet) {
        if (ingredient.testIgnoreCount(stack)) {
          return CUSTOM_METAL_ITEMS.get(ingredient).get();
        }
      }

      return getMetalItemFromOrePrefix(stack);
    }
    return null;
  }

  @Nullable
  private static ICapabilityProvider getMetalItemFromOrePrefix(ItemStack stack) {
    for (var orePrefix : ORE_DICT_METAL_ITEMS.keySet()) {
      var prefix = OreDictUnifier.getPrefix(stack);
      if (orePrefix == prefix) {
        var materialName = OreDictUnifier.getMaterial(stack).material.toString(); //костыль
        //noinspection ConstantConditions
        return TFCRegistries.METALS.getValuesCollection()
                                   .stream()
                                   .filter(metal -> {
                                     var metalName = metal.getRegistryName().getPath();
                                     return materialName.equals(metalName);
                                   })
                                   .findFirst()
                                   .map(metal -> {
                                     int smeltAmount = ORE_DICT_METAL_ITEMS.get(orePrefix);
                                     return new MetalItemHandler(metal, smeltAmount, true);

                                   }).orElse(null);
      }
    }
    return null;
  }
}
