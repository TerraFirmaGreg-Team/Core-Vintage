package net.dries007.sharkbark.cellars.util.handlers;

import su.terrafirmagreg.modules.device.object.tile.TileIceBunker;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.dries007.sharkbark.cellars.util.Reference;

public class TileEntityHandler {

  public static void registerTileEntities() {
    GameRegistry.registerTileEntity(TileIceBunker.class, new ResourceLocation(Reference.MOD_ID + ":ice_shelf"));
  }
}
