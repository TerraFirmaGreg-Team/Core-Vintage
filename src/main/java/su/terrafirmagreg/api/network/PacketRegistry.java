package su.terrafirmagreg.api.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;

public class PacketRegistry implements IPacketRegistry {

    private final ThreadedNetworkWrapper threadedNetworkWrapper;

    private int id = 0;

    public PacketRegistry(ThreadedNetworkWrapper threadedNetworkWrapper) {
        this.threadedNetworkWrapper = threadedNetworkWrapper;
    }

    @Override
    public <Q extends IMessage, A extends IMessage> IPacketRegistry register(
            Class<? extends IMessageHandler<Q, A>> messageHandler,
            Class<Q> requestMessageType,
            Side side
    ) {

        this.threadedNetworkWrapper.registerMessage(messageHandler, requestMessageType, this.nextId(), side);
        return this;
    }

    @Override
    public <Q extends IMessage, A extends IMessage> IPacketRegistry register(
            IMessageHandler<Q, A> messageHandler,
            Class<Q> requestMessageType,
            Side side
    ) {

        this.threadedNetworkWrapper.registerMessage(messageHandler, requestMessageType, this.nextId(), side);
        return this;
    }

    private int nextId() {
        return this.id++;
    }

}
