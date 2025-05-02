package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;
import su.terrafirmagreg.api.base.network.packet.spi.NetworkPacketBase;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import net.dries007.tfc.ConfigTFC;

import java.util.function.BooleanSupplier;

public class SCPacketSimple extends NetworkPacketBase implements INetworkPacket.Client {

  private ITextComponent text;
  private MessageCategory category;

  public SCPacketSimple() {}

  public SCPacketSimple(MessageCategory category, ITextComponent text) {
    this.text = text;
    this.category = category;
  }

  /**
   * Utility method for making a message with just a single {@link TextComponentTranslation} element.
   */
  public static SCPacketSimple translateMessage(MessageCategory category, String unlocalized, Object... args) {
    return new SCPacketSimple(category, new TextComponentTranslation(unlocalized, args));
  }

  /**
   * Utility method for making a message with just a single {@link TextComponentString} element.
   */
  public static SCPacketSimple stringMessage(MessageCategory category, String localized) {
    return new SCPacketSimple(category, new TextComponentString(localized));
  }


  @Override
  public void process(Minecraft minecraft) {
    EntityPlayer player = minecraft.player;
    if (player != null) {
      player.sendStatusMessage(text, category.displayToToolbar.getAsBoolean());
    }
  }


  public enum MessageCategory {
    ANVIL(() -> ConfigTFC.Client.TOOLTIP.anvilWeldOutputToActionBar),
    VESSEL(() -> ConfigTFC.Client.TOOLTIP.vesselOutputToActionBar),
    ANIMAL(() -> ConfigTFC.Client.TOOLTIP.animalsOutputToActionBar);

    private final BooleanSupplier displayToToolbar;

    MessageCategory(BooleanSupplier displayToToolbar) {
      this.displayToToolbar = displayToToolbar;
    }
  }
}
