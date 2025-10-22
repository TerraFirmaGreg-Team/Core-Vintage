package su.terrafirmagreg.modules.rock.init;

import su.terrafirmagreg.api.data.enums.EnumColor;
import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.content.block.BlockAlabaster;
import su.terrafirmagreg.modules.rock.content.block.BlockRockAnvil;
import su.terrafirmagreg.modules.rock.content.block.BlockRockBricks;
import su.terrafirmagreg.modules.rock.content.block.BlockRockButton;
import su.terrafirmagreg.modules.rock.content.block.BlockRockCobble;
import su.terrafirmagreg.modules.rock.content.block.BlockRockGravel;
import su.terrafirmagreg.modules.rock.content.block.BlockRockMagma;
import su.terrafirmagreg.modules.rock.content.block.BlockRockPressurePlate;
import su.terrafirmagreg.modules.rock.content.block.BlockRockRaw;
import su.terrafirmagreg.modules.rock.content.block.BlockRockSand;
import su.terrafirmagreg.modules.rock.content.block.BlockRockSmooth;
import su.terrafirmagreg.modules.rock.content.block.BlockRockSpeleothem;
import su.terrafirmagreg.modules.rock.content.block.BlockRockStandGem;
import su.terrafirmagreg.modules.rock.content.block.BlockRockSurface;

import java.util.Arrays;
import java.util.Map;

public class BlocksRock {

  public static Map<RockType, BlockRockCobble> COBBLE;
  public static Map<RockType, BlockRockRaw> RAW;
  public static Map<RockType, BlockRockBricks> BRICKS;
  public static Map<RockType, BlockRockSmooth> SMOOTH;

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


  public static Map<EnumColor, BlockAlabaster> ALABASTER_BRICKS;
  public static Map<EnumColor, BlockAlabaster> ALABASTER_SMOOTH;
  public static Map<EnumColor, BlockAlabaster> ALABASTER_RAW;

  public static void onRegister(IContentRegistrar registrar) {

    COBBLE = registrar.addBlock("cobble", BlockRockCobble::new, RockType.getTypes());
    RAW = registrar.addBlock("raw", BlockRockRaw::new, RockType.getTypes());
    SMOOTH = registrar.addBlock("smooth", BlockRockSmooth::new, RockType.getTypes());
    GRAVEL = registrar.addBlock("gravel", BlockRockGravel::new, RockType.getTypes());
    SAND = registrar.addBlock("sand", BlockRockSand::new, RockType.getTypes());
    SURFACE = registrar.addBlock("surface", BlockRockSurface::new, RockType.getTypes());
    SPELEOTHEM = registrar.addBlock("speleothem", BlockRockSpeleothem::new, RockType.getTypes());
    BUTTON = registrar.addBlock("button", BlockRockButton::new, RockType.getTypes());
    PRESSURE_PLATE = registrar.addBlock("pressure_plate", BlockRockPressurePlate::new, RockType.getTypes());
    ANVIL = registrar.addBlock("anvil", BlockRockAnvil::new, RockType.getTypes());
    MAGMA = registrar.addBlock("magma", BlockRockMagma::new, RockType.getTypes());
    STAND_GEM = registrar.addBlock("stand_gem", BlockRockStandGem::new, RockType.getTypes());

    ALABASTER_BRICKS = registrar.addBlock("alabaster/bricks", BlockAlabaster::new, Arrays.asList(EnumColor.values()));
    ALABASTER_SMOOTH = registrar.addBlock("alabaster/smooth", BlockAlabaster::new, Arrays.asList(EnumColor.values()));
    ALABASTER_RAW = registrar.addBlock("alabaster/raw", BlockAlabaster::new, Arrays.asList(EnumColor.values()));
  }
}
