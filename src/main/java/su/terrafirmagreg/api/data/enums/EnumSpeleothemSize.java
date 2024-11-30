package su.terrafirmagreg.api.data.enums;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;

import org.jetbrains.annotations.NotNull;

public enum EnumSpeleothemSize implements IStringSerializable {

  SMALL(0, 2),
  MEDIUM(1, 4),
  BIG(2, 8);

  public final int strength;
  public final AxisAlignedBB aabb;

  EnumSpeleothemSize(int strength, int width) {
    this.strength = strength;

    float pad = (((16 - width) / 2f) / 16F);
    aabb = new AxisAlignedBB(pad, 0F, pad, 1F - pad, 1F, 1F - pad);
  }

  @Override
  public @NotNull String getName() {
    return name().toLowerCase();
  }
}
