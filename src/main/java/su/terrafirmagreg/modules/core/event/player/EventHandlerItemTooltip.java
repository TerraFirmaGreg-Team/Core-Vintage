package su.terrafirmagreg.modules.core.event.player;

import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.capabilities.egg.CapabilityEgg;
import su.terrafirmagreg.modules.core.capabilities.sharpness.CapabilitySharpness;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Set;

public class EventHandlerItemTooltip {

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  @SideOnly(Side.CLIENT)
  public static void onHighest(ItemTooltipEvent event) {
    var stack = event.getItemStack();
    var tooltip = event.getToolTip();
    var isAdvanced = event.getFlags().isAdvanced();

    if (!StackUtils.isValid(stack)) {return;}

    capabilitySize(tooltip, stack);
    capabilitySharpness(tooltip, stack);
    capabilityEgg(tooltip, stack);
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  @SideOnly(Side.CLIENT)
  public static void onLowest(ItemTooltipEvent event) {

    var stack = event.getItemStack();
    var tooltip = event.getToolTip();
    var isAdvanced = event.getFlags().isAdvanced();

    if (!StackUtils.isValid(stack)) {return;}

    advancedData(tooltip, stack);
  }

  private static void capabilitySharpness(List<String> tooltips, ItemStack stack) {
    var sharpness = CapabilitySharpness.get(stack);
    if (sharpness != null) {
      sharpness.addTooltipInfo(stack, tooltips);
    }
  }

  private static void capabilityEgg(List<String> tooltips, ItemStack stack) {
    var egg = CapabilityEgg.get(stack);
    if (egg != null) {
      egg.addTooltipInfo(stack, tooltips);
    }
  }

  private static void capabilitySize(List<String> tooltips, ItemStack stack) {
    var size = CapabilitySize.get(stack);
    if (size != null) {
      size.addTooltipInfo(stack, tooltips);
    }
  }

  // TODO: Добавить перевод
  private static void advancedData(List<String> tooltips, ItemStack stack) {

    Item item = stack.getItem();
    String translationKey = item.getTranslationKey();
    String metaName = item.getTranslationKey(stack);
    int itemDamage = stack.getItemDamage();
    int maxDamage = stack.getMaxDamage();

    final String ADVANCED_INFO_TEXT_PRE = TextFormatting.DARK_GRAY + "     "; //§8
    final String ADVANCED_INFO_HEADER_PRE = TextFormatting.GRAY + "  -"; //§7

    if (!ConfigCore.MISC.TOOLTIP.requireCTRL || GuiScreen.isCtrlKeyDown()) {

      tooltips.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + "Advanced Data:");

      // OreDict Names
      if (ConfigCore.MISC.TOOLTIP.showOreDictionary) {
        Set<String> oreNames = OreDictUtils.getOreNames(stack);
        if (!oreNames.isEmpty()) {
          tooltips.add(ADVANCED_INFO_HEADER_PRE + "Ore Dictionary Names:");
          for (String oreName : oreNames) {
            tooltips.add(ADVANCED_INFO_TEXT_PRE + oreName);
          }
        }
      }

      // Tool Class
      if (ConfigCore.MISC.TOOLTIP.showToolClass) {
        Set<String> toolClasses = item.getToolClasses(stack);

        if (!toolClasses.isEmpty()) {
          tooltips.add(ADVANCED_INFO_HEADER_PRE + "Tool Classes:");
          for (String toolClass : toolClasses) {
            int harvestLevel = item.getHarvestLevel(stack, toolClass, null, null);
            tooltips.add(ADVANCED_INFO_TEXT_PRE + toolClass + " (" + harvestLevel + ")");
          }
        }
      }

      // Code Name
      if (ConfigCore.MISC.TOOLTIP.showCodeName) {
        tooltips.add(ADVANCED_INFO_HEADER_PRE + "Code Name:");
        tooltips.add(ADVANCED_INFO_TEXT_PRE + Item.REGISTRY.getNameForObject(item));
      }

      // Base Item's Unlocalized Name
      if (ConfigCore.MISC.TOOLTIP.showOreDictionary) {
        tooltips.add(ADVANCED_INFO_HEADER_PRE + "Item's Unlocalized Name:");
        tooltips.add(ADVANCED_INFO_TEXT_PRE + translationKey);
      }

      // Metadata
      if (ConfigCore.MISC.TOOLTIP.showMetadata) {
        tooltips.add(ADVANCED_INFO_HEADER_PRE + "Metadata:");
        tooltips.add(ADVANCED_INFO_TEXT_PRE + itemDamage + (maxDamage > 0 ? "/" + maxDamage : ""));
      }

      // Meta's Unlocalized Name
      if (ConfigCore.MISC.TOOLTIP.showMetaUnlocalizedName) {
        if (!metaName.equals(translationKey)) {
          tooltips.add(ADVANCED_INFO_HEADER_PRE + "Meta's Unlocalized Name:");
          tooltips.add(ADVANCED_INFO_TEXT_PRE + metaName);
        }
      }

      //NBT
      if (ConfigCore.MISC.TOOLTIP.showNBT) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound != null && !compound.isEmpty()) {
          tooltips.add(ADVANCED_INFO_HEADER_PRE + "NBT:");
          if (GuiScreen.isShiftKeyDown()) {
            int limit = ConfigCore.MISC.TOOLTIP.charLimitNBT;
            String compoundStrg = compound.toString();
            int compoundStrgLength = compoundStrg.length();

            String compoundDisplay;
            if (limit > 0 && compoundStrgLength > limit) {
              compoundDisplay = compoundStrg.substring(0, limit) + TextFormatting.GRAY + " (" + (compoundStrgLength - limit) + " more characters...)";
            } else {
              compoundDisplay = compoundStrg;
            }
            tooltips.add(ADVANCED_INFO_TEXT_PRE + compoundDisplay);
          } else {
            tooltips.add(ADVANCED_INFO_TEXT_PRE + TextFormatting.ITALIC + "[Press Shift] " + compound.getKeySet().size() + " tag(s)");
          }
        }
      }
    } else {
      tooltips.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + "[Press CTRL] for Advanced Data");
    }
  }
}
