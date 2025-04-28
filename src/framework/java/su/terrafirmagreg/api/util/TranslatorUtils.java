package su.terrafirmagreg.api.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.registries.IForgeRegistryEntry;

import com.google.common.base.Joiner;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
@SuppressWarnings({"unused", "deprecation"})
public final class TranslatorUtils {

  private static final Joiner JOINER_DOT = Joiner.on('.');

  public static String translate(String key) {
    if (ModUtils.isClient()) {
      return I18n.format(key);
    } else {
      return key;
    }
  }

  public static String translate(String key, Object... args) {
    if (ModUtils.isClient()) {
      return I18n.format(key, args);
    } else {
      return key;
    }
  }

  public static String getEnumName(Enum<?> anEnum) {
    return ModUtils.localize(ModUtils.localize("enum"), anEnum.getDeclaringClass().getSimpleName(), anEnum.name());
  }

  public static String getTypeName(IForgeRegistryEntry<?> type) {
    //noinspection ConstantConditions
    return ModUtils.localize(ModUtils.localize("types"), type.getRegistryType().getSimpleName(), type.getRegistryName().getPath());
  }

  /**
   * Wrapper for I18n but cuts up the result using line break character ($)
   */
  public static String[] resolveKeyArray(String s, Object... args) {
    return resolveKey(s, args).split("\\$");
  }

  /**
   * Simple wrapper for I18n, for consistency
   */
  public static String resolveKey(String s, Object... args) {
    return I18n.format(s, args);
  }

  /**
   * The same as autoBreak, but it also respects break character ($) for manual line breaking in addition to the automatic ones
   */
  public static List<String> autoBreakWithParagraphs(FontRenderer fontRenderer, String text, int width) {

    String[] paragraphs = text.split("\\$");
    List<String> lines = new ArrayList<>();

    for (String paragraph : paragraphs) {
      lines.addAll(autoBreak(fontRenderer, paragraph, width));
    }

    return lines;
  }

  /**
   * Turns one string into a list of strings, cutting sentences up to fit within the defined width if they were rendered in a GUI
   */
  public static List<String> autoBreak(FontRenderer fontRenderer, String text, int width) {

    List<String> lines = new ArrayList<>();
    //split the text by all spaces
    String[] words = text.split(" ");

    //add the first word to the first line, no matter what
    lines.add(words[0]);
    //starting indent is the width of the first word
    int indent = fontRenderer.getStringWidth(words[0]);

    for (int w = 1; w < words.length; w++) {

      //increment the indent by the width of the next word + leading space
      indent += fontRenderer.getStringWidth(" " + words[w]);

      //if the indent is within bounds
      if (indent <= width) {
        //add the next word to the last line (i.e. the one in question)
        String last = lines.get(lines.size() - 1);
        lines.set(lines.size() - 1, last += (" " + words[w]));
      } else {
        //otherwise, start a new line and reset the indent
        lines.add(words[w]);
        indent = fontRenderer.getStringWidth(words[w]);
      }
    }

    return lines;
  }

  public static String translateString(String key, Object... vars) {
    String result = translateToLocal(key);

    for (int i = 0; i < vars.length; i++) {
      String optionCheck = "[%" + (i + 1) + "->";
      int pos = result.indexOf(optionCheck);

      if (pos != -1) {
        int endPos = result.indexOf("]");
        if (endPos != -1) {
          String[] options = result.substring(pos + optionCheck.length(), endPos).split("\\|");
          int pickedOption = ((boolean) vars[i]) ? 1 : 0;
          if (options.length > pickedOption) {
            String opt = options[pickedOption];
            result = result.substring(0, pos) + opt + result.substring(endPos + 1);

            i--;
          }
        }
      } else {
        result = result.replace("[%" + (i + 1) + "]", String.valueOf(vars[i]));
      }
    }

    return result;
  }

  public static String translateToLocal(String key) {
    if (net.minecraft.util.text.translation.I18n.canTranslate(key)) {
      return net.minecraft.util.text.translation.I18n.translateToLocal(key);
    } else {
      return net.minecraft.util.text.translation.I18n.translateToFallback(key);
    }
  }

  public static TextComponentString formatMessage(String unformattedText) {
    return new TextComponentString(formattedString(unformattedText));
  }

  public static String formattedString(String unformattedString) {
    return unformattedString.replace("&", "\u00a7");
  }
}
