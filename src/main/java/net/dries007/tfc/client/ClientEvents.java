package net.dries007.tfc.client;

import su.terrafirmagreg.modules.core.capabilities.egg.CapabilityEgg;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.metal.CapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.sharpness.CapabilitySharpness;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;
import su.terrafirmagreg.modules.core.feature.skills.SmithingSkill;
import su.terrafirmagreg.modules.worldgen.ModuleWorld;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.client.button.GuiButtonPlayerInventoryTab;
import net.dries007.tfc.network.PacketSwitchPlayerInventoryTab;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static su.terrafirmagreg.api.data.Reference.MODID_TFC;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MODID_TFC)
public class ClientEvents {

  @SideOnly(Side.CLIENT)
  @SubscribeEvent(priority = EventPriority.HIGH)
  public static void onInitGuiPre(GuiScreenEvent.InitGuiEvent.Pre event) {
    if (ConfigTFC.General.OVERRIDES.forceTFCWorldType && event.getGui() instanceof GuiCreateWorld gui) {
      // Only change if default is selected, because coming back from customisation, this will be set already.
      if (gui.selectedIndex == WorldType.DEFAULT.getId()) {
        gui.selectedIndex = ModuleWorld.WORLD_TYPE_CLASSIC.getId();
      }
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public static void onInitGuiPost(GuiScreenEvent.InitGuiEvent.Post event) {
    if (event.getGui() instanceof GuiInventory) {
      int buttonId = event.getButtonList().size();
      int guiLeft = ((GuiInventory) event.getGui()).getGuiLeft();
      int guiTop = ((GuiInventory) event.getGui()).getGuiTop();

      event.getButtonList()
           .add(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.INVENTORY, guiLeft, guiTop, ++buttonId, false));
      event.getButtonList()
           .add(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.SKILLS, guiLeft, guiTop, ++buttonId, true));
      event.getButtonList()
           .add(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.CALENDAR, guiLeft, guiTop, ++buttonId, true));
      event.getButtonList()
           .add(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.NUTRITION, guiLeft, guiTop, ++buttonId, true));
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
  public static void onItemTooltip(ItemTooltipEvent event) {
    ItemStack stack = event.getItemStack();
    Item item = stack.getItem();
    List<String> tt = event.getToolTip();
    if (!stack.isEmpty()) {
      // Stuff that should always be shown as part of the tooltip
      var size = CapabilitySize.getIItemSize(stack);
      if (size != null) {
        size.addSizeInfo(stack, tt);
      }

      var sharpness = CapabilitySharpness.get(event.getItemStack());
      if (sharpness != null) {
        sharpness.addSharpnessInfo(stack, tt);
      }

      var heat = CapabilityHeat.get(stack);
      if (heat != null) {
        heat.addHeatInfo(stack, tt);
      }
      var forging = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
      if (forging != null && forging.getWork() > 0) {
        tt.add(I18n.format("tfc.tooltip.forging_in_progress"));
      }
      IFood nutrients = stack.getCapability(CapabilityFood.CAPABILITY, null);
      if (nutrients != null) {
        nutrients.addTooltipInfo(stack, tt, event.getEntityPlayer());
      }
      var egg = CapabilityEgg.get(stack);
      if (egg != null) {
        egg.addEggInfo(stack, tt);
      }
      float skillMod = SmithingSkill.getSkillBonus(stack);
      if (skillMod > 0) {
        String skillValue = String.format("%.2f", skillMod * 100);
        tt.add(I18n.format("tfc.tooltip.smithing_skill", skillValue));
      }

      // Only added with advanced tooltip mode
      if (event.getFlags().isAdvanced()) {
        var metalObject = CapabilityMetal.getMetalItem(stack);
        if (metalObject != null) {
          metalObject.addMetalInfo(stack, tt);
        }
        if (item instanceof IRockObject) {
          ((IRockObject) item).addRockInfo(stack, tt);
        } else if (item instanceof ItemBlock) {
          Block block = ((ItemBlock) item).getBlock();
          if (block instanceof IRockObject) {
            ((IRockObject) block).addRockInfo(stack, tt);
          }
        }

        if (ConfigTFC.Client.TOOLTIP.showToolClassTooltip) {
          Set<String> toolClasses = item.getToolClasses(stack);
          if (toolClasses.size() == 1) {
            tt.add(I18n.format("tfc.tooltip.toolclass", toolClasses.iterator().next()));
          } else if (toolClasses.size() > 1) {
            tt.add(I18n.format("tfc.tooltip.toolclasses"));
            for (String toolClass : toolClasses) {
              tt.add("+ " + toolClass);
            }
          }
        }
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
        if (ConfigTFC.Client.TOOLTIP.showNBTTooltip) {
          if (stack.hasTagCompound()) {
            tt.add("NBT: " + stack.getTagCompound());
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
}
