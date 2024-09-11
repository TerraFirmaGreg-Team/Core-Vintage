package su.terrafirmagreg.data.lib.json.creator;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map.Entry;

public class JsonCreator {

  private static final String CURRENT_DIRECTORY = new File("").getAbsolutePath() + "/../";
  private static final HashMap<String, String> currentTypeReplaceable = new HashMap<>();
  private static final boolean enable = false;
  private static JsonTemplateType currentType;
  private static String currentFileName;

  public static void set(JsonTemplateType type, String fileName) {
    if (enable) {
      JsonCreator.currentType = type;
      JsonCreator.currentFileName = fileName;
    }
  }

  public static void replace(String regex, String replacement) {
    if (enable) {
      currentTypeReplaceable.put(regex, replacement);
    }
  }

  public static void end() {
    if (enable) {
      Util.write(Util.replace(Util.read(JsonCreator.currentType.getTemplateFileName()),
              currentTypeReplaceable), currentFileName);
      currentTypeReplaceable.clear();
    }
  }

  public static class Util {

    public static String read(String fileName) {
      try {
        InputStream is = JsonCreator.class.getResourceAsStream(fileName);
        if (is != null) {
          String text = IOUtils.toString(is, StandardCharsets.UTF_8);
          is.close();
          return text;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return "Not found file";
    }

    public static void write(String text, String fileName) {
      try {
        OutputStream os = new FileOutputStream(JsonCreator.CURRENT_DIRECTORY + fileName);
        os.write(text.getBytes(), 0, text.length());
        os.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public static String replace(String text, HashMap<String, String> replaceable) {
      for (Entry<String, String> entry : replaceable.entrySet()) {
        text = text.replaceAll(entry.getKey(), entry.getValue());
      }
      return text;
    }

  }

}
