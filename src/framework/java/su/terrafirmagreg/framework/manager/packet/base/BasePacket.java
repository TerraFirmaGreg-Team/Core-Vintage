package su.terrafirmagreg.framework.manager.packet.base;

import su.terrafirmagreg.framework.manager.packet.api.IPacketEntry;

import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import lombok.Getter;

@Getter
public abstract class BasePacket implements IPacketEntry {


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
