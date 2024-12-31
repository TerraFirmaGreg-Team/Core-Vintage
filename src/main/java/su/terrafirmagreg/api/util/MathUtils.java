package su.terrafirmagreg.api.util;

import su.terrafirmagreg.api.library.ThermiteRandom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@UtilityClass
@SuppressWarnings("unused")
public final class MathUtils {

  /**
   * A close to zero double epsilon value
   */
  public static final double DBL_EPSILON = 2.220446049250313E-16d;
  /**
   * A close to zero float epsilon value
   */
  public static final float FLT_EPSILON = 1.1920928955078125E-7f;
  /**
   * A close to one float value
   */
  public static final float FLT_ONE_MINUS_EPSILON = 1.0f - FLT_EPSILON;
  /**
   * For use as zero tolerance value
   */
  public static final float ZERO_TOLERANCE = 0.0001f;
  /**
   * One divided by three floating point value
   */
  public static final float ONE_THIRD = 1.0f / 3.0f;
  /**
   * E as a floating point value
   */
  public static final float E = (float) Math.E;
  /**
   * PI as a floating point value
   */
  public static final float PI = (float) Math.PI;
  /**
   * Multiply by to convert from radians to degrees
   */
  public static final float RAD_TO_DEG = 180.0f / PI;
  /**
   * Multiply by to convert from degrees to radians
   */
  public static final float DEG_TO_RAD = PI / 180.0f;
  /**
   * PI/360 as a floating point value
   */
  public static final float PI_OVER_360 = PI / 360.0f;
  /**
   * 1/PI as a floating point value
   */
  public static final float INV_PI = 1.0f / PI;
  /**
   * 3PI/4 as a floating point value
   */
  public static final float THREE_QUARTER_PI = 0.75f * PI;
  /**
   * PI/4 as a floating point value
   */
  public static final float QUARTER_PI = 0.25f * PI;
  /**
   * PI/2 as a floating point value
   */
  public static final float HALF_PI = 0.5f * PI;
  /**
   * 2PI as a floating point value
   */
  public static final float TWO_PI = 2.0f * PI;
  /**
   * 1/(2PI) as a floating point value
   */
  public static final float INV_TWO_PI = 1.0f / TWO_PI;
  /**
   * Square root of 3 as a floating point value
   */
  public static final float SQRT3 = 1.7320508075688772935274463415059f;
  /**
   * Pre-generated random object
   */
  public static final ThermiteRandom TRNG = new ThermiteRandom();
  public static final Random RNG = new Random();
  // Used for fastFloor, fastRound, fastCeil
  // http://riven8192.blogspot.com/2010/02/fastmath-fast-floor.html
  private static final int BIG_ENOUGH_INT = 16 * 1024;
  private static final double BIG_ENOUGH_CEIL = BIG_ENOUGH_INT + 0.9999;
  private static final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5000;
  private static final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT + 0.0000;

  /**
   * Checks if a double is within range of two other doubles.
   *
   * @param min:   The smallest valid value.
   * @param max:   The largest valid value.
   * @param value: The value to check.
   * @return boolean: Whether or not the value is within the provided scope.
   */
  public static boolean isInRange(double min, double max, double value) {

    return value <= max && value >= min;
  }

  /**
   * Calculates the distance between two Vec3 positions.
   *
   * @param firstPos:  The first position to work with.
   * @param secondPos: The second position to work with.
   * @return double: The distance between the two provided locations.
   */
  public static double getDistanceBetweenPoints(Vec3d firstPos, Vec3d secondPos) {

    final double distanceX = firstPos.x - secondPos.x;
    final double distanceY = firstPos.y - secondPos.y;
    final double distanceZ = firstPos.z - secondPos.z;

    return Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
  }

  /**
   * This method can be used to round a double to a certain amount of places.
   *
   * @param value:  The double being round.
   * @param places: The amount of places to round the double to.
   * @return double: The double entered however being rounded to the amount of places specified.
   */
  public static double round(double value, int places) {

    return value >= 0 && places > 0 ? BigDecimal.valueOf(value)
                                                .setScale(places, RoundingMode.HALF_UP).doubleValue() : value;
  }

  /**
   * Used to retrieve a random integer between the two provided integers. The integers provided are also possible outcomes.
   *
   * @param min: The minimum value which can be returned by this method.
   * @param max: The maximum value which can be returned by this method.
   */
  public static int nextIntInclusive(int min, int max) {

    return RNG.nextInt(max - min + 1) + min;
  }

  /**
   * Performs a ray trace on where an entity is looking. This will return information about the blocks in their way.
   *
   * @param entity The entity to perform the ray trace on.
   * @param length The amount of distance in world to perform the trace on. In survival mode players have 4.5 blocks of length.
   * @return An object containing the results of the ray trace that was performed.
   */
  public static RayTraceResult rayTrace(EntityLivingBase entity, double length) {

    final Vec3d vec1 = new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
    final Vec3d vec2 = entity.getLookVec();
    final Vec3d vec3 = vec1.add(vec2.x * length, vec2.y * length, vec2.z * length);
    return entity.world.rayTraceBlocks(vec1, vec3);
  }

  /**
   * Copy from Item#rayTrace Returns a RayTraceResult containing first found block in Players reach.
   *
   * @param worldIn    the world obj player stands in.
   * @param playerIn   the player obj
   * @param useLiquids do fluids count as block?
   */
  @Nullable
  public static RayTraceResult rayTrace(World worldIn, EntityPlayer playerIn, boolean useLiquids) {
    Vec3d playerVec = new Vec3d(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(),
                                playerIn.posZ);
    float cosYaw = MathHelper.cos(-playerIn.rotationYaw * 0.017453292F - PI);
    float sinYaw = MathHelper.sin(-playerIn.rotationYaw * 0.017453292F - PI);
    float cosPitch = -MathHelper.cos(-playerIn.rotationPitch * 0.017453292F);
    float sinPitch = MathHelper.sin(-playerIn.rotationPitch * 0.017453292F);
    double reachDistance = playerIn.getEntityAttribute(EntityPlayer.REACH_DISTANCE)
                                   .getAttributeValue();
    Vec3d targetVec = playerVec.add(
      (sinYaw * cosPitch) * reachDistance,
      sinPitch * reachDistance,
      (cosYaw * cosPitch) * reachDistance
    );
    return worldIn.rayTraceBlocks(playerVec, targetVec, useLiquids, !useLiquids, false);
  }

  /**
   * Copied from {@link Entity#rayTrace(double, float)} as it is client only
   *
   * @param blockReachDistance the reach distance
   * @param partialTicks       idk
   * @return the ray trace result
   */
  @Nullable
  public static RayTraceResult rayTrace(Entity entity, double blockReachDistance, float partialTicks) {
    Vec3d eyePosition = entity.getPositionEyes(partialTicks);
    Vec3d lookVector = entity.getLook(partialTicks);
    Vec3d rayTraceVector = eyePosition.add(
      lookVector.x * blockReachDistance,
      lookVector.y * blockReachDistance,
      lookVector.z * blockReachDistance
    );
    return entity.world.rayTraceBlocks(eyePosition, rayTraceVector, false, false, true);
  }

  /**
   * A method which handles the calculating of percentages. While this isn't a particularly difficult piece of code, it has been added for the sake of
   * simplicity.
   *
   * @param percent: The percent chance that this method should return true. 1.00 = 100%
   * @return boolean: Returns are randomly true or false, based on the suplied percentage.
   */
  public static boolean tryPercentage(double percent) {

    return Math.random() < percent;
  }

  /**
   * Gets the middle integer between two other integers. The order is not important.
   *
   * @param first:  The first integer.
   * @param second: The second integer.
   * @return int: The integer that is between the two provided integers.
   */
  public static int getAverage(int first, int second) {

    return Math.round((first + second) / 2.0F);
  }

  /**
   * Converts time in ticks to a human readable string.
   *
   * @param ticks: The amount of ticks to convert.
   * @return String: A human readable version of the time.
   */
  public static String ticksToTime(int ticks) {

    final int seconds = ticks / 20;
    final int minutes = seconds / 60;
    return minutes + ":" + seconds;
  }

  /**
   * Gets the percentage of an integer. Result is an integer and decimal is lost.
   *
   * @param value The value to get the percentage of.
   * @param total The total/max value.
   * @return The percentage as an integer.
   */
  public static int getPercentage(int value, int total) {

    return (int) ((float) value / (float) total * 100f);
  }

  /**
   * Creates a bounding box using pixel size.
   *
   * @param minX The min X pos.
   * @param minY The min Y pos.
   * @param minZ The min Z pos.
   * @param maxX The max X pos.
   * @param maxY The max Y pos.
   * @param maxZ The max Z pos.
   * @return A bounding box that is made to a pixel specific size.
   */
  public static AxisAlignedBB getBoundsForPixels(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {

    return new AxisAlignedBB(
      getPixelDistance(minX),
      getPixelDistance(minY),
      getPixelDistance(minZ),
      getPixelDistance(maxX),
      getPixelDistance(maxY),
      getPixelDistance(maxZ)
    );
  }

  /**
   * Gets the distance in world for an amount of pixels. A basic block is a cubic meter, and each pixel is 1/16th of a block.
   *
   * @param pixels The amount of pixels
   * @return The distance in game for those pixels.
   */
  public static double getPixelDistance(int pixels) {

    return pixels / 16d;
  }

  /**
   * Takes an integer value and fits it within a range. If the initial value is less than the minimum it will be set to the minimum. If the initial value is
   * greater than the maximum it will be lowered to the maximum.
   *
   * @param initial The initial value.
   * @param min     The minimum value.
   * @param max     The maximum value.
   * @return The adjusted value.
   */
  public static int adjustToRange(int initial, int min, int max) {

    return initial < min ? min : Math.min(initial, max);
  }
}
