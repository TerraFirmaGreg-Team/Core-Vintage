package su.terrafirmagreg.api.util;


import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public final class GroupTabUtils {

  public static void addGroupTab(Block blockIn, List<CreativeTabs> tabs) {
    tabs.forEach(blockIn::setCreativeTab);
  }

  public static void addGroupTab(Item itemIn, List<CreativeTabs> tabs) {
    tabs.forEach(itemIn::setCreativeTab);
  }
}
