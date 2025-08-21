package su.terrafirmagreg.modules.rock.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.object.block.BlockAlabasterBricks;
import su.terrafirmagreg.modules.rock.object.block.BlockAlabasterRaw;
import su.terrafirmagreg.modules.rock.object.block.BlockAlabasterSmooth;
import su.terrafirmagreg.modules.rock.object.block.BlockRockAnvil;
import su.terrafirmagreg.modules.rock.object.block.BlockRockBricks;
import su.terrafirmagreg.modules.rock.object.block.BlockRockButton;
import su.terrafirmagreg.modules.rock.object.block.BlockRockCobble;
import su.terrafirmagreg.modules.rock.object.block.BlockRockGravel;
import su.terrafirmagreg.modules.rock.object.block.BlockRockMagma;
import su.terrafirmagreg.modules.rock.object.block.BlockRockPressurePlate;
import su.terrafirmagreg.modules.rock.object.block.BlockRockRaw;
import su.terrafirmagreg.modules.rock.object.block.BlockRockSand;
import su.terrafirmagreg.modules.rock.object.block.BlockRockSmooth;
import su.terrafirmagreg.modules.rock.object.block.BlockRockSpeleothem;
import su.terrafirmagreg.modules.rock.object.block.BlockRockStandGem;
import su.terrafirmagreg.modules.rock.object.block.BlockRockSurface;

import net.minecraft.item.EnumDyeColor;

import java.util.Arrays;
import java.util.Map;

public class BlocksRock {

  public static Map<RockType, BlockRockCobble> COBBLE;
  public static Map<RockType, BlockRockCobble> COBBLE_STAIRS;
  public static Map<RockType, BlockRockCobble> COBBLE_SLAB_DOUBLE;
  public static Map<RockType, BlockRockCobble> COBBLE_SLAB;
  public static Map<RockType, BlockRockCobble> COBBLE_WALL;

  public static Map<RockType, BlockRockRaw> RAW;
  public static Map<RockType, BlockRockCobble> RAW_STAIRS;
  public static Map<RockType, BlockRockCobble> RAW_SLAB_DOUBLE;
  public static Map<RockType, BlockRockCobble> RAW_SLAB;
  public static Map<RockType, BlockRockCobble> RAW_WALL;

  public static Map<RockType, BlockRockBricks> BRICKS;
  public static Map<RockType, BlockRockCobble> BRICKS_STAIRS;
  public static Map<RockType, BlockRockCobble> BRICKS_SLAB_DOUBLE;
  public static Map<RockType, BlockRockCobble> BRICKS_SLAB;
  public static Map<RockType, BlockRockCobble> BRICKS_WALL;

  public static Map<RockType, BlockRockSmooth> SMOOTH;
  public static Map<RockType, BlockRockCobble> SMOOTH_STAIRS;
  public static Map<RockType, BlockRockCobble> SMOOTH_SLAB_DOUBLE;
  public static Map<RockType, BlockRockCobble> SMOOTH_SLAB;
  public static Map<RockType, BlockRockCobble> SMOOTH_WALL;

  public static Map<RockType, BlockRockCobble> BRICKS_CRACKED;
  public static Map<RockType, BlockRockCobble> CHISELED;

  public static Map<RockType, BlockRockGravel> GRAVEL;
  public static Map<RockType, BlockRockSand> SAND;
  public static Map<RockType, BlockRockSurface> SURFACE;
  public static Map<RockType, BlockRockSpeleothem> SPELEOTHEM;
  public static Map<RockType, BlockRockButton> BUTTON;
  public static Map<RockType, BlockRockPressurePlate> PRESSURE_PLATE;
  public static Map<RockType, BlockRockAnvil> ANVIL;
  public static Map<RockType, BlockRockMagma> MAGMA;
  public static Map<RockType, BlockRockStandGem> STAND_GEM;


  public static Map<EnumDyeColor, BlockAlabasterBricks> ALABASTER_BRICKS;
  public static Map<EnumDyeColor, BlockAlabasterSmooth> ALABASTER_SMOOTH;
  public static Map<EnumDyeColor, BlockAlabasterRaw> ALABASTER_RAW;

  public static void onRegister(IRegistryRegistrar registrar) {

    COBBLE = registrar.addBlock(BlockRockCobble::new, RockType.getTypes());
    RAW = registrar.addBlock(BlockRockRaw::new, RockType.getTypes());
    SMOOTH = registrar.addBlock(BlockRockSmooth::new, RockType.getTypes());
    GRAVEL = registrar.addBlock(BlockRockGravel::new, RockType.getTypes());
    SAND = registrar.addBlock(BlockRockSand::new, RockType.getTypes());
    SURFACE = registrar.addBlock(BlockRockSurface::new, RockType.getTypes());
    SPELEOTHEM = registrar.addBlock(BlockRockSpeleothem::new, RockType.getTypes());
    BUTTON = registrar.addBlock(BlockRockButton::new, RockType.getTypes());
    PRESSURE_PLATE = registrar.addBlock(BlockRockPressurePlate::new, RockType.getTypes());
    ANVIL = registrar.addBlock(BlockRockAnvil::new, RockType.getTypes());
    MAGMA = registrar.addBlock(BlockRockMagma::new, RockType.getTypes());
    STAND_GEM = registrar.addBlock(BlockRockStandGem::new, RockType.getTypes());

    ALABASTER_BRICKS = registrar.addBlock(BlockAlabasterBricks::new, Arrays.asList(EnumDyeColor.values()));
    ALABASTER_SMOOTH = registrar.addBlock(BlockAlabasterSmooth::new, Arrays.asList(EnumDyeColor.values()));
    ALABASTER_RAW = registrar.addBlock(BlockAlabasterRaw::new, Arrays.asList(EnumDyeColor.values()));
  }
}
