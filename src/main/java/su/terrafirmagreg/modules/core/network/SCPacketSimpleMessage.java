package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.TerraFirmaGreg;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;
import net.dries007.tfc.ConfigTFC;

import java.nio.charset.Charset;
import java.util.function.BooleanSupplier;

public class SCPacketSimpleMessage implements IMessage,
                                              IMessageHandler<SCPacketSimpleMessage, IMessage> {

  private ITextComponent text;
  private MessageCategory category;

  public SCPacketSimpleMessage() {
  }

  public SCPacketSimpleMessage(MessageCategory category, ITextComponent text) {
    this.text = text;
    this.category = category;
  }

  /**
   * Utility method for making a message with just a single {@link TextComponentTranslation} element.
   */
  public static SCPacketSimpleMessage translateMessage(MessageCategory category, String unlocalized,
                                                       Object... args) {
    return new SCPacketSimpleMessage(category, new TextComponentTranslation(unlocalized, args));
  }

  /**
   * Utility method for making a message with just a single {@link TextComponentString} element.
   */
  public static SCPacketSimpleMessage stringMessage(MessageCategory category, String localized) {
    return new SCPacketSimpleMessage(category, new TextComponentString(localized));
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    category = MessageCategory.values()[buf.readInt()];
    text = ITextComponent.Serializer.jsonToComponent(
      buf.readCharSequence(buf.readInt(), Charset.defaultCharset())
         .toString());
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(category.ordinal());
    String json = ITextComponent.Serializer.componentToJson(text);
    buf.writeInt(json.length());
    buf.writeCharSequence(json, Charset.defaultCharset());
  }

  @Override
  public IMessage onMessage(SCPacketSimpleMessage message, MessageContext ctx) {
    TerraFirmaGreg.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
      EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(ctx);
      if (player != null) {
        player.sendStatusMessage(message.text, message.category.displayToToolbar.getAsBoolean());
      }
    });
    return null;
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
