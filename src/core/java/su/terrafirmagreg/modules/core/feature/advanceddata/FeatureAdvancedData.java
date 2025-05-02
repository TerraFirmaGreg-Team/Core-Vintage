package su.terrafirmagreg.modules.core.feature.advanceddata;

import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.framework.manager.feature.spi.FeatureBase;
import su.terrafirmagreg.modules.core.ConfigCore;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
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

public class FeatureAdvancedData extends FeatureBase {

  @SubscribeEvent(priority = EventPriority.LOWEST)
  @SideOnly(Side.CLIENT)
  public static void onItemTooltipEvent(ItemTooltipEvent event) {

    var stack = event.getItemStack();
    var tooltip = event.getToolTip();
    var isAdvanced = event.getFlags().isAdvanced();

    if (!StackUtils.isValid(stack)) {
      return;
    }

    advancedData(tooltip, stack);

    if (isAdvanced) {
      // MC debug tooltips. Remove these always, as we will format them differently later
      if (stack.getTagCompound() != null) {
        String nbtTags = TextFormatting.DARK_GRAY + I18n.format("item.nbt_tags", stack.getTagCompound().getKeySet().size());
        tooltip.remove(nbtTags);
      }
      if (stack.getItem().getRegistryName() != null) {
        String registryName = TextFormatting.DARK_GRAY + stack.getItem().getRegistryName().toString();
        tooltip.remove(registryName);
      }
    }

  }

  private static void advancedData(List<String> tooltips, ItemStack stack) {

    if (!ConfigCore.FEATURE.ADVANCED_DATA.enable) {
      return;
    }
    Item item = stack.getItem();
    String translationKey = item.getTranslationKey();
    String registryName = item.getRegistryName().toString();
    String metaName = item.getTranslationKey(stack);
    int itemDamage = stack.getItemDamage();
    int maxDamage = stack.getMaxDamage();

    final String TEXT_PRE = TextFormatting.DARK_GRAY + "     "; //§8
    final String HEADER_PRE = TextFormatting.GRAY + "  -"; //§7

    if (!ConfigCore.FEATURE.ADVANCED_DATA.requireCTRL || GuiScreen.isCtrlKeyDown()) {

      tooltips.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + "Advanced Data:");

      // OreDict Names
      if (ConfigCore.FEATURE.ADVANCED_DATA.showOreDictionary) {
        Set<String> oreNames = OreDictUtils.getOreNames(stack);
        if (!oreNames.isEmpty()) {
          tooltips.add(HEADER_PRE + "Ore Dictionary Names:");
          for (String oreName : oreNames) {
            tooltips.add(TEXT_PRE + oreName);
          }
        }
      }

      // Tool Class
      if (ConfigCore.FEATURE.ADVANCED_DATA.showToolClass) {
        Set<String> toolClasses = item.getToolClasses(stack);

        if (!toolClasses.isEmpty()) {
          tooltips.add(HEADER_PRE + "Tool Classes:");
          for (String toolClass : toolClasses) {
            int harvestLevel = item.getHarvestLevel(stack, toolClass, null, null);
            tooltips.add(TEXT_PRE + toolClass + " (" + harvestLevel + ")");
          }
        }
      }

      // Code Name
      if (ConfigCore.FEATURE.ADVANCED_DATA.showCodeName) {
        tooltips.add(HEADER_PRE + "Code Name:");
        tooltips.add(TEXT_PRE + registryName);
      }

      // Base Item's Unlocalized Name
      if (ConfigCore.FEATURE.ADVANCED_DATA.showOreDictionary) {
        tooltips.add(HEADER_PRE + "Item's Unlocalized Name:");
        tooltips.add(TEXT_PRE + translationKey);
      }

      // Metadata
      if (ConfigCore.FEATURE.ADVANCED_DATA.showMetadata) {
        tooltips.add(HEADER_PRE + "Metadata:");
        tooltips.add(TEXT_PRE + itemDamage + (maxDamage > 0 ? "/" + maxDamage : ""));
      }

      // Meta's Unlocalized Name
      if (ConfigCore.FEATURE.ADVANCED_DATA.showMetaUnlocalizedName) {
        if (!metaName.equals(translationKey)) {
          tooltips.add(HEADER_PRE + "Meta's Unlocalized Name:");
          tooltips.add(TEXT_PRE + metaName);
        }
      }

      //NBT
      if (ConfigCore.FEATURE.ADVANCED_DATA.showNBT) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound != null && !compound.isEmpty()) {
          tooltips.add(HEADER_PRE + "NBT:");
          if (GuiScreen.isShiftKeyDown()) {
            int limit = ConfigCore.FEATURE.ADVANCED_DATA.charLimitNBT;
            String compoundStrg = compound.toString();
            int compoundStrgLength = compoundStrg.length();

            String compoundDisplay;
            if (limit > 0 && compoundStrgLength > limit) {
              compoundDisplay = compoundStrg.substring(0, limit) + TextFormatting.GRAY + " (" + (compoundStrgLength - limit) + " more characters...)";
            } else {
              compoundDisplay = compoundStrg;
            }
            tooltips.add(TEXT_PRE + compoundDisplay);
          } else {
            tooltips.add(TEXT_PRE + TextFormatting.ITALIC + "[Press Shift] " + compound.getKeySet().size() + " tag(s)");
          }
        }
      }
    } else {
      tooltips.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + "[Press CTRL] for Advanced Data");
    }
  }

  @Override
  public boolean isEnabled() {
    return ConfigCore.FEATURE.ADVANCED_DATA.enable;
  }


}
