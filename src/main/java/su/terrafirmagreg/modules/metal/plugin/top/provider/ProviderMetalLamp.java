package su.terrafirmagreg.modules.metal.plugin.top.provider;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.metal.objects.block.BlockMetalLamp;
import su.terrafirmagreg.modules.metal.objects.tile.TileMetalLamp;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;

import java.util.ArrayList;
import java.util.List;

public class ProviderMetalLamp
  implements IProbeInfoProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "metal.lamp");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world,
                           IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockMetalLamp) {
      TileUtils.getTile(world, pos, TileMetalLamp.class).ifPresent(tile -> {
        List<String> currentTooltip = new ArrayList<>();

        NBTTagCompound nbt = new NBTTagCompound();
        nbt = tile.writeToNBT(nbt);

        IFluidHandler fluidHandler = tile.getCapability(
          CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        FluidStack fluid = fluidHandler != null ? fluidHandler.drain(Integer.MAX_VALUE, false) : null;
        if (fluid != null && fluid.amount > 0) {
          currentTooltip.add(
            new TextComponentTranslation(ModUtils.localize("top", "barrel.contents"), fluid.amount,
                                         fluid.getLocalizedName()).getFormattedText());
        }

        for (String string : currentTooltip) {
          info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
              .text(string);
        }
      });
    }
  }
}
