package su.terrafirmagreg.framework.manager.registry.provider;

/**
 * in 1.15, use {@link net.minecraft.world.World#notifyBlockUpdate} instead to keep client updated
 */
public interface IProviderTileFields {

  int getFieldCount();

  void setField(int index, int value);

  int getField(int index);
}
