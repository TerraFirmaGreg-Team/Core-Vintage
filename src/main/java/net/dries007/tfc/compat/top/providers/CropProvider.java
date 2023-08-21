package net.dries007.tfc.compat.top.providers;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.objects.blocks.crop.BlockCropDead;
import net.dries007.tfc.common.objects.blocks.crop.BlockCropGrowing;
import net.dries007.tfc.common.objects.tileentities.TECropBase;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class CropProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return TerraFirmaCraft.MOD_ID + ":crop";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        var blockPos = iProbeHitData.getPos();
        var state = world.getBlockState(blockPos);
        var te = Helpers.getTE(world, blockPos, TECropBase.class);

        if (state.getBlock() instanceof BlockCropGrowing blockCropGrowing && te != null) {
            var type = blockCropGrowing.getType();

            boolean isWild = state.getValue(BlockCropGrowing.WILD);
            float temp = ClimateTFC.getActualTemp(world, blockPos, -te.getLastUpdateTick());
            float rainfall = ChunkDataTFC.getRainfall(world, blockPos);

            if (isWild) {
                iProbeInfo.text(new TextComponentTranslation("top.tfc.crop.wild").getFormattedText());
            } else if (type.isValidForGrowth(temp, rainfall)) {
                iProbeInfo.text(new TextComponentTranslation("top.tfc.crop.growing").getFormattedText());
            } else {
                iProbeInfo.text(new TextComponentTranslation("top.tfc.crop.not_growing").getFormattedText());
            }

        } else if (state.getBlock() instanceof BlockCropDead) {
            iProbeInfo.text(new TextComponentTranslation("top.tfc.crop.dead_crop").getFormattedText());
        }
    }
}
