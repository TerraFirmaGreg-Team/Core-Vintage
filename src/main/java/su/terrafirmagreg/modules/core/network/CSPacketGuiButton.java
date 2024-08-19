package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.base.gui.component.button.IButtonHandler;
import su.terrafirmagreg.modules.metal.client.gui.GuiMetalAnvil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


import io.netty.buffer.ByteBuf;
import net.dries007.tfc.objects.container.ContainerAnvilTFC;

import org.jetbrains.annotations.Nullable;

/**
 * This is a generic packet that sends a button notification to the players open container, which can delegate to the tile entity if needed See {@link GuiMetalAnvil} for an example
 * of its usage, and {@link ContainerAnvilTFC} for an example of the message handling
 *
 * @author AlcatrazEscapee
 */
public class CSPacketGuiButton implements IMessage, IMessageHandler<CSPacketGuiButton, IMessage> {

    private int buttonID;
    private NBTTagCompound extraNBT;

    @SuppressWarnings("unused")
    public CSPacketGuiButton() {}

    public CSPacketGuiButton(int buttonID, @Nullable NBTTagCompound extraNBT) {
        this.buttonID = buttonID;
        this.extraNBT = extraNBT;
    }

    public CSPacketGuiButton(int buttonID) {
        this(buttonID, null);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        buttonID = buf.readInt();
        if (buf.readBoolean()) {
            extraNBT = ByteBufUtils.readTag(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(buttonID);
        buf.writeBoolean(extraNBT != null);
        if (extraNBT != null) {
            ByteBufUtils.writeTag(buf, extraNBT);
        }
    }

    @Override
    public IMessage onMessage(CSPacketGuiButton message, MessageContext ctx) {
        EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(ctx);
        if (player != null) {
            TerraFirmaGreg.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
                if (player.openContainer instanceof IButtonHandler buttonHandler) {
                    buttonHandler.onButtonPress(message.buttonID, message.extraNBT);
                }
            });
        }
        return null;
    }
}
