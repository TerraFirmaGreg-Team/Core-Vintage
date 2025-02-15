package net.dries007.tfc.client;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.ICapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.forge.CapabilityForgeable;
import su.terrafirmagreg.modules.core.capabilities.forge.ICapabilityForge;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.ICapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.metal.CapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.Month;
import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.core.feature.climate.ClimateHelper;
import su.terrafirmagreg.modules.core.feature.skill.SmithingSkill;

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
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static net.minecraft.util.text.TextFormatting.AQUA;
import static net.minecraft.util.text.TextFormatting.GRAY;
import static net.minecraft.util.text.TextFormatting.WHITE;
import static su.terrafirmagreg.api.data.enums.Mods.Names.TFC;

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

    ICapabilityHeat heat = stack.getCapability(CapabilityHeat.CAPABILITY, null);
    if (heat != null) {
      heat.addHeatInfo(stack, tooltip);
    }
    ICapabilityForge forging = stack.getCapability(CapabilityForgeable.CAPABILITY, null);
    if (forging != null && forging.getWork() > 0) {
      tooltip.add(I18n.format("tfc.tooltip.forging_in_progress"));
    }
    ICapabilityFood food = stack.getCapability(CapabilityFood.CAPABILITY, null);
    if (food != null) {
      food.addTooltipInfo(stack, tooltip, event.getEntityPlayer());
    }

    float skillMod = SmithingSkill.getSkillBonus(stack);
    if (skillMod > 0) {
      String skillValue = String.format("%.2f", skillMod * 100);
      tooltip.add(I18n.format("tfc.tooltip.smithing_skill", skillValue));
    }

    if (event.getFlags().isAdvanced()) // Only added with advanced tooltip mode
    {
      ICapabilityMetal metalObject = CapabilityMetal.getMetalItem(stack);
      if (metalObject != null) {
        metalObject.addMetalInfo(stack, tooltip);
      }
      if (item instanceof IRockObject) {
        ((IRockObject) item).addRockInfo(stack, tooltip);
      } else if (item instanceof ItemBlock) {
        Block block = ((ItemBlock) item).getBlock();
        if (block instanceof IRockObject) {
          ((IRockObject) block).addRockInfo(stack, tooltip);
        }
      }

      if (ConfigTFC.Client.TOOLTIP.showToolClassTooltip) {
        Set<String> toolClasses = item.getToolClasses(stack);
        if (toolClasses.size() == 1) {
          tooltip.add(I18n.format("tfc.tooltip.toolclass", toolClasses.iterator().next()));
        } else if (toolClasses.size() > 1) {
          tooltip.add(I18n.format("tfc.tooltip.toolclasses"));
          for (String toolClass : toolClasses) {
            tooltip.add("+ " + toolClass);
          }
        }
      }
      if (ConfigTFC.Client.TOOLTIP.showOreDictionaryTooltip) {
        int[] ids = OreDictionary.getOreIDs(stack);
        if (ids.length == 1) {
          tooltip.add(I18n.format("tfc.tooltip.oredictionaryentry", OreDictionary.getOreName(ids[0])));
        } else if (ids.length > 1) {
          tooltip.add(I18n.format("tfc.tooltip.oredictionaryentries"));
          ArrayList<String> names = new ArrayList<>(ids.length);
          for (int id : ids) {
            names.add("+ " + OreDictionary.getOreName(id));
          }
          names.sort(null); // Natural order (String.compare)
          tooltip.addAll(names);
        }
      }
      if (ConfigTFC.Client.TOOLTIP.showNBTTooltip) {
        if (stack.hasTagCompound()) {
          tooltip.add("NBT: " + stack.getTagCompound());
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
