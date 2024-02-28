package net.sharkbark.cellars.util.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.sharkbark.cellars.blocks.tileentity.TECellarShelf;
import net.sharkbark.cellars.blocks.tileentity.TEFreezeDryer;
import net.sharkbark.cellars.blocks.tileentity.TEIceBunker;
import net.sharkbark.cellars.blocks.tileentity.TEInfectedAir;

import static su.terrafirmagreg.api.lib.Constants.MODID_CELLARS;

public class TileEntityHandler {
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TECellarShelf.class, new ResourceLocation(MODID_CELLARS + ":cellar_shelf"));
		GameRegistry.registerTileEntity(TEIceBunker.class, new ResourceLocation(MODID_CELLARS + ":ice_shelf"));
		GameRegistry.registerTileEntity(TEFreezeDryer.class, new ResourceLocation(MODID_CELLARS + ":freeze_dryer"));
		GameRegistry.registerTileEntity(TEInfectedAir.class, new ResourceLocation(MODID_CELLARS + ":infected_air"));
	}
}
