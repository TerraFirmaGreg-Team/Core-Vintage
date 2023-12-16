package com.speeedcraft.tfgmod.mixins.astralsorcery;

import hellfirepvp.astralsorcery.common.event.listener.EventHandlerServer;
import hellfirepvp.astralsorcery.common.network.PacketChannel;
import hellfirepvp.astralsorcery.common.network.packet.server.PktCraftingTableFix;
import net.dries007.tfc.objects.blocks.wood.BlockWorkbenchTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = EventHandlerServer.class, remap = false)
public class EventHandlerServerMixin {

	@Inject(method = "onRightClickLast", at = @At(value = "HEAD"), remap = false, cancellable = true)
	private void onRightClickLast(PlayerInteractEvent.RightClickBlock event, CallbackInfo ci) {
		if (!event.getWorld().isRemote) {
			IBlockState interacted = event.getWorld().getBlockState(event.getPos());
			if (interacted.getBlock() instanceof BlockWorkbenchTFC) {
				PktCraftingTableFix fix = new PktCraftingTableFix(event.getPos());
				PacketChannel.CHANNEL.sendTo(fix, (EntityPlayerMP) event.getEntityPlayer());
			}
		}

		ci.cancel();
	}
}
