package com.buuz135.hotornot.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.buuz135.hotornot.HotOrNot;
import com.buuz135.hotornot.ServerEvents;
import io.netty.buffer.ByteBuf;

import org.jetbrains.annotations.Nullable;

public class PacketClientSettings implements IMessage {

    private boolean replaceBrokenHotHolder;

    @Deprecated
    public PacketClientSettings() {
    }

    @SideOnly(Side.CLIENT)
    public PacketClientSettings(final boolean replaceBrokenHotHolder) {
        this.replaceBrokenHotHolder = replaceBrokenHotHolder;
    }

    @Override
    public void fromBytes(final ByteBuf byteBuf) {
        replaceBrokenHotHolder = byteBuf.readBoolean();
    }

    @Override
    public void toBytes(final ByteBuf byteBuf) {
        byteBuf.writeBoolean(replaceBrokenHotHolder);
    }

    public static class Handler implements IMessageHandler<PacketClientSettings, IMessage> {

        @Nullable
        @Override
        public IMessage onMessage(final PacketClientSettings message, final MessageContext ctx) {
            final EntityPlayer player = ctx.getServerHandler().player;
            if (player == null) return null;
            ServerEvents.setReplaceHotHolderConfigForPlayer(player, message.replaceBrokenHotHolder);
            HotOrNot.getLog().info("Player {} has replace as {}", player.getName(), message.replaceBrokenHotHolder);
            return null;
        }
    }
}
