package su.terrafirmagreg.core.mixin.tfc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.events.ProspectEvent;
import net.dries007.tfc.network.PacketProspectResult;
import net.dries007.tfc.objects.items.metal.ItemProspectorPick.ProspectResult.Type;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PacketProspectResult.Handler.class, remap = false)
public abstract class PacketProspectResultMixin implements IMessageHandler<PacketProspectResult, IMessage> {

    @Inject(method = "onMessage(Lnet/dries007/tfc/network/PacketProspectResult;Lnet/minecraftforge/fml/common/network/simpleimpl/MessageContext;)Lnet/minecraftforge/fml/common/network/simpleimpl/IMessage;", at = @At(value = "HEAD"), cancellable = true)
    public void onMessage(PacketProspectResult message, MessageContext ctx, CallbackInfoReturnable<IMessage> cir) {
        BlockPos pos = ((IPacketProspectResultAccessor) message).getPos();
        Type type = ((IPacketProspectResultAccessor) message).getType();
        ItemStack vein = ((IPacketProspectResultAccessor) message).getVein();

        TerraFirmaCraft.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
            EntityPlayer player = TerraFirmaCraft.getProxy().getPlayer(ctx);
            if (player != null) {
                ITextComponent text = new TextComponentTranslation(type.translation);
                if (type != Type.NOTHING) {
                    text.appendText(" ").appendSibling(new TextComponentTranslation(vein.getDisplayName()));
                }
                player.sendStatusMessage(text, ConfigTFC.Client.TOOLTIP.propickOutputToActionBar);
            }

            ProspectEvent event = new ProspectEvent.Client(player, pos, type, vein);
            MinecraftForge.EVENT_BUS.post(event);
        });

        cir.setReturnValue(null);
    }
}
