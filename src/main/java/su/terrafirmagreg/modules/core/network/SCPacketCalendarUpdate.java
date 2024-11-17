package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.base.packet.BasePacket;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SCPacketCalendarUpdate extends BasePacket<SCPacketCalendarUpdate> {

  private Calendar instance;

  @SuppressWarnings("unused")
  @Deprecated
  public SCPacketCalendarUpdate() {
  }

  public SCPacketCalendarUpdate(Calendar instance) {
    this.instance = instance;
  }

  @Override
  public IMessage handleMessage(MessageContext context) {
    TerraFirmaGreg.getProxy()
                  .getThreadListener(context)
                  .addScheduledTask(() -> Calendar.INSTANCE.resetTo(instance));
    return null;
  }

//  @Override
//  public void fromBytes(ByteBuf buf) {
//    instance = new Calendar();
//    instance.read(buf);
//  }
//
//  @Override
//  public void toBytes(ByteBuf buf) {
//    instance.write(buf);
//  }

}
