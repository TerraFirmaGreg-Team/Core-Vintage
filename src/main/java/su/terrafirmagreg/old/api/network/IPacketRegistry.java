package su.terrafirmagreg.old.api.network;

import net.minecraftforge.fml.relauncher.Side;

public interface IPacketRegistry {

  <P extends IPacket<P>> IPacketRegistry register(Side side, Class<P> clazz);
}
