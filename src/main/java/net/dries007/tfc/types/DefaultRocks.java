/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.types;

import su.terrafirmagreg.api.data.ToolMaterials;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFC;

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = TFC)
public final class DefaultRocks {

  public static final ResourceLocation SEDIMENTARY = new ResourceLocation(TFC, "sedimentary");
  public static final ResourceLocation METAMORPHIC = new ResourceLocation(TFC, "metamorphic");
  public static final ResourceLocation IGNEOUS_INTRUSIVE = new ResourceLocation(TFC, "igneous_intrusive");
  public static final ResourceLocation IGNEOUS_EXTRUSIVE = new ResourceLocation(TFC, "igneous_extrusive");

  public static final ResourceLocation GRANITE = new ResourceLocation(TFC, "granite");
  public static final ResourceLocation DIORITE = new ResourceLocation(TFC, "diorite");
  public static final ResourceLocation GABBRO = new ResourceLocation(TFC, "gabbro");
  public static final ResourceLocation SHALE = new ResourceLocation(TFC, "shale");
  public static final ResourceLocation CLAYSTONE = new ResourceLocation(TFC, "claystone");
  public static final ResourceLocation ROCKSALT = new ResourceLocation(TFC, "rocksalt");
  public static final ResourceLocation LIMESTONE = new ResourceLocation(TFC, "limestone");
  public static final ResourceLocation CONGLOMERATE = new ResourceLocation(TFC, "conglomerate");
  public static final ResourceLocation DOLOMITE = new ResourceLocation(TFC, "dolomite");
  public static final ResourceLocation CHERT = new ResourceLocation(TFC, "chert");
  public static final ResourceLocation CHALK = new ResourceLocation(TFC, "chalk");
  public static final ResourceLocation RHYOLITE = new ResourceLocation(TFC, "rhyolite");
  public static final ResourceLocation BASALT = new ResourceLocation(TFC, "basalt");
  public static final ResourceLocation ANDESITE = new ResourceLocation(TFC, "andesite");
  public static final ResourceLocation DACITE = new ResourceLocation(TFC, "dacite");
  public static final ResourceLocation QUARTZITE = new ResourceLocation(TFC, "quartzite");
  public static final ResourceLocation SLATE = new ResourceLocation(TFC, "slate");
  public static final ResourceLocation PHYLLITE = new ResourceLocation(TFC, "phyllite");
  public static final ResourceLocation SCHIST = new ResourceLocation(TFC, "schist");
  public static final ResourceLocation GNEISS = new ResourceLocation(TFC, "gneiss");
  public static final ResourceLocation MARBLE = new ResourceLocation(TFC, "marble");

  @SubscribeEvent
  @SuppressWarnings("ConstantConditions")
  public static void onPreRegisterRockCategory(TFCRegistryEvent.RegisterPreBlock<RockCategory> event) {
    event.getRegistry().registerAll(
      new RockCategory(IGNEOUS_INTRUSIVE, ToolMaterials.IGNEOUS_INTRUSIVE, true, true, true, -0.4f, 0f, 1.6F, 10F, true),
      new RockCategory(IGNEOUS_EXTRUSIVE, ToolMaterials.IGNEOUS_EXTRUSIVE, true, true, true, -0.5f, 0f, 1.6F, 10F, true),
      new RockCategory(SEDIMENTARY, ToolMaterials.SEDIMENTARY, true, false, false, 0.3f, 5f, 1.4F, 10F, false),
      new RockCategory(METAMORPHIC, ToolMaterials.METAMORPHIC, true, true, false, 0.2f, 0f, 1.5F, 10F, false)
    );
  }

  @SubscribeEvent
  public static void onPreRegisterRock(TFCRegistryEvent.RegisterPreBlock<Rock> event) {
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
      new Rock(MARBLE, METAMORPHIC, true)
    );
  }
}
