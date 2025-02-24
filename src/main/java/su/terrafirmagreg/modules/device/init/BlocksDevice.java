package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.device.object.block.BlockAlloyCalculator;
import su.terrafirmagreg.modules.device.object.block.BlockBearTrap;
import su.terrafirmagreg.modules.device.object.block.BlockBellows;
import su.terrafirmagreg.modules.device.object.block.BlockBlastFurnace;
import su.terrafirmagreg.modules.device.object.block.BlockBloom;
import su.terrafirmagreg.modules.device.object.block.BlockBloomery;
import su.terrafirmagreg.modules.device.object.block.BlockCellarDoor;
import su.terrafirmagreg.modules.device.object.block.BlockCellarShelf;
import su.terrafirmagreg.modules.device.object.block.BlockCellarWall;
import su.terrafirmagreg.modules.device.object.block.BlockCharcoalForge;
import su.terrafirmagreg.modules.device.object.block.BlockCharcoalPile;
import su.terrafirmagreg.modules.device.object.block.BlockCrate;
import su.terrafirmagreg.modules.device.object.block.BlockCrucible;
import su.terrafirmagreg.modules.device.object.block.BlockElectricForge;
import su.terrafirmagreg.modules.device.object.block.BlockFirePit;
import su.terrafirmagreg.modules.device.object.block.BlockFreezeDryer;
import su.terrafirmagreg.modules.device.object.block.BlockFridge;
import su.terrafirmagreg.modules.device.object.block.BlockGreenhouseDoor;
import su.terrafirmagreg.modules.device.object.block.BlockGreenhouseRoof;
import su.terrafirmagreg.modules.device.object.block.BlockGreenhouseWall;
import su.terrafirmagreg.modules.device.object.block.BlockGrindstoneManual;
import su.terrafirmagreg.modules.device.object.block.BlockIceBunker;
import su.terrafirmagreg.modules.device.object.block.BlockInductionCrucible;
import su.terrafirmagreg.modules.device.object.block.BlockInfectedAir;
import su.terrafirmagreg.modules.device.object.block.BlockLatexExtractor;
import su.terrafirmagreg.modules.device.object.block.BlockLeafMat;
import su.terrafirmagreg.modules.device.object.block.BlockLogPile;
import su.terrafirmagreg.modules.device.object.block.BlockMolten;
import su.terrafirmagreg.modules.device.object.block.BlockOven;
import su.terrafirmagreg.modules.device.object.block.BlockOvenChimney;
import su.terrafirmagreg.modules.device.object.block.BlockOvenWall;
import su.terrafirmagreg.modules.device.object.block.BlockPitKiln;
import su.terrafirmagreg.modules.device.object.block.BlockPowderKeg;
import su.terrafirmagreg.modules.device.object.block.BlockQuernHorse;
import su.terrafirmagreg.modules.device.object.block.BlockQuernManual;
import su.terrafirmagreg.modules.device.object.block.BlockSmelteryCauldron;
import su.terrafirmagreg.modules.device.object.block.BlockSmelteryFirebox;
import su.terrafirmagreg.modules.device.object.block.BlockSnare;
import su.terrafirmagreg.modules.device.object.block.BlockThatchBed;

import java.util.function.Supplier;

public final class BlocksDevice {


  public static Supplier<BlockAlloyCalculator> ALLOY_CALCULATOR;
  public static Supplier<BlockBearTrap> BEAR_TRAP;
  public static Supplier<BlockSnare> SNARE;
  public static Supplier<BlockCrate> CRATE;
  public static Supplier<BlockBellows> BELLOWS;
  public static Supplier<BlockBlastFurnace> BLAST_FURNACE;
  public static Supplier<BlockBloom> BLOOM; // TODO noItems
  public static Supplier<BlockBloomery> BLOOMERY;
  public static Supplier<BlockCharcoalForge> CHARCOAL_FORGE;
  public static Supplier<BlockCharcoalPile> CHARCOAL_PILE; // TODO noItems
  public static Supplier<BlockCrucible> CRUCIBLE;
  public static Supplier<BlockFirePit> FIRE_PIT;
  public static Supplier<BlockPitKiln> PIT_KILN;
  public static Supplier<BlockQuernManual> QUERN_MANUAL;
  public static Supplier<BlockQuernHorse> QUERN_HORSE;
  public static Supplier<BlockMolten> MOLTEN; // TODO noItems
  public static Supplier<BlockLogPile> LOG_PILE;
  public static Supplier<BlockCellarShelf> CELLAR_SHELF;
  public static Supplier<BlockCellarDoor> CELLAR_DOOR;
  public static Supplier<BlockCellarWall> CELLAR_WALL;
  public static Supplier<BlockIceBunker> ICE_BUNKER;
  public static Supplier<BlockInfectedAir> INFECTED_AIR;
  public static Supplier<BlockFreezeDryer> FREEZE_DRYER;
  public static Supplier<BlockPowderKeg> POWDERKEG;
  public static Supplier<BlockThatchBed> THATCH_BED;
  public static Supplier<BlockGrindstoneManual> GRINDSTONE_MANUAL;
  public static Supplier<BlockElectricForge> ELECTRIC_FORGE;
  public static Supplier<BlockInductionCrucible> INDUCTION_CRUCIBLE;
  public static Supplier<BlockFridge> FRIDGE;
  public static Supplier<BlockLatexExtractor> LATEX_EXTRACTOR;
  public static Supplier<BlockSmelteryCauldron> SMELTERY_CAULDRON;
  public static Supplier<BlockSmelteryFirebox> SMELTERY_FIREBOX;
  public static Supplier<BlockGreenhouseDoor> GREENHOUSE_DOOR;
  public static Supplier<BlockGreenhouseRoof> GREENHOUSE_ROOF;
  public static Supplier<BlockGreenhouseWall> GREENHOUSE_WALL;
  public static Supplier<BlockOven> OVEN;
  public static Supplier<BlockOvenWall> OVEN_WALL;
  public static Supplier<BlockOvenChimney> OVEN_CHIMNEY;
  public static Supplier<BlockLeafMat> LEAF_MAT;
//  public static Supplier<BlockDryingMat> DRYING_MAT;


  public static void onRegister(IRegistryManager registry) {

    ALLOY_CALCULATOR = registry.block(new BlockAlloyCalculator());
    BEAR_TRAP = registry.block(new BlockBearTrap());
    SNARE = registry.block(new BlockSnare());
//    CRATE = registry.block(new BlockCrate());
    BELLOWS = registry.block(new BlockBellows());
    BLAST_FURNACE = registry.block(new BlockBlastFurnace());
    BLOOM = registry.block(new BlockBloom());
    BLOOMERY = registry.block(new BlockBloomery());
    CHARCOAL_FORGE = registry.block(new BlockCharcoalForge());
    CHARCOAL_PILE = registry.block(new BlockCharcoalPile());
    CRUCIBLE = registry.block(new BlockCrucible());
    FIRE_PIT = registry.block(new BlockFirePit());
    PIT_KILN = registry.block(new BlockPitKiln());
//    QUERN_MANUAL = registry.block(new BlockQuernManual());
//    QUERN_HORSE = registry.block(new BlockQuernHorse());
    MOLTEN = registry.block(new BlockMolten());
//    LOG_PILE = registry.block(new BlockLogPile());
    CELLAR_SHELF = registry.block(new BlockCellarShelf());
    CELLAR_DOOR = registry.block(new BlockCellarDoor());
    CELLAR_WALL = registry.block(new BlockCellarWall());
//    ICE_BUNKER = registry.block(new BlockIceBunker());
//    INFECTED_AIR = registry.block(new BlockInfectedAir());
//    FREEZE_DRYER = registry.block(new BlockFreezeDryer());
//    POWDERKEG = registry.block(new BlockPowderKeg());
//    THATCH_BED = registry.block(new BlockThatchBed());
//    GRINDSTONE_MANUAL = registry.block(new BlockGrindstoneManual());
//    ELECTRIC_FORGE = registry.block(new BlockElectricForge());
//    INDUCTION_CRUCIBLE = registry.block(new BlockInductionCrucible());
//    FRIDGE = registry.block(new BlockFridge());
//    LATEX_EXTRACTOR = registry.block(new BlockLatexExtractor());
    SMELTERY_CAULDRON = registry.block(new BlockSmelteryCauldron());
    SMELTERY_FIREBOX = registry.block(new BlockSmelteryFirebox());
//    GREENHOUSE_DOOR = registry.block(new BlockGreenhouseDoor());
//    GREENHOUSE_ROOF = registry.block(new BlockGreenhouseRoof());
//    GREENHOUSE_WALL = registry.block(new BlockGreenhouseWall());
//    OVEN = registry.block(new BlockOven());
//    OVEN_WALL = registry.block(new BlockOvenWall());
//    OVEN_CHIMNEY = registry.block(new BlockOvenChimney());
//    LEAF_MAT = registry.block(new BlockLeafMat());
//    DRYING_MAT = registry.block(new BlockDryingMat());

  }
}
