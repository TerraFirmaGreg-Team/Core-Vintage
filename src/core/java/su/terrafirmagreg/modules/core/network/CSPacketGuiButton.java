package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.api.base.client.gui.button.api.IButtonHandler;
import su.terrafirmagreg.api.base.packet.spi.BasePacket;
import su.terrafirmagreg.framework.manager.packet.api.IPacket;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import org.jetbrains.annotations.Nullable;

/// **
// * This is a generic packet that sends a button notification to the players open container, which can delegate to the tile entity if needed See
// * {@link GuiMetalAnvil} for an example of its usage, and {@link ContainerMetalAnvil} for an example of the message handling
// *
// * @author AlcatrazEscapee
// */
public class CSPacketGuiButton extends BasePacket implements IPacket.Server {

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
  public void process(EntityPlayerMP player) {
    if (player.openContainer instanceof IButtonHandler buttonHandler) {
      buttonHandler.onButtonPress(buttonID, extraNBT);
    }
  }
}
