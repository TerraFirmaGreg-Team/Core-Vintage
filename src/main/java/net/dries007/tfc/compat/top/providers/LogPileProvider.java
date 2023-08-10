package net.dries007.tfc.compat.top.providers;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.objects.te.TELogPile;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

public class LogPileProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return TerraFirmaCraft.MOD_ID + ":log_pile";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        var logPile = Helpers.getTE(world, iProbeHitData.getPos(), TELogPile.class);
        if (logPile != null) {
            var inventory = logPile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

            if (inventory != null) {
                var horizontalLayout = iProbeInfo.horizontal();

                for (int i = 0; i < inventory.getSlots(); i++) {
                    var slotStack = inventory.getStackInSlot(i);
                    if (!slotStack.isEmpty()) {
                        horizontalLayout.item(slotStack);
                    }

                    iProbeInfo.itemLabel(slotStack);
                }
            }
        }
    }
}
