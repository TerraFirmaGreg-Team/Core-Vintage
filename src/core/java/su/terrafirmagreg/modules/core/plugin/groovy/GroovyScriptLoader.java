package su.terrafirmagreg.modules.core.plugin.groovy;

import su.terrafirmagreg.modules.core.ModuleCore;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class GroovyScriptLoader {

  private static final String SCRIPTS_FOLDER = "groovy";
  private static final File MODS_DIR = Loader.instance().getConfigDir().getParentFile();
  private static final File GROOVY_SCRIPTS_DIR = new File(MODS_DIR, SCRIPTS_FOLDER);

  public static void init() {

    if (!GROOVY_SCRIPTS_DIR.exists()) {
      if (!GROOVY_SCRIPTS_DIR.mkdirs()) {
        ModuleCore.LOGGER.error("Failed to create groovy scripts directory at: " + GROOVY_SCRIPTS_DIR.getAbsolutePath());
        return;
      }
    }

    try {
      copyScriptsFromJar();
      ModuleCore.LOGGER.info("Successfully loaded Groovy scripts");
    } catch (IOException e) {
      ModuleCore.LOGGER.error("Failed to load Groovy scripts", e);
    }
  }

  private static void copyScriptsFromJar() throws IOException {
    Loader.instance()
      .getActiveModList()
      .stream()
      .filter(mod -> mod.getModId().equals("tfg"))
      .forEach(GroovyScriptLoader::processModScripts);

    logScriptsCopyResult();
  }

  private static void processModScripts(ModContainer mod) {
    File modFile = mod.getSource();
    String modId = mod.getModId();
    ModuleCore.LOGGER.info("Searching for groovy scripts in mod: {}", modId);

    try {
      if (modFile.isFile()) {
        processJarMod(modFile, modId);
      } else if (modFile.isDirectory()) {
        // processDevMod(modFile, modId);
      }
    } catch (Exception e) {
      ModuleCore.LOGGER.error("Failed to process mod: " + modId, e);
    }
  }

  private static void processJarMod(File modFile, String modId) throws IOException {
    try (ZipFile zip = new ZipFile(modFile)) {
      zip.stream()
        .filter(entry -> !entry.isDirectory())
        .filter(entry -> entry.getName().startsWith(SCRIPTS_FOLDER + "/"))
        .forEach(entry -> {
          try {
            copyZipEntry(zip, entry, modId);
          } catch (IOException e) {
            ModuleCore.LOGGER.error("Failed to copy zip entry: " + entry.getName(), e);
          }
        });

      // Check if any files were actually processed
      boolean hasScripts = zip.stream()
        .anyMatch(entry -> !entry.isDirectory() && entry.getName().startsWith(SCRIPTS_FOLDER + "/"));

      if (hasScripts) {
        ModuleCore.LOGGER.info("Found groovy scripts in mod: {}", modId);
      }
    }
  }

  private static void copyZipEntry(ZipFile zip, ZipEntry entry, String modId) throws IOException {
    String relativePath = entry.getName().substring(SCRIPTS_FOLDER.length() + 1);
    File outputFile = new File(GROOVY_SCRIPTS_DIR, relativePath);

    createParentDirs(outputFile);

    try (InputStream in = zip.getInputStream(entry)) {
      Files.copy(in, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
  }

  private static void processDevMod(File modFile, String modId) throws IOException {
    Path sourceDir = modFile.toPath()
      .resolve("src/main/resources")
      .resolve(SCRIPTS_FOLDER);

    if (Files.exists(sourceDir)) {
      ModuleCore.LOGGER.info("Found development groovy scripts in: {}", sourceDir);
      copyDirectoryRecursively(sourceDir, modId);
    }
  }

  private static void copyDirectoryRecursively(Path sourceDir, String modId) throws IOException {
    Path modTargetDir = GROOVY_SCRIPTS_DIR.toPath().resolve(modId);

    Files.walk(sourceDir)
      .filter(Files::isRegularFile)
      .forEach(source -> copyFile(sourceDir, source, modTargetDir));
  }

  private static void createParentDirs(File file) {
    File parent = file.getParentFile();
    if (parent != null) {
      parent.mkdirs();
    }
  }

  private static void copyFile(Path sourceDir, Path source, Path targetDir) {
    try {
      Path relative = sourceDir.relativize(source);
      Path target = targetDir.resolve(relative);
      Files.createDirectories(target.getParent());
      Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      ModuleCore.LOGGER.error("Failed to copy groovy script: " + source, e);
    }
  }

  private static void logScriptsCopyResult() {
    if (!GROOVY_SCRIPTS_DIR.exists() || !GROOVY_SCRIPTS_DIR.isDirectory()) {
      ModuleCore.LOGGER.warn("Groovy scripts directory not found");
      return;
    }

    String[] files = GROOVY_SCRIPTS_DIR.list();
    if (files == null || files.length == 0) {
      ModuleCore.LOGGER.warn("No groovy scripts were found in any mod");
    } else {
      ModuleCore.LOGGER.info("Successfully copied {} groovy scripts", files.length);
    }
  }


}
