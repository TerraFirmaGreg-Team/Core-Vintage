package su.terrafirmagreg.framework.manager.packet.base;

import su.terrafirmagreg.framework.manager.packet.api.IPacketEntry;

import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class BasePacket implements Serializable, IPacketEntry {

  /**
   * The serial version for this UID.
   */
  private static final long serialVersionUID = 3214832642504369023L;

  private final Settings settings;

  public BasePacket() {
    this(Settings.of());
  }

  public BasePacket(Settings settings) {
    this.settings = settings;
  }


}
