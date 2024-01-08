package su.terrafirmagreg.api.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;


public interface IPacketRegistry {

	<Q extends IMessage, A extends IMessage> IPacketRegistry register(
			Class<? extends IMessageHandler<Q, A>> messageHandler,
			Class<Q> requestMessageType,
			Side side
	);

	<Q extends IMessage, A extends IMessage> IPacketRegistry register(
			IMessageHandler<Q, A> messageHandler,
			Class<Q> requestMessageType,
			Side side
	);
}
