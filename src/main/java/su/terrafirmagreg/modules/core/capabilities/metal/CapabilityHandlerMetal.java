package su.terrafirmagreg.modules.core.capabilities.metal;

import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static gregtech.api.items.toolitem.ToolOreDict.toolAxe;
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

import gregtech.api.unification.OreDictUnifier;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

public class CapabilityHandlerMetal {

  //Used inside CT, set custom IMetalItem for items outside TFC
  public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>();
  //Used inside CT, set custom IMetalItem for items outside TFC
  public static final Map<String, Integer> ORE_DICT_METAL_ITEMS = new LinkedHashMap<>();

  public static void init() {
    CUSTOM_ITEMS.put(IIngredient.of(Blocks.IRON_BARS),
      () -> CapabilityProviderMetal.of(Metal.WROUGHT_IRON, 25, true));

    // Register ore dict prefix values
    ORE_DICT_METAL_ITEMS.put(nugget.name(), 16);
    ORE_DICT_METAL_ITEMS.put(ingot.name(), 144);
    ORE_DICT_METAL_ITEMS.put(ingotDouble.name(), 288);
    ORE_DICT_METAL_ITEMS.put(plate.name(), 144);
    ORE_DICT_METAL_ITEMS.put(plateDouble.name(), 288);
    ORE_DICT_METAL_ITEMS.put(dustTiny.name(), 16); //7
    ORE_DICT_METAL_ITEMS.put(dustSmall.name(), 36); //17
    ORE_DICT_METAL_ITEMS.put(dustImpure.name(), 120);
    ORE_DICT_METAL_ITEMS.put(crushed.name(), 120);
    ORE_DICT_METAL_ITEMS.put(crushedPurified.name(), 124);
    ORE_DICT_METAL_ITEMS.put(crushedCentrifuged.name(), 134);
    ORE_DICT_METAL_ITEMS.put(dustPure.name(), 134);
    ORE_DICT_METAL_ITEMS.put(dust.name(), 144);
    ORE_DICT_METAL_ITEMS.put(stick.name(), 72);
    ORE_DICT_METAL_ITEMS.put(stickLong.name(), 144);
    ORE_DICT_METAL_ITEMS.put(bolt.name(), 36);
    ORE_DICT_METAL_ITEMS.put(screw.name(), 36);
    ORE_DICT_METAL_ITEMS.put(gear.name(), 576);
    ORE_DICT_METAL_ITEMS.put(block.name(), 1296);

    ORE_DICT_METAL_ITEMS.put(toolHeadDrill.name(), 576);
    ORE_DICT_METAL_ITEMS.put(toolHeadChainsaw.name(), 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadWrench.name(), 576);
    ORE_DICT_METAL_ITEMS.put(toolHeadBuzzSaw.name(), 576);
    ORE_DICT_METAL_ITEMS.put(toolHeadScrewdriver.name(), 144);
    ORE_DICT_METAL_ITEMS.put(toolHeadSword.name(), 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadPickaxe.name(), 432);
    ORE_DICT_METAL_ITEMS.put(toolHeadShovel.name(), 144);
    ORE_DICT_METAL_ITEMS.put(toolHeadAxe.name(), 432);
    ORE_DICT_METAL_ITEMS.put(toolHeadHoe.name(), 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadSense.name(), 432);
    ORE_DICT_METAL_ITEMS.put(toolHeadFile.name(), 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadHammer.name(), 864);
    ORE_DICT_METAL_ITEMS.put(toolHeadSaw.name(), 288);
    ORE_DICT_METAL_ITEMS.put(toolHeadKnife.name(), 144);
    ORE_DICT_METAL_ITEMS.put(toolHeadPropick.name(), 432);
    ORE_DICT_METAL_ITEMS.put(toolHeadChisel.name(), 288);

    ORE_DICT_METAL_ITEMS.put(toolAxe.name(), 432);
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
    if (unificationEntry != null) {
      return ORE_DICT_METAL_ITEMS.entrySet().stream()
        .filter(entry -> unificationEntry.orePrefix.name().startsWith(entry.getKey()))
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
    }
    return OreDictUtils.getOreNames(stack).stream()
      .map(CapabilityHandlerMetal::getMetalItemFromOreDict)
      .filter(Objects::nonNull)
      .findFirst()
      .orElse(null);
  }


  @Nullable
  private static ICapabilityProvider getMetalItemFromOreDict(String oreDict) {
    return ORE_DICT_METAL_ITEMS.entrySet().stream()
      .filter(entry -> oreDict.startsWith(entry.getKey()))
      .flatMap(entry -> TFCRegistries.METALS.getValuesCollection().stream()
        .filter(m -> oreDict.equals(OreDictUtils.toString(entry.getKey(), m.getRegistryName().getPath())))
        .map(m -> CapabilityProviderMetal.of(m, entry.getValue(), true)))
      .findFirst()
      .orElse(null);
  }
}
