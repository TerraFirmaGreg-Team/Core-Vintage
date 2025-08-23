package su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore;


import su.terrafirmagreg.api.library.Triple;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.block.SoundType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Andesite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Basalt;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Breccia;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Catlinite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Chalk;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Chert;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Claystone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Conglomerate;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Dacite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Diorite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Dolomite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Gabbro;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Gneiss;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Granite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Komatiite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Limestone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Marble;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Mudstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Novaculite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Peridotite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Phyllite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Porphyry;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Quartzite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Rhyolite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.RockSalt;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Sandstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Schist;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Shale;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Siltstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Slate;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Soapstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreAndesite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreBasalt;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreBreccia;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreCatlinite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreChalk;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreChert;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreClaystone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreConglomerate;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreDacite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreDiorite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreDolomite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreGabbro;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreGneiss;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreGranite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreKomatiite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreLimestone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreMarble;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreMudstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreNovaculite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.orePeridotite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.orePhyllite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.orePorphyry;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreQuartzite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreRhyolite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreRockSalt;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreSandstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreSchist;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreShale;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreSiltstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreSlate;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock.oreSoapstone;

public class StoneTypesRock {

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
        () -> ForgeRegistries.BLOCKS.getValue(ModUtils.resource("tfc", "raw/" + triple.getLeft())).getDefaultState(),
        state -> state.getBlock() == ForgeRegistries.BLOCKS.getValue(ModUtils.resource("tfc", "raw/" + triple.getLeft())),
        false
      );
    }
  }
}
