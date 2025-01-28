package su.terrafirmagreg.framework.generator.spi;

import su.terrafirmagreg.api.data.enums.ResourceType;
import su.terrafirmagreg.api.library.Pair;
import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.generator.api.aggregator.ResourceAggregator;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.SimpleResource;

import com.google.common.hash.Funnels;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ResourceCache {

  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

  static ResourceCache create(Path outputDirectory, Path manualDirectory) {
    return new Impl(outputDirectory, manualDirectory);
  }

  /**
   * Checks whether a resource exists. The resource may be either a generated file, or a file from a loaded resource pack.
   *
   * @param resourceType whether the resource is part of the server data or the client assets
   * @param namespace    the namespace of the resource
   * @param directory    name of the directory within the namespace
   * @param fileName     name of the file
   * @param extension    the file's extension
   */
  public abstract boolean doesResourceExist(ResourceType resourceType, String namespace, String directory, String fileName, String extension);

  /**
   * Tracks the given location as if a file has been saved there. Specifically, this means
   * {@link #doesResourceExist(ResourceType, String, String, String, String)} will return {@code true} for the given location. A resource should later be saved
   * for the given location.
   *
   * @param resourceType whether the resource is part of the server data or the client assets
   * @param namespace    the namespace of the resource
   * @param directory    name of the directory within the namespace
   * @param fileName     name of the file
   * @param extension    the file's extension
   */
  public abstract void trackToBeGeneratedResource(ResourceType resourceType, String namespace, String directory, String fileName, String extension);

  /**
   * Saves the given data in the appropriate location. Also checks if a file is already present to avoid redundant writes.
   *
   * @param resourceType whether the given data is part of the server data or the client assets
   * @param namespace    the namespace which the data should be saved under
   * @param directory    name of the directory within the namespace
   * @param fileName     name of the file
   * @param extension    extension of the file
   */
  public abstract void saveResource(ResourceType resourceType, byte[] data, String namespace, String directory, String fileName, String extension);

  /**
   * Saves the given data in the appropriate location. Also checks if a file is already present to avoid redundant writes.
   *
   * @param resourceType whether the given data is part of the server data or the client assets
   * @param aggregator   aggregator used when multiple generator write to the same file location
   * @param namespace    the namespace which the data should be saved under
   * @param directory    name of the directory within the namespace
   * @param fileName     name of the file
   * @param extension    extension of the file
   */
  public abstract <T> void saveResource(ResourceType resourceType, ResourceAggregator<?, T> aggregator, T data, String namespace, String directory, String fileName, String extension);

  /**
   * Saves the given data in the appropriate location. Also checks if a file is already present to avoid redundant writes.
   *
   * @param resourceType whether the given data is part of the server data or the client assets
   * @param json         the data to be saved
   * @param namespace    the namespace which the data should be saved under
   * @param directory    name of the directory within the namespace
   * @param fileName     name of the file
   */
  public void saveJsonResource(ResourceType resourceType, JsonObject json, String namespace, String directory, String fileName) {
    byte[] bytes = GSON.toJson(json).getBytes(StandardCharsets.UTF_8);
    this.saveResource(resourceType, bytes, namespace, directory, fileName, fileName.endsWith(".json") ? "" : ".json");
  }

  /**
   * Opens an input stream for the requested resource.
   *
   * @param resourceType whether the resource is part of the server data or the client assets
   * @param namespace    the namespace of the resource
   * @param directory    name of the directory within the namespace
   * @param fileName     name of the file
   * @param extension    the file's extension
   * @return an input stream for the requested resource, or an empty optional if the resource does not exist
   */
  public abstract Optional<InputStream> getExistingResource(ResourceType resourceType, String namespace, String directory, String fileName, String extension);

  @SuppressWarnings("UnstableApiUsage")
  static class Impl extends ResourceCache {

    private final Map<Path, HashCode> presentFiles = new Object2ObjectOpenHashMap<>();
    private final Map<Path, HashCode> writtenFiles = new Object2ObjectOpenHashMap<>();
    private final Map<Path, Pair<ResourceAggregator<Object, Object>, Object>> aggregatedResources = new Object2ObjectOpenHashMap<>();
    private final Set<Path> toBeGenerated = new HashSet<>();

    private final Path outputDirectory;
    private final Path manualDirectory;
    private final File cacheFile;
    private boolean allowWrites = true;

    private Impl(Path outputFolder, Path manualFolder) {
      if (outputFolder == null) {throw new IllegalArgumentException("Output directory must not be null!");}
      this.outputDirectory = outputFolder;
      this.manualDirectory = manualFolder;
      this.cacheFile = outputFolder.resolve(".cache/cache").toFile();

      this.loadCacheFromFile();
    }

    private void loadCacheFromFile() {
      if (!this.cacheFile.exists()) {return;}

      // Read cache
      try (BufferedReader reader = new BufferedReader(new FileReader(this.cacheFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
          String[] parts = line.split(" ");
          if (parts.length > 2) {continue;}
          this.presentFiles.put(Paths.get(parts[0]), HashCode.fromString(parts[0]));
        }
      } catch (IOException e) {
        throw new RuntimeException("Encountered an exception whilst trying to read the generator cache file!", e);
      }

      // Look for files which are not in the cache
      try (Stream<Path> paths = Files.walk(this.outputDirectory)) {
        paths
          .filter(path -> !this.presentFiles.containsKey(this.outputDirectory.relativize(path)))
          .filter(path -> path.toFile().exists() && path.toFile().isFile() && !"cache".equals(path.toFile().getName()))
          .forEach(
            path -> {
              // Calculate hash
              HashCode hashCode;
              try (InputStream inputStream = Files.newInputStream(path.toFile().toPath())) {
                Hasher hasher = Hashing.sha1().newHasher();
                ByteStreams.copy(inputStream, Funnels.asOutputStream(hasher));
                hashCode = hasher.hash();
              } catch (IOException e) {
                throw new RuntimeException(e);
              }

              // Put the file into the cache
              this.presentFiles.put(this.outputDirectory.relativize(path), hashCode);
            }
          );
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    void writeCacheToFile() {
      if (!this.cacheFile.getParentFile().exists()) {this.cacheFile.getParentFile().mkdirs();}

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.cacheFile))) {
        for (Map.Entry<Path, HashCode> entry : this.writtenFiles.entrySet()) {
          writer.write(entry.getValue().toString());
          writer.write(' ');
          writer.write(entry.getKey().toString());
          writer.newLine();
        }
      } catch (IOException e) {
        throw new RuntimeException("Encountered an exception whilst trying to write the generator cache file!", e);
      }
    }

    void removeRemnants() {
      // Remove remaining files
      this.presentFiles.keySet()
        .stream()
        .filter(((Predicate<Path>) this.writtenFiles::containsKey).negate())
        .map(path -> this.outputDirectory.resolve(path).toFile())
        .filter(File::exists)
        .forEach(File::delete);

      // Remove empty folders
      List<Path> pathList = new ArrayList<>();
      try (Stream<Path> paths = Files.walk(this.outputDirectory)) {
        paths.filter(Files::isDirectory).forEach(pathList::add);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      pathList.sort((a, b) -> Integer.compare(b.toString().length(), a.toString().length()));
      pathList.stream()
        .filter(Files::isDirectory)
        .filter(
          path -> {
            try (DirectoryStream<Path> files = Files.newDirectoryStream(path)) {
              return !files.iterator().hasNext();
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }
        )
        .map(Path::toFile)
        .forEach(File::delete);
    }

    private boolean existsInGeneratedFiles(Path path) {
      return this.toBeGenerated.contains(path) || this.writtenFiles.containsKey(path) || this.aggregatedResources.containsKey(path);
    }

    private boolean existsInManualFiles(Path path) {
      return this.manualDirectory != null && Files.exists(this.manualDirectory.resolve(path));
    }

    private boolean existsInLoadedResources(ResourceType resourceType, String namespace, String directory, String fileName, String extension) {
      IResourceManager resourceManager = GameUtils.getMinecraft().getResourceManager();
      try (IResource resource = resourceManager.getResource(ModUtils.resource(namespace, directory + "/" + fileName + extension))) {
        return true;
      } catch (FileNotFoundException ignored) {
        return false;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    private Path constructPath(ResourceType resourceType, String namespace, String directory, String fileName, String extension) {
      if (!extension.isEmpty() && extension.charAt(0) != '.') {extension = '.' + extension;}
      return Paths.get(resourceType.getDirectoryName(), namespace, directory, fileName + extension);
    }

    @Override
    public boolean doesResourceExist(ResourceType resourceType, String namespace, String directory, String fileName, String extension) {
      Path path = this.constructPath(resourceType, namespace, directory, fileName, extension);
      return this.existsInGeneratedFiles(path)
             || this.existsInManualFiles(path)
             || this.existsInLoadedResources(resourceType, namespace, directory, fileName, extension);
    }

    @Override
    public void trackToBeGeneratedResource(ResourceType resourceType, String namespace, String directory, String fileName, String extension) {
      this.toBeGenerated.add(this.constructPath(resourceType, namespace, directory, fileName, extension));
    }

    @Override
    public Optional<InputStream> getExistingResource(ResourceType resourceType, String namespace, String directory, String fileName, String extension) {
      // Check manual files
      Path path = this.constructPath(resourceType, namespace, directory, fileName, extension);
      if (Files.exists(this.manualDirectory.resolve(path))) {
        try {
          return Optional.of(Files.newInputStream(this.manualDirectory.resolve(path)));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }

      // Check resource packs
      IResourceManager resourceManager = GameUtils.getMinecraft().getResourceManager();
      try {
        IResource resource = resourceManager.getResource(ModUtils.resource(namespace, directory + "/" + fileName + extension));
        if (resource instanceof SimpleResource && resource.hasMetadata()) // This should cover most cases and since it's just for datagen, meh
        {((SimpleResource) resource).mcmetaInputStream.close();}
        return Optional.of(resource.getInputStream());
      } catch (FileNotFoundException ignored) {
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return Optional.empty();
    }

    @Override
    public void saveResource(ResourceType resourceType, byte[] data, String namespace, String directory, String fileName, String extension) {
      if (!this.allowWrites) {throw new RuntimeException("Resources cannot be saved during this stage!");}

      Path path = this.constructPath(resourceType, namespace, directory, fileName, extension);
      if (this.writtenFiles.containsKey(path) || this.aggregatedResources.containsKey(path)) {throw new RuntimeException("Duplicate file '" + path + "'!");}
      if (this.existsInManualFiles(path)) {throw new RuntimeException("File '" + path + "' clashes with a manually created file!");}

      // Skip writing if the present file matches the one to be written
      Path fullPath = this.outputDirectory.resolve(path);
      HashCode hashCode = Hashing.sha1().hashBytes(data);
      if (this.presentFiles.containsKey(path) && this.presentFiles.get(path).equals(hashCode) && fullPath.toFile().exists()) {
        this.writtenFiles.put(path, hashCode);
        this.toBeGenerated.remove(path);
        return;
      }

      // Write the data to file
      fullPath.toFile().getParentFile().mkdirs();
      try (OutputStream outputStream = Files.newOutputStream(fullPath)) {
        outputStream.write(data);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      this.writtenFiles.put(path, hashCode);
      this.toBeGenerated.remove(path);
    }

    @Override
    public <T> void saveResource(ResourceType resourceType, ResourceAggregator<?, T> aggregator, T data, String namespace, String directory, String fileName, String extension) {
      if (!this.allowWrites) {throw new RuntimeException("Resources cannot be saved during this stage!");}

      Path path = this.constructPath(resourceType, namespace, directory, fileName, extension);
      if (this.writtenFiles.containsKey(path)) {throw new RuntimeException("Duplicate file '" + path + "'!");}
      if (this.existsInManualFiles(path)) {throw new RuntimeException("File '" + path + "' clashes with a manually created file!");}

      // Validate the aggregators match
      Pair<ResourceAggregator<Object, Object>, Object> oldEntry = this.aggregatedResources.get(path);
      if (oldEntry != null && oldEntry.left() != aggregator) {
        throw new RuntimeException(
          "Incompatible aggregators for file '" + path + "': '" + oldEntry.left().getClass() + "' and '" + aggregator.getClass() + "'!");
      }

      // Combine the old with the new data
      Object oldData = oldEntry == null ? aggregator.initialData() : oldEntry.right();
      try {
        //noinspection unchecked
        oldData = ((ResourceAggregator<Object, Object>) aggregator).combine(oldData, data);
      } catch (Exception e) {
        throw new RuntimeException("Failed to combine data for file '" + path + "'!", e);
      }
      //noinspection unchecked
      this.aggregatedResources.put(path, Pair.of((ResourceAggregator<Object, Object>) aggregator, oldData));
    }

    public void allowWrites(boolean allow) {
      this.allowWrites = allow;
    }

    public void finish() {
      // Write all aggregated resources
      this.aggregatedResources.forEach((path, pair) -> {
        // Convert the data to bytes
        ResourceAggregator<Object, Object> aggregator = pair.left();
        Object data = pair.right();
        byte[] bytes;
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
          aggregator.write(stream, data);
          bytes = stream.toByteArray();
        } catch (Exception e) {
          throw new RuntimeException(e);
        }

        // Skip writing if the present file matches the one to be written
        Path fullPath = this.outputDirectory.resolve(path);
        HashCode hashCode = Hashing.sha1().hashBytes(bytes);
        if (this.presentFiles.containsKey(path) && this.presentFiles.get(path).equals(hashCode) && fullPath.toFile().exists()) {
          this.writtenFiles.put(path, hashCode);
          this.toBeGenerated.remove(path);
          return;
        }

        // Write the data to file
        fullPath.toFile().getParentFile().mkdirs();
        try (OutputStream outputStream = Files.newOutputStream(fullPath)) {
          outputStream.write(bytes);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        this.writtenFiles.put(path, hashCode);
        this.toBeGenerated.remove(path);
      });

      // Write the cache file
      this.removeRemnants();
      this.writeCacheToFile();

      // Validate all promised files have actually been written
      if (!this.toBeGenerated.isEmpty()) {
        throw new RuntimeException("Some tracked files did not get written: " + this.toBeGenerated.stream()
          .map(Path::toString)
          .map(s -> "'" + s + "'")
          .collect(Collectors.joining(",")));
      }
    }
  }
}
