package su.terrafirmagreg.api.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;

public interface IPacketRegistry {

  @SuppressWarnings({"rawtypes"})
  IPacketRegistry register(Class clazz, Side side);

  <REQ extends IMessage, REPLY extends IMessage> IPacketRegistry register(
      Class<? extends IMessageHandler<REQ, REPLY>> messageHandler,
      Class<REQ> requestMessageType,
      Side side
  );

  <REQ extends IMessage, REPLY extends IMessage> IPacketRegistry register(
      IMessageHandler<REQ, REPLY> messageHandler,
      Class<REQ> requestMessageType,
      Side side
  );
}
