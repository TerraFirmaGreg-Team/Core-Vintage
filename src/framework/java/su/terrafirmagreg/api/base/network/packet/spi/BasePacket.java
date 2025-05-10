package su.terrafirmagreg.api.base.network.packet.spi;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;

import java.io.Serializable;

public abstract class BasePacket implements Serializable, INetworkPacket {

  /**
   * The serial version for this UID.
   */
  private static final long serialVersionUID = 3214832642504369023L;


}
