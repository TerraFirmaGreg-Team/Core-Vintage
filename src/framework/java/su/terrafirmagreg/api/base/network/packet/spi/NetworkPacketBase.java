package su.terrafirmagreg.api.base.network.packet.spi;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;

import java.io.Serializable;

public abstract class NetworkPacketBase implements Serializable, INetworkPacket {

//  @Override
//  public final void write(PacketBuffer buffer) {
//
//    ClassUtils.processFields(this, (obj, field) -> BufUtils.writeField(obj, field, buffer));
//  }
//
//  @Override
//  public final void read(PacketBuffer buffer) {
//
//    ClassUtils.processFields(this, (obj, field) -> BufUtils.readField(obj, field, buffer));
//  }


}
