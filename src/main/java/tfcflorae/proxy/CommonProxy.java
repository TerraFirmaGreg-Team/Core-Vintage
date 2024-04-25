package tfcflorae.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;


import net.dries007.tfc.world.classic.worldgen.WorldGenCorals;
import net.dries007.tfc.world.classic.worldgen.WorldGenGlowPlant;
import net.dries007.tfc.world.classic.worldgen.WorldGenGourds;
import net.dries007.tfc.world.classic.worldgen.WorldGenMesaStrata;
import net.dries007.tfc.world.classic.worldgen.WorldGenMossyRaw;
import net.dries007.tfc.world.classic.worldgen.WorldGeneratorPlants;
import net.dries007.tfc.world.classic.worldgen.WorldGeneratorTrees;
import net.dries007.tfc.world.classic.worldgen.cave.WorldGenLightstones;
import net.dries007.tfc.world.classic.worldgen.cave.WorldGeneratorUnderground;
import net.dries007.tfc.world.classic.worldgen.groundcover.WorldGenSurfaceBones;
import net.dries007.tfc.world.classic.worldgen.groundcover.WorldGenSurfaceDriftwood;
import net.dries007.tfc.world.classic.worldgen.groundcover.WorldGenSurfaceFlint;
import net.dries007.tfc.world.classic.worldgen.groundcover.WorldGenSurfacePinecone;
import net.dries007.tfc.world.classic.worldgen.groundcover.WorldGenSurfaceSeashells;
import net.dries007.tfc.world.classic.worldgen.groundcover.WorldGenSurfaceTwig;
import net.dries007.tfc.world.classic.worldgen.soil.WorldGenClays;
import net.dries007.tfc.world.classic.worldgen.soil.WorldGenSoilDecorative;
import net.dries007.tfc.world.classic.worldgen.soil.WorldGenSoilTypes;
import net.dries007.tfc.world.classic.worldgen.structures.WorldGenStructures;
import net.dries007.tfc.world.classic.worldgen.structures.WorldGenStructuresCorals;
import tfcflorae.ConfigTFCF;
import tfcflorae.TFCFlorae;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFCF;

@Mod.EventBusSubscriber(modid = MODID_TFCF)
public class CommonProxy {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
    }

    public void preInit(FMLPreInitializationEvent event) {
        if (ConfigTFCF.General.STRUCTURES.activateStructureGeneration) {
            GameRegistry.registerWorldGenerator(new WorldGenStructures(), 0);
            if (ConfigTFCF.General.WORLD.enableCoralWorldGen) {
                GameRegistry.registerWorldGenerator(new WorldGenStructuresCorals(), 0);
            }
        }
        if (ConfigTFCF.General.WORLD.enableAllWorldGen) {
            if (ConfigTFCF.General.WORLD.enableMesaStrata) {
                GameRegistry.registerWorldGenerator(new WorldGenMesaStrata(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableTrees) {
                GameRegistry.registerWorldGenerator(new WorldGeneratorTrees(), 0);
            }
            //GameRegistry.registerWorldGenerator(new WorldGenWildCropsTFCF(), 0);
            if (ConfigTFCF.General.WORLD.enableCoralWorldGen) {
                GameRegistry.registerWorldGenerator(new WorldGenCorals(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableMossyRawWorldGen) {
                GameRegistry.registerWorldGenerator(new WorldGenMossyRaw(), 0);
            }
            if (ConfigTFCF.General.WORLD.enablePlantWorldGen) {
                GameRegistry.registerWorldGenerator(new WorldGeneratorPlants(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableUndergroundPlantWorldGen) {
                GameRegistry.registerWorldGenerator(new WorldGeneratorUnderground(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableLightstoneWorldGen) {
                GameRegistry.registerWorldGenerator(new WorldGenLightstones(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableOceanGlowPlantWorldGen) {
                GameRegistry.registerWorldGenerator(new WorldGenGlowPlant(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableSoilPits) {
                //GameRegistry.registerWorldGenerator(new WorldGenSoil(), 0);
                GameRegistry.registerWorldGenerator(new WorldGenSoilTypes(), 0);
                GameRegistry.registerWorldGenerator(new WorldGenSoilDecorative(), 0);
                GameRegistry.registerWorldGenerator(new WorldGenClays(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverSeashell) {
                GameRegistry.registerWorldGenerator(new WorldGenSurfaceSeashells(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverFlint) {
                GameRegistry.registerWorldGenerator(new WorldGenSurfaceFlint(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverBones) {
                GameRegistry.registerWorldGenerator(new WorldGenSurfaceBones(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverPinecone) {
                GameRegistry.registerWorldGenerator(new WorldGenSurfacePinecone(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverDriftwood) {
                GameRegistry.registerWorldGenerator(new WorldGenSurfaceDriftwood(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverTwig) {
                GameRegistry.registerWorldGenerator(new WorldGenSurfaceTwig(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGourdWorldGen && TFCFlorae.FirmaLifeAdded) {
                GameRegistry.registerWorldGenerator(new WorldGenGourds(), 0);
            }
        }
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }
}
