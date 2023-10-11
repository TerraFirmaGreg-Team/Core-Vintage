package net.dries007.tfc.module.core.network;

import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.core.init.BlocksCore;
import net.dries007.tfc.module.core.objects.tiles.TEPlacedItem;
import net.dries007.tfc.module.core.objects.tiles.TEPlacedItemFlat;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.dries007.tfc.TerraFirmaCraft;

/**
 * This packet is send when the client player presses the "Place Block Special" keybind. It has no special information
 */
public class SCPacketPlaceBlockSpecial implements IMessageEmpty, IMessageHandler<SCPacketPlaceBlockSpecial, IMessage> {
    @Override
    public IMessage onMessage(SCPacketPlaceBlockSpecial message, MessageContext ctx) {
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
                        if (world.getBlockState(pos).getBlock() == BlocksCore.PLACED_ITEM) {
                            TEPlacedItem tile = Helpers.getTE(world, pos, TEPlacedItem.class);
                            if (tile != null) {
                                tile.onRightClick(player, stack, rayTrace);
                            }
                        } else if (offsetState.getBlock() == BlocksCore.PLACED_ITEM) {
                            TEPlacedItem tile = Helpers.getTE(world, pos.offset(hitFace), TEPlacedItem.class);
                            if (tile != null) {
                                tile.onRightClick(player, stack, rayTrace);
                            }
                        } else if (!stack.isEmpty() && world.getBlockState(pos.offset(hitFace).down()).isSideSolid(world, pos.offset(hitFace).down(), EnumFacing.UP) && offsetState.getBlock().isAir(offsetState, world, pos)) {
                            if (player.isSneaking()) {
                                // If sneaking, place a flat item
                                world.setBlockState(pos.offset(hitFace), BlocksCore.PLACED_ITEM_FLAT.getDefaultState());
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
                                world.setBlockState(pos.offset(hitFace), BlocksCore.PLACED_ITEM.getDefaultState());
                                TEPlacedItem tile = Helpers.getTE(world, pos.offset(hitFace), TEPlacedItem.class);
                                if (tile != null) {
                                    tile.insertItem(player, stack, rayTrace);
                                }
                            }
                        }
                    }
                }
            });
        }
        return null;
    }
}
