package net.dries007.tfc.network;

import io.netty.buffer.ByteBuf;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.events.ProspectEvent;
import net.dries007.tfc.compat.gregtech.items.tools.behaviors.PropickBehavior.ProspectResult.Type;
import net.dries007.tfc.module.core.config.ConfigTFC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketProspectResult implements IMessage {
    private BlockPos pos;
    private Type type;
    private String materialName;

    @SuppressWarnings("unused")
    @Deprecated
    public PacketProspectResult() {
    }

    public PacketProspectResult(BlockPos pos, Type type, String materialName) {
        this.pos = pos;
        this.type = type;
        this.materialName = materialName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        type = Type.valueOf(buf.readByte());

        if (type != Type.NOTHING) {
            materialName = ByteBufUtils.readUTF8String(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeByte(type.ordinal());

        if (type != Type.NOTHING) {
            ByteBufUtils.writeUTF8String(buf, materialName);
        }
    }

    public static final class Handler implements IMessageHandler<PacketProspectResult, IMessage> {
        @Override
        public IMessage onMessage(PacketProspectResult message, MessageContext ctx) {
            TerraFirmaCraft.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
                EntityPlayer player = TerraFirmaCraft.getProxy().getPlayer(ctx);
                if (player != null) {
                    ITextComponent text = new TextComponentTranslation(message.type.translation);
                    if (message.type != Type.NOTHING) {
                        text.appendText(" ").appendSibling(new TextComponentTranslation(message.materialName));
                    }
                    player.sendStatusMessage(text, ConfigTFC.Client.TOOLTIP.propickOutputToActionBar);
                }

                ProspectEvent event = new ProspectEvent.Client(player, message.pos, message.type, message.materialName);
                MinecraftForge.EVENT_BUS.post(event);
            });
            return null;
        }
    }
}
