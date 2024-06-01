package su.terrafirmagreg.api.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;


import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
@SuppressWarnings("unused")
public final class TranslatorUtil {

    public static String translate(String key) {
        if (GameUtils.isClient()) {
            return I18n.format(key);
        } else {
            return key;
        }
    }

    /**
     * Simple wrapper for I18n, for consistency
     */
    public static String resolveKey(String s, Object... args) {
        return I18n.format(s, args);
    }

    /**
     * Wrapper for I18n but cuts up the result using line break character ($)
     */
    public static String[] resolveKeyArray(String s, Object... args) {
        return resolveKey(s, args).split("\\$");
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
}
