package net.dries007.horsepower.util;

import su.terrafirmagreg.api.util.TranslatorUtil;

public final class Localization {


  public enum INFO {
    GRINDSTONE_INVALID,
    CHOPPING_INVALID,
    PRESS_INVALID,
    ITEM_REVEAL;

    public String translate(Object... vars) {
      return TranslatorUtil.translateString(key(), vars);
    }

    public String key() {
      return "info.horsepower:" + toString().toLowerCase().replaceAll("_", ".");
    }
  }

  public enum GUI {
    CATEGORY_GRINDING,
    CATEGORY_HAND_GRINDING,
    CATEGORY_CHOPPING,
    CATEGORY_MANUAL_CHOPPING,
    CATEGORY_PRESS_ITEM,
    CATEGORY_PRESS_FLUID;

    public String translate(Object... vars) {
      return TranslatorUtil.translateString(key(), vars);
    }

    public String key() {
      return "gui.horsepower.jei." + toString().toLowerCase().replaceAll("_", ".");
    }

    public enum JEI {
      CHOPPING,
      GRINDING,
      GRINDING_CHANCE,
      PRESSING,
      MANUAL_CHOPPING_DESC_1,
      MANUAL_CHOPPING_DESC_2,
      MANUAL_CHOPPING_DESC_3,
      MANUAL_CHOPPING;

      public String translate(Object... vars) {
        return TranslatorUtil.translateString(key(), vars);
      }

      public String key() {
        return "gui.horsepower.jei.tooltip." + toString().toLowerCase().replaceAll("_", ".");
      }

    }
  }

  public enum WAILA {
    GRINDSTONE_PROGRESS,
    WINDUP_PROGRESS,
    CHOPPING_PROGRESS,
    PRESS_PROGRESS,
    SHOW_ITEMS;

    public String translate(Object... vars) {
      return TranslatorUtil.translateString(key(), vars);
    }

    public String key() {
      return "gui.horsepower.waila." + toString().toLowerCase().replaceAll("_", ".");
    }
  }

  public enum TOP {
    GRINDSTONE_PROGRESS,
    WINDUP_PROGRESS,
    CHOPPING_PROGRESS,
    PRESS_PROGRESS;

    public String translate(Object... vars) {
      return TranslatorUtil.translateString(key(), vars);
    }

    public String key() {
      return "gui.horsepower.top." + toString().toLowerCase().replaceAll("_", ".");
    }
  }

  public static class ITEM {

    public enum HORSE_GRINDSTONE {
      SIZE,
      LOCATION,
      USE;

      public String translate(Object... vars) {
        return TranslatorUtil.translateString("item.horsepower:grindstone.description." + toString().toLowerCase(), vars);
      }
    }

    public enum HORSE_CHOPPING {
      SIZE,
      LOCATION,
      USE;

      public String translate(Object... vars) {
        return TranslatorUtil.translateString("item.horsepower:chopping.description." + toString().toLowerCase(), vars);
      }
    }

    public enum HORSE_PRESS {
      SIZE,
      LOCATION,
      USE;

      public String translate(Object... vars) {
        return TranslatorUtil.translateString("item.horsepower:press.description." + toString().toLowerCase(), vars);
      }
    }
  }
}
