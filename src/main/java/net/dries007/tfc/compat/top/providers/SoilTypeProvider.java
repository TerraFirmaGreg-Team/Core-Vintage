package net.dries007.tfc.compat.top.providers;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types2.soil.util.ISoilTypeBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SoilTypeProvider implements IProbeInfoProvider {
	@Override
	public String getID() {
		return TerraFirmaCraft.MOD_ID + ":soil_block";
	}

	@Override
	public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
		if (iBlockState.getBlock() instanceof ISoilTypeBlock soilTypeBlock) {
			probeInfo.text("DEBUG ONLY");
			probeInfo.text("SOIL TYPE BLOCK");
			probeInfo.text(soilTypeBlock.getSoilVariant().getName());
			probeInfo.text(soilTypeBlock.getSoilType().getName());
		}
	}
}
