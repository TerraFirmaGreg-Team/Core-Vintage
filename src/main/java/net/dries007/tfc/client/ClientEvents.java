package net.dries007.tfc.client;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.Month;
import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.core.feature.climate.ClimateHelper;
import su.terrafirmagreg.modules.core.feature.skill.SmithingSkill;
import su.terrafirmagreg.temp.config.HotLists;
import su.terrafirmagreg.temp.config.TFGConfig;
import su.terrafirmagreg.temp.modules.hotornot.FluidEffect;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.client.button.GuiButtonPlayerInventoryTab;
import net.dries007.tfc.client.render.RenderBoatTFC;
import net.dries007.tfc.client.render.projectile.RenderThrownJavelin;
import net.dries007.tfc.network.PacketSwitchPlayerInventoryTab;
import net.dries007.tfc.objects.entity.EntityBoatTFC;
import net.dries007.tfc.objects.entity.EntityFallingBlockTFC;
import net.dries007.tfc.objects.entity.projectile.EntityThrownJavelin;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import java.util.List;

import static net.minecraft.util.text.TextFormatting.AQUA;
import static net.minecraft.util.text.TextFormatting.GRAY;
import static net.minecraft.util.text.TextFormatting.WHITE;
import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFC;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TFC)
public class ClientEvents {

  public static void preInit() {
    RenderingRegistry.registerEntityRenderingHandler(EntityFallingBlockTFC.class, RenderFallingBlock::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityThrownJavelin.class, RenderThrownJavelin::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityBoatTFC.class, RenderBoatTFC::new);
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent(priority = EventPriority.HIGH)
  public static void onInitGuiPre(GuiScreenEvent.InitGuiEvent.Pre event) {
    if (ConfigTFC.General.OVERRIDES.forceTFCWorldType && event.getGui() instanceof GuiCreateWorld gui) {
      // Only change if default is selected, because coming back from customisation, this will be set already.
      if (gui.selectedIndex == WorldType.DEFAULT.getId()) {
        gui.selectedIndex = TerraFirmaCraft.getWorldType().getId();
      }
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public static void onInitGuiPost(GuiScreenEvent.InitGuiEvent.Post event) {
    if (event.getGui() instanceof GuiInventory guiInventory) {
      int buttonId = event.getButtonList().size();
      int guiLeft = guiInventory.getGuiLeft();
      int guiTop = guiInventory.getGuiTop();

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
          TerraFirmaCraft.getNetwork().sendToServer(new PacketSwitchPlayerInventoryTab(button.getGuiType()));
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
            GRAY, WHITE, Climate.getMonthlyTemp(blockpos),
            WHITE, Climate.getActualTemp(blockpos)));
          list.add(GRAY + "Rainfall: " + WHITE + data.getRainfall());
          list.add(GRAY + "Spawn Protection = " + WHITE + data.isSpawnProtected());
        } else if (mc.world.provider.getDimension() == 0) {
          list.add("Invalid Chunk Data (?)");
        }

        // Always add calendar info
        list.add(I18n.format("tfc.tooltip.date", Calendar.CALENDAR_TIME.getTimeAndDate()));

        if (ConfigTFC.General.DEBUG.enable) {
          list.add(I18n.format("tfc.tooltip.debug_times", Calendar.PLAYER_TIME.getTicks(), Calendar.CALENDAR_TIME.getTicks()));

          if (chunkDataValid) {
            list.add(GRAY + "Flora Density: " + WHITE + data.getFloraDensity());
            list.add(GRAY + "Flora Diversity: " + WHITE + data.getFloraDiversity());

            list.add(GRAY + "Valid Trees: ");
            data.getValidTrees().forEach(t -> list.add(String.format("%s %s (%.1f)", WHITE, t.getRegistryName(), t.getDominance())));

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
    ItemStack stack = event.getItemStack();
    Item item = stack.getItem();
    List<String> tooltip = event.getToolTip();
    if (stack.isEmpty()) {return;}
    // Stuff that should always be shown as part of the tooltip

    float skillMod = SmithingSkill.getSkillBonus(stack);
    if (skillMod > 0) {
      String skillValue = String.format("%.2f", skillMod * 100);
      tooltip.add(I18n.format("tfc.tooltip.smithing_skill", skillValue));
    }

    if (TFGConfig.General.TOOLTIP && !stack.isEmpty() && !HotLists.isRemoved(stack)) {
      if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
        IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (fluidHandlerItem == null) {return;}
        FluidStack fluidStack = fluidHandlerItem.drain(1000, false);
        if (fluidStack != null) {
          for (FluidEffect effect : FluidEffect.values()) {
            if (effect.isValid.test(fluidStack)) {
              tooltip.add(effect.color + new TextComponentTranslation(effect.tooltip).getUnformattedText());
            }
          }
        }
      } else if (HotLists.isHot(stack)) {
        tooltip.add(FluidEffect.HOT.color + new TextComponentTranslation(FluidEffect.HOT.tooltip).getUnformattedText());
      } else if (HotLists.isCold(stack)) {
        tooltip.add(FluidEffect.COLD.color + new TextComponentTranslation(FluidEffect.COLD.tooltip).getUnformattedText());
      } else if (HotLists.isGaseous(stack)) {
        tooltip.add(FluidEffect.GAS.color + new TextComponentTranslation(FluidEffect.GAS.tooltip).getUnformattedText());
      } else if (Loader.isModLoaded("tfc")) {
        if (CapabilityHeat.has(stack)) {
          var heat = CapabilityHeat.get(stack);
          if (heat == null) {return;}
          if (heat.getTemperature() >= TFGConfig.General.HOT_ITEM) {
            tooltip.add(FluidEffect.HOT.color + new TextComponentTranslation(FluidEffect.HOT.tooltip).getUnformattedText());
          }
        }
      }
    }

    if (event.getFlags().isAdvanced()) // Only added with advanced tooltip mode
    {
      if (item instanceof IRockObject rockObject) {
        rockObject.addRockInfo(stack, tooltip);
      } else if (item instanceof ItemBlock itemBlock) {
        Block block = itemBlock.getBlock();
        if (block instanceof IRockObject rockObject) {
          rockObject.addRockInfo(stack, tooltip);
        }
      }
    }
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public static void textureStitched(TextureStitchEvent.Post event) {
    FluidSpriteCache.clear();
  }
}
