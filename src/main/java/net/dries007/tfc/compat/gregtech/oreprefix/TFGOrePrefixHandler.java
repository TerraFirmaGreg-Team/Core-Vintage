package net.dries007.tfc.compat.gregtech.oreprefix;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;

import java.util.Arrays;
import java.util.HashMap;

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
        final var orePrefixesThatHaveMold = Arrays.asList(
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
        final var orePrefixesThatHaveMetalCapability = Arrays.asList(
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
        final var orePrefixToMeltAmount = new HashMap<OrePrefix, Integer>() {{
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

        for (var item : orePrefixToMeltAmount.entrySet()) {
            var extendedOrePrefix = (IOrePrefixExtension) item.getKey();

            extendedOrePrefix.setMetalAmount(item.getValue());
        }

        // Set clay knapping pattern for orePrefixes
        final var orePrefixToClayPattern = new HashMap<OrePrefix, String[]>() {{
            put(OrePrefix.ingot, new String[]{"XXXX", "X  X", "X  X", "X  X", "XXXX"});
            put(TFGOrePrefix.toolHeadSword, new String[]{"XXX  ", "XX   ", "X   X", "X  XX", " XXXX"});
            put(TFGOrePrefix.toolHeadPickaxe, new String[]{"XXXXX", "X   X", " XXX ", "XXXXX"});
            put(TFGOrePrefix.toolHeadShovel, new String[]{"X   X", "X   X", "X   X", "X   X", "XX XX"});
            put(TFGOrePrefix.toolHeadAxe, new String[]{"X XXX", "    X", "     ", "    X", "X XXX"});
            put(TFGOrePrefix.toolHeadHoe, new String[]{"XXXXX", "     ", "  XXX", "XXXXX"});
            put(TFGOrePrefix.toolHeadSense, new String[]{"XXXXX", "X    ", "    X", "  XXX", "XXXXX"});
            // (TFGOrePrefix.toolHeadFile, null),
            put(TFGOrePrefix.toolHeadHammer, new String[]{"XXXXX", "     ", "     ", "XX XX", "XXXXX"});
            put(TFGOrePrefix.toolHeadSaw, new String[]{"XXX  ", "XX   ", "X   X", "    X", "  XXX"});
            put(TFGOrePrefix.toolHeadKnife, new String[]{"XX X", "X  X", "X  X", "X  X", "X  X"});
            put(TFGOrePrefix.toolHeadPropick, new String[]{"XXXXX", "    X", " XXX ", " XXXX", "XXXXX"});
            put(TFGOrePrefix.toolHeadChisel, new String[]{"X X", "X X", "X X", "X X", "X X"});
        }};

        for (var item : orePrefixToClayPattern.entrySet()) {
            var extendedOrePrefix = (IOrePrefixExtension) item.getKey();

            extendedOrePrefix.setClayKnappingPattern(item.getValue());
        }

        // Set rock knapping pattern for orePrefixes
        final var orePrefixToRockPattern = new HashMap<OrePrefix, String[]>() {{
            put(TFGOrePrefix.toolHeadShovel, new String[]{"XXX", "XXX", "XXX", "XXX", " X "});
            put(TFGOrePrefix.toolHeadAxe, new String[]{" X   ", "XXXX ", "XXXXX", "XXXX ", " X   "});
            put(TFGOrePrefix.toolHeadHoe, new String[]{"XXXXX", "   XX"});
            put(TFGOrePrefix.toolHeadHammer, new String[]{"XXXXX", "XXXXX", "  X  "});
            put(TFGOrePrefix.toolHeadKnife, new String[]{"X ", "XX", "XX", "XX", "XX"});
        }};

        for (var item : orePrefixToRockPattern.entrySet()) {
            var extendedOrePrefix = (IOrePrefixExtension) item.getKey();

            extendedOrePrefix.setRockKnappingPattern(item.getValue());
        }
    }
}
