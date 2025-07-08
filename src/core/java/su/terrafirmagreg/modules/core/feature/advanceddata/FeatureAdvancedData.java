package su.terrafirmagreg.modules.core.feature.advanceddata;

import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
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
import java.util.Objects;
import java.util.Set;

public class FeatureAdvancedData extends BaseFeature {

  private static final String TEXT_PRE = TextFormatting.DARK_GRAY + "     "; //§8
  private static final String HEADER_PRE = TextFormatting.GRAY + "  -"; //§7

  public FeatureAdvancedData() {
    super(Settings.of()
      .name("Advanced Data")
      .enabled(ConfigCore.FEATURE.ADVANCED_DATA.enable));
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  @SideOnly(Side.CLIENT)
  public static void onItemTooltipEvent(ItemTooltipEvent event) {

    var stack = event.getItemStack();
    var tooltip = event.getToolTip();
    var isAdvanced = event.getFlags().isAdvanced();

    if (!StackUtils.isValid(stack)) {
      return;
    }

    if (!ConfigCore.FEATURE.ADVANCED_DATA.requireCTRL || GuiScreen.isCtrlKeyDown()) {
      tooltip.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + "Advanced Data:");

      if (ConfigCore.FEATURE.ADVANCED_DATA.showOreDictionary) {
        oreDictNames(tooltip, stack);
      }

      if (ConfigCore.FEATURE.ADVANCED_DATA.showToolClass) {
        toolClass(tooltip, stack);
      }

      if (ConfigCore.FEATURE.ADVANCED_DATA.showCodeName) {
        codeName(tooltip, stack);
      }

      if (ConfigCore.FEATURE.ADVANCED_DATA.showUnlocalizedName) {
        unlocalizedName(tooltip, stack);
      }

      // Metadata
      if (ConfigCore.FEATURE.ADVANCED_DATA.showMetaData) {
        metadata(tooltip, stack);
      }

      // Meta's Unlocalized Name
      if (ConfigCore.FEATURE.ADVANCED_DATA.showMetaUnlocalizedName) {
        metaUnlocalizedName(tooltip, stack);
      }

      //NBT
      if (ConfigCore.FEATURE.ADVANCED_DATA.showNBT) {
        nbt(tooltip, stack);
      }
    } else {
      tooltip.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + "[Press CTRL] for Advanced Data");
    }

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

  private static void oreDictNames(List<String> tooltip, ItemStack stack) {
    Set<String> oreNames = OreDictUtils.getOreNames(stack);
    if (!oreNames.isEmpty()) {
      tooltip.add(HEADER_PRE + "Ore Dictionary Names:");
      for (String oreName : oreNames) {
        tooltip.add(TEXT_PRE + oreName);
      }
    }
  }

  private static void toolClass(List<String> tooltip, ItemStack stack) {
    Item item = stack.getItem();
    Set<String> toolClasses = item.getToolClasses(stack);

    if (!toolClasses.isEmpty()) {
      tooltip.add(HEADER_PRE + "Tool Classes:");
      for (String toolClass : toolClasses) {
        int harvestLevel = item.getHarvestLevel(stack, toolClass, null, null);
        tooltip.add(TEXT_PRE + toolClass + " (" + harvestLevel + ")");
      }
    }
  }

  private static void codeName(List<String> tooltip, ItemStack stack) {
    Item item = stack.getItem();
    String registryName = Objects.requireNonNull(item.getRegistryName()).toString();

    tooltip.add(HEADER_PRE + "Code Name:");
    tooltip.add(TEXT_PRE + registryName);
  }

  private static void unlocalizedName(List<String> tooltip, ItemStack stack) {
    Item item = stack.getItem();
    String translationKey = item.getTranslationKey();
    tooltip.add(HEADER_PRE + "Item's Unlocalized Name:");
    tooltip.add(TEXT_PRE + translationKey);
  }

  private static void metadata(List<String> tooltip, ItemStack stack) {
    int itemDamage = stack.getItemDamage();
    int maxDamage = stack.getMaxDamage();

    tooltip.add(HEADER_PRE + "Metadata:");
    tooltip.add(TEXT_PRE + itemDamage + (maxDamage > 0 ? "/" + maxDamage : ""));
  }

  private static void metaUnlocalizedName(List<String> tooltip, ItemStack stack) {
    Item item = stack.getItem();
    String translationKey = item.getTranslationKey();
    String metaName = item.getTranslationKey(stack);

    if (!metaName.equals(translationKey)) {
      tooltip.add(HEADER_PRE + "Meta's Unlocalized Name:");
      tooltip.add(TEXT_PRE + metaName);
    }
  }

  private static void nbt(List<String> tooltip, ItemStack stack) {
    NBTTagCompound compound = stack.getTagCompound();
    if (compound != null && !compound.isEmpty()) {
      tooltip.add(HEADER_PRE + "NBT:");
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
        tooltip.add(TEXT_PRE + compoundDisplay);
      } else {
        tooltip.add(TEXT_PRE + TextFormatting.ITALIC + "[Press Shift] " + compound.getKeySet().size() + " tag(s)");
      }
    }
  }


}
