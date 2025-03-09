package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.base.client.gui.button.api.IButtonHandler;
import su.terrafirmagreg.framework.network.spi.packet.PacketBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import org.jetbrains.annotations.Nullable;

/// **
// * This is a generic packet that sends a button notification to the players open container, which can delegate to the tile entity if needed See
// * {@link GuiMetalAnvil} for an example of its usage, and {@link ContainerMetalAnvil} for an example of the message handling
// *
// * @author AlcatrazEscapee
// */
public class CSPacketGuiButton extends PacketBase<CSPacketGuiButton> {

  private int buttonID;
  private NBTTagCompound extraNBT;

  @SuppressWarnings("unused")
  public CSPacketGuiButton() {}

  public CSPacketGuiButton(int buttonID) {
    this(buttonID, null);
  }

  public CSPacketGuiButton(int buttonID, @Nullable NBTTagCompound extraNBT) {
    this.buttonID = buttonID;
    this.extraNBT = extraNBT;
  }

  @Override
  public IMessage handleMessage(MessageContext context) {
    EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(context);
    if (player != null) {
      TerraFirmaGreg.getProxy().getThreadListener(context).addScheduledTask(() -> {
        if (player.openContainer instanceof IButtonHandler buttonHandler) {
          buttonHandler.onButtonPress(buttonID, extraNBT);
        }
      });
    }
    return null;
  }
}
