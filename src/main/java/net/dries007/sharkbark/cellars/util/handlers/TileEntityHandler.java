package net.dries007.sharkbark.cellars.util.handlers;

import su.terrafirmagreg.modules.device.object.tile.TileCellarShelf;
import su.terrafirmagreg.modules.device.object.tile.TileFreezeDryer;
import su.terrafirmagreg.modules.device.object.tile.TileIceBunker;
import su.terrafirmagreg.modules.device.object.tile.TileInfectedAir;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.dries007.sharkbark.cellars.util.Reference;

public class TileEntityHandler {

  public static void registerTileEntities() {
    GameRegistry.registerTileEntity(TileCellarShelf.class, new ResourceLocation(Reference.MOD_ID + ":cellar_shelf"));
    GameRegistry.registerTileEntity(TileIceBunker.class, new ResourceLocation(Reference.MOD_ID + ":ice_shelf"));
    GameRegistry.registerTileEntity(TileFreezeDryer.class, new ResourceLocation(Reference.MOD_ID + ":freeze_dryer"));
    GameRegistry.registerTileEntity(TileInfectedAir.class, new ResourceLocation(Reference.MOD_ID + ":infected_air"));
  }
}
