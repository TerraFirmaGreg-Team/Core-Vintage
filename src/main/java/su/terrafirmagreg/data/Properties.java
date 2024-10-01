package su.terrafirmagreg.data;


import su.terrafirmagreg.data.lib.property.PropertyUnlistedDirection;
import su.terrafirmagreg.data.lib.property.PropertyUnlistedObject;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodLeaves.EnumLeafState;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import com.eerussianguy.firmalife.init.EnumAging;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

@SuppressWarnings("unused")
@UtilityClass
public final class Properties {


  public static final PropertyUnlistedObject<IBlockState> HELD_STATE = new PropertyUnlistedObject<>("held_state", IBlockState.class);
  public static final PropertyUnlistedObject<IBlockAccess> BLOCK_ACCESS = new PropertyUnlistedObject<>("world", IBlockAccess.class);
  public static final PropertyUnlistedObject<BlockPos> BLOCK_POS = new PropertyUnlistedObject<>("pos", BlockPos.class);


  public static final PropertyUnlistedDirection UNLISTED_FACING = new PropertyUnlistedDirection("facing");


  public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);
  public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);
  public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class);
  public static final PropertyEnum<EnumFacing.Axis> XZ = PropertyEnum.create("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);
  public static final PropertyEnum<EnumAging> AGE = PropertyEnum.create("age", EnumAging.class);
  public static final PropertyEnum<EnumLeafState> LEAF_STATE = PropertyEnum.create("state", EnumLeafState.class);


  public static final PropertyDirection DIRECTIONAL = PropertyDirection.create("facing");
  public static final PropertyDirection HORIZONTAL = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
  public static final PropertyDirection HORIZONTALS = PropertyDirection.create("facing", Arrays.asList(EnumFacing.HORIZONTALS));
  public static final PropertyDirection VERTICAL = PropertyDirection.create("facing", EnumFacing.Plane.VERTICAL);


  public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 2);
  public static final PropertyInteger JARS = PropertyInteger.create("jars", 1, 4);
  public static final PropertyInteger WEDGES = PropertyInteger.create("wedges", 0, 3);
  public static final PropertyInteger CLAY_LEVEL = PropertyInteger.create("clay", 0, 4);
  public static final PropertyInteger SAPLING_STAGE = PropertyInteger.create("stage", 0, 4);


  public static final PropertyBool DOWN = PropertyBool.create("down");
  public static final PropertyBool UP = PropertyBool.create("up");
  public static final PropertyBool NORTH = PropertyBool.create("north");
  public static final PropertyBool SOUTH = PropertyBool.create("south");
  public static final PropertyBool EAST = PropertyBool.create("east");
  public static final PropertyBool WEST = PropertyBool.create("west");
  public static final PropertyBool ENABLED = PropertyBool.create("enabled");
  public static final PropertyBool POWERED = PropertyBool.create("powered");
  public static final PropertyBool MOSSY = PropertyBool.create("mossy");
  public static final PropertyBool CAN_FALL = PropertyBool.create("can_fall");
  public static final PropertyBool SEALED = PropertyBool.create("sealed");
  public static final PropertyBool HARVESTABLE = PropertyBool.create("harvestable");
  public static final PropertyBool PLACED = PropertyBool.create("placed");
  public static final PropertyBool SMALL = PropertyBool.create("small");
  public static final PropertyBool CLOSED = PropertyBool.create("closed");
  public static final PropertyBool BURIED = PropertyBool.create("buried");
  public static final PropertyBool BAITED = PropertyBool.create("baited");
  public static final PropertyBool OPEN = PropertyBool.create("open");
  public static final PropertyBool UPPER = PropertyBool.create("upper");
  public static final PropertyBool FULL = PropertyBool.create("full");
  public static final PropertyBool LIT = PropertyBool.create("lit");
  public static final PropertyBool GROWN = PropertyBool.create("grown");
  public static final PropertyBool CAN_GROW = PropertyBool.create("can_grow");
  public static final PropertyBool CONNECTED = PropertyBool.create("connected");
  public static final PropertyBool CLAY = PropertyBool.create("clay");
  public static final PropertyBool GLASS = PropertyBool.create("glass");
  public static final PropertyBool TOP = PropertyBool.create("top");
  public static final PropertyBool CURED = PropertyBool.create("cured");
  public static final PropertyBool WET = PropertyBool.create("wet");
  public static final PropertyBool WATERED = PropertyBool.create("watered");
  public static final PropertyBool NEEDS_SOURCE = PropertyBool.create("needs_source");
  public static final PropertyBool STASIS = PropertyBool.create("stasis");
  public static final PropertyBool SNOWY = PropertyBool.create("snowy");
  public static final PropertyBool FANCY = PropertyBool.create("fancy");
  public static final PropertyBool FILLED = PropertyBool.create("filled");

}
