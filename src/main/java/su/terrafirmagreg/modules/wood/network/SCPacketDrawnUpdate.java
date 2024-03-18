package su.terrafirmagreg.modules.wood.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;

public class SCPacketDrawnUpdate implements IMessage, IMessageHandler<SCPacketDrawnUpdate, IMessage> {

	private int pullingId;
	private int cartId;

	public SCPacketDrawnUpdate() {}

	public SCPacketDrawnUpdate(int horseIn, int cartIn) {
		pullingId = horseIn;
		cartId = cartIn;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pullingId = buf.readInt();
		cartId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pullingId);
		buf.writeInt(cartId);
	}

	@Override
	public IMessage onMessage(SCPacketDrawnUpdate message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			EntityWoodCart cart = (EntityWoodCart) Minecraft.getMinecraft().world.getEntityByID(message.cartId);
			assert cart != null;
			if (message.pullingId < 0) {
				cart.setPulling(null);
				return;
			} else {
				cart.setPullingId(message.pullingId);
			}
		});
		return null;
	}
}
