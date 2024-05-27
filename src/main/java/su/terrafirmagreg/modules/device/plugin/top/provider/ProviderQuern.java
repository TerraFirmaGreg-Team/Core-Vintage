package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.objects.blocks.BlockQuern;
import su.terrafirmagreg.modules.device.objects.tiles.TileQuern;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;

import static su.terrafirmagreg.modules.device.objects.tiles.TileQuern.SLOT_HANDSTONE;

public class ProviderQuern implements IProbeInfoProvider {

    @Override
    public String getID() {
        return ModUtils.localize("top", "device.quern");
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData hitData) {
        Block block = state.getBlock();
        BlockPos pos = hitData.getPos();

        if (block instanceof BlockQuern) {
            var tile = TileUtils.getTile(world, pos, TileQuern.class);
            if (tile == null) return;

            IItemHandler handler;
            ItemStack handstone;

            if (tile.hasHandstone()) {

                handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (handler != null) {
                    handstone = handler.getStackInSlot(SLOT_HANDSTONE);

                    if (!handstone.isEmpty()) {
                        info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                                .text(new TextComponentTranslation(ModUtils.localize("top", "device.quern.handstone.durability"),
                                        handstone.getItemDamage(),
                                        handstone.getMaxDamage()).getFormattedText());
                    }
                }
            }

        }
    }

}
