package net.dries007.tfc.module.agriculture.plugin.top.provider;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.Tags;
import net.dries007.tfc.module.agriculture.objects.blocks.BlockCropDead;
import net.dries007.tfc.module.agriculture.objects.blocks.BlockCropGrowing;
import net.dries007.tfc.module.agriculture.objects.tile.TECropBase;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class CropProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return Tags.MOD_ID + ":crop";
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
