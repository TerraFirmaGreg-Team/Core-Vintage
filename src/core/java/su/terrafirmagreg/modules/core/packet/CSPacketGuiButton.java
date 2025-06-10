package su.terrafirmagreg.modules.core.packet;

import su.terrafirmagreg.framework.manager.packet.base.BasePacketServer;
import su.terrafirmagreg.framework.manager.registry.base.gui.button.api.IButtonHandler;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import org.jetbrains.annotations.Nullable;

import lombok.NoArgsConstructor;

/// **
// * This is a generic packet that sends a button notification to the players open container, which can delegate to the tile entity if needed See
// * {@link GuiMetalAnvil} for an example of its usage, and {@link ContainerMetalAnvil} for an example of the message handling
// *
// * @author AlcatrazEscapee
// */
@NoArgsConstructor
public class CSPacketGuiButton extends BasePacketServer {

  private int buttonID;
  private NBTTagCompound extraNBT;


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
