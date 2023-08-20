package net.dries007.tfc.compat.top.providers;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.common.objects.blocks.wood.fruit.BlockFruitTreeLeaves;
import net.dries007.tfc.common.objects.blocks.wood.fruit.BlockFruitTreeSapling;
import net.dries007.tfc.common.objects.blocks.wood.fruit.BlockFruitTreeTrunk;
import net.dries007.tfc.common.objects.tileentities.TETickCounter;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class FruitTreeProvider implements IProbeInfoProvider {
    private static void addInfo(WoodType woodType, TETickCounter te, float temperature, float rainfall, IProbeInfo probeInfo) {
        if (woodType.isValidForGrowth(temperature, rainfall)) {
            probeInfo.text(new TextComponentTranslation("top.tfc.crop.growing").getFormattedText());
            if (te != null) {
                long hours = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_HOUR;
                // Don't show 100% since it still needs to check on randomTick to grow
                float perc = Math.min(0.99F, hours / (woodType.getGrowthTime() * (float) ConfigTFC.General.FOOD.fruitTreeGrowthTimeModifier)) * 100;
                String growth = String.format("%d%%", Math.round(perc));
                probeInfo.text(new TextComponentTranslation("top.tfc.crop.growth", growth).getFormattedText());
            }
        } else {
            probeInfo.text(new TextComponentTranslation("top.tfc.crop.not_growing").getFormattedText());
        }
    }

    @Override
    public String getID() {
        return TerraFirmaCraft.MOD_ID + ":fruit_tree";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        var blockPos = iProbeHitData.getPos();
        IBlockState state = world.getBlockState(blockPos);
        if (state.getBlock() instanceof BlockFruitTreeLeaves) {
            var block = (BlockFruitTreeLeaves) state.getBlock();
            if (state.getValue(BlockFruitTreeLeaves.HARVESTABLE) && block.getType().isHarvestMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear())) {
                if (state.getValue(BlockFruitTreeLeaves.LEAF_STATE) != BlockFruitTreeLeaves.EnumLeafState.FRUIT) {
                    TETickCounter te = Helpers.getTE(world, blockPos, TETickCounter.class);
                    addInfo(block.getType(), te, ClimateTFC.getActualTemp(world, blockPos), ChunkDataTFC.getRainfall(world, blockPos), iProbeInfo);
                }
            } else {
                iProbeInfo.text(new TextComponentTranslation("top.tfc.agriculture.harvesting_months").getFormattedText());
                for (Month month : Month.values()) {
                    if (block.getType().isHarvestMonth(month)) {
                        iProbeInfo.text(TerraFirmaCraft.getProxy().getMonthName(month, true));
                    }
                }
            }
        } else if (state.getBlock() instanceof BlockFruitTreeSapling) {
            BlockFruitTreeSapling block = (BlockFruitTreeSapling) state.getBlock();
            TETickCounter te = Helpers.getTE(world, blockPos, TETickCounter.class);
            addInfo(block.getType(), te, ClimateTFC.getActualTemp(world, blockPos), ChunkDataTFC.getRainfall(world, blockPos), iProbeInfo);
        } else if (state.getBlock() instanceof BlockFruitTreeTrunk) {
            // Gets the top most trunk, since that one is the responsible for growth
            IBlockState topMost = state;
            while (world.getBlockState(blockPos.up()).getBlock() instanceof BlockFruitTreeTrunk) {
                blockPos = blockPos.up();
                topMost = world.getBlockState(blockPos);
            }
            BlockFruitTreeTrunk block = (BlockFruitTreeTrunk) topMost.getBlock();
            TETickCounter te = Helpers.getTE(world, blockPos.up(), TETickCounter.class);
            addInfo(block.getType(), te, ClimateTFC.getActualTemp(world, blockPos), ChunkDataTFC.getRainfall(world, blockPos), iProbeInfo);
        }
    }
}
