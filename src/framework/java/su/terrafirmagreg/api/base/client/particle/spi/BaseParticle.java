package su.terrafirmagreg.api.base.client.particle.spi;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public abstract class BaseParticle extends Particle {

  protected BaseParticle(World worldIn, double posXIn, double posYIn, double posZIn) {
    super(worldIn, posXIn, posYIn, posZIn);
  }

  public BaseParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
    super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
  }
}
