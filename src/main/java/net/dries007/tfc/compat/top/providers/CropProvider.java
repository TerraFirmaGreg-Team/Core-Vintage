package net.dries007.tfc.compat.top.providers;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.crop.ICropBlock;
import net.dries007.tfc.common.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.common.objects.blocks.agriculture.BlockCropTFC;
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

        if (state.getBlock() instanceof BlockCropTFC bs && te != null) {
            ICropBlock crop = bs.getCrop();

            boolean isWild = state.getValue(BlockCropTFC.WILD);
            float temp = ClimateTFC.getActualTemp(world, blockPos, -te.getLastUpdateTick());
            float rainfall = ChunkDataTFC.getRainfall(world, blockPos);

            if (isWild) {
                iProbeInfo.text(new TextComponentTranslation("waila.tfc.crop.wild").getFormattedText());
            } else if (crop.isValidForGrowth(temp, rainfall)) {
                iProbeInfo.text(new TextComponentTranslation("waila.tfc.crop.growing").getFormattedText());
            } else {
                iProbeInfo.text(new TextComponentTranslation("waila.tfc.crop.not_growing").getFormattedText());
            }

            int curStage = state.getValue(bs.getStageProperty());
            int maxStage = crop.getMaxStage();

            if (curStage == maxStage) {
                iProbeInfo.text(new TextComponentTranslation("waila.tfc.crop.growth", new TextComponentTranslation("waila.tfc.crop.mature").getFormattedText()).getFormattedText());
            } else {
                float remainingTicksToGrow = Math.max(0, (crop.getGrowthTicks() * (float) ConfigTFC.General.FOOD.cropGrowthTimeModifier) - te.getTicksSinceUpdate());
                float curStagePerc = 1.0F - remainingTicksToGrow / crop.getGrowthTicks();
                // Don't show 100% since it still needs to check on randomTick to grow
                float totalPerc = Math.min(0.99f, curStagePerc / maxStage + (float) curStage / maxStage) * 100;
                String growth = String.format("%d%%", Math.round(totalPerc));
                iProbeInfo.text(new TextComponentTranslation("waila.tfc.crop.growth", growth).getFormattedText());
            }
        } else if (state.getBlock() instanceof BlockCropDead) {
            iProbeInfo.text(new TextComponentTranslation("waila.tfc.crop.dead_crop").getFormattedText());
        }
    }
}
