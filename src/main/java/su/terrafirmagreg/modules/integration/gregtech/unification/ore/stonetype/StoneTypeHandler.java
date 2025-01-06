package su.terrafirmagreg.modules.integration.gregtech.unification.ore.stonetype;


import su.terrafirmagreg.api.library.Triple;

import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static gregtech.api.unification.material.Materials.Andesite;
import static gregtech.api.unification.material.Materials.Basalt;
import static gregtech.api.unification.material.Materials.Diorite;
import static gregtech.api.unification.material.Materials.Granite;
import static gregtech.api.unification.material.Materials.Marble;
import static gregtech.api.unification.material.Materials.Quartzite;
import static gregtech.api.unification.material.Materials.RockSalt;
import static gregtech.api.unification.material.Materials.Soapstone;
import static gregtech.api.unification.ore.OrePrefix.oreAndesite;
import static gregtech.api.unification.ore.OrePrefix.oreBasalt;
import static gregtech.api.unification.ore.OrePrefix.oreDiorite;
import static gregtech.api.unification.ore.OrePrefix.oreGranite;
import static gregtech.api.unification.ore.OrePrefix.oreMarble;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Breccia;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Catlinite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Chalk;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Chert;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Claystone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Conglomerate;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Dacite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Dolomite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Gabbro;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Gneiss;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Komatiite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Limestone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Mudstone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Novaculite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Peridotite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Phyllite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Porphyry;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Rhyolite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Sandstone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Schist;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Shale;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Siltstone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Slate;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreBreccia;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreCatlinite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreChalk;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreChert;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreClaystone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreConglomerate;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreDacite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreDolomite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreGabbro;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreGneiss;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreKomatiite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreLimestone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreMudstone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreNovaculite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.orePeridotite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.orePhyllite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.orePorphyry;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreQuartzite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreRhyolite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreRockSalt;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreSandstone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreSchist;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreShale;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreSiltstone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreSlate;
import static su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler.oreSoapstone;

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

    final AtomicInteger idCounter = new AtomicInteger(16);

    for (Triple<String, OrePrefix, Material> triple : rockTypes) {
      new StoneType(
        idCounter.getAndIncrement(), "tfc_" + triple.getLeft(), SoundType.STONE, triple.getMiddle(), triple.getRight(),
        () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("tfc:raw/" + triple.getLeft())).getDefaultState(),
        state -> state.getBlock() == ForgeRegistries.BLOCKS.getValue(new ResourceLocation("tfc:raw/" + triple.getLeft())),
        false
      );
    }
  }
}
