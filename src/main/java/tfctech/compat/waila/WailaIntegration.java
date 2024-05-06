package tfctech.compat.waila;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.objects.blocks.BlockFridge;
import su.terrafirmagreg.modules.device.objects.blocks.BlockLatexExtractor;
import su.terrafirmagreg.modules.device.objects.tiles.TileFridge;
import su.terrafirmagreg.modules.device.objects.tiles.TileLatexExtractor;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;


import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@WailaPlugin
public final class WailaIntegration implements IWailaDataProvider, IWailaPlugin {

    @Override
    public void register(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(this, BlockFridge.class);
        registrar.registerBodyProvider(this, TileLatexExtractor.class);
    }

    @NotNull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        Block b = accessor.getBlock();
        if (b instanceof BlockFridge) {
            BlockPos TEPos = accessor.getPosition();
            if (!accessor.getBlockState().getValue(BlockFridge.UPPER)) {
                TEPos = TEPos.up();
            }
            TileFridge fridge = TileUtils.getTile(accessor.getWorld(), TEPos, TileFridge.class);
            if (fridge != null) {
                currenttip.add((new TextComponentTranslation("waila.tfctech.fridge.efficiency", (int) fridge.getEfficiency())).getFormattedText());
                if (fridge.isOpen()) {
                    int slot = BlockFridge.getPlayerLookingItem(TEPos.down(), accessor.getPlayer(), accessor.getBlockState()
                            .getValue(BlockFridge.FACING));
                    if (slot > -1) {
                        ItemStack stack = fridge.getSlot(slot);
                        if (!stack.isEmpty()) {
                            currenttip.add(TextFormatting.WHITE + stack.getDisplayName());
                            IFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
                            if (cap != null) {
                                cap.addTooltipInfo(stack, currenttip, accessor.getPlayer());
                            }
                        }
                    }
                }
            }
        }
        if (b instanceof BlockLatexExtractor) {
            BlockPos TEPos = accessor.getPosition();
            TileLatexExtractor extractor = TileUtils.getTile(accessor.getWorld(), TEPos, TileLatexExtractor.class);
            if (extractor != null) {
                currenttip.add((new TextComponentTranslation("waila.tfctech.latex.quantity", extractor.getFluidAmount())).getFormattedText());
            }
        }
        return currenttip;
    }
}
