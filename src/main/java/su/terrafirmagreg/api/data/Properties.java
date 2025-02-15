package su.terrafirmagreg.api.data;


import su.terrafirmagreg.api.data.enums.EnumDefault;
import su.terrafirmagreg.api.data.enums.EnumFirePitAttachment;
import su.terrafirmagreg.api.library.property.PropertyUnlistedDirection;
import su.terrafirmagreg.api.library.property.PropertyUnlistedObject;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

@SuppressWarnings("unused")
@UtilityClass
public final class Properties {

  public static class Unlisted {

    public static class ObjectProp {

      // PropertyUnlistedObject
      public static final PropertyUnlistedObject<IBlockState> HELD_STATE = PropertyUnlistedObject.create("held_state", IBlockState.class);
      public static final PropertyUnlistedObject<IBlockAccess> BLOCK_ACCESS = PropertyUnlistedObject.create("world", IBlockAccess.class);
      public static final PropertyUnlistedObject<BlockPos> BLOCK_POS = PropertyUnlistedObject.create("pos", BlockPos.class);
    }

    public static class DirectionProp {

      // PropertyUnlistedDirection
      public static final PropertyUnlistedDirection UNLISTED_FACING = PropertyUnlistedDirection.create("facing");
    }

  }

  public static class EnumProp {

    // PropertyEnum
    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);
    public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class);
    public static final PropertyEnum<EnumFacing.Axis> XZ = PropertyEnum.create("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);

    //    public static final PropertyEnum<EnumAging> AGING = PropertyEnum.create("age", EnumAging.class);
//    public static final PropertyEnum<EnumLeafState> LEAF_STATE = PropertyEnum.create("state", EnumLeafState.class);
//    public static final PropertyEnum<EnumFruitLeafState> FRUIT_LEAF_STATE = PropertyEnum.create("state", EnumFruitLeafState.class);
//    public static final PropertyEnum<EnumPlantPart> PLANT_PART = PropertyEnum.create("part", EnumPlantPart.class);
    public static final PropertyEnum<EnumFirePitAttachment> FIRE_PIT_ATTACHMENT = PropertyEnum.create("attachment", EnumFirePitAttachment.class);
    //    public static final PropertyEnum<EnumSpeleothemSize> SPELEOTHEM_SIZE = PropertyEnum.create("size", EnumSpeleothemSize.class);
//    public static final PropertyEnum<EnumHideSize> HIDE_SIZE = PropertyEnum.create("size", EnumHideSize.class);
    public static final PropertyEnum<EnumDefault> DEFAULT = PropertyEnum.create("variant", EnumDefault.class);
//    public static final PropertyEnum<EnumPressPart> PRESS_PART = PropertyEnum.create("part", EnumPressPart.class);
//    public static final PropertyEnum<EnumChopperPart> CHOPPER_PART = PropertyEnum.create("part", EnumChopperPart.class);
//    public static final PropertyEnum<EnumGradeOre> GRADE_ORE = PropertyEnum.create("grade", EnumGradeOre.class);
  }

  public static class DirectionProp {

    // PropertyDirection
    public static final PropertyDirection DIRECTIONAL = PropertyDirection.create("facing");
    public static final PropertyDirection HORIZONTAL = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyDirection HORIZONTALS = PropertyDirection.create("facing", Arrays.asList(EnumFacing.HORIZONTALS));
    public static final PropertyDirection VERTICAL = PropertyDirection.create("facing", EnumFacing.Plane.VERTICAL);
  }

  public static class IntProp {

    // PropertyInteger
    public static final PropertyInteger STAGE_3 = PropertyInteger.create("stage", 0, 2);
    public static final PropertyInteger STAGE_4 = PropertyInteger.create("stage", 0, 3);
    public static final PropertyInteger STAGE_5 = PropertyInteger.create("stage", 0, 4);
    public static final PropertyInteger STAGE_6 = PropertyInteger.create("stage", 0, 5);
    public static final PropertyInteger STAGE_7 = PropertyInteger.create("stage", 0, 6);
    public static final PropertyInteger STAGE_8 = PropertyInteger.create("stage", 0, 7);

    public static final PropertyInteger AGE_4 = PropertyInteger.create("age", 0, 3);
    public static final PropertyInteger AGE_5 = PropertyInteger.create("age", 0, 4);
    public static final PropertyInteger AGE_6 = PropertyInteger.create("age", 0, 5);
    public static final PropertyInteger AGE_7 = PropertyInteger.create("age", 0, 6);
    public static final PropertyInteger AGE_8 = PropertyInteger.create("age", 0, 7);

    public static final PropertyInteger NORTH_INT = PropertyInteger.create("north", 0, 2);
    public static final PropertyInteger EAST_INT = PropertyInteger.create("east", 0, 2);
    public static final PropertyInteger SOUTH_INT = PropertyInteger.create("south", 0, 2);
    public static final PropertyInteger WEST_INT = PropertyInteger.create("west", 0, 2);
    public static final PropertyInteger UP_INT = PropertyInteger.create("up", 0, 2);


    public static final PropertyInteger OFFSET = PropertyInteger.create("offset", 0, 7);
    public static final PropertyInteger JARS = PropertyInteger.create("jars", 1, 4);
    public static final PropertyInteger WEDGES = PropertyInteger.create("wedges", 0, 3);
    public static final PropertyInteger CLAY_LEVEL = PropertyInteger.create("clay", 0, 4);

    public static final PropertyInteger DAYPERIOD = PropertyInteger.create("dayperiod", 0, 3);
    public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, 7);
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
    public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 1, 4);
    public static final PropertyInteger TYPE = PropertyInteger.create("type", 1, 8);
    public static final PropertyInteger CUT = PropertyInteger.create("cut", 0, 2);
  }

  public static class BoolProp {

    // PropertyBool
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
    public static final PropertyBool BOTTOM = PropertyBool.create("bottom");
    public static final PropertyBool WILD = PropertyBool.create("wild");
    public static final PropertyBool BURIED = PropertyBool.create("buried");
    public static final PropertyBool BAITED = PropertyBool.create("baited");
    public static final PropertyBool OPEN = PropertyBool.create("open");
    public static final PropertyBool UPPER = PropertyBool.create("upper");
    public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
    public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
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
    public static final PropertyBool AXIS = PropertyBool.create("axis");
    public static final PropertyBool FILLED = PropertyBool.create("filled");
    public static final PropertyBool BASE = PropertyBool.create("base");
    public static final PropertyBool POT = PropertyBool.create("pot");
    public static final PropertyBool MATURE = PropertyBool.create("mature");
    public static final PropertyBool FRUITING = PropertyBool.create("fruiting");

    public static final PropertyBool[] ALL_FACES = new PropertyBool[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
  }

}
