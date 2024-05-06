package tfctech.compat.waila;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.objects.blocks.BlockFridge;
import su.terrafirmagreg.modules.device.objects.blocks.BlockLatexExtractor;
import su.terrafirmagreg.modules.device.objects.tiles.TileFridge;
import su.terrafirmagreg.modules.device.objects.tiles.TileLatexExtractor;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;


import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static su.terrafirmagreg.api.data.Constants.MODID_TFCTECH;

public final class TOPPlugin implements Function<ITheOneProbe, Void>, IProbeInfoProvider {

    @Override
    public String getID() {
        return MODID_TFCTECH + ":top_provider";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        Block b = iBlockState.getBlock();
        BlockPos pos = iProbeHitData.getPos();
        if (b instanceof BlockFridge) {
            BlockPos TEPos = pos;
            if (!iBlockState.getValue(BlockFridge.UPPER)) {
                TEPos = TEPos.up();
            }
            TileFridge fridge = TileUtils.getTile(world, TEPos, TileFridge.class);
            if (fridge != null) {
                IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle()
                        .alignment(ElementAlignment.ALIGN_CENTER));
                horizontalPane.text(
                        (new TextComponentTranslation("waila.tfctech.fridge.efficiency", (int) fridge.getEfficiency())).getFormattedText());
                if (fridge.isOpen()) {
                    int slot = BlockFridge.getPlayerLookingItem(TEPos.down(), entityPlayer, iBlockState.getValue(BlockFridge.FACING));
                    if (slot > -1) {
                        ItemStack stack = fridge.getSlot(slot);
                        if (!stack.isEmpty()) {
                            iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle()
                                            .alignment(ElementAlignment.ALIGN_CENTER))
                                    .item(stack)
                                    .vertical()
                                    .itemLabel(stack);
                            IFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
                            List<String> list = new ArrayList<>();
                            if (cap != null) {
                                cap.addTooltipInfo(stack, list, entityPlayer);
                            }
                            for (String text : list) {
                                iProbeInfo.text(text);
                            }
                        }
                    }
                }
            }
        }
        if (b instanceof BlockLatexExtractor) {
            TileLatexExtractor extractor = TileUtils.getTile(world, pos, TileLatexExtractor.class);
            if (extractor != null) {
                if (extractor.getFluidAmount() > 0) {
                    IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle()
                            .alignment(ElementAlignment.ALIGN_CENTER));
                    horizontalPane.text(
                            (new TextComponentTranslation("waila.tfctech.latex.quantity", extractor.getFluidAmount())).getFormattedText());
                }
            }
        }
    }

    @Nullable
    @Override
    public Void apply(ITheOneProbe iTheOneProbe) {
        iTheOneProbe.registerProvider(this);
        return null;
    }
}
