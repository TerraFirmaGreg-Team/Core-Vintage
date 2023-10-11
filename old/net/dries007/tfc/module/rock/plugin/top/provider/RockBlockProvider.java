package net.dries007.tfc.module.rock.plugin.top.provider;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.Tags;
import net.dries007.tfc.module.rock.api.types.variant.block.IRockBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class RockBlockProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return Tags.MOD_ID + ":rock_block";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, EntityPlayer entityPlayer, World world, IBlockState blockState, IProbeHitData data) {
        if (blockState.getBlock() instanceof IRockBlock rockBlock) {
            probeInfo.text(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": " + rockBlock.getCategory().getLocalizedName());
        }
    }
}
