package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.capabilities.temperature.CapabilityTemperature;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


import io.netty.buffer.ByteBuf;

public class SCPacketTemperature implements IMessage, IMessageHandler<SCPacketTemperature, IMessage> {

    public NBTTagCompound tag = new NBTTagCompound();

    public SCPacketTemperature() {
    }

    public SCPacketTemperature(NBTTagCompound tag) {
        this.tag = tag;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        tag = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, tag);
    }

    @Override
    public IMessage onMessage(SCPacketTemperature message, MessageContext ctx) {
        TerraFirmaGreg.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
            EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(ctx);
            if (player != null) {
                var sys = CapabilityTemperature.get(player);
                if (sys != null) {
                    sys.deserializeNBT(message.tag);
                }
            }
        });
        return null;
    }

}
