package su.terrafirmagreg.modules.core.capabilities.metal;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

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
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.ingotDouble;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.toolHeadAxe;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.toolHeadChisel;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.toolHeadFile;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.toolHeadHammer;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.toolHeadHoe;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.toolHeadKnife;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.toolHeadPickaxe;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.toolHeadPropick;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.toolHeadSaw;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.toolHeadSense;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.toolHeadShovel;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.toolHeadSword;

public class CapabilityHandlerMetal {

  //Used inside CT, set custom IMetalItem for items outside TFC
  public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>();
  //Used inside CT, set custom IMetalItem for items outside TFC
  public static final Map<OrePrefix, Integer> ORE_DICT_METAL_ITEMS = new LinkedHashMap<>();

  public static void init() {
    CUSTOM_ITEMS.put(IIngredient.of(Blocks.IRON_BARS),
      () -> new CapabilityProviderMetal(Metal.WROUGHT_IRON, 25, true));

    // Register ore dict prefix values
    ORE_DICT_METAL_ITEMS.put(nugget, 16);
    ORE_DICT_METAL_ITEMS.put(ingot, 144);
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

  @Nullable
  public static ICapabilityProvider getCustom(ItemStack stack) {
    for (var entry : CUSTOM_ITEMS.entrySet()) {
      if (entry.getKey().testIgnoreCount(stack)) {
        return entry.getValue().get();
      }
    }
    return getMetalItemFromOrePrefix(stack);
  }


  @Nullable
  private static ICapabilityProvider getMetalItemFromOrePrefix(ItemStack stack) {
    for (var orePrefix : ORE_DICT_METAL_ITEMS.keySet()) {
      var prefix = OreDictUnifier.getPrefix(stack);
      if (orePrefix == prefix) {
        var materialName = OreDictUnifier.getMaterial(stack).material.toString(); // TODO костыль
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
            return new CapabilityProviderMetal(metal, smeltAmount, true);

          }).orElse(null);
      }
    }
    return null;
  }
}
