package su.terrafirmagreg.core.modules.gregtech.stonetypes;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import su.terrafirmagreg.core.util.Triple;

import java.util.LinkedHashSet;
import java.util.Set;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static su.terrafirmagreg.core.modules.gregtech.material.TFGMaterials.*;
import static su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix.*;

public class StoneTypeHandler {

    @SuppressWarnings("ConstantConditions")
    public static void init() {
        final Set<Triple<String, OrePrefix, Material>> rockTypes = new LinkedHashSet<>() {
            {
                add(new Triple<>("andesite", oreAndesite, Andesite));
                add(new Triple<>("basalt", oreBasalt, Basalt));
                add(new Triple<>("breccia", oreBreccia, Breccia));
                add(new Triple<>("catlinite", oreCatlinite, Catlinite));
                add(new Triple<>("chalk", oreChalk, Chalk));
                add(new Triple<>("chert", oreChert, Chert));
                add(new Triple<>("claystone", oreClaystone, Claystone));
                add(new Triple<>("conglomerate", oreConglomerate, Conglomerate));
                add(new Triple<>("dacite", oreDacite, Dacite));
                add(new Triple<>("diorite", oreDiorite, Diorite));
                add(new Triple<>("dolomite", oreDolomite, Dolomite));
                add(new Triple<>("gabbro", oreGabbro, Gabbro));
                add(new Triple<>("gneiss", oreGneiss, Gneiss));
                add(new Triple<>("granite", oreGranite, Granite));
                add(new Triple<>("komatiite", oreKomatiite, Komatiite));
                add(new Triple<>("limestone", oreLimestone, Limestone));
                add(new Triple<>("marble", oreMarble, Marble));
                add(new Triple<>("mudstone", oreMudstone, Mudstone));
                add(new Triple<>("novaculite", oreNovaculite, Novaculite));
                add(new Triple<>("peridotite", orePeridotite, Peridotite));
                add(new Triple<>("porphyry", orePorphyry, Porphyry));
                add(new Triple<>("quartzite", oreQuartzite, Quartzite));
                add(new Triple<>("rhyolite", oreRhyolite, Rhyolite));
                add(new Triple<>("rocksalt", oreRockSalt, RockSalt));
                add(new Triple<>("sandstone", oreSandstone, Sandstone));
                add(new Triple<>("schist", oreSchist, Schist));
                add(new Triple<>("shale", oreShale, Shale));
                add(new Triple<>("siltstone", oreSiltstone, Siltstone));
                add(new Triple<>("slate", oreSlate, Slate));
                add(new Triple<>("soapstone", oreSoapstone, Soapstone));
                add(new Triple<>("phyllite", orePhyllite, Phyllite));
            }
        };

        int counter = 16;

        for (Triple<String, OrePrefix, Material> triple : rockTypes) {
            new StoneType(
                    counter, "tfc_" + triple.getLeft(), net.minecraft.block.SoundType.STONE, triple.getMiddle(), triple.getRight(),
                    () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("tfc:raw/" + triple.getLeft())).getDefaultState(),
                    state -> state.getBlock() == ForgeRegistries.BLOCKS.getValue(new ResourceLocation("tfc:raw/" + triple.getLeft())),
                    false
            );

            counter++;
        }
    }
}
