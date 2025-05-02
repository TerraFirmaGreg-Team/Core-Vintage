package su.terrafirmagreg.modules.core.feature.advanceddata;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RangeInt;

public final class FeatureAdvancedDataConfig {

  @Comment({
    "If true, advanced data enable ",
    "Default = true"
  })
  public boolean enable = true;

  @Comment({
    "Is the control key needed to show the advanced data?",
    "Default = true"
  })
  public boolean requireCTRL = true;

  @Comment({
    "Number of characters for NBT data",
    "Default = 40"
  })
  @RangeInt(min = 1, max = 500)
  public int charLimitNBT = 40;

  @Comment({
    "Show ItemStack tool classes when advanced tooltips are enabled. (F3+H)",
    "Default = true"
  })
  public boolean showToolClass = true;

  @Comment({
    "Show ItemStack OreDictionary matches when advanced tooltips are enabled. (F3+H)",
    "Default = true"
  })
  public boolean showOreDictionary = true;

  @Comment({
    "Show ItemStack Code Name when advanced tooltips are enabled. (F3+H)",
    "Default = true"
  })
  public boolean showCodeName = true;

  @Comment({
    "Show ItemStack Unlocalized Name when advanced tooltips are enabled. (F3+H)",
    "Default = true"
  })
  public boolean showUnlocalizedName = true;

  @Comment({
    "Show ItemStack Metadata when advanced tooltips are enabled. (F3+H)",
    "Default = true"
  })
  public boolean showMetaData = true;

  @Comment({
    "Show ItemStack Meta's Unlocalized Name when advanced tooltips are enabled. (F3+H)",
    "Default = true"
  })
  public boolean showMetaUnlocalizedName = true;

  @Comment({
    "Show ItemStack NBT on the tooltip when advanced tooltips are enabled. (F3+H)",
    "Default = true"
  })
  public boolean showNBT = true;

}
