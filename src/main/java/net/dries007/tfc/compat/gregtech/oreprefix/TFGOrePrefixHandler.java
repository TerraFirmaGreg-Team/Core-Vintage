package net.dries007.tfc.compat.gregtech.oreprefix;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;

import java.util.Arrays;
import java.util.List;

public class TFGOrePrefixHandler {
    public static void init() {

        // Register various OrePrefixes in GTCEu.
        MetaItems.addOrePrefix(TFGOrePrefix.toolHeadSword);
        MetaItems.addOrePrefix(TFGOrePrefix.toolHeadPickaxe);
        MetaItems.addOrePrefix(TFGOrePrefix.toolHeadShovel);
        MetaItems.addOrePrefix(TFGOrePrefix.toolHeadAxe);
        MetaItems.addOrePrefix(TFGOrePrefix.toolHeadHoe);
        MetaItems.addOrePrefix(TFGOrePrefix.toolHeadSense);
        MetaItems.addOrePrefix(TFGOrePrefix.toolHeadFile);
        MetaItems.addOrePrefix(TFGOrePrefix.toolHeadHammer);
        MetaItems.addOrePrefix(TFGOrePrefix.toolHeadSaw);

        MetaItems.addOrePrefix(TFGOrePrefix.toolHeadKnife);
        MetaItems.addOrePrefix(TFGOrePrefix.toolHeadPropick);
        MetaItems.addOrePrefix(TFGOrePrefix.toolHeadChisel);

        MetaItems.addOrePrefix(TFGOrePrefix.oreChunk);
        MetaItems.addOrePrefix(TFGOrePrefix.ingotDouble);
        MetaItems.addOrePrefix(TFGOrePrefix.ingotTriple);
        MetaItems.addOrePrefix(TFGOrePrefix.ingotHex);

        // Set HasMold = true for various OrePrefixes
        final List<OrePrefix> orePrefixesThatHaveMold = Arrays.asList(
                OrePrefix.ingot,
                TFGOrePrefix.toolHeadSword,
                TFGOrePrefix.toolHeadPickaxe,
                TFGOrePrefix.toolHeadShovel,
                TFGOrePrefix.toolHeadAxe,
                TFGOrePrefix.toolHeadHoe,
                TFGOrePrefix.toolHeadSense,
                // TFGOrePrefix.toolHeadFile,
                TFGOrePrefix.toolHeadHammer,
                TFGOrePrefix.toolHeadSaw,
                TFGOrePrefix.toolHeadKnife,
                TFGOrePrefix.toolHeadPropick,
                TFGOrePrefix.toolHeadChisel
        );

        for (var item : orePrefixesThatHaveMold) {
            var extendedOrePrefix = (IOrePrefixExtension) item;

            extendedOrePrefix.setHasMold(true);
        }

        // Set ShouldHasMetalCapability = true for various OrePrefixes
        final List<OrePrefix> orePrefixesThatHaveMetalCapability = Arrays.asList(
                OrePrefix.plate,
                TFGOrePrefix.ingotDouble,
                TFGOrePrefix.ingotTriple,
                TFGOrePrefix.ingotHex
        );

        for (var item : orePrefixesThatHaveMold) {
            var extendedOrePrefix = (IOrePrefixExtension) item;

            extendedOrePrefix.setShouldHasMetalCapability(true);
        }

        for (var item : orePrefixesThatHaveMetalCapability) {
            var extendedOrePrefix = (IOrePrefixExtension) item;

            extendedOrePrefix.setShouldHasMetalCapability(true);
        }

        //

    }
}
