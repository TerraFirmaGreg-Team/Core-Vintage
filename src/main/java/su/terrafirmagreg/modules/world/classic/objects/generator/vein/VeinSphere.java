package su.terrafirmagreg.modules.world.classic.objects.generator.vein;

import su.terrafirmagreg.api.data.enums.EnumGradeOre;

import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class VeinSphere extends Vein {

  private final double radiusSq;

  VeinSphere(BlockPos pos, VeinType type, EnumGradeOre grade, Random rand) {
    super(pos, type, grade);
    double innerRadius = (0.5 + 0.3 * rand.nextDouble()) * type.getWidth();
    this.radiusSq = innerRadius * innerRadius;
  }

  @Override
  public double getChanceToGenerate(BlockPos pos) {
    double dist = this.pos.distanceSq(pos) / radiusSq;
    if (dist < 0.8) {
      return type.getDensity();
    } else if (dist < 1) {
      return type.getDensity() * (1 - dist) / 0.2;
    } else {
      return 0;
    }
  }
}
