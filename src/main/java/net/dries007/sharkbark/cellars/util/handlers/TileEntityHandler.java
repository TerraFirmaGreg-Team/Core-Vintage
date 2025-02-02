package net.dries007.sharkbark.cellars.util.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.dries007.sharkbark.cellars.blocks.tileentity.TECellarShelf;
import net.dries007.sharkbark.cellars.blocks.tileentity.TEFreezeDryer;
import net.dries007.sharkbark.cellars.blocks.tileentity.TEIceBunker;
import net.dries007.sharkbark.cellars.blocks.tileentity.TEInfectedAir;
import net.dries007.sharkbark.cellars.util.Reference;

public class TileEntityHandler {

  public static void registerTileEntities() {
    GameRegistry.registerTileEntity(TECellarShelf.class, new ResourceLocation(Reference.MOD_ID + ":cellar_shelf"));
    GameRegistry.registerTileEntity(TEIceBunker.class, new ResourceLocation(Reference.MOD_ID + ":ice_shelf"));
    GameRegistry.registerTileEntity(TEFreezeDryer.class, new ResourceLocation(Reference.MOD_ID + ":freeze_dryer"));
    GameRegistry.registerTileEntity(TEInfectedAir.class, new ResourceLocation(Reference.MOD_ID + ":infected_air"));
  }
}
