package com.speeedcraft.tfgmod.mixins.tfc;

import com.feed_the_beast.ftbutilities.data.ClaimedChunks;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.network.IMessageEmpty;
import net.dries007.tfc.network.PacketPlaceBlockSpecial;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.te.TEPlacedItem;
import net.dries007.tfc.objects.te.TEPlacedItemFlat;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(targets = "net.dries007.tfc.network.PacketPlaceBlockSpecial$Handler", remap = false)
public class PlacingFlatItemsInRegionsMixin implements IMessageEmpty {

	@Inject(method = "onMessage(Lnet/dries007/tfc/network/PacketPlaceBlockSpecial;Lnet/minecraftforge/fml/common/network/simpleimpl/MessageContext;)Lnet/minecraftforge/fml/common/network/simpleimpl/IMessage;", at = @At(value = "HEAD"), remap = false, cancellable = true)
	public void onMessage(PacketPlaceBlockSpecial message, MessageContext ctx, CallbackInfoReturnable<IMessage> cir) {
		final EntityPlayer player = TerraFirmaCraft.getProxy().getPlayer(ctx);

		if (player != null) {
			TerraFirmaCraft.getProxy().getThreadListener(ctx).addScheduledTask(() -> {

				final World world = player.getEntityWorld();
				final RayTraceResult rayTrace = Helpers.rayTrace(player, player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue(), 1);
				final ItemStack stack = player.getHeldItemMainhand().isEmpty() ? player.getHeldItemOffhand() : player.getHeldItemMainhand();

				if (rayTrace != null) {
					BlockPos pos = rayTrace.getBlockPos();
					EnumFacing hitFace = rayTrace.sideHit;
					double placeReach = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
					if (player.getDistanceSq(pos) <= placeReach * placeReach && hitFace != null) {
						IBlockState offsetState = world.getBlockState(pos.offset(hitFace));
						if (world.getBlockState(pos).getBlock() == BlocksTFC.PLACED_ITEM) {
							TEPlacedItem tile = Helpers.getTE(world, pos, TEPlacedItem.class);
							if (tile != null) {
								tile.onRightClick(player, stack, rayTrace);
							}
						} else if (offsetState.getBlock() == BlocksTFC.PLACED_ITEM) {
							TEPlacedItem tile = Helpers.getTE(world, pos.offset(hitFace), TEPlacedItem.class);
							if (tile != null) {
								tile.onRightClick(player, stack, rayTrace);
							}
						} else if (!stack.isEmpty() && world.getBlockState(pos.offset(hitFace).down()).isSideSolid(world, pos.offset(hitFace).down(), EnumFacing.UP) && offsetState.getBlock().isAir(offsetState, world, pos)) {
							// FTB-Utils Fix
							if (!ClaimedChunks.blockBlockEditing(player, pos, offsetState)) {
								// Можно взять и поднять
								if (player.isSneaking()) {
									// If sneaking, place a flat item
									world.setBlockState(pos.offset(hitFace), BlocksTFC.PLACED_ITEM_FLAT.getDefaultState());
									TEPlacedItemFlat tile = Helpers.getTE(world, pos.offset(hitFace), TEPlacedItemFlat.class);
									if (tile != null) {
										ItemStack input;
										if (player.isCreative()) {
											input = stack.copy();
											input.setCount(1);
										} else {
											input = stack.splitStack(1);
										}
										tile.setStack(input);
									}
								} else {
									world.setBlockState(pos.offset(hitFace), BlocksTFC.PLACED_ITEM.getDefaultState());
									TEPlacedItem tile = Helpers.getTE(world, pos.offset(hitFace), TEPlacedItem.class);
									if (tile != null) {
										tile.insertItem(player, stack, rayTrace);
									}
								}
							}
						}
					}
				}
			});
		}

		cir.setReturnValue(null);
	}

}
