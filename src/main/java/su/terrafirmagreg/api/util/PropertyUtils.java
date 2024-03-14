package su.terrafirmagreg.api.util;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import su.terrafirmagreg.api.models.property.PropertyObject;

public final class PropertyUtils {

	/**
	 * Used to handle whether or not the block is on or off. Used mainly by redstone blocks.
	 */
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public static final PropertyBool MOSSY = PropertyBool.create("mossy");

	public static final PropertyBool CAN_FALL = PropertyBool.create("can_fall");

	public static final PropertyBool SEALED = PropertyBool.create("sealed");

	public static final PropertyBool HARVESTABLE = PropertyBool.create("harvestable");
	public static final PropertyBool PLACED = PropertyBool.create("placed");
	public static final PropertyBool SMALL = PropertyBool.create("small");

	/**
	 * Used to hold another block state. This is useful for mimicking other blocks.
	 */
	public static final PropertyObject<IBlockState> HELD_STATE = new PropertyObject<>("held_state", IBlockState.class);

	/**
	 * Used to hold an IBlockAccess of the block.
	 */
	public static final PropertyObject<IBlockAccess> BLOCK_ACCESS = new PropertyObject<>("world", IBlockAccess.class);

	/**
	 * Used to hold the BlockPos of the block.
	 */
	public static final PropertyObject<BlockPos> BLOCKPOS = new PropertyObject<>("pos", BlockPos.class);

	/**
	 * Used to determine the color of a block. Only supports the 16 vanilla colors.
	 */
	public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);

	/**
	 * Used to determine the direction a block is facing.
	 */
	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);

	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class);

	/**
	 * Used to determine the direction a block is facing. Only includes horizontal directions.
	 * (N-S-W-E)
	 */
	public static final PropertyDirection HORIZONTAL = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	/**
	 * Used to determine if a block is connected on the bottom face.
	 */
	public static final PropertyBool DOWN = PropertyBool.create("down");

	/**
	 * Used to determine if a block is connected on the upward face.
	 */
	public static final PropertyBool UP = PropertyBool.create("up");

	/**
	 * Used to determine if a block is connected on the northern face.
	 */
	public static final PropertyBool NORTH = PropertyBool.create("north");

	/**
	 * Used to determine if a block is connected on the southern face.
	 */
	public static final PropertyBool SOUTH = PropertyBool.create("south");

	/**
	 * Used to determine if a block is connected on the eastern face.
	 */
	public static final PropertyBool EAST = PropertyBool.create("east");

	/**
	 * Used to determine if a block is connected on the western face.
	 */
	public static final PropertyBool WEST = PropertyBool.create("west");

	/**
	 * Used to determine if a block has been enabled or not.
	 */
	public static final PropertyBool ENABLED = PropertyBool.create("enabled");

	/**
	 * Utility classes, such as this one, are not meant to be instantiated. Java adds an
	 * implicit public constructor to every class which does not define at lease one
	 * explicitly. Hence why this constructor was added.
	 */
	private PropertyUtils() {

		throw new IllegalAccessError("Utility class");
	}
}