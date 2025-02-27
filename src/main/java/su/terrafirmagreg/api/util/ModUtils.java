package su.terrafirmagreg.api.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLContainer;
import net.minecraftforge.fml.common.InjectedModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Objects;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;

@UtilityClass
@SuppressWarnings("unused")
public final class ModUtils {

  /**
   * This cache is used by {@link #getSortedEntries(IForgeRegistry)} to improve repeat performance of the method. Calling {@link #getSortedEntries(IForgeRegistry)} before all entries have been registered will lock out new ones from being
   * found.
   */
  private static final Map<IForgeRegistry<?>, Multimap<String, ?>> REGISTRY_CACHE = new Object2ObjectOpenHashMap<>();

  /**
   * Возвращает идентификатор ресурса на основе строки.
   *
   * @param name строка идентификатора ресурса
   * @return идентификатор ресурса
   */
  public static ResourceLocation resource(String name) {
    return new ResourceLocation(MOD_ID, name);
  }

  public static ResourceLocation resource(String key, String name) {
    return new ResourceLocation(key, name);
  }

  public static ResourceLocation resource(String key, String name, String subname) {
    return new ResourceLocation(key, regKey(name, subname));
  }

  public static String id(String name) {
    return String.format("%s:%s", MOD_ID, name);
  }

  public static String id(String key, String name) {
    return String.format("%s:%s", key, name);
  }

  public static String id(String key, String name, String subname) {
    return String.format("%s:%s.%s", key, name, subname);
  }

  public static String name(String name) {
    return String.format("%s [ %s ]", MOD_NAME, name);
  }

  public static String name(String key, String name) {
    return String.format("%s [ %s ]", key, name);
  }

  public static String localize(String key) {
    return String.format("%s.%s", key, MOD_ID).toLowerCase().replace("/", ".");
  }

  public static String localize(String key, String name) {
    return String.format("%s.%s", ModUtils.localize(key), name).toLowerCase().replace("/", ".");
  }

  public static String localize(String key, ResourceLocation resourceLocation) {
    return String.format("%s.%s", key, localize(resourceLocation)).toLowerCase().replace("/", ".");
  }

  public static String localize(ResourceLocation resourceLocation) {
    return resourceLocation.toString().toLowerCase().replace("/", ".").replace(":", ".");
  }

  public static String localize(String key, String name, String subname) {
    return String.format("%s.%s.%s", key, name, subname).toLowerCase().replace("/", ".");
  }

  public static String regKey(String key, String name) {
    return String.format("%s/%s", key, name);
  }

  /**
   * This is meant to avoid Intellij's warnings about null fields that are injected to at runtime Use this for things like @ObjectHolder,
   *
   * @param <T> anything and everything
   * @return null, but not null
   * @CapabilityInject etc. AKA - The @Nullable is intentional. If it crashes your dev env, then fix your dev env, not this. :)
   */
  @NotNull
  @SuppressWarnings("ConstantConditions")
  public static <T> T getNull() {
    return null;
  }

  public static boolean isModLoaded(String modName) {
    return Loader.isModLoaded(modName);
  }

  /**
   * Gets the name of a mod that registered the passed object. Has support for a wide range of registerable objects such as blocks, items, enchantments, potions, sounds, villagers, biomes, and so on.
   *
   * @param registerable The registerable object. Accepts anything that extends IForgeRegistryEntry.Impl. Current list includes BiomeGenBase, Block, Enchantment, Item, Potion, PotionType, SoundEvent and VillagerProfession.
   * @return String The name of the mod that registered the object.
   */
  public static String getModName(IForgeRegistryEntry.Impl<?> registerable) {

    final String modID = Objects.requireNonNull(registerable.getRegistryName()).getNamespace();

    final ModContainer mod = getModContainer(modID);
    return mod != null ? mod.getName() : "minecraft".equals(modID) ? "Minecraft" : "Unknown";
  }

  /**
   * Gets a mod container by it's ID.
   *
   * @param modID The ID of the mod to grab.
   * @return The ModContainer using that ID.
   */
  public static ModContainer getModContainer(String modID) {

    return Loader.instance().getIndexedModList().get(modID);
  }

  public static IThreadListener getThreadListener(Side side) {
    return side.isClient()
           ? GameUtils.getMinecraft()
           : FMLCommonHandler.instance().getMinecraftServerInstance();
  }

  public static void queueTask(Side side, Runnable task) {
    final IThreadListener target = ModUtils.getThreadListener(side);
    addScheduledTask(target, task);
  }

  public static void addScheduledTask(IThreadListener target, Runnable task) {
    if (target != null) {
      target.addScheduledTask(task);
    }
  }


  /**
   * Gets the name of a mod that registered the entity. Due to Entity not using IForgeRegistryEntry.Impl a special method is required.
   *
   * @param entity The entity to get the mod name for.
   * @return String The name of the mod that registered the entity.
   */
  public static String getModName(Entity entity) {

    if (entity == null) {
      return "Unknown";
    }

    final EntityRegistry.EntityRegistration reg = getRegistryInfo(entity);

    if (reg != null) {

      final ModContainer mod = reg.getContainer();

      if (mod != null) {
        return mod.getName();
      }

      return "Unknown";
    }

    return "Minecraft";
  }


  /**
   * Gets registry info for an entity.
   *
   * @param entity The entity to get registry info of.
   * @return The entities registry info. Can be null.
   */
  public static EntityRegistry.EntityRegistration getRegistryInfo(Entity entity) {

    return getRegistryInfo(entity.getClass());
  }

  /**
   * Gets registry info for an entity, from it's class.
   *
   * @param entity The class to look for.
   * @return The entities registry info. Can be null.
   */
  public static EntityRegistry.EntityRegistration getRegistryInfo(Class<? extends Entity> entity) {

    return EntityRegistry.instance().lookupModSpawn(entity, false);
  }

  /**
   * Gets the name of a mod from it's ID.
   *
   * @param modId The mod to look up.
   * @return The name of the mod.
   */
  public static String getModName(String modId) {

    final ModContainer mod = getModContainer(modId);
    return mod != null ? mod.getName() : modId;
  }

  /**
   * Searches through the array of CreativeTabs and finds the first tab with the same label as the one passed.
   *
   * @param label: The label of the tab you are looking for.
   * @return CreativeTabs: A CreativeTabs with the same label as the one passed. If this is not found, you will get null.
   */
  @SideOnly(Side.CLIENT)
  public static @Nullable CreativeTabs getTabFromLabel(String label) {

    for (final CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
      if (tab.getTabLabel().equalsIgnoreCase(label)) {
        return tab;
      }
    }

    return null;
  }

  /**
   * Creates a ResourceLocation for a string, using the active mod container as the owner of the ID.
   *
   * @param id The id for the specific entry.
   * @return A ResourceLocation for the entry.
   */
  public static ResourceLocation getIdForCurrentContainer(String id) {

    final int index = id.lastIndexOf(':');
    final String entryName = index == -1 ? id : id.substring(index + 1);
    final ModContainer mod = Loader.instance().activeModContainer();
    final String prefix = mod == null
                          || mod instanceof InjectedModContainer injectedModContainer
                             && injectedModContainer.wrappedContainer instanceof FMLContainer
                          ? "minecraft"
                          : mod.getModId().toLowerCase();

    return new ResourceLocation(prefix, entryName);
  }

  /**
   * Creates a sorted version of a ForgeRegistry. This will only contain entries that were present at the time of calling it. Entries are sorted by their owning modid.
   *
   * @param registry The registry to sort.
   * @return A map of all entries sorted by the owning mod id.
   */
  @SuppressWarnings("unchecked")
  public static <T extends IForgeRegistryEntry<T>> Multimap<String, T> getSortedEntries(IForgeRegistry<T> registry) {

    if (REGISTRY_CACHE.containsKey(registry)) {

      return (Multimap<String, T>) REGISTRY_CACHE.get(registry);
    }

    final Multimap<String, T> map = ArrayListMultimap.create();

    for (final T entry : registry.getValuesCollection()) {

      if (entry.getRegistryName() != null) {

        map.put(entry.getRegistryName().getNamespace(), entry);
      }
    }

    REGISTRY_CACHE.put(registry, map);
    return map;
  }

  /**
   * Gets a ResourceLocation where the domain/modid is pulled from the active mod id.
   *
   * @param name The name of the id to create.
   * @return The ResourceLocation with the active mod as the domain/id.
   */
  public static ResourceLocation getIdForActiveMod(String name) {

    if (!name.contains(":")) {

      final ModContainer container = Loader.instance().activeModContainer();

      if (container != null) {

        return new ResourceLocation(container.getModId(), name);
      }
    }

    return new ResourceLocation(name);
  }

  public static boolean isValidIdentifier(ResourceLocation identifier) {
    return isValidIdentifier(identifier.toString());
  }

  /**
   * Checks whether given identifier contains illegal characters
   *
   * @param identifier identifier to be checked
   * @return {@code true} if the identifier is valid
   */
  public static boolean isValidIdentifier(String identifier) {
    Objects.requireNonNull(identifier);

    String[] parts = identifier.split(":");
    boolean isValid = (parts.length == 1 && isValidPath(parts[0])) || (parts.length == 2 && isValidIdentifier(parts[0], parts[1]));
    if (!isValid) {
      throw new IllegalArgumentException("Identifier '" + identifier + "' must only contain characters [a-z0-9_./-]!");
    }
    return isValid;
  }

  /**
   * Checks whether the given path contains illegal characters
   *
   * @param path identifier path to be checked
   * @return {@code true} if the path is valid
   */
  public static boolean isValidPath(String path) {
    Objects.requireNonNull(path);

    boolean isValid = path.length() > 0 && path.matches("[a-z0-9_./-]*");
    if (!isValid) {
      throw new IllegalArgumentException("Path '" + path + "' must only contain characters [a-z0-9_./-]!");
    }
    return isValid;
  }

  /**
   * Checks whether given identifier contains illegal characters
   *
   * @param namespace identifier namespace to be checked
   * @param path      identifier path to be checked
   * @return {@code true} if the identifier is valid
   */
  public static boolean isValidIdentifier(String namespace, String path) {
    return isValidNamespace(namespace) && isValidPath(path);
  }

  /**
   * Checks whether the given namespace contains illegal characters
   *
   * @param namespace namespace to be checked
   * @return {@code true} if the namespace is valid
   */
  public static boolean isValidNamespace(String namespace) {
    Objects.requireNonNull(namespace);

    boolean isValid = namespace.length() > 0 && namespace.matches("[a-z0-9_.-]*");
    if (!isValid) {
      throw new IllegalArgumentException("Namespace '" + namespace + "' must only contain characters [a-z0-9_./-]!");
    }
    return isValid;
  }

  /**
   * Checks if the game is running on the client or not.
   *
   * @return Whether or not the current thread is client sided.
   */
  public static boolean isClient() {
    return FMLCommonHandler.instance().getSide().isClient();
  }

  public static boolean isClient(Side side) {
    return Side.CLIENT == side;
  }

  public static boolean isServer() {
    return FMLCommonHandler.instance().getSide().isServer();
  }

  public static boolean isServer(Side side) {
    return Side.SERVER == side;
  }

  /**
   * Is this a development environment?
   *
   * @return boolean showing if the game is currently in dev
   */
  public static boolean isDevEnv() {
    return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
  }
}
