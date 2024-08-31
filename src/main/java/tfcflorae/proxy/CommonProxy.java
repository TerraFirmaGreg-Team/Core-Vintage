package tfcflorae.proxy;

import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorCorals;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorGlowPlant;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorGourds;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorMesaStrata;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorPlants;
import su.terrafirmagreg.modules.world.classic.objects.generator.cave.GeneratorLightstones;
import su.terrafirmagreg.modules.world.classic.objects.generator.cave.GeneratorUnderground;
import su.terrafirmagreg.modules.world.classic.objects.generator.groundcover.GeneratorSurfaceBones;
import su.terrafirmagreg.modules.world.classic.objects.generator.groundcover.GeneratorSurfaceDriftwood;
import su.terrafirmagreg.modules.world.classic.objects.generator.groundcover.GeneratorSurfaceFlint;
import su.terrafirmagreg.modules.world.classic.objects.generator.groundcover.GeneratorSurfacePinecone;
import su.terrafirmagreg.modules.world.classic.objects.generator.groundcover.GeneratorSurfaceSeashells;
import su.terrafirmagreg.modules.world.classic.objects.generator.groundcover.GeneratorSurfaceTwig;
import su.terrafirmagreg.modules.world.classic.objects.generator.structures.WorldGenStructures;
import su.terrafirmagreg.modules.world.classic.objects.generator.structures.WorldGenStructuresCorals;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;


import tfcflorae.ConfigTFCF;
import tfcflorae.TFCFlorae;

import static su.terrafirmagreg.data.Constants.MODID_TFCF;

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
                GameRegistry.registerWorldGenerator(new GeneratorMesaStrata(), 0);
            }
            //GameRegistry.registerWorldGenerator(new WorldGenWildCropsTFCF(), 0);
            if (ConfigTFCF.General.WORLD.enableCoralWorldGen) {
                GameRegistry.registerWorldGenerator(new GeneratorCorals(), 0);
            }
            if (ConfigTFCF.General.WORLD.enablePlantWorldGen) {
                GameRegistry.registerWorldGenerator(new GeneratorPlants(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableUndergroundPlantWorldGen) {
                GameRegistry.registerWorldGenerator(new GeneratorUnderground(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableLightstoneWorldGen) {
                GameRegistry.registerWorldGenerator(new GeneratorLightstones(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableOceanGlowPlantWorldGen) {
                GameRegistry.registerWorldGenerator(new GeneratorGlowPlant(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableSoilPits) {
                //GameRegistry.registerWorldGenerator(new WorldGenSoil(), 0);
                //                GameRegistry.registerWorldGenerator(new GeneratorSoilTypes(), 0);
                //                GameRegistry.registerWorldGenerator(new GeneratorSoilDecorative(), 0);
                //                GameRegistry.registerWorldGenerator(new GeneratorClays(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverSeashell) {
                GameRegistry.registerWorldGenerator(new GeneratorSurfaceSeashells(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverFlint) {
                GameRegistry.registerWorldGenerator(new GeneratorSurfaceFlint(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverBones) {
                GameRegistry.registerWorldGenerator(new GeneratorSurfaceBones(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverPinecone) {
                GameRegistry.registerWorldGenerator(new GeneratorSurfacePinecone(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverDriftwood) {
                GameRegistry.registerWorldGenerator(new GeneratorSurfaceDriftwood(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGroundcoverTwig) {
                GameRegistry.registerWorldGenerator(new GeneratorSurfaceTwig(), 0);
            }
            if (ConfigTFCF.General.WORLD.enableGourdWorldGen && TFCFlorae.FirmaLifeAdded) {
                GameRegistry.registerWorldGenerator(new GeneratorGourds(), 0);
            }
        }
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }
}
