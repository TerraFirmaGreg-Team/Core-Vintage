package net.anodecathode.time4tfc.network.packet;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import net.anodecathode.time4tfc.data.SessionData;
import io.netty.buffer.ByteBuf;

/**
 * @author dmillerw
 */
public class PacketServerSettings implements IMessage, IMessageHandler<PacketServerSettings, IMessage> {

	@Override
	public void toBytes(ByteBuf buf) {
		SessionData.writeToBuffer(buf);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		SessionData.readFromBuffer(buf);
	}

	@Override
	public IMessage onMessage(PacketServerSettings message, MessageContext ctx) {
		SessionData.modEnabled = true;
		return null;
	}
}
