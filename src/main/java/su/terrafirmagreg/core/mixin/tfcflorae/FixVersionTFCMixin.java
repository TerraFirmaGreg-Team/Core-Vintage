package su.terrafirmagreg.core.mixin.tfcflorae;

import net.minecraft.launchwrapper.Launch;
import org.apache.commons.io.FileUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfcflorae.util.ClassAdder;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Mixin(value = ClassAdder.class, remap = false)
public class FixVersionTFCMixin {
	@Shadow
	@Final
	private static final String ITEMFIRESTARTER_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/ItemFireStarter.class";
	@Shadow
	@Final
	private static final String ITEM_SEEDS_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/ItemSeedsTFC.class";
	@Shadow
	@Final
	private static final String ITEMBLOCKBLOCKCROPDEAD_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/itemblock/ItemBlockCropDeadWaterTFC.class";
	@Shadow
	@Final
	private static final String ITEMBLOCKBLOCKCROP_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/itemblock/ItemBlockCropWaterTFC.class";
	@Shadow
	@Final
	private static final String ITEMPROSPECTORSPICKRESULTTYPE_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/metal/ItemProspectorPick$ProspectResult$Type.class";
	@Shadow
	@Final
	private static final String ITEMPROSPECTORSPICKRESULT_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/metal/ItemProspectorPick$ProspectResult.class";
	@Shadow
	@Final
	private static final String ITEMPROSPECTORSPICK_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/metal/ItemProspectorPick.class";
	@Shadow
	@Final
	private static final String ITEMPROSPECTORSPICK1_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/metal/ItemProspectorPick$1.class";
	@Shadow
	@Final
	private static final String BLOCKSTFC1_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/BlocksTFC$1.class";
	@Shadow
	@Final
	private static final String BLOCKSTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/BlocksTFC.class";
	@Shadow
	@Final
	private static final String BLOCK_CROPDEAD_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/agriculture/BlockCropDead.class";
	@Shadow
	@Final
	private static final String BLOCK_CROP_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/agriculture/BlockCropTFC.class";
	@Shadow
	@Final
	private static final String BLOCKPLANTTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockPlantTFC.class";
	@Shadow
	@Final
	private static final String BLOCKPLANTTFC1_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockPlantTFC$1.class";
	@Shadow
	@Final
	private static final String BLOCKSHORTGRASSTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockShortGrassTFC.class";
	@Shadow
	@Final
	private static final String BLOCKTALLGRASSTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockTallGrassTFC.class";
	@Shadow
	@Final
	private static final String BLOCKHANGINGTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockHangingPlantTFC.class";
	@Shadow
	@Final
	private static final String WORLDREGEN_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/util/WorldRegenHandler.class";
	@Shadow
	@Final
	private static final String CHUNKGENTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/ChunkGenTFC.class";
	@Shadow
	@Final
	private static final String DATALAYER_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/DataLayer.class";
	@Shadow
	@Final
	private static final String BIOMEDECORATOR_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeDecoratorTFC.class";
	@Shadow
	@Final
	private static final String BIOMEDECORATOR1_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeDecoratorTFC$1.class";
	@Shadow
	@Final
	private static final String BIOMEMESATFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeMesaTFC.class";
	@Shadow
	@Final
	private static final String BIOMESTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomesTFC.class";
	@Shadow
	@Final
	private static final String BIOMETFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeTFC.class";
	@Shadow
	@Final
	private static final String CHUNKDATATFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/chunkdata/ChunkDataTFC.class";
	@Shadow
	@Final
	private static final String CHUNKDATATFC_STORAGE_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/chunkdata/ChunkDataTFC$ChunkDataStorage.class";
	@Shadow
	@Final
	private static final String GENLAYERTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/GenLayerTFC.class";
	@Shadow
	@Final
	private static final String GENLAYERBIOMEEDGE_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/biome/GenLayerBiomeEdge.class";
	@Shadow
	@Final
	private static final String GENLAYERSHORETFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/biome/GenLayerShoreTFC.class";
	@Shadow
	@Final
	private static final String GENLAYERTREEINIT_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/datalayers/tree/GenLayerTreeInit.class";
	@Shadow
	@Final
	private static final String GENRIVERLAYER_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/datalayers/tree/GenRiverLayer.class";
	@Shadow
	@Final
	private static final String GENLAYERRIVERINITTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/river/GenLayerRiverInitTFC.class";
	@Shadow
	@Final
	private static final String GENLAYERRIVERMIXTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/river/GenLayerRiverMixTFC.class";
	@Shadow
	@Final
	private static final String GENLAYERRIVERTFC_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/river/GenLayerRiverTFC.class";
	@Shadow
	@Final
	private static final String WORLDGENWILDCROPS_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/worldgen/WorldGenWildCrops.class";
	@Shadow
	@Final
	private static final String DIR = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc";
	@Shadow
	@Final
	private static final String SUBDIR_ITEM = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items";
	@Shadow
	@Final
	private static final String SUBDIR_ITEM_ITEMBLOCK = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/itemblock";
	@Shadow
	@Final
	private static final String SUBDIR_ITEMPROSPECTORSPICKRESULTTYPE = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/metal";
	@Shadow
	@Final
	private static final String SUBDIR_ITEMPROSPECTORSPICKRESULT = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/metal";
	@Shadow
	@Final
	private static final String SUBDIR_ITEMPROSPECTORSPICK = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/metal";
	@Shadow
	@Final
	private static final String SUBDIR_ITEMPROSPECTORSPICK1 = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/metal";
	@Shadow
	@Final
	private static final String SUBDIR_BLOCK = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks";
	@Shadow
	@Final
	private static final String SUBDIR_BLOCK_AGRICULTURE = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/agriculture";
	@Shadow
	@Final
	private static final String SUBDIR_BLOCKPLANT = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants";
	@Shadow
	@Final
	private static final String SUBDIR_UTIL = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/util";
	@Shadow
	@Final
	private static final String SUBDIR_WORLD = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic";
	@Shadow
	@Final
	private static final String SUBDIR_WORLD_BIOMES = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes";
	@Shadow
	@Final
	private static final String SUBDIR_WORLD_CHUNKDATA = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/chunkdata";
	@Shadow
	@Final
	private static final String SUBDIR_WORLD_GENLAYERS = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers";
	@Shadow
	@Final
	private static final String SUBDIR_WORLD_GENLAYERS_BIOME = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/biome";
	@Shadow
	@Final
	private static final String SUBDIR_WORLD_GENLAYERS_DATALAYERS = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/datalayers";
	@Shadow
	@Final
	private static final String SUBDIR_WORLD_GENLAYERS_DATALAYERS_TREE = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/datalayers/tree";
	@Shadow
	@Final
	private static final String SUBDIR_WORLD_GENLAYERS_RIVER = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/river";
	@Shadow
	@Final
	private static final String SUBDIR_WORLD_WORLDGEN = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/worldgen";

	@Inject(method = "addClasses", at = @At(value = "HEAD"), remap = false, cancellable = true)
	private static void addClasses(File dir, CallbackInfo ci) {
		File bansoukouFolder = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc");
		if (!bansoukouFolder.exists()) {
			bansoukouFolder.mkdirs();
		}

		File subDirItem = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/objects/items");
		if (!subDirItem.exists()) {
			subDirItem.mkdirs();
		}

		File subDirItemItemBlock = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/objects/items/itemblock");
		if (!subDirItemItemBlock.exists()) {
			subDirItemItemBlock.mkdirs();
		}

		File subDirItemProspectorPickResultType = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/objects/items/metal");
		if (!subDirItemProspectorPickResultType.exists()) {
			subDirItemProspectorPickResultType.mkdirs();
		}

		File subDirItemProspectorPickResult = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/objects/items/metal");
		if (!subDirItemProspectorPickResult.exists()) {
			subDirItemProspectorPickResult.mkdirs();
		}

		File subDirItemProspectorPick = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/objects/items/metal");
		if (!subDirItemProspectorPick.exists()) {
			subDirItemProspectorPick.mkdirs();
		}

		File subDirItemProspectorPick1 = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/objects/items/metal");
		if (!subDirItemProspectorPick1.exists()) {
			subDirItemProspectorPick1.mkdirs();
		}

		File subDirBlock = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/objects/blocks");
		if (!subDirBlock.exists()) {
			subDirBlock.mkdirs();
		}

		File subDirBlockAgriculture = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/objects/blocks/agriculture");
		if (!subDirBlockAgriculture.exists()) {
			subDirBlockAgriculture.mkdirs();
		}

		File subDirBlockPlant = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/objects/blocks/plants");
		if (!subDirBlockPlant.exists()) {
			subDirBlockPlant.mkdirs();
		}

		File subDirUtil = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/util");
		if (!subDirUtil.exists()) {
			subDirUtil.mkdirs();
		}

		File subDirWorld = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/world/classic");
		if (!subDirWorld.exists()) {
			subDirWorld.mkdirs();
		}

		File subDirWorldBiomes = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/world/classic/biomes");
		if (!subDirWorldBiomes.exists()) {
			subDirWorldBiomes.mkdirs();
		}

		File subDirWorldChunkData = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/world/classic/chunkdata");
		if (!subDirWorldChunkData.exists()) {
			subDirWorldChunkData.mkdirs();
		}

		File subDirWorldGenLayers = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/world/classic/genlayers");
		if (!subDirWorldGenLayers.exists()) {
			subDirWorldGenLayers.mkdirs();
		}

		File subDirWorldGenLayersBiome = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/world/classic/genlayers/biome");
		if (!subDirWorldGenLayersBiome.exists()) {
			subDirWorldGenLayersBiome.mkdirs();
		}

		File subDirWorldGenLayersDataLayers = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/world/classic/genlayers/datalayers");
		if (!subDirWorldGenLayersDataLayers.exists()) {
			subDirWorldGenLayersDataLayers.mkdirs();
		}

		File subDirWorldGenLayersDataLayersTree = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/world/classic/genlayers/datalayers/tree");
		if (!subDirWorldGenLayersDataLayersTree.exists()) {
			subDirWorldGenLayersDataLayersTree.mkdirs();
		}

		File subDirWorldGenLayersRiver = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/world/classic/genlayers/river");
		if (!subDirWorldGenLayersRiver.exists()) {
			subDirWorldGenLayersRiver.mkdirs();
		}

		File subDirWorldGen = new File(Launch.minecraftHome, "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.23.181/net/dries007/tfc/world/classic/worldgen");
		if (!subDirWorldGen.exists()) {
			subDirWorldGen.mkdirs();
		}

		File itemFireStarter = new File(bansoukouFolder + "/objects/items", "ItemFireStarter.class");
		File itemSeeds = new File(bansoukouFolder + "/objects/items", "ItemSeedsTFC.class");
		File itemBlockCropWaterDead = new File(bansoukouFolder + "/objects/items/itemblock", "ItemBlockCropDeadWaterTFC.class");
		File itemBlockCropWater = new File(bansoukouFolder + "/objects/items/itemblock", "ItemBlockCropWaterTFC.class");
		new File(bansoukouFolder + "/objects/items/metal", "ItemProspectorPick$ProspectResult$Type.class");
		new File(bansoukouFolder + "/objects/items/metal", "ItemProspectorPick$ProspectResult.class");
		new File(bansoukouFolder + "/objects/items/metal", "ItemProspectorPick.class");
		new File(bansoukouFolder + "/objects/items/metal", "ItemProspectorPick$1.class");
		File blockBlocksTFC1 = new File(bansoukouFolder + "/objects/blocks", "BlocksTFC$1.class");
		File blockBlocksTFC = new File(bansoukouFolder + "/objects/blocks", "BlocksTFC.class");
		File blockCropDead = new File(bansoukouFolder + "/objects/blocks/agriculture", "BlockCropDead.class");
		File blockCrop = new File(bansoukouFolder + "/objects/blocks/agriculture", "BlockCropTFC.class");
		File blockPlantTFC = new File(bansoukouFolder + "/objects/blocks/plants", "BlockPlantTFC.class");
		File blockPlantTFC1 = new File(bansoukouFolder + "/objects/blocks/plants", "BlockPlantTFC$1.class");
		File blockShortGrassTFC = new File(bansoukouFolder + "/objects/blocks/plants", "BlockShortGrassTFC.class");
		File blockTallGrassTFC = new File(bansoukouFolder + "/objects/blocks/plants", "BlockTallGrassTFC.class");
		File blockHangingTFC = new File(bansoukouFolder + "/objects/blocks/plants", "BlockHangingPlantTFC.class");
		File worldRegenHandler = new File(bansoukouFolder + "/util", "WorldRegenHandler.class");
		File chunkGenTFC = new File(bansoukouFolder + "/world/classic", "ChunkGenTFC.class");
		File dataLayer = new File(bansoukouFolder + "/world/classic", "DataLayer.class");
		File biomeDecoratorTFC = new File(bansoukouFolder + "/world/classic/biomes", "BiomeDecoratorTFC.class");
		File biomeDecoratorTFC1 = new File(bansoukouFolder + "/world/classic/biomes", "BiomeDecoratorTFC$1.class");
		File biomeMesaTFC = new File(bansoukouFolder + "/world/classic/biomes", "BiomeMesaTFC.class");
		File biomesTFC = new File(bansoukouFolder + "/world/classic/biomes", "BiomesTFC.class");
		File biomeTFC = new File(bansoukouFolder + "/world/classic/biomes", "BiomeTFC.class");
		File chunkDataTFC = new File(bansoukouFolder + "/world/classic/chunkdata", "ChunkDataTFC.class");
		File chunkDataTFCStorage = new File(bansoukouFolder + "/world/classic/chunkdata", "ChunkDataTFC$ChunkDataStorage.class");
		File genLayerTFC = new File(bansoukouFolder + "/world/classic/genlayers", "GenLayerTFC.class");
		File genLayerBiomeEdge = new File(bansoukouFolder + "/world/classic/genlayers/biome", "GenLayerBiomeEdge.class");
		File genLayerShoreTFC = new File(bansoukouFolder + "/world/classic/genlayers/biome", "GenLayerShoreTFC.class");
		File genLayerTreeInit = new File(bansoukouFolder + "/world/classic/genlayers/datalayers/tree", "GenLayerTreeInit.class");
		File genRiverLayer = new File(bansoukouFolder + "/world/classic/genlayers/datalayers/tree", "GenRiverLayer.class");
		File genWildCrops = new File(bansoukouFolder + "/world/classic/worldgen", "WorldGenWildCrops.class");

		try {
			if (itemFireStarter.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/ItemFireStarter.class")), itemFireStarter);
			}

			if (itemSeeds.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/ItemSeedsTFC.class")), itemSeeds);
			}

			if (itemBlockCropWaterDead.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/itemblock/ItemBlockCropDeadWaterTFC.class")), itemBlockCropWaterDead);
			}

			if (itemBlockCropWater.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/items/itemblock/ItemBlockCropWaterTFC.class")), itemBlockCropWater);
			}

			if (blockBlocksTFC1.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/BlocksTFC$1.class")), blockBlocksTFC1);
			}

			if (blockBlocksTFC.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/BlocksTFC.class")), blockBlocksTFC);
			}

			if (blockCropDead.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/agriculture/BlockCropDead.class")), blockCropDead);
			}

			if (blockCrop.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/agriculture/BlockCropTFC.class")), blockCrop);
			}

			if (blockPlantTFC.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockPlantTFC.class")), blockPlantTFC);
			}

			if (blockPlantTFC1.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockPlantTFC$1.class")), blockPlantTFC1);
			}

			if (blockShortGrassTFC.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockShortGrassTFC.class")), blockShortGrassTFC);
			}

			if (blockTallGrassTFC.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockTallGrassTFC.class")), blockTallGrassTFC);
			}

			if (blockHangingTFC.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockHangingPlantTFC.class")), blockHangingTFC);
			}

			if (worldRegenHandler.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/util/WorldRegenHandler.class")), worldRegenHandler);
			}

			if (chunkGenTFC.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/ChunkGenTFC.class")), chunkGenTFC);
			}

			if (dataLayer.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/DataLayer.class")), dataLayer);
			}

			if (biomeDecoratorTFC.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeDecoratorTFC.class")), biomeDecoratorTFC);
			}

			if (biomeDecoratorTFC1.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeDecoratorTFC$1.class")), biomeDecoratorTFC1);
			}

			if (biomeMesaTFC.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeMesaTFC.class")), biomeMesaTFC);
			}

			if (biomesTFC.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomesTFC.class")), biomesTFC);
			}

			if (biomeTFC.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeTFC.class")), biomeTFC);
			}

			if (chunkDataTFC.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/chunkdata/ChunkDataTFC.class")), chunkDataTFC);
			}

			if (chunkDataTFCStorage.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/chunkdata/ChunkDataTFC$ChunkDataStorage.class")), chunkDataTFCStorage);
			}

			if (genLayerTFC.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/GenLayerTFC.class")), genLayerTFC);
			}

			if (genLayerBiomeEdge.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/biome/GenLayerBiomeEdge.class")), genLayerBiomeEdge);
			}

			if (genLayerShoreTFC.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/biome/GenLayerShoreTFC.class")), genLayerShoreTFC);
			}

			if (genLayerTreeInit.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/datalayers/tree/GenLayerTreeInit.class")), genLayerTreeInit);
			}

			if (genRiverLayer.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/genlayers/datalayers/tree/GenRiverLayer.class")), genRiverLayer);
			}

			if (genWildCrops.createNewFile()) {
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader()
				                                                                       .getResourceAsStream("assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/worldgen/WorldGenWildCrops.class")), genWildCrops);
			}

		} catch (IOException var55) {
			throw new Error("tfcflorae: Sorry, but I couldn't copy the class files into Bansoukou's directory.", var55);
		}

		ci.cancel();
	}
}
