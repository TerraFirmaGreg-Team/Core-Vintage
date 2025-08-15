package su.terrafirmagreg.framework.manager.packet.base;

import su.terrafirmagreg.framework.manager.packet.api.IPacketEntry;

import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class BasePacket implements Serializable, IPacketEntry {

  /**
   * The serial version for this UID.
   */
  private static final long serialVersionUID = 3214832642504369023L;

  protected final PacketSettings settings;

  public BasePacket() {
    this(PacketSettings.of());
  }

  public BasePacket(PacketSettings settings) {
    this.settings = settings;
  }

  public boolean verify(MessageContext context) {

    return true;
  }


}
