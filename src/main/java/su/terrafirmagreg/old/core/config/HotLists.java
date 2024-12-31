package su.terrafirmagreg.old.core.config;

import net.minecraft.item.ItemStack;

public class HotLists {

  public static boolean isRemoved(ItemStack stack) {
    String regName = stack.getItem().getRegistryName().toString();
    for (String s : TFGConfig.General.ITEM_REMOVALS) {
      if (regName.equals(s)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isHot(ItemStack stack) {
    String regName = stack.getItem().getRegistryName().toString();
    for (String s : TFGConfig.General.HOT_ITEM_ADDITIONS) {
      if (regName.equals(s)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isCold(ItemStack stack) {
    String regName = stack.getItem().getRegistryName().toString();
    for (String s : TFGConfig.General.COLD_ITEM_ADDITIONS) {
      if (regName.equals(s)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isGaseous(ItemStack stack) {
    String regName = stack.getItem().getRegistryName().toString();
    for (String s : TFGConfig.General.GASEOUS_ITEM_ADDITIONS) {
      if (regName.equals(s)) {
        return true;
      }
    }
    return false;
  }
}
