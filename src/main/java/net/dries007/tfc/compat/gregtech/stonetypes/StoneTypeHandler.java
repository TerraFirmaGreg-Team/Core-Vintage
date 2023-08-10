package net.dries007.tfc.compat.gregtech.stonetypes;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import gregtech.integration.jei.basic.OreByProduct;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.rock.type.RockTypes;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariants;
import net.dries007.tfc.api.util.Triple;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;
import java.util.function.Supplier;

import static gregtech.api.unification.material.Materials.*;

import static gregtech.api.unification.ore.OrePrefix.*;
import static net.dries007.tfc.compat.gregtech.material.TFGMaterials.*;
import static net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix.*;

public class StoneTypeHandler {
    public static void init() {

        OreByProduct.addOreByProductPrefix(oreChunk);

        final HashMap<String, Triple<Supplier<Block>, OrePrefix, Material>> rockTypes = new LinkedHashMap<>();

        rockTypes.put("andesite", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.ANDESITE), oreAndesite, Andesite));
        rockTypes.put("basalt", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.BASALT), oreBasalt, Basalt));
        rockTypes.put("chalk", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.CHALK), oreChalk, Chalk));
        rockTypes.put("chert", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.CHERT), oreChert, Chert));
        rockTypes.put("claystone", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.CLAYSTONE), oreClaystone, Claystone));
        rockTypes.put("conglomerate", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.CONGLOMERATE), oreConglomerate, Conglomerate));
        rockTypes.put("dacite", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.DACITE), oreDacite, Dacite));
        rockTypes.put("diorite", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.DIORITE), oreDiorite, Diorite));
        rockTypes.put("dolomite", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.DOLOMITE),oreDolomite, Dolomite));
        rockTypes.put("gabbro", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.GABBRO), oreGabbro, Gabbro));
        rockTypes.put("gneiss", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.GNEISS), oreGneiss, Gneiss));
        rockTypes.put("granite", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.GRANITE), oreGranite, Granite));
        rockTypes.put("limestone", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.LIMESTONE), oreLimestone, Limestone));
        rockTypes.put("marble", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.MARBLE), oreMarble, Marble));
        rockTypes.put("quartzite", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.QUARTZITE), oreQuartzite, Quartzite));
        rockTypes.put("rhyolite", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.RHYOLITE), oreRhyolite, Rhyolite));
        rockTypes.put("schist", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.SCHIST), oreSchist, Schist));
        rockTypes.put("shale", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.SHALE), oreShale, Shale));
        rockTypes.put("slate", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.SLATE), oreSlate, Slate));
        rockTypes.put("phyllite", new Triple<>(() -> TFCStorage.getRockBlock(RockBlockVariants.RAW, RockTypes.PHYLLITE), orePhyllite, Phyllite));

        int counter = 16;

        for (Map.Entry<String, Triple<Supplier<Block>, OrePrefix, Material>> entry : rockTypes.entrySet()) {
            new StoneType(
                    counter, "tfc_" + entry.getKey(), SoundType.STONE, entry.getValue().getMiddle(), entry.getValue().getRight(),
                    () -> entry.getValue().getLeft().get().getDefaultState(),
                    state -> state.getBlock() == entry.getValue().getLeft().get(),
                    false
            );

            counter++;
        }
    }
}
