package net.dries007.time4tfc.network.packet;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;
import net.dries007.time4tfc.time4tfc;

import static su.terrafirmagreg.modules.core.ConfigCore.MISC;

/**
 * @author dmillerw
 */
public class PacketServerSettings implements IMessage, IMessageHandler<PacketServerSettings, IMessage> {

  public static int dayDuration = MISC.CALENDAR.MONTH.defaultDayDuration;
  public static int nightDuration = MISC.CALENDAR.MONTH.defaultNightDuration;
  public static int staticAngle = MISC.CALENDAR.MONTH.staticAngle;
  public static boolean staticMoon = MISC.CALENDAR.MONTH.staticMoon;


  @Override
  public void fromBytes(ByteBuf buf) {
    dayDuration = buf.readInt();
    nightDuration = buf.readInt();
    staticMoon = buf.readBoolean();
    staticAngle = buf.readInt();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(dayDuration);
    buf.writeInt(nightDuration);
    buf.writeBoolean(staticMoon);
    buf.writeInt(staticAngle);
  }

  @Override
  public IMessage onMessage(PacketServerSettings message, MessageContext ctx) {
    time4tfc.modEnabled = true;
    return null;
  }
}
