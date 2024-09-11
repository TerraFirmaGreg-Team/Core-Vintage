package net.dries007.tfc.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


import io.netty.buffer.ByteBuf;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.util.calendar.Calendar;

public class PacketCalendarUpdate implements IMessage {

  private Calendar instance;

  @SuppressWarnings("unused")
  @Deprecated
  public PacketCalendarUpdate() {
  }

  public PacketCalendarUpdate(Calendar instance) {
    this.instance = instance;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    instance = new Calendar();
    instance.read(buf);
  }

  @Override
  public void toBytes(ByteBuf buf) {
    instance.write(buf);
  }

  public static class Handler implements IMessageHandler<PacketCalendarUpdate, IMessage> {

    @Override
    public IMessage onMessage(PacketCalendarUpdate message, MessageContext ctx) {
      TerraFirmaCraft.getProxy()
              .getThreadListener(ctx)
              .addScheduledTask(() -> Calendar.INSTANCE.resetTo(message.instance));
      return null;
    }
  }
}
