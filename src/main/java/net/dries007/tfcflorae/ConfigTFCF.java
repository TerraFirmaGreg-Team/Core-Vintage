package net.dries007.tfcflorae;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFCF;

@Mod.EventBusSubscriber(modid = TFCF)
public class ConfigTFCF {

  @SubscribeEvent
  public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equals(TFCF)) {
      TFCFlorae.getLog().warn("Config changed");
      ConfigManager.sync(TFCF, Config.Type.INSTANCE);
    }
  }

  @Config(modid = TFCF, category = "general", name = "TFCFlorae - General")
  @Config.LangKey("config." + TFCF + ".general")
  public static final class General {

    @Config.Comment("World generation settings")
    @Config.LangKey("config." + TFCF + ".general.world")
    public static final WorldCFG WORLD = new WorldCFG();

    @Config.Comment("Structure generation settings")
    @Config.LangKey("config." + TFCF + ".general.structures")
    public static final StructuresCFG STRUCTURES = new StructuresCFG();

    public static final class WorldCFG {

      @Config.Comment("The rarity for humus pits to occur. On average 1 / N chunks will have a humus deposit, if the chunk in question is valid for humus to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.humusRarity")
      public int humusRarity = 8;

      @Config.Comment("The rarity for podzol pits to occur. On average 1 / N chunks will have a podzol deposit, if the chunk in question is valid for podzol to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.podzolRarity")
      public int podzolRarity = 1;

      @Config.Comment("The rarity for mud pits to occur. On average 1 / N chunks will have a mud deposit, if the chunk in question is valid for mud to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.mudRarity")
      public int mudRarity = 1;

      @Config.Comment("The rarity for bog iron pits to occur. On average 1 / N chunks will have a bog iron deposit, if the chunk in question is valid for bog iron to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.bogIronRarity")
      public int bogIronRarity = 4;

      @Config.Comment("The rarity for sparse grass to occur. On average 1 / N chunks will have sparse grass, if the chunk in question is valid for sparse grass to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.sparseGrassRarity")
      public int sparseGrassRarity = 1;

      @Config.Comment("The rarity for loamy sand pits to occur. On average 1 / N chunks will have a loamy sand deposit, if the chunk in question is valid for loamy sand to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.loamySandRarity")
      public int loamySandRarity = 8;

      @Config.Comment("The rarity for sandy loam pits to occur. On average 1 / N chunks will have a sandy loam deposit, if the chunk in question is valid for sandy loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.sandyLoamRarity")
      public int sandyLoamRarity = 8;

      @Config.Comment("The rarity for loam pits to occur. On average 1 / N chunks will have a loam deposit, if the chunk in question is valid for loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.loamRarity")
      public int loamRarity = 8;

      @Config.Comment("The rarity for silt loam pits to occur. On average 1 / N chunks will have a silt loam deposit, if the chunk in question is valid for silt loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.siltLoamRarity")
      public int siltLoamRarity = 8;

      @Config.Comment("The rarity for silt pits to occur. On average 1 / N chunks will have a silt deposit, if the chunk in question is valid for silt to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.siltRarity")
      public int siltRarity = 8;

      @Config.Comment("The rarity for sandy clay pits to occur. On average 1 / N chunks will have a sandy clay deposit, if the chunk in question is valid for sandy clay to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.sandyClayRarity")
      public int sandyClayRarity = 40;

      @Config.Comment("The rarity for sandy clay loam pits to occur. On average 1 / N chunks will have a sandy clay loam deposit, if the chunk in question is valid for sandy clay loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.sandyClayLoamRarity")
      public int sandyClayLoamRarity = 40;

      @Config.Comment("The rarity for clay loam pits to occur. On average 1 / N chunks will have a clay loam deposit, if the chunk in question is valid for clay loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.clayLoamRarity")
      public int clayLoamRarity = 40;

      @Config.Comment("The rarity for silty clay loam pits to occur. On average 1 / N chunks will have a silty clay loam deposit, if the chunk in question is valid for silty clay loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.siltyClayLoamRarity")
      public int siltyClayLoamRarity = 40;

      @Config.Comment("The rarity for silty clay pits to occur. On average 1 / N chunks will have a silty clay deposit, if the chunk in question is valid for silty clay to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.siltyClayRarity")
      public int siltyClayRarity = 40;

      @Config.Comment("The rarity for clay humus pits to occur. On average 1 / N chunks will have a clay humus deposit, if the chunk in question is valid for clay humus to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.clayHumusRarity")
      public int clayHumusRarity = 40;

      @Config.Comment("The rarity for sandy earthenware clay pits to occur. On average 1 / N chunks will have a sandy earthenware clay deposit, if the chunk in question is valid for sandy earthenware clay to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.sandyEarthenwareClayRarity")
      public int sandyEarthenwareClayRarity = 150;

      @Config.Comment("The rarity for sandy earthenware clay loam pits to occur. On average 1 / N chunks will have a sandy earthenware clay loam deposit, if the chunk in question is valid for sandy earthenware clay loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.sandyEarthenwareClayLoamRarity")
      public int sandyEarthenwareClayLoamRarity = 150;

      @Config.Comment("The rarity for silty earthenware clay loam pits to occur. On average 1 / N chunks will have a silty earthenware clay loam deposit, if the chunk in question is valid for silty earthenware clay loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.siltyEarthenwareClayLoamRarity")
      public int siltyEarthenwareClayLoamRarity = 150;

      @Config.Comment("The rarity for silty earthenware clay pits to occur. On average 1 / N chunks will have a silty earthenware clay deposit, if the chunk in question is valid for silty earthenware clay to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.siltyEarthenwareClayRarity")
      public int siltyEarthenwareClayRarity = 150;

      @Config.Comment("The rarity for kaolinite clay pits to occur. On average 1 / N chunks will have a kaolinite clay deposit, if the chunk in question is valid for kaolinite clay to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.kaoliniteClayRarity")
      public int kaoliniteClayRarity = 150;

      @Config.Comment("The rarity for sandy kaolinite clay pits to occur. On average 1 / N chunks will have a sandy kaolinite clay deposit, if the chunk in question is valid for sandy kaolinite clay to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.sandyKaoliniteClayRarity")
      public int sandyKaoliniteClayRarity = 150;

      @Config.Comment("The rarity for sandy kaolinite clay loam pits to occur. On average 1 / N chunks will have a sandy kaolinite clay loam deposit, if the chunk in question is valid for sandy kaolinite clay loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.sandyKaoliniteClayLoamRarity")
      public int sandyKaoliniteClayLoamRarity = 150;

      @Config.Comment("The rarity for kaolinite clay loam pits to occur. On average 1 / N chunks will have a kaolinite clay loam deposit, if the chunk in question is valid for kaolinite clay loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.kaoliniteClayLoamRarity")
      public int kaoliniteClayLoamRarity = 150;

      @Config.Comment("The rarity for silty kaolinite clay loam pits to occur. On average 1 / N chunks will have a silty kaolinite clay loam deposit, if the chunk in question is valid for silty kaolinite clay loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.siltyKaoliniteClayLoamRarity")
      public int siltyKaoliniteClayLoamRarity = 150;

      @Config.Comment("The rarity for silty kaolinite clay pits to occur. On average 1 / N chunks will have a silty kaolinite clay deposit, if the chunk in question is valid for silty kaolinite clay to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.siltyKaoliniteClayRarity")
      public int siltyKaoliniteClayRarity = 150;

      @Config.Comment("The rarity for kaolinite clay humus pits to occur. On average 1 / N chunks will have a kaolinite clay humus deposit, if the chunk in question is valid for kaolinite clay humus to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.kaoliniteClayHumusRarity")
      public int kaoliniteClayHumusRarity = 150;

      @Config.Comment("The rarity for sandy stoneware clay pits to occur. On average 1 / N chunks will have a sandy stoneware clay deposit, if the chunk in question is valid for sandy stoneware clay to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.sandyStonewareClayRarity")
      public int sandyStonewareClayRarity = 150;

      @Config.Comment("The rarity for sandy stoneware clay loam pits to occur. On average 1 / N chunks will have a sandy stoneware clay loam deposit, if the chunk in question is valid for sandy stoneware clay loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.sandyStonewareClayLoamRarity")
      public int sandyStonewareClayLoamRarity = 150;

      @Config.Comment("The rarity for silty stoneware clay loam pits to occur. On average 1 / N chunks will have a silty stoneware clay loam deposit, if the chunk in question is valid for silty stoneware clay loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.siltyStonewareClayLoamRarity")
      public int siltyStonewareClayLoamRarity = 150;

      @Config.Comment("The rarity for silty stoneware clay pits to occur. On average 1 / N chunks will have a silty stoneware clay deposit, if the chunk in question is valid for silty stoneware clay to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.siltyStonewareClayRarity")
      public int siltyStonewareClayRarity = 150;

      @Config.Comment("The rarity for coarse dirt pits to occur. On average 1 / N chunks will have a coarse dirt deposit, if the chunk in question is valid for coarse dirt to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.coarseDirtRarity")
      public int coarseDirtRarity = 15;

      @Config.Comment("The rarity for coarse loamy sand pits to occur. On average 1 / N chunks will have a coarse loamy sand deposit, if the chunk in question is valid for coarse loamy sand to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.coarseLoamySandRarity")
      public int coarseLoamySandRarity = 15;

      @Config.Comment("The rarity for coarse sandy loam pits to occur. On average 1 / N chunks will have a coarse sandy loam deposit, if the chunk in question is valid for coarse sandy loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.coarseSandyLoamRarity")
      public int coarseSandyLoamRarity = 15;

      @Config.Comment("The rarity for coarse loam pits to occur. On average 1 / N chunks will have a coarse loam deposit, if the chunk in question is valid for coarse loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.coarseLoamRarity")
      public int coarseLoamRarity = 15;

      @Config.Comment("The rarity for coarse silt loam pits to occur. On average 1 / N chunks will have a coarse silt loam deposit, if the chunk in question is valid for coarse silt loam to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.coarseSiltLoamRarity")
      public int coarseSiltLoamRarity = 15;

      @Config.Comment("The rarity for coarse silt pits to occur. On average 1 / N chunks will have a coarse silt deposit, if the chunk in question is valid for coarse silt to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.coarseSiltRarity")
      public int coarseSiltRarity = 15;

      @Config.Comment("The rarity for coarse humus pits to occur. On average 1 / N chunks will have a coarse humus deposit, if the chunk in question is valid for coarse humus to spawn.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.coarseHumusRarity")
      public int coarseHumusRarity = 15;

      @Config.Comment("The number of attempts per chunk to spawn bones.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.groundcoverBonesFrequency")
      public int groundcoverBonesFrequency = 3;

      @Config.Comment("The number of attempts per chunk to spawn driftwood.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.groundcoverDriftwoodFrequency")
      public int groundcoverDriftwoodFrequency = 4;

      @Config.Comment("The number of attempts per chunk to spawn flint.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.groundcoverFlintFrequency")
      public int groundcoverFlintFrequency = 6;

      @Config.Comment("The number of attempts per chunk to spawn ore deposits.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.groundcoverOreDepositFrequency")
      public int groundcoverOreDepositFrequency = 18;

      @Config.Comment("The number of attempts per chunk to spawn pinecones.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.groundcoverPineconeFrequency")
      public int groundcoverPineconeFrequency = 8;

      @Config.Comment("The number of attempts per chunk to spawn rocks.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.groundcoverRockFrequency")
      public int groundcoverRockFrequency = 10;

      @Config.Comment("The number of attempts per chunk to spawn seashells.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.groundcoverSeashellFrequency")
      public int groundcoverSeashellFrequency = 50;

      @Config.Comment("The number of attempts per chunk to spawn twigs.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.groundcoverTwigFrequency")
      public int groundcoverTwigFrequency = 11;

      @Config.Comment("The fresh water plant frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.waterCount")
      public float waterCount = 12f;

      @Config.Comment("The tall fresh water plant frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.waterTallCount")
      public float waterTallCount = 8f;

      @Config.Comment("The ocean water plant frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.waterSeaCount")
      public float waterSeaCount = 12f;

      @Config.Comment("The tall ocean water plant frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.waterTallSeaCount")
      public float waterTallSeaCount = 7f;

      @Config.Comment("The ocean water algae frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.waterSeaAlgaeCount")
      public float waterSeaAlgaeCount = 0.3f;

      @Config.Comment("The hanging plant frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.hangingCount")
      public float hangingCount = 1f;

      @Config.Comment("The bearded moss plant frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.beardedMossCount")
      public float beardedMossCount = 1f;

      @Config.Comment("The grass plant frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.grassCount")
      public float grassCount = 12f;

      @Config.Comment("The tall grass plant frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.tallGrassCount")
      public float tallGrassCount = 8f;

      @Config.Comment("The tall plant frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.tallPlantCount")
      public float tallPlantCount = 3f;

      @Config.Comment("The epiphyte plant frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.epiphyteCount")
      public float epiphyteCount = 3f;

      @Config.Comment("The standard plant frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.standardCount")
      public float standardCount = 3f;

      @Config.Comment("The underground fungi generation frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.fungiUndergroundCount")
      public float fungiUndergroundCount = 0.5f;

      @Config.Comment("The underground hanging vines generation frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.hangingVinesUndergroundCount")
      public float hangingVinesUndergroundCount = 1f;

      @Config.Comment("The underground creeping vines generation frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.creepingVinesUndergroundCount")
      public float creepingVinesUndergroundCount = 0.5f;

      @Config.Comment("The underground creeping plant generation frequency. Higher value equals higher frequency.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + TFCF + ".general.world.creepingUndergroundCount")
      public float creepingUndergroundCount = 5f;

      @Config.Comment("The rarity for bamboo trees to generate. On average 1 / N chunks will have a bamboo tree, if the chunk in question is valid for bamboo to generate.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.bambooRarity")
      public int bambooRarity = 15;

      @Config.Comment("The rarity for cinnamon trees to generate. On average 1 / N chunks will have a cinnamon tree, if the chunk in question is valid for cinnamon to generate.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.world.cinnamonRarity")
      public int cinnamonRarity = 100;

            /*@Config.Comment("Enable podzol generation?")
            @Config.LangKey("config." + MODID + ".general.world.enablePodzolGen")
            public boolean enablePodzolGen = true;*/

      @Config.Comment("Enable mud generation?")
      @Config.LangKey("config." + TFCF + ".general.world.enableMudGen")
      public boolean enableMudGen = true;

      @Config.Comment("Enable sand generation?")
      @Config.LangKey("config." + TFCF + ".general.world.enableSandGen")
      public boolean enableSandGen = true;

      @Config.Comment("Enable plant generation?")
      @Config.LangKey("config." + TFCF + ".general.world.enablePlantWorldGen")
      public boolean enablePlantWorldGen = true;

      @Config.Comment("Enable underground plant generation?")
      @Config.LangKey("config." + TFCF + ".general.world.enableUndergroundPlantWorldGen")
      public boolean enableUndergroundPlantWorldGen = true;

      @Config.Comment("Enable underground lightstone generation?")
      @Config.LangKey("config." + TFCF + ".general.world.enableLightstoneWorldGen")
      public boolean enableLightstoneWorldGen = true;

      @Config.Comment("Enable ocean glow plant generation?")
      @Config.LangKey("config." + TFCF + ".general.world.enableOceanGlowPlantWorldGen")
      public boolean enableOceanGlowPlantWorldGen = true;

      @Config.Comment("Enable mossy raw stone generation?")
      @Config.LangKey("config." + TFCF + ".general.world.enableMossyRawWorldGen")
      public boolean enableMossyRawWorldGen = true;

      @Config.Comment("Enable coral generation?")
      @Config.LangKey("config." + TFCF + ".general.world.enableCoralWorldGen")
      public boolean enableCoralWorldGen = true;

      @Config.Comment("Enable gourd generation?")
      @Config.LangKey("config." + TFCF + ".general.world.enableGourdWorldGen")
      public boolean enableGourdWorldGen = true;

      @Config.Comment("Enable vines?")
      @Config.LangKey("config." + TFCF + ".general.world.enableAllVines")
      public boolean enableAllVines = true;

      @Config.Comment("Enable bone groundcover?")
      @Config.LangKey("config." + TFCF + ".general.world.enableGroundcoverBones")
      public boolean enableGroundcoverBones = true;

      @Config.Comment("Enable driftwood groundcover?")
      @Config.LangKey("config." + TFCF + ".general.world.enableGroundcoverDriftwood")
      public boolean enableGroundcoverDriftwood = true;

      @Config.Comment("Enable flint groundcover?")
      @Config.LangKey("config." + TFCF + ".general.world.enableGroundcoverFlint")
      public boolean enableGroundcoverFlint = true;

      @Config.Comment("Enable pinecone groundcover?")
      @Config.LangKey("config." + TFCF + ".general.world.enableGroundcoverPinecone")
      public boolean enableGroundcoverPinecone = true;

      @Config.Comment("Enable seashell groundcover?")
      @Config.LangKey("config." + TFCF + ".general.world.enableGroundcoverSeashell")
      public boolean enableGroundcoverSeashell = true;

      @Config.Comment("Enable twig groundcover?")
      @Config.LangKey("config." + TFCF + ".general.world.enableGroundcoverTwig")
      public boolean enableGroundcoverTwig = true;

      @Config.Comment("Enable soil pit world generation?")
      @Config.LangKey("config." + TFCF + ".general.world.enableSoilPits")
      public boolean enableSoilPits = true;

      @Config.Comment("Enable mesa clay strata world generation within mesa biomes?")
      @Config.LangKey("config." + TFCF + ".general.world.enableMesaStrata")
      public boolean enableMesaStrata = true;

      @Config.Comment("Enable bamboo and cinnamon tree world generation?")
      @Config.LangKey("config." + TFCF + ".general.world.enableTrees")
      public boolean enableTrees = true;

      @Config.Comment("Enable all special block types? (Loam, humus, silt etc.)")
      @Config.LangKey("config." + TFCF + ".general.world.enableAllBlockTypes")
      public boolean enableAllBlockTypes = true;

      @Config.Comment("Enable rock types for every kind of block?")
      @Config.LangKey("config." + TFCF + ".general.world.enableAllBlockRockTypes")
      public boolean enableAllBlockRockTypes = false;

      @Config.Comment("Enable all world generation?")
      @Config.LangKey("config." + TFCF + ".general.world.enableAllWorldGen")
      public boolean enableAllWorldGen = true;

      @Config.Comment("Enable bog iron blocks?")
      @Config.LangKey("config." + TFCF + ".general.world.enableAllBogIron")
      public boolean enableAllBogIron = true;

      @Config.Comment("Enable coarse soil blocks?")
      @Config.LangKey("config." + TFCF + ".general.world.enableAllCoarse")
      public boolean enableAllCoarse = true;

      @Config.Comment("Enable podzol blocks?")
      @Config.LangKey("config." + TFCF + ".general.world.enableAllPodzol")
      public boolean enableAllPodzol = true;

      @Config.Comment("Enable sparse grass blocks?")
      @Config.LangKey("config." + TFCF + ".general.world.enableAllSparseGrass")
      public boolean enableAllSparseGrass = true;

      @Config.Comment("Enable special soil blocks?")
      @Config.LangKey("config." + TFCF + ".general.world.enableAllSpecialSoil")
      public boolean enableAllSpecialSoil = true;

      @Config.Comment("Enable special soil farmland blocks?")
      @Config.LangKey("config." + TFCF + ".general.world.enableAllFarmland")
      public boolean enableAllFarmland = true;

      @Config.Comment("Enable water plants?")
      @Config.LangKey("config." + TFCF + ".general.world.enableAllWaterPlants")
      public boolean enableAllWaterPlants = true;
    }

    public static final class StructuresCFG {

      @Config.Comment(value = "Activate structure generation?")
      @Config.LangKey("config." + TFCF + ".general.structures.activateStructureGeneration")
      public boolean activateStructureGeneration = true;

      @Config.Comment(value = "This value multiplies the generation chance. E.g. the spawn chance is 1000 and the config says 0.3 ==> 1000 x 0.3 = 300. '300' is now the new generation chance.")
      @Config.RangeDouble(min = 0.1f, max = 10.0f)
      @Config.LangKey("config." + TFCF + ".general.structures.generationModifier")
      public float generationModifier = 1.0f;

      @Config.Comment("Generation chance for stone circle ruins. On average 1 / N chunks will have stone circle ruins.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.structures.spawnChanceRuins")
      public int spawnChanceRuins = 100;

      @Config.Comment("Generation chance for moai statues. On average 1 / N chunks will have moai statues.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.structures.spawnChanceMoai")
      public int spawnChanceMoai = 100;

      @Config.Comment("Generation chance for mayan temples. On average 1 / N chunks will have mayan temples.")
      @Config.RangeInt(min = 1)
      @Config.LangKey("config." + TFCF + ".general.structures.spawnChanceMaya")
      public int spawnChanceMaya = 1000;
    }
  }
}
