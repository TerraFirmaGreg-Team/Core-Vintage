package net.dries007.tfc.module.core.network;

import io.netty.buffer.ByteBuf;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import su.terrafirmagreg.tfc.TerraFirmaCraft;

public class SCPacketCalendarUpdate implements IMessage, IMessageHandler<SCPacketCalendarUpdate, IMessage> {
    private CalendarTFC instance;

    @SuppressWarnings("unused")
    public SCPacketCalendarUpdate() {
    }

    public SCPacketCalendarUpdate(CalendarTFC instance) {
        this.instance = instance;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        instance = new CalendarTFC();
        instance.read(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        instance.write(buf);
    }

    @Override
    public IMessage onMessage(SCPacketCalendarUpdate message, MessageContext ctx) {
        TerraFirmaCraft.getProxy().getThreadListener(ctx).addScheduledTask(() -> CalendarTFC.INSTANCE.resetTo(message.instance));
        return null;
    }
}
