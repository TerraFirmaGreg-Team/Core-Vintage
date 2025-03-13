package su.terrafirmagreg.modules.core.capabilities.metal;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import static gregtech.api.unification.ore.OrePrefix.block;
import static gregtech.api.unification.ore.OrePrefix.bolt;
import static gregtech.api.unification.ore.OrePrefix.crushed;
import static gregtech.api.unification.ore.OrePrefix.crushedCentrifuged;
import static gregtech.api.unification.ore.OrePrefix.crushedPurified;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.dustImpure;
import static gregtech.api.unification.ore.OrePrefix.dustPure;
import static gregtech.api.unification.ore.OrePrefix.dustSmall;
import static gregtech.api.unification.ore.OrePrefix.dustTiny;
import static gregtech.api.unification.ore.OrePrefix.gear;
import static gregtech.api.unification.ore.OrePrefix.ingot;
import static gregtech.api.unification.ore.OrePrefix.ingotHot;
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
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.ingotDouble;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.toolHeadAxe;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.toolHeadChisel;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.toolHeadFile;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.toolHeadHammer;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.toolHeadHoe;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.toolHeadKnife;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.toolHeadPickaxe;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.toolHeadPropick;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.toolHeadSaw;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.toolHeadSense;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.toolHeadShovel;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.toolHeadSword;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

public class CapabilityHandlerMetal {

  public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>();
  public static final Map<OrePrefix, Integer> ORE_DICT_METAL_ITEMS = new LinkedHashMap<>();

  public static void init() {
    CUSTOM_ITEMS.put(IIngredient.of(Blocks.IRON_BARS),
      () -> CapabilityProviderMetal.of(Metal.WROUGHT_IRON, 25, true));

    // Register ore dict prefix values
    ORE_DICT_METAL_ITEMS.put(nugget, 16);
    ORE_DICT_METAL_ITEMS.put(ingot, 144);
    ORE_DICT_METAL_ITEMS.put(ingotHot, 144);
    ORE_DICT_METAL_ITEMS.put(ingotDouble, 288);
    ORE_DICT_METAL_ITEMS.put(plate, 144);
    ORE_DICT_METAL_ITEMS.put(plateDouble, 288);
    ORE_DICT_METAL_ITEMS.put(dustTiny, 16); //7
    ORE_DICT_METAL_ITEMS.put(dustSmall, 36); //17
    ORE_DICT_METAL_ITEMS.put(dustImpure, 120);
    ORE_DICT_METAL_ITEMS.put(crushed, 120);
    ORE_DICT_METAL_ITEMS.put(crushedPurified, 124);
    ORE_DICT_METAL_ITEMS.put(crushedCentrifuged, 134);
    ORE_DICT_METAL_ITEMS.put(dustPure, 134);
    ORE_DICT_METAL_ITEMS.put(dust, 144);
    ORE_DICT_METAL_ITEMS.put(stick, 72);
    ORE_DICT_METAL_ITEMS.put(stickLong, 144);
    ORE_DICT_METAL_ITEMS.put(bolt, 36);
    ORE_DICT_METAL_ITEMS.put(screw, 36);
    ORE_DICT_METAL_ITEMS.put(gear, 576);
    ORE_DICT_METAL_ITEMS.put(block, 1296);

    ORE_DICT_METAL_ITEMS.put(toolHeadDrill, 576);
    ORE_DICT_METAL_ITEMS.put(toolHeadChainsaw, 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadWrench, 576);
    ORE_DICT_METAL_ITEMS.put(toolHeadBuzzSaw, 576);
    ORE_DICT_METAL_ITEMS.put(toolHeadScrewdriver, 144);
    ORE_DICT_METAL_ITEMS.put(toolHeadSword, 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadPickaxe, 432);
    ORE_DICT_METAL_ITEMS.put(toolHeadShovel, 144);
    ORE_DICT_METAL_ITEMS.put(toolHeadAxe, 432);
    ORE_DICT_METAL_ITEMS.put(toolHeadHoe, 144);
    ORE_DICT_METAL_ITEMS.put(toolHeadSense, 432);
    ORE_DICT_METAL_ITEMS.put(toolHeadFile, 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadHammer, 864);
    ORE_DICT_METAL_ITEMS.put(toolHeadSaw, 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadKnife, 144);
    ORE_DICT_METAL_ITEMS.put(toolHeadPropick, 144);
    ORE_DICT_METAL_ITEMS.put(toolHeadChisel, 144);

//    ORE_DICT_METAL_ITEMS.put(toolAxe.name(), 432);
  }

  @Nullable
  public static ICapabilityProvider getCustom(ItemStack stack) {
    for (var entry : CUSTOM_ITEMS.entrySet()) {
      if (entry.getKey().testIgnoreCount(stack)) {
        return entry.getValue().get();
      }
    }
    Item item = stack.getItem();

    if (item instanceof ICapabilityMetal capability) {
      return CapabilityProviderMetal.of(capability.getMetal(stack), capability.getSmeltAmount(stack), capability.canMelt(stack));
    }

    if (item instanceof ItemBlock itemBlock && itemBlock.getBlock() instanceof ICapabilityMetal capability) {
      return CapabilityProviderMetal.of(capability.getMetal(stack), capability.getSmeltAmount(stack), capability.canMelt(stack));
    }
    return getMetalItemFromOre(stack);

  }

  @Nullable
  private static ICapabilityProvider getMetalItemFromOre(ItemStack stack) {
    var unificationEntry = OreDictUnifier.getUnificationEntry(stack);
    return ORE_DICT_METAL_ITEMS.entrySet().stream()
      .filter(entry -> unificationEntry != null)
      .filter(entry -> unificationEntry.orePrefix.equals(entry.getKey()))
      .flatMap(entry -> {
        var material = unificationEntry.material;
        if (material != null) {
          return TFCRegistries.METALS.getValuesCollection().stream()
            .filter(m -> material.getName().equals(m.getRegistryName().getPath()))
            .map(m -> CapabilityProviderMetal.of(m, entry.getValue(), true));
        }
        return null;
      })
      .findFirst()
      .orElse(null);
//    return OreDictUtils.getOreNames(stack).stream()
//      .map(CapabilityHandlerMetal::getMetalItemFromOreDict)
//      .filter(Objects::nonNull)
//      .findFirst()
//      .orElse(null);
  }

//  @Nullable
//  private static ICapabilityProvider getMetalItemFromOreDict(String oreDict) {
//    return ORE_DICT_METAL_ITEMS.entrySet().stream()
//      .filter(entry -> oreDict.startsWith(entry.getKey()))
//      .flatMap(entry -> TFCRegistries.METALS.getValuesCollection().stream()
//        .filter(m -> oreDict.equals(OreDictUtils.toString(entry.getKey(), m.getRegistryName().getPath())))
//        .map(m -> CapabilityProviderMetal.of(m, entry.getValue(), true)))
//      .findFirst()
//      .orElse(null);
//  }
}
