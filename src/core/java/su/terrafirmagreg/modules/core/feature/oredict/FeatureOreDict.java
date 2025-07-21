package su.terrafirmagreg.modules.core.feature.oredict;

import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import gregtech.api.unification.OreDictUnifier;
import net.dries007.tfc.util.OreDictionaryHelper;

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
import static su.terrafirmagreg.modules.core.plugin.gregtech.unification.ore.oreprefix.OrePrefixCore.ingotDouble;

public class FeatureOreDict extends BaseFeature {

  @Override
  public void onPostInit() {

    OreDictionaryHelper.init();
    minecraftOreDict();

    // GregTech
    Arrays.asList(Bronze, BlackBronze, BismuthBronze).forEach(bronze -> {
      Arrays.asList(plate, plateDouble, ingot, ingotDouble, dust, dustTiny, dustSmall, nugget).forEach(ore -> {
        var stack = OreDictUnifier.get(ore, bronze);
        OreDictionary.registerOre(OreDictUtils.toString(ore.name, "Any", "Bronze"), stack);
      });
    });


  }

  private void minecraftOreDict() {
    // Vanilla ore dict values
    OreDictionary.registerOre("fireStarter", new ItemStack(Items.FLINT_AND_STEEL, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("fireStarter", new ItemStack(Items.FIRE_CHARGE));

    //adding oredict to dyeables for dye support. Instead of adding specific recipes color can be changed universally.
    OreDictionary.registerOre("bed", new ItemStack(Items.BED, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("powderConcrete", new ItemStack(Blocks.CONCRETE_POWDER, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("terracotta", new ItemStack(Blocks.HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("terracotta", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("blockPackedIce", new ItemStack(Blocks.PACKED_ICE, 1, OreDictionary.WILDCARD_VALUE));

    // Register a name without any items
    OreDictionary.getOres("infiniteFire", true);
  }
}
