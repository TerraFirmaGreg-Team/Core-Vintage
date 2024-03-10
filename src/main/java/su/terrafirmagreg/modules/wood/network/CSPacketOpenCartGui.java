package su.terrafirmagreg.modules.wood.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import su.terrafirmagreg.TerraFirmaGreg;

public class CSPacketOpenCartGui implements IMessage, IMessageHandler<CSPacketOpenCartGui, IMessage> {

	private int invId;
	private int cartId;

	public CSPacketOpenCartGui() {
	}

	public CSPacketOpenCartGui(int invIdIn, int cartIdIn) {
		invId = invIdIn;
		cartId = cartIdIn;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		invId = buf.readInt();
		cartId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(invId);
		buf.writeInt(cartId);
	}

	@Override
	public IMessage onMessage(CSPacketOpenCartGui message, MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().player;
		player.openGui(TerraFirmaGreg.getInstance(), message.invId, player.world, message.cartId, 0, 0);
		return null;
	}
}
