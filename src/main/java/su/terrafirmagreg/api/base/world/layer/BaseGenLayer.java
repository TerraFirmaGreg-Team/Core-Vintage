package su.terrafirmagreg.api.base.world.layer;

import net.minecraft.world.gen.layer.GenLayer;

public abstract class BaseGenLayer extends GenLayer {

  public BaseGenLayer(long seed) {
    super(seed);
  }

  // make nextInt public
  @Override
  public int nextInt(int a) {
    return super.nextInt(a);
  }
}
