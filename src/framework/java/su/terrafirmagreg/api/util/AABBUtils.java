package su.terrafirmagreg.api.util;

import net.minecraft.util.math.AxisAlignedBB;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public final class AABBUtils {


  public static AxisAlignedBB rotateHorizontalCCW90Centered(AxisAlignedBB axisAlignedBB) {

    return new AxisAlignedBB(
      0.5 + (axisAlignedBB.minX - 0.5) * MathUtils.COS_90 - (axisAlignedBB.minZ - 0.5) * MathUtils.SIN_90, axisAlignedBB.minY,
      0.5 + (axisAlignedBB.minX - 0.5) * MathUtils.SIN_90 + (axisAlignedBB.minZ - 0.5) * MathUtils.COS_90,
      0.5 + (axisAlignedBB.maxX - 0.5) * MathUtils.COS_90 - (axisAlignedBB.maxZ - 0.5) * MathUtils.SIN_90, axisAlignedBB.maxY,
      0.5 + (axisAlignedBB.maxX - 0.5) * MathUtils.SIN_90 + (axisAlignedBB.maxZ - 0.5) * MathUtils.COS_90
    );
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
  public static AxisAlignedBB create(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {

    return new AxisAlignedBB(
      MathUtils.getPixelDistance(minX),
      MathUtils.getPixelDistance(minY),
      MathUtils.getPixelDistance(minZ),
      MathUtils.getPixelDistance(maxX),
      MathUtils.getPixelDistance(maxY),
      MathUtils.getPixelDistance(maxZ)
    );
  }

  public static boolean contains(AxisAlignedBB bounds, double x, double y, double z) {

    if (x > bounds.minX && x < bounds.maxX) {

      if (y > bounds.minY && y < bounds.maxY) {
        return z > bounds.minZ && z < bounds.maxZ;

      } else {
        return false;
      }

    } else {
      return false;
    }
  }


}
