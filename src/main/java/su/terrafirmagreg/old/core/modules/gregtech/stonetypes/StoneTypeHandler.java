package su.terrafirmagreg.old.core.modules.gregtech.stonetypes;

import su.terrafirmagreg.old.core.util.Triple;

import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;

import java.util.LinkedHashSet;
import java.util.Set;

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
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Breccia;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Catlinite;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Chalk;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Chert;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Claystone;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Conglomerate;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Dacite;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Dolomite;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Gabbro;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Gneiss;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Komatiite;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Limestone;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Mudstone;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Novaculite;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Peridotite;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Phyllite;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Porphyry;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Rhyolite;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Sandstone;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Schist;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Shale;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Siltstone;
import static su.terrafirmagreg.old.core.modules.gregtech.material.TFGMaterials.Slate;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreBreccia;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreCatlinite;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreChalk;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreChert;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreClaystone;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreConglomerate;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreDacite;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreDolomite;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreGabbro;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreGneiss;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreKomatiite;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreLimestone;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreMudstone;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreNovaculite;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.orePeridotite;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.orePhyllite;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.orePorphyry;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreQuartzite;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreRhyolite;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreRockSalt;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreSandstone;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreSchist;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreShale;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreSiltstone;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreSlate;
import static su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix.oreSoapstone;

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
        counter, "tfc_" + triple.getLeft(), SoundType.STONE, triple.getMiddle(), triple.getRight(),
        () -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation("tfc:raw/" + triple.getLeft())).getDefaultState(),
        state -> state.getBlock() == ForgeRegistries.BLOCKS.getValue(new ResourceLocation("tfc:raw/" + triple.getLeft())),
        false
      );

      counter++;
    }
  }
}
