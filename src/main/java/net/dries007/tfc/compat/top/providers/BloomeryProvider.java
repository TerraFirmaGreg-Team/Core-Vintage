/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.compat.top.providers;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.recipes.BloomeryRecipe;
import net.dries007.tfc.objects.blocks.devices.BlockBloomery;
import net.dries007.tfc.objects.blocks.property.ILightableBlock;
import net.dries007.tfc.objects.te.TEBloom;
import net.dries007.tfc.objects.te.TEBloomery;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class BloomeryProvider implements IProbeInfoProvider
{
    @Override
    public String getID() {
        return TerraFirmaCraft.MOD_ID + ":bloomery";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {

        var blockPos = iProbeHitData.getPos();

        IBlockState state = world.getBlockState(blockPos);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TEBloomery bloomery)
        {
            if (state.getValue(ILightableBlock.LIT))
            {
                List<ItemStack> oreStacks = bloomery.getOreStacks();
                BloomeryRecipe recipe = oreStacks.size() > 0 ? BloomeryRecipe.get(oreStacks.get(0)) : null;
                long remainingTicks = bloomery.getRemainingTicks();
                switch (ConfigTFC.Client.TOOLTIP.timeTooltipMode)
                {
                    case NONE:
                        break;
                    case TICKS:
                        iProbeInfo.text(new TextComponentTranslation("waila.tfc.devices.ticks_remaining", remainingTicks).getFormattedText());
                        break;
                    case MINECRAFT_HOURS:
                        long remainingHours = Math.round(remainingTicks / (float) ICalendar.TICKS_IN_HOUR);
                        iProbeInfo.text(new TextComponentTranslation("waila.tfc.devices.hours_remaining", remainingHours).getFormattedText());
                        break;
                    case REAL_MINUTES:
                        long remainingMinutes = Math.round(remainingTicks / 1200.0f);
                        iProbeInfo.text(new TextComponentTranslation("waila.tfc.devices.minutes_remaining", remainingMinutes).getFormattedText());
                        break;
                }
                if (recipe != null)
                {
                    ItemStack output = recipe.getOutput(oreStacks);
                    IForgeable cap = output.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                    if (cap instanceof IForgeableMeasurableMetal)
                    {
                        IForgeableMeasurableMetal forgeCap = ((IForgeableMeasurableMetal) cap);
                        iProbeInfo.text(new TextComponentTranslation("waila.tfc.bloomery.output", forgeCap.getMetalAmount(), forgeCap.getMaterial().getLocalizedName()).getFormattedText());
                    }
                }
            }
            else
            {
                int ores = bloomery.getOreStacks().size();
                int fuel = bloomery.getFuelStacks().size();
                int max = BlockBloomery.getChimneyLevels(world, bloomery.getInternalBlock()) * 8;
                iProbeInfo.text(new TextComponentTranslation("waila.tfc.bloomery.ores", ores, max).getFormattedText());
                iProbeInfo.text(new TextComponentTranslation("waila.tfc.bloomery.fuel", fuel, max).getFormattedText());
            }
        }
        else if (tileEntity instanceof TEBloom bloom)
        {
            IItemHandler cap = bloom.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (cap != null)
            {
                ItemStack bloomStack = cap.getStackInSlot(0);
                IForgeable forgeCap = bloomStack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (forgeCap instanceof IForgeableMeasurableMetal bloomCap)
                {
                    iProbeInfo.text(new TextComponentTranslation("waila.tfc.metal.output", bloomCap.getMetalAmount(), bloomCap.getMaterial().getLocalizedName()).getFormattedText());
                }
            }
        }
    }




}
