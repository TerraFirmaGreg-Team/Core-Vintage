package net.dries007.tfc.module.core.events;

import net.dries007.tfc.client.button.GuiButtonPlayerInventoryTab;
import net.dries007.tfc.client.util.TFCGuiHandler;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.core.ModuleCoreOld;
import net.dries007.tfc.module.core.api.capability.size.CapabilityItemSize;
import net.dries007.tfc.module.core.network.SCPacketSwitchPlayerInventoryTab;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateHelper;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.minecraft.util.text.TextFormatting.*;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Tags.MOD_ID, value = Side.CLIENT)
public class ClientEventsHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onInitGuiPost(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiInventory) {
            int buttonId = event.getButtonList().size();
            int guiLeft = ((GuiInventory) event.getGui()).getGuiLeft();
            int guiTop = ((GuiInventory) event.getGui()).getGuiTop();

            event.getButtonList().add(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.INVENTORY, guiLeft, guiTop, ++buttonId, false));
            event.getButtonList().add(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.SKILLS, guiLeft, guiTop, ++buttonId, true));
            event.getButtonList().add(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.CALENDAR, guiLeft, guiTop, ++buttonId, true));
            event.getButtonList().add(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.NUTRITION, guiLeft, guiTop, ++buttonId, true));
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onGuiButtonPressPre(GuiScreenEvent.ActionPerformedEvent.Pre event) {
        if (event.getGui() instanceof GuiInventory) {
            if (event.getButton() instanceof GuiButtonPlayerInventoryTab button) {
                // This is to prevent the button from immediately firing after moving (enabled is set to false then)
                if (button.isActive() && button.enabled) {
                    ModuleCoreOld.PACKET_SERVICE.sendToServer(new SCPacketSwitchPlayerInventoryTab(button.getGuiType()));
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onGuiButtonPressPost(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.getGui() instanceof GuiInventory) {
            // This is necessary to catch the resizing of the inventory gui when you open the recipe book
            for (GuiButton button : event.getButtonList()) {
                if (button instanceof GuiButtonPlayerInventoryTab) {
                    ((GuiButtonPlayerInventoryTab) button).updateGuiLeft(((GuiInventory) event.getGui()).getGuiLeft());
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRenderGameOverlayText(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();
        List<String> list = event.getRight();
        if (mc.gameSettings.showDebugInfo) {
            //noinspection ConstantConditions
            BlockPos blockpos = new BlockPos(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().getEntityBoundingBox().minY, mc.getRenderViewEntity().posZ);
            Chunk chunk = mc.world.getChunk(blockpos);
            if (mc.world.isBlockLoaded(blockpos) && !chunk.isEmpty()) {
                final int x = blockpos.getX() & 15, z = blockpos.getZ() & 15;
                ChunkDataTFC data = chunk.getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);

                list.add("");
                list.add(TextFormatting.AQUA + "TerraFirmaCraft");
                boolean chunkDataValid = data != null && data.isInitialized();

                if (chunkDataValid) {
                    list.add(String.format("%sRegion: %s%.1f\u00b0C%s Avg: %s%.1f\u00b0C%s Min: %s%.1f\u00b0C%s Max: %s%.1f\u00b0C",
                            TextFormatting.GRAY, TextFormatting.WHITE, data.getRegionalTemp(), TextFormatting.GRAY,
                            TextFormatting.WHITE, data.getAverageTemp(), TextFormatting.GRAY,
                            TextFormatting.WHITE, ClimateHelper.monthFactor(data.getRegionalTemp(), Month.JANUARY.getTemperatureModifier(), blockpos.getZ()), TextFormatting.GRAY,
                            TextFormatting.WHITE, ClimateHelper.monthFactor(data.getRegionalTemp(), Month.JULY.getTemperatureModifier(), blockpos.getZ())));
                    list.add(String.format("%sTemperature: %s%.1f\u00b0C Daily: %s%.1f\u00b0C",
                            TextFormatting.GRAY, TextFormatting.WHITE, ClimateTFC.getMonthlyTemp(blockpos),
                            TextFormatting.WHITE, ClimateTFC.getActualTemp(blockpos)));
                    list.add(TextFormatting.GRAY + "Rainfall: " + TextFormatting.WHITE + data.getRainfall());
                    list.add(TextFormatting.GRAY + "Spawn Protection = " + TextFormatting.WHITE + data.isSpawnProtected());
                } else if (mc.world.provider.getDimension() == 0) {
                    list.add("Invalid Chunk Data (?)");
                }

                // Always add calendar info
                list.add(I18n.format("tfc.tooltip.date", CalendarTFC.CALENDAR_TIME.getTimeAndDate()));

                if (ConfigTFC.General.DEBUG.enable) {
                    list.add(I18n.format("tfc.tooltip.debug_times", CalendarTFC.PLAYER_TIME.getTicks(), CalendarTFC.CALENDAR_TIME.getTicks()));

                    if (chunkDataValid) {
                        list.add(TextFormatting.GRAY + "Flora Density: " + TextFormatting.WHITE + data.getFloraDensity());
                        list.add(TextFormatting.GRAY + "Flora Diversity: " + TextFormatting.WHITE + data.getFloraDiversity());

                        list.add(TextFormatting.GRAY + "Valid Trees: ");
                        data.getValidTrees().forEach(t -> list.add(String.format("%s %s (%.1f)", TextFormatting.WHITE, t.toString(), t.getDominance())));

                        list.add(TextFormatting.GRAY + "Sea level offset: " + TextFormatting.WHITE + data.getSeaLevelOffset(x, z));
                        list.add(TextFormatting.GRAY + "Spawn Protection: " + TextFormatting.WHITE + data.getSpawnProtection());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onItemTooltip(ItemTooltipEvent event) {
        var stack = event.getItemStack();
        var toolTip = event.getToolTip();

        // GuiScreen.isShiftKeyDown()

        if (!stack.isEmpty()) {
            // Size
            var size = CapabilityItemSize.getIItemSize(stack);
            if (size != null) {
                size.addSizeInfo(stack, toolTip);
            }

//            // Temperature
//            var heat = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
//            if (heat != null) {
//                heat.addHeatInfo(stack, toolTip);
//            }

//            // Forging steps
//            var forging = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
//            if (forging != null && forging.getWork() > 0) {
//                toolTip.add(I18n.format("tfc.tooltip.forging_in_progress"));
//            }

//            // Nutriens
//            var nutrients = stack.getCapability(CapabilityFood.CAPABILITY, null);
//            if (nutrients != null) {
//                nutrients.addTooltipInfo(stack, toolTip);
//            }
//
//            // Egg
//            var eggInfo = stack.getCapability(CapabilityEgg.CAPABILITY, null);
//            if (eggInfo != null) {
//                eggInfo.addEggInfo(stack, toolTip);
//            }
//
//            // Metal
//            var metalObject = CapabilityMetalItem.getMaterialItem(stack);
//            if (metalObject != null) {
//                metalObject.addMetalInfo(stack, toolTip);
//            }

            if (event.getFlags().isAdvanced()) {
                if (ConfigTFC.Client.TOOLTIP.showOreDictionaryTooltip) {
                    int[] ids = OreDictionary.getOreIDs(stack);
                    if (ids.length == 1) {
                        toolTip.add(I18n.format("tfc.tooltip.oredictionaryentry", OreDictionary.getOreName(ids[0])));
                    } else if (ids.length > 1) {
                        toolTip.add(I18n.format("tfc.tooltip.oredictionaryentries"));
                        ArrayList<String> names = new ArrayList<>(ids.length);
                        for (int id : ids) {
                            names.add("+ " + OreDictionary.getOreName(id));
                        }
                        names.sort(null); // Natural order (String.compare)
                        toolTip.addAll(names);
                    }
                }
            }
        }
    }
}
