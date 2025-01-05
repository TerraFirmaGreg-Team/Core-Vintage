package su.terrafirmagreg.modules.core.plugin.gregtech.stonetype;


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
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Breccia;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Catlinite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Chalk;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Chert;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Claystone;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Conglomerate;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Dacite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Dolomite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Gabbro;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Gneiss;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Komatiite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Limestone;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Mudstone;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Novaculite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Peridotite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Phyllite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Porphyry;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Rhyolite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Sandstone;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Schist;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Shale;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Siltstone;
import static su.terrafirmagreg.modules.core.plugin.gregtech.material.MaterialsCore.Slate;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreBreccia;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreCatlinite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreChalk;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreChert;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreClaystone;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreConglomerate;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreDacite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreDolomite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreGabbro;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreGneiss;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreKomatiite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreLimestone;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreMudstone;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreNovaculite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.orePeridotite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.orePhyllite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.orePorphyry;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreQuartzite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreRhyolite;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreRockSalt;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreSandstone;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreSchist;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreShale;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreSiltstone;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreSlate;
import static su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler.oreSoapstone;

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
