package net.dries007.tfc.client;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.egg.CapabilityEgg;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.FoodHandler;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.size.CapabilityItemSize;
import net.dries007.tfc.client.button.GuiButtonPlayerInventoryTab;
import net.dries007.tfc.client.gui.overlay.PlayerDataOverlay;
import net.dries007.tfc.client.render.RenderBoatTFC;
import net.dries007.tfc.client.render.animal.*;
import net.dries007.tfc.client.render.projectile.RenderThrownJavelin;
import net.dries007.tfc.command.*;
import net.dries007.tfc.network.PacketSwitchPlayerInventoryTab;
import net.dries007.tfc.objects.entity.EntityBoatTFC;
import net.dries007.tfc.objects.entity.EntityFallingBlockTFC;
import net.dries007.tfc.objects.entity.animal.*;
import net.dries007.tfc.objects.entity.projectile.EntityThrownJavelin;
import net.dries007.tfc.common.CommonProxy;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateHelper;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.util.text.TextFormatting.*;
import static net.minecraft.util.text.TextFormatting.WHITE;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);

        registerEntityRenderer();
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        super.onInit(event);

        TFCKeybindings.init();
        // Enable overlay to render health, thirst and hunger bars, TFC style.
        // Also renders animal familiarity
        MinecraftForge.EVENT_BUS.register(PlayerDataOverlay.getInstance());
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        super.onPostInit(event);
    }

    public void onLoadComplete(FMLLoadCompleteEvent event) {
        super.onLoadComplete(event);
    }

    public void onServerStarting(FMLServerStartingEvent event) {
        super.onServerStarting(event);
    }

    public static void registerEntityRenderer() {
        RenderingRegistry.registerEntityRenderingHandler(EntityFallingBlockTFC.class, RenderFallingBlock::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityThrownJavelin.class, RenderThrownJavelin::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySheepTFC.class, RenderSheepTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCowTFC.class, RenderCowTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGrizzlyBearTFC.class, RenderGrizzlyBearTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityChickenTFC.class, RenderChickenTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPheasantTFC.class, RenderPheasantTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDeerTFC.class, RenderDeerTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPigTFC.class, RenderPigTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWolfTFC.class, RenderWolfTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRabbitTFC.class, RenderRabbitTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHorseTFC.class, RenderHorseTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDonkeyTFC.class, RenderAbstractHorseTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMuleTFC.class, RenderAbstractHorseTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBoatTFC.class, RenderBoatTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPolarBearTFC.class, RenderPolarBearTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityParrotTFC.class, RenderParrotTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLlamaTFC.class, RenderLlamaTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityOcelotTFC.class, RenderOcelotTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPantherTFC.class, RenderPantherTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDuckTFC.class, RenderDuckTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityAlpacaTFC.class, RenderAlpacaTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGoatTFC.class, RenderGoatTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySaberToothTFC.class, RenderSaberToothTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCamelTFC.class, RenderCamelTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLionTFC.class, RenderLionTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHyenaTFC.class, RenderHyenaTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDireWolfTFC.class, RenderDireWolfTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHareTFC.class, RenderHareTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBoarTFC.class, RenderBoarTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityZebuTFC.class, RenderZebuTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGazelleTFC.class, RenderGazelleTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWildebeestTFC.class, RenderWildebeestTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityQuailTFC.class, RenderQuailTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGrouseTFC.class, RenderGrouseTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMongooseTFC.class, RenderMongooseTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTurkeyTFC.class, RenderTurkeyTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityJackalTFC.class, RenderJackalTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMuskOxTFC.class, RenderMuskOxTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityYakTFC.class, RenderYakTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBlackBearTFC.class, RenderBlackBearTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCougarTFC.class, RenderCougarTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCoyoteTFC.class, RenderCoyoteTFC::new);
    }

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
                    TerraFirmaCraft.NETWORK.sendToServer(new PacketSwitchPlayerInventoryTab(button.getGuiType()));
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
                list.add(AQUA + "TerraFirmaCraft");
                boolean chunkDataValid = data != null && data.isInitialized();

                if (chunkDataValid) {
                    list.add(String.format("%sRegion: %s%.1f\u00b0C%s Avg: %s%.1f\u00b0C%s Min: %s%.1f\u00b0C%s Max: %s%.1f\u00b0C",
                            GRAY, WHITE, data.getRegionalTemp(), GRAY,
                            WHITE, data.getAverageTemp(), GRAY,
                            WHITE, ClimateHelper.monthFactor(data.getRegionalTemp(), Month.JANUARY.getTemperatureModifier(), blockpos.getZ()), GRAY,
                            WHITE, ClimateHelper.monthFactor(data.getRegionalTemp(), Month.JULY.getTemperatureModifier(), blockpos.getZ())));
                    list.add(String.format("%sTemperature: %s%.1f\u00b0C Daily: %s%.1f\u00b0C",
                            GRAY, WHITE, ClimateTFC.getMonthlyTemp(blockpos),
                            WHITE, ClimateTFC.getActualTemp(blockpos)));
                    list.add(GRAY + "Rainfall: " + WHITE + data.getRainfall());
                    list.add(GRAY + "Spawn Protection = " + WHITE + data.isSpawnProtected());
                } else if (mc.world.provider.getDimension() == 0) {
                    list.add("Invalid Chunk Data (?)");
                }

                // Always add calendar info
                list.add(I18n.format("tfc.tooltip.date", CalendarTFC.CALENDAR_TIME.getTimeAndDate()));

                if (ConfigTFC.General.DEBUG.enable) {
                    list.add(I18n.format("tfc.tooltip.debug_times", CalendarTFC.PLAYER_TIME.getTicks(), CalendarTFC.CALENDAR_TIME.getTicks()));

                    if (chunkDataValid) {
                        list.add(GRAY + "Flora Density: " + WHITE + data.getFloraDensity());
                        list.add(GRAY + "Flora Diversity: " + WHITE + data.getFloraDiversity());

                        list.add(GRAY + "Valid Trees: ");
                        data.getValidTrees().forEach(t -> list.add(String.format("%s %s (%.1f)", WHITE, t.toString(), t.getDominance())));

                        list.add(GRAY + "Sea level offset: " + WHITE + data.getSeaLevelOffset(x, z));
                        list.add(GRAY + "Spawn Protection: " + WHITE + data.getSpawnProtection());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onItemTooltip(ItemTooltipEvent event) {
        var stack = event.getItemStack();
        var tt = event.getToolTip();

        if (!stack.isEmpty()) {
            // Size
            var size = CapabilityItemSize.getIItemSize(stack);
            if (size != null) {
                size.addSizeInfo(stack, tt);
            }

            // Temperature
            var heat = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
            if (heat != null) {
                heat.addHeatInfo(stack, tt);
            }

            // Forging steps
            var forging = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
            if (forging != null && forging.getWork() > 0) {
                tt.add(I18n.format("tfc.tooltip.forging_in_progress"));
            }

            // Nutriens
            var nutrients = stack.getCapability(CapabilityFood.CAPABILITY, null);
            if (nutrients != null) {
                nutrients.addTooltipInfo(stack, tt, event.getEntityPlayer());
            }

            // Egg
            var eggInfo = stack.getCapability(CapabilityEgg.CAPABILITY, null);
            if (eggInfo != null) {
                eggInfo.addEggInfo(stack, tt);
            }

            // Metal
            var metalObject = CapabilityMetalItem.getMaterialItem(stack);
            if (metalObject != null) {
                metalObject.addMetalInfo(stack, tt);
            }

            if (event.getFlags().isAdvanced()) {
                if (ConfigTFC.Client.TOOLTIP.showOreDictionaryTooltip) {
                    int[] ids = OreDictionary.getOreIDs(stack);
                    if (ids.length == 1) {
                        tt.add(I18n.format("tfc.tooltip.oredictionaryentry", OreDictionary.getOreName(ids[0])));
                    } else if (ids.length > 1) {
                        tt.add(I18n.format("tfc.tooltip.oredictionaryentries"));
                        ArrayList<String> names = new ArrayList<>(ids.length);
                        for (int id : ids) {
                            names.add("+ " + OreDictionary.getOreName(id));
                        }
                        names.sort(null); // Natural order (String.compare)
                        tt.addAll(names);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void textureStitched(TextureStitchEvent.Post event) {
        FluidSpriteCache.clear();
    }

    @Nonnull
    @Override
    public IThreadListener getThreadListener(MessageContext context) {
        if (context.side.isClient()) {
            return Minecraft.getMinecraft();
        } else {
            return context.getServerHandler().player.server;
        }
    }

    @Override
    @Nullable
    public EntityPlayer getPlayer(MessageContext context) {
        if (context.side.isClient()) {
            return Minecraft.getMinecraft().player;
        } else {
            return context.getServerHandler().player;
        }
    }

    @Override
    @Nullable
    public World getWorld(MessageContext context) {
        if (context.side.isClient()) {
            return Minecraft.getMinecraft().world;
        } else {
            return context.getServerHandler().player.getEntityWorld();
        }
    }

    @Nonnull
    @Override
    public String getMonthName(Month month, boolean useSeasons) {
        return I18n.format(useSeasons ? "tfc.enum.season." + month.name().toLowerCase() : Helpers.getEnumName(month));
    }

    @Nonnull
    @Override
    public String getDayName(int dayOfMonth, long totalDays) {
        String date = CalendarTFC.CALENDAR_TIME.getMonthOfYear().name() + dayOfMonth;
        String birthday = CalendarTFC.BIRTHDAYS.get(date);
        if (birthday != null) {
            return birthday;
        }
        return I18n.format("tfc.enum.day." + CalendarTFC.DAY_NAMES[(int) (totalDays % 7)]);
    }

    @Nonnull
    @Override
    public String getDate(int hour, int minute, String monthName, int day, long years) {
        // We call an additional String.format for the time, because vanilla doesn't support %02d format specifiers
        return I18n.format("tfc.tooltip.calendar_full_date", String.format("%02d:%02d", hour, minute), monthName, day, years);
    }
}
