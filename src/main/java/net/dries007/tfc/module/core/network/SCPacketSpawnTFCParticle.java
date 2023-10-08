package net.dries007.tfc.module.core.network;

import io.netty.buffer.ByteBuf;
import net.dries007.tfc.client.particle.TFCParticles;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import su.terrafirmagreg.tfc.TerraFirmaCraft;
import su.terrafirmagreg.util.spi.packet.PacketBlockPosBase;

public class SCPacketSpawnTFCParticle implements IMessage, IMessageHandler<SCPacketSpawnTFCParticle, IMessage> {
    private int particleID;
    private double x, y, z;
    private double speedX, speedY, speedZ;
    private int duration;

    @SuppressWarnings("unused")
    public SCPacketSpawnTFCParticle() {
    }

    public SCPacketSpawnTFCParticle(TFCParticles particle, double x, double y, double z, double speedX, double speedY, double speedZ, int duration) {
        this.particleID = particle.ordinal();
        this.x = x;
        this.y = y;
        this.z = z;
        this.speedX = speedX;
        this.speedY = speedY;
        this.speedZ = speedZ;
        this.duration = duration;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.particleID = buffer.readInt();
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
        this.speedX = buffer.readDouble();
        this.speedY = buffer.readDouble();
        this.speedZ = buffer.readDouble();
        this.duration = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(particleID);
        byteBuf.writeDouble(x);
        byteBuf.writeDouble(y);
        byteBuf.writeDouble(z);
        byteBuf.writeDouble(speedX);
        byteBuf.writeDouble(speedY);
        byteBuf.writeDouble(speedZ);
        byteBuf.writeInt(duration);
    }

    @Override
    public IMessage onMessage(SCPacketSpawnTFCParticle message, MessageContext ctx) {
        TerraFirmaCraft.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
            EntityPlayer player = TerraFirmaCraft.getProxy().getPlayer(ctx);
            if (player != null) {
                TFCParticles particle = TFCParticles.values()[message.particleID];
                particle.spawn(player.getEntityWorld(), message.x, message.y, message.z, message.speedX, message.speedY, message.speedZ, message.duration);
            }
        });
        return null;
    }
}
