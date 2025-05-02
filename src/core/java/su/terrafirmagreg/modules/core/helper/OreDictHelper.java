package su.terrafirmagreg.modules.core.helper;

import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import gregtech.api.unification.OreDictUnifier;

import java.util.Arrays;

import static gregtech.api.unification.material.Materials.BismuthBronze;
import static gregtech.api.unification.material.Materials.BlackBronze;
import static gregtech.api.unification.material.Materials.Bronze;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.dustSmall;
import static gregtech.api.unification.ore.OrePrefix.dustTiny;
import static gregtech.api.unification.ore.OrePrefix.ingot;
import static gregtech.api.unification.ore.OrePrefix.nugget;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.api.unification.ore.OrePrefix.plateDouble;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore.ingotDouble;

public class OreDictHelper {

  public static void init() {

    // Vanilla ore dict values
    OreDictionary.registerOre("clay", Items.CLAY_BALL);
    OreDictionary.registerOre("gemCoal", new ItemStack(Items.COAL, 1, 0));
    OreDictionary.registerOre("charcoal", new ItemStack(Items.COAL, 1, 1));
    OreDictionary.registerOre("fireStarter", new ItemStack(Items.FLINT_AND_STEEL, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("fireStarter", new ItemStack(Items.FIRE_CHARGE));
    OreDictionary.registerOre("bowl", Items.BOWL);
    OreDictionary.registerOre("blockClay", Blocks.CLAY);

    //adding oredict to dyeables for dye support. Instead of adding specific recipes color can be changed universally.
    OreDictionary.registerOre("bed", new ItemStack(Items.BED, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("powderConcrete", new ItemStack(Blocks.CONCRETE_POWDER, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("terracotta", new ItemStack(Blocks.HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("terracotta", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE));

    // TFC Florae
    OreDictionary.registerOre("thatch", new ItemStack(Blocks.HAY_BLOCK));
    OreDictionary.registerOre("bale", new ItemStack(Blocks.HAY_BLOCK));
    OreDictionary.registerOre("baleHay", new ItemStack(Blocks.HAY_BLOCK));

    // Flint
    OreDictionary.registerOre("flint", new ItemStack(Items.FLINT));
    OreDictionary.registerOre("itemFlint", new ItemStack(Items.FLINT));

    // Register a name without any items
    OreDictionary.getOres("infiniteFire", true);

    // GregTech
    Arrays.asList(Bronze, BlackBronze, BismuthBronze).forEach(bronze -> {
      Arrays.asList(plate, plateDouble, ingot, ingotDouble, dust, dustTiny, dustSmall, nugget).forEach(ore -> {
        var stack = OreDictUnifier.get(ore, bronze);
        OreDictionary.registerOre(OreDictUtils.toString(ore.name, "Any", "Bronze"), stack);
      });
    });


  }
}
