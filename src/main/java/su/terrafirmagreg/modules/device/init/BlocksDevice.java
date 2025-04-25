package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
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
  public static Supplier<BlockLatexExtractor> LATEX_EXTRACTOR; // TODO noItems
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


  public static void onRegister(IRegistryRegistrar registrar) {

    ALLOY_CALCULATOR = registrar.addBlock(new BlockAlloyCalculator());
    BEAR_TRAP = registrar.addBlock(new BlockBearTrap());
    SNARE = registrar.addBlock(new BlockSnare());
//    CRATE = registrar.addBlock(new BlockCrate());
    BELLOWS = registrar.addBlock(new BlockBellows());
    BLAST_FURNACE = registrar.addBlock(new BlockBlastFurnace());
    BLOOM = registrar.addBlock(new BlockBloom());
    BLOOMERY = registrar.addBlock(new BlockBloomery());
    CHARCOAL_FORGE = registrar.addBlock(new BlockCharcoalForge());
    CHARCOAL_PILE = registrar.addBlock(new BlockCharcoalPile());
    CRUCIBLE = registrar.addBlock(new BlockCrucible());
    FIRE_PIT = registrar.addBlock(new BlockFirePit());
    PIT_KILN = registrar.addBlock(new BlockPitKiln());
//    QUERN_MANUAL = registrar.addBlock(new BlockQuernManual());
//    QUERN_HORSE = registrar.addBlock(new BlockQuernHorse());
    MOLTEN = registrar.addBlock(new BlockMolten());
//    LOG_PILE = registrar.addBlock(new BlockLogPile());
    CELLAR_SHELF = registrar.addBlock(new BlockCellarShelf());
    CELLAR_DOOR = registrar.addBlock(new BlockCellarDoor());
    CELLAR_WALL = registrar.addBlock(new BlockCellarWall());
    ICE_BUNKER = registrar.addBlock(new BlockIceBunker());
    INFECTED_AIR = registrar.addBlock(new BlockInfectedAir());
    FREEZE_DRYER = registrar.addBlock(new BlockFreezeDryer());
    POWDERKEG = registrar.addBlock(new BlockPowderKeg());
//    THATCH_BED = registrar.addBlock(new BlockThatchBed());
//    GRINDSTONE_MANUAL = registrar.addBlock(new BlockGrindstoneManual());
//    ELECTRIC_FORGE = registrar.addBlock(new BlockElectricForge());
//    INDUCTION_CRUCIBLE = registrar.addBlock(new BlockInductionCrucible());
//    FRIDGE = registrar.addBlock(new BlockFridge());
    LATEX_EXTRACTOR = registrar.addBlock(new BlockLatexExtractor());
    SMELTERY_CAULDRON = registrar.addBlock(new BlockSmelteryCauldron());
    SMELTERY_FIREBOX = registrar.addBlock(new BlockSmelteryFirebox());
//    GREENHOUSE_DOOR = registrar.addBlock(new BlockGreenhouseDoor());
//    GREENHOUSE_ROOF = registrar.addBlock(new BlockGreenhouseRoof());
//    GREENHOUSE_WALL = registrar.addBlock(new BlockGreenhouseWall());
//    OVEN = registrar.addBlock(new BlockOven());
//    OVEN_WALL = registrar.addBlock(new BlockOvenWall());
//    OVEN_CHIMNEY = registrar.addBlock(new BlockOvenChimney());
//    LEAF_MAT = registrar.addBlock(new BlockLeafMat());
//    DRYING_MAT = registrar.addBlock(new BlockDryingMat());

  }
}
