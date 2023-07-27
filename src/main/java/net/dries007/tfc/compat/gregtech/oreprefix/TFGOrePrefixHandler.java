package net.dries007.tfc.compat.gregtech.oreprefix;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                OrePrefix.nugget,
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

        // Set metal amount for orePrefixes
        Map<OrePrefix, Integer> someMap = new HashMap<>() {{
            put(OrePrefix.nugget, 16);
            put(OrePrefix.ingot, 144);
            put(OrePrefix.plate, 144);
            put(TFGOrePrefix.ingotDouble, 288);
            put(TFGOrePrefix.ingotTriple, 432);
            put(TFGOrePrefix.ingotHex, 864);
            put(TFGOrePrefix.toolHeadSword, 288);
            put(TFGOrePrefix.toolHeadPickaxe, 432);
            put(TFGOrePrefix.toolHeadShovel, 144);
            put(TFGOrePrefix.toolHeadAxe, 432);
            put(TFGOrePrefix.toolHeadHoe, 288);
            put(TFGOrePrefix.toolHeadSense, 432);
            // (TFGOrePrefix.toolHeadFile, 144),
            put(TFGOrePrefix.toolHeadHammer, 864);
            put(TFGOrePrefix.toolHeadSaw, 288);
            put(TFGOrePrefix.toolHeadKnife, 144);
            put(TFGOrePrefix.toolHeadPropick, 432);
            put(TFGOrePrefix.toolHeadChisel, 288);

        }};

        for (var item : someMap.entrySet()) {
            var extendedOrePrefix = (IOrePrefixExtension) item.getKey();

            extendedOrePrefix.setMetalAmount(item.getValue());
        }
    }
}
