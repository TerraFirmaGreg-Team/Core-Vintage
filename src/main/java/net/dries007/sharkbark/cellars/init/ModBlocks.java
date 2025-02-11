package net.dries007.sharkbark.cellars.init;

import su.terrafirmagreg.modules.device.object.block.BlockCellarDoor;
import su.terrafirmagreg.modules.device.object.block.BlockCellarShelf;
import su.terrafirmagreg.modules.device.object.block.BlockCellarWall;
import su.terrafirmagreg.modules.device.object.block.BlockFreezeDryer;
import su.terrafirmagreg.modules.device.object.block.BlockIceBunker;
import su.terrafirmagreg.modules.device.object.block.BlockInfectedAir;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

  public static final List<Block> BLOCKS = new ArrayList<Block>();

  public static final Block CELLAR_WALL = new BlockCellarWall("cellar_wall", Material.ROCK);
  public static final Block CELLAR_DOOR = new BlockCellarDoor("cellar_door", Material.ROCK);
  public static final Block CELLAR_SHELF = new BlockCellarShelf("cellar_shelf", Material.WOOD);
  public static final Block ICE_BUNKER = new BlockIceBunker("ice_bunker", Material.ROCK);

  public static final Block FREEZE_DRYER = new BlockFreezeDryer("freeze_dryer", Material.ROCK);

  public static final Block CELLAR_AIR = new BlockInfectedAir("infected_air", Material.AIR);
}
