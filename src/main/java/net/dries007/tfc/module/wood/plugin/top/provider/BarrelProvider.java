package net.dries007.tfc.module.wood.plugin.top.provider;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.Tags;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.module.wood.common.tile.TEWoodBarrel;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BarrelProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return Tags.MOD_ID + ":barrel";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        var te = Helpers.getTE(world, iProbeHitData.getPos(), TEWoodBarrel.class);

        if (te != null) {
            IFluidHandler fluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
            FluidStack fluid = fluidHandler != null ? fluidHandler.drain(Integer.MAX_VALUE, false) : null;

            if (te.isSealed()) {
                probeInfo.text(new TextComponentTranslation("top.tfc.barrel.sealed", te.getSealedDate()).getFormattedText());
                BarrelRecipe recipe = te.getRecipe();
                if (recipe != null) {
                    probeInfo.text(new TextComponentTranslation("top.tfc.barrel.recipe", recipe.getResultName()).getFormattedText());
                } else {
                    probeInfo.text(new TextComponentTranslation("top.tfc.barrel.no_recipe").getFormattedText());
                }
            }
            if (fluid != null && fluid.amount > 0) {
                probeInfo.text(new TextComponentTranslation("top.tfc.barrel.contents", fluid.amount, fluid.getLocalizedName()).getFormattedText());
            }
        }
    }
}
