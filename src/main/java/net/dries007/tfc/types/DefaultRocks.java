/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.types;

import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.objects.ToolMaterialsTFC;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;
import static su.terrafirmagreg.api.lib.Constants.MODID_TFCF;

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = MODID_TFC)
public final class DefaultRocks {
	public static final ResourceLocation SEDIMENTARY = new ResourceLocation(MODID_TFC, "sedimentary");
	public static final ResourceLocation METAMORPHIC = new ResourceLocation(MODID_TFC, "metamorphic");
	public static final ResourceLocation IGNEOUS_INTRUSIVE = new ResourceLocation(MODID_TFC, "igneous_intrusive");
	public static final ResourceLocation IGNEOUS_EXTRUSIVE = new ResourceLocation(MODID_TFC, "igneous_extrusive");

	public static final ResourceLocation GRANITE = new ResourceLocation(MODID_TFC, "granite");
	public static final ResourceLocation DIORITE = new ResourceLocation(MODID_TFC, "diorite");
	public static final ResourceLocation GABBRO = new ResourceLocation(MODID_TFC, "gabbro");
	public static final ResourceLocation SHALE = new ResourceLocation(MODID_TFC, "shale");
	public static final ResourceLocation CLAYSTONE = new ResourceLocation(MODID_TFC, "claystone");
	public static final ResourceLocation ROCKSALT = new ResourceLocation(MODID_TFC, "rocksalt");
	public static final ResourceLocation LIMESTONE = new ResourceLocation(MODID_TFC, "limestone");
	public static final ResourceLocation CONGLOMERATE = new ResourceLocation(MODID_TFC, "conglomerate");
	public static final ResourceLocation DOLOMITE = new ResourceLocation(MODID_TFC, "dolomite");
	public static final ResourceLocation CHERT = new ResourceLocation(MODID_TFC, "chert");
	public static final ResourceLocation CHALK = new ResourceLocation(MODID_TFC, "chalk");
	public static final ResourceLocation RHYOLITE = new ResourceLocation(MODID_TFC, "rhyolite");
	public static final ResourceLocation BASALT = new ResourceLocation(MODID_TFC, "basalt");
	public static final ResourceLocation ANDESITE = new ResourceLocation(MODID_TFC, "andesite");
	public static final ResourceLocation DACITE = new ResourceLocation(MODID_TFC, "dacite");
	public static final ResourceLocation QUARTZITE = new ResourceLocation(MODID_TFC, "quartzite");
	public static final ResourceLocation SLATE = new ResourceLocation(MODID_TFC, "slate");
	public static final ResourceLocation PHYLLITE = new ResourceLocation(MODID_TFC, "phyllite");
	public static final ResourceLocation SCHIST = new ResourceLocation(MODID_TFC, "schist");
	public static final ResourceLocation GNEISS = new ResourceLocation(MODID_TFC, "gneiss");
	public static final ResourceLocation MARBLE = new ResourceLocation(MODID_TFC, "marble");
	public static final ResourceLocation BRECCIA = new ResourceLocation(MODID_TFCF, "breccia");
	//public static final ResourceLocation FOIDOLITE = new ResourceLocation(MODID, "foidolite");
	public static final ResourceLocation PORPHYRY = new ResourceLocation(MODID_TFCF, "porphyry");
	public static final ResourceLocation PERIDOTITE = new ResourceLocation(MODID_TFCF, "peridotite");
	public static final ResourceLocation MUDSTONE = new ResourceLocation(MODID_TFCF, "mudstone");
	public static final ResourceLocation SANDSTONE = new ResourceLocation(MODID_TFCF, "sandstone");
	public static final ResourceLocation SILTSTONE = new ResourceLocation(MODID_TFCF, "siltstone");
	//public static final ResourceLocation BLUESCHIST = new ResourceLocation(MODID, "blueschist");
	public static final ResourceLocation CATLINITE = new ResourceLocation(MODID_TFCF, "catlinite");
	//public static final ResourceLocation GREENSCHIST = new ResourceLocation(MODID, "greenschist");
	public static final ResourceLocation NOVACULITE = new ResourceLocation(MODID_TFCF, "novaculite");
	public static final ResourceLocation SOAPSTONE = new ResourceLocation(MODID_TFCF, "soapstone");
	public static final ResourceLocation KOMATIITE = new ResourceLocation(MODID_TFCF, "komatiite");

	@SubscribeEvent
	@SuppressWarnings("ConstantConditions")
	public static void onPreRegisterRockCategory(TFCRegistryEvent.RegisterPreBlock<RockCategory> event) {
		event.getRegistry().registerAll(
				new RockCategory(IGNEOUS_INTRUSIVE, ToolMaterialsTFC.IGNEOUS_INTRUSIVE, true, true, true, -0.4f, 0f, 1.6F, 10F, true),
				new RockCategory(IGNEOUS_EXTRUSIVE, ToolMaterialsTFC.IGNEOUS_EXTRUSIVE, true, true, true, -0.5f, 0f, 1.6F, 10F, true),
				new RockCategory(SEDIMENTARY, ToolMaterialsTFC.SEDIMENTARY, true, false, false, 0.3f, 5f, 1.4F, 10F, false),
				new RockCategory(METAMORPHIC, ToolMaterialsTFC.METAMORPHIC, true, true, false, 0.2f, 0f, 1.5F, 10F, false)
		);
	}

	@SubscribeEvent
	public static void onPreRegisterRock(TFCRegistryEvent.RegisterPreBlock<Rock> event) {
		IForgeRegistry<Rock> r = event.getRegistry();
		event.getRegistry().registerAll(
				new Rock(GRANITE, IGNEOUS_INTRUSIVE, false),
				new Rock(DIORITE, IGNEOUS_INTRUSIVE, false),
				new Rock(GABBRO, IGNEOUS_INTRUSIVE, false),
				new Rock(SHALE, SEDIMENTARY, false),
				new Rock(CLAYSTONE, SEDIMENTARY, false),
				new Rock(ROCKSALT, SEDIMENTARY, false),
				new Rock(LIMESTONE, SEDIMENTARY, true),
				new Rock(CONGLOMERATE, SEDIMENTARY, false),
				new Rock(DOLOMITE, SEDIMENTARY, true),
				new Rock(CHERT, SEDIMENTARY, false),
				new Rock(CHALK, SEDIMENTARY, true),
				new Rock(RHYOLITE, IGNEOUS_EXTRUSIVE, false),
				new Rock(BASALT, IGNEOUS_EXTRUSIVE, false),
				new Rock(ANDESITE, IGNEOUS_EXTRUSIVE, false),
				new Rock(DACITE, IGNEOUS_EXTRUSIVE, false),
				new Rock(QUARTZITE, METAMORPHIC, false),
				new Rock(SLATE, METAMORPHIC, false),
				new Rock(PHYLLITE, METAMORPHIC, false),
				new Rock(SCHIST, METAMORPHIC, false),
				new Rock(GNEISS, METAMORPHIC, false),
				new Rock(MARBLE, METAMORPHIC, true),

				new Rock(BRECCIA, IGNEOUS_INTRUSIVE, false),
				//new Rock(FOIDOLITE, IGNEOUS_INTRUSIVE, false),
				new Rock(PORPHYRY, IGNEOUS_INTRUSIVE, false),
				new Rock(PERIDOTITE, IGNEOUS_EXTRUSIVE, false),
				new Rock(MUDSTONE, SEDIMENTARY, false),
				new Rock(SANDSTONE, SEDIMENTARY, false),
				new Rock(SILTSTONE, SEDIMENTARY, false),
				//new Rock(BLUESCHIST, METAMORPHIC, false),
				new Rock(CATLINITE, METAMORPHIC, false),
				//new Rock(GREENSCHIST, METAMORPHIC, false),
				new Rock(NOVACULITE, METAMORPHIC, false),
				new Rock(SOAPSTONE, METAMORPHIC, false),
				new Rock(KOMATIITE, METAMORPHIC, false)
		);
	}
}
