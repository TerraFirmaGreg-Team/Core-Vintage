package su.terrafirmagreg.modules.device.plugin.top.provider;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.base.plugin.top.provider.spi.BaseProvider;
import su.terrafirmagreg.api.library.TextComponents;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.object.block.BlockLatexExtractor;
import su.terrafirmagreg.modules.device.object.tile.TileLatexExtractor;

public final class ProviderLatexExtractor extends BaseProvider {

    @Override
    public @NotNull String getID() {
        return ModUtils.localize("top", "device.latex_extractor");
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData hitData) {
        Block block = state.getBlock();
        BlockPos pos = hitData.getPos();

        if (block instanceof BlockLatexExtractor) {
            TileUtils.getTile(world, pos, TileLatexExtractor.class).ifPresent(tile -> {
                var probeInfo = info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                if (tile.getFluidAmount() > 0) {
                    probeInfo.text(TextComponents.translation(String.format("%s.%s", getID(), "quantity"), tile.getFluidAmount()).format());
                }
            });
        }
    }
}
