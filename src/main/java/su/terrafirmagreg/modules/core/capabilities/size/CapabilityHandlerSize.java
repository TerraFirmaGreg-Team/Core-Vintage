package su.terrafirmagreg.modules.core.capabilities.size;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;

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

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

public class CapabilityHandlerSize {

  //Used inside CT, set custom IItemSize for items outside TFC
  public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new LinkedHashMap<>();
  public static final Map<OrePrefix, Supplier<ICapabilityProvider>> ORE_DICT_METAL_ITEMS = new LinkedHashMap<>();

  public static void init() {
    // Add hardcoded size values for vanilla items
    CUSTOM_ITEMS.put(IIngredient.of(Items.COAL),
      () -> CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT, true)); // Store anywhere stacksize = 32

    CUSTOM_ITEMS.put(IIngredient.of(Items.STICK),
      () -> CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64

    CUSTOM_ITEMS.put(IIngredient.of(Items.CLAY_BALL),
      () -> CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64

    CUSTOM_ITEMS.put(IIngredient.of(Items.BED),
      () -> CapabilityProviderSize.of(Size.LARGE, Weight.VERY_HEAVY, false)); // Store only in chests stacksize = 1

    CUSTOM_ITEMS.put(IIngredient.of(Items.MINECART),
      () -> CapabilityProviderSize.of(Size.LARGE, Weight.VERY_HEAVY, false)); // Store only in chests stacksize = 1

    CUSTOM_ITEMS.put(IIngredient.of(Items.ARMOR_STAND),
      () -> CapabilityProviderSize.of(Size.LARGE, Weight.HEAVY, true)); // Store only in chests stacksize = 4

    CUSTOM_ITEMS.put(IIngredient.of(Items.CAULDRON),
      () -> CapabilityProviderSize.of(Size.LARGE, Weight.LIGHT, true)); // Store only in chests stacksize = 32

    CUSTOM_ITEMS.put(IIngredient.of(Blocks.TRIPWIRE_HOOK),
      () -> CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64

    CUSTOM_ITEMS.put(IIngredient.of(Blocks.TRIPWIRE_HOOK),
      () -> CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64

    ORE_DICT_METAL_ITEMS.put(nugget, () -> CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT));
    ORE_DICT_METAL_ITEMS.put(ingot, () -> CapabilityProviderSize.of(Size.LARGE, Weight.LIGHT));
    ORE_DICT_METAL_ITEMS.put(ingotDouble, () -> CapabilityProviderSize.of(Size.LARGE, Weight.LIGHT));
    ORE_DICT_METAL_ITEMS.put(plate, () -> CapabilityProviderSize.of(Size.LARGE, Weight.LIGHT));
    ORE_DICT_METAL_ITEMS.put(plateDouble, () -> CapabilityProviderSize.of(Size.LARGE, Weight.LIGHT));
    ORE_DICT_METAL_ITEMS.put(dustTiny, () -> CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT));
    ORE_DICT_METAL_ITEMS.put(dustSmall, () -> CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT));
    ORE_DICT_METAL_ITEMS.put(dustImpure, () -> CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT));
    ORE_DICT_METAL_ITEMS.put(crushed, () -> CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT));
    ORE_DICT_METAL_ITEMS.put(crushedPurified, () -> CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT));
    ORE_DICT_METAL_ITEMS.put(crushedCentrifuged, () -> CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT));
    ORE_DICT_METAL_ITEMS.put(dustPure, () -> CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT));
    ORE_DICT_METAL_ITEMS.put(dust, () -> CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT));
    ORE_DICT_METAL_ITEMS.put(stick, () -> CapabilityProviderSize.of(Size.SMALL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(stickLong, () -> CapabilityProviderSize.of(Size.SMALL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(bolt, () -> CapabilityProviderSize.of(Size.SMALL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(screw, () -> CapabilityProviderSize.of(Size.SMALL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(gear, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(block, () -> CapabilityProviderSize.of(Size.LARGE, Weight.LIGHT));

    ORE_DICT_METAL_ITEMS.put(toolHeadDrill, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadChainsaw, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadWrench, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadBuzzSaw, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadScrewdriver, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadSword, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadPickaxe, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadShovel, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadAxe, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadHoe, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadSense, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadFile, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadHammer, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadSaw, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadKnife, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadPropick, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
    ORE_DICT_METAL_ITEMS.put(toolHeadChisel, () -> CapabilityProviderSize.of(Size.NORMAL, Weight.MEDIUM));
  }

  @NotNull
  public static ICapabilityProvider getCustom(ItemStack stack) {

    for (var entry : CUSTOM_ITEMS.entrySet()) {
      if (entry.getKey().testIgnoreCount(stack)) {
        return entry.getValue().get();
      }
    }
    Item item = stack.getItem();

    if (item instanceof ICapabilitySize capability) {
      return CapabilityProviderSize.of(capability.getSize(stack), capability.getWeight(stack), capability.canStack(stack));
    }

    if (item instanceof ItemBlock itemBlock && itemBlock.getBlock() instanceof ICapabilitySize capability) {
      return CapabilityProviderSize.of(capability.getSize(stack), capability.getWeight(stack), capability.canStack(stack));
    }

    if (item instanceof ItemTool || item instanceof ItemSword) {
      return CapabilityProviderSize.of(Size.LARGE, Weight.MEDIUM, true); // Stored only in chests, stacksize should be limited to 1 since it is a tool

    }
    if (item instanceof ItemArmor) {
      return CapabilityProviderSize.of(Size.LARGE, Weight.VERY_HEAVY, true); // Stored only in chests and stacksize = 1

    }
    if (item instanceof ItemBlock itemBlock) {
      Block block = itemBlock.getBlock();

//      if (block instanceof ICapabilitySize capabilitySize) {
//        return capabilitySize;
//      }

      if (block instanceof BlockLadder) {
        return CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT, true); // Fits small vessels and stacksize = 64
      }
      return CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT, true); // Fits small vessels and stacksize = 32

    }
    return getMetalItemFromOre(stack);
  }

  private static ICapabilityProvider getMetalItemFromOre(ItemStack stack) {
    var unificationEntry = OreDictUnifier.getUnificationEntry(stack);
    return ORE_DICT_METAL_ITEMS.entrySet().stream()
      .filter(entry -> unificationEntry != null)
      .filter(entry -> unificationEntry.orePrefix.equals(entry.getKey()))
      .map(entry -> entry.getValue().get())
      .findFirst()
      .orElse(CapabilityProviderSize.of(Size.VERY_SMALL, Weight.VERY_LIGHT, true));

  }
}
