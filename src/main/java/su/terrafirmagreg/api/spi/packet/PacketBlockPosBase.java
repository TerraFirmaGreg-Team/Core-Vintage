package su.terrafirmagreg.api.spi.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

import io.netty.buffer.ByteBuf;

public abstract class PacketBlockPosBase<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, IMessage> {

    protected BlockPos blockPos;

    public PacketBlockPosBase() {
        // serialization
    }

    public PacketBlockPosBase(BlockPos blockPos) {

        this.blockPos = blockPos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        PacketBuffer packetBuffer = new PacketBuffer(buf);
        this.blockPos = packetBuffer.readBlockPos();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        PacketBuffer packetBuffer = new PacketBuffer(buf);
        packetBuffer.writeBlockPos(this.blockPos);
    }
}
