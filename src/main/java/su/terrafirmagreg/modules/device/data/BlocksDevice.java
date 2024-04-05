package su.terrafirmagreg.modules.device.data;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.device.objects.blocks.*;


public final class BlocksDevice {

	public static BlockAlloyCalculator ALLOY_CALCULATOR;
	public static BlockBearTrap BEAR_TRAP;
	public static BlockSnare SNARE;
	public static BlockCrate CRATE;
	public static BlockBellows BELLOWS;
	public static BlockBlastFurnace BLAST_FURNACE;
	public static BlockBloom BLOOM;
	public static BlockBloomery BLOOMERY;
	public static BlockCharcoalForge CHARCOAL_FORGE;
	public static BlockCharcoalPile CHARCOAL_PILE;
	public static BlockCrucible CRUCIBLE;
	public static BlockFirePit FIRE_PIT;
	public static BlockPitKiln PIT_KILN;
	public static BlockQuern QUERN;
	public static BlockMolten MOLTEN;
	public static BlockLogPile LOG_PILE;
	public static BlockCellarShelf CELLAR_SHELF;
	public static BlockCellarDoor CELLAR_DOOR;
	public static BlockCellarWall CELLAR_WALL;
	public static BlockIceBunker ICE_BUNKER;
	public static BlockInfectedAir INFECTED_AIR;
	public static BlockFreezeDryer FREEZE_DRYER;
	public static BlockNestBox NEST_BOX;

	public static void onRegister(RegistryManager registry) {
		//==== Other =================================================================================================//

		ALLOY_CALCULATOR = registry.registerBlock(new BlockAlloyCalculator());
		BEAR_TRAP = registry.registerBlock(new BlockBearTrap());
		SNARE = registry.registerBlock(new BlockSnare());
		CRATE = registry.registerBlock(new BlockCrate());
		BELLOWS = registry.registerBlock(new BlockBellows());
		BLAST_FURNACE = registry.registerBlock(new BlockBlastFurnace());
		BLOOM = registry.registerBlock(new BlockBloom());
		BLOOMERY = registry.registerBlock(new BlockBloomery());
		CHARCOAL_FORGE = registry.registerBlock(new BlockCharcoalForge());
		CHARCOAL_PILE = registry.registerBlock(new BlockCharcoalPile());
		CRUCIBLE = registry.registerBlock(new BlockCrucible());
		FIRE_PIT = registry.registerBlock(new BlockFirePit());
		PIT_KILN = registry.registerBlock(new BlockPitKiln());
		QUERN = registry.registerBlock(new BlockQuern());
		MOLTEN = registry.registerBlock(new BlockMolten());
		LOG_PILE = registry.registerBlock(new BlockLogPile());
		CELLAR_SHELF = registry.registerBlock(new BlockCellarShelf());
		CELLAR_DOOR = registry.registerBlock(new BlockCellarDoor());
		CELLAR_WALL = registry.registerBlock(new BlockCellarWall());
		ICE_BUNKER = registry.registerBlock(new BlockIceBunker());
		INFECTED_AIR = registry.registerBlock(new BlockInfectedAir());
		FREEZE_DRYER = registry.registerBlock(new BlockFreezeDryer());
		NEST_BOX = registry.registerBlock(new BlockNestBox());
	}

}
