package su.terrafirmagreg.api.library;

import su.terrafirmagreg.framework.manager.content.api.IContentEntry;
import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A holder for registry entries that may be created later. Similar to NeoForge's DeferredHolder but for Minecraft 1.12.2.
 *
 * @param <T> The type of the registry entry
 */
public class DeferredHolder<T extends IForgeRegistryEntry<T>> implements Supplier<T> {

  protected final IForgeRegistry<T> registry;
  protected final ResourceLocation id;
  protected T value;
  protected boolean bound = false;

  private DeferredHolder(IForgeRegistry<T> registry, ResourceLocation id) {
    this.registry = Objects.requireNonNull(registry, "Registry cannot be null");
    this.id = Objects.requireNonNull(id, "ID cannot be null");
  }

  /**
   * Creates a new DeferredHolder for the given registry and ID.
   *
   * @param registry The registry to get the entry from
   * @param id       The ID of the entry
   * @param <T>      The type of the registry entry
   * @return A new DeferredHolder
   */
  public static <T extends IForgeRegistryEntry<T>> DeferredHolder<T> create(IForgeRegistry<T> registry, ResourceLocation id) {
    return new DeferredHolder<>(registry, id);
  }

  /**
   * Creates a new DeferredHolder for the given registry and ID.
   *
   * @param registry The registry to get the entry from
   * @param id       The ID of the entry as a string (will be converted to ResourceLocation)
   * @param <T>      The type of the registry entry
   * @return A new DeferredHolder
   */
  public static <T extends IForgeRegistryEntry<T>> DeferredHolder<T> create(IForgeRegistry<T> registry, String id) {
    return create(registry, new ResourceLocation(id));
  }

  /**
   * Gets the value from the registry. If the value hasn't been retrieved yet, it will be looked up in the registry.
   *
   * @return The registry entry, or null if not found
   */
  @Nullable
  public T get() {
    if (!bound) {
      this.value = registry.getValue(id);
      this.bound = true;
    }
    return value;
  }

  /**
   * Gets the value from the registry, throwing an exception if not found.
   *
   * @return The registry entry
   * @throws IllegalStateException if the entry is not found
   */
  @Nonnull
  public T getOrThrow() {
    T value = get();
    if (value == null) {
      throw new IllegalStateException("No such registry entry: " + id);
    }
    return value;
  }

  /**
   * Gets the ID of this holder.
   *
   * @return The resource location ID
   */
  @Nonnull
  public ResourceLocation getId() {
    return id;
  }

  /**
   * Checks if the holder is bound to a value.
   *
   * @return true if the holder has attempted to get the value
   */
  public boolean isBound() {
    return bound;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {return true;}
    if (o == null || getClass() != o.getClass()) {return false;}
    DeferredHolder<?> that = (DeferredHolder<?>) o;
    return id.equals(that.id) && registry.equals(that.registry);
  }

  @Override
  public int hashCode() {
    return Objects.hash(registry, id);
  }

  @Override
  public String toString() {
    return "DeferredHolder{" + id + '}';
  }

  /**
   * Создает DeferredHolder для контента
   *
   * @param registry Реестр Forge
   * @param modId    ID мода
   * @param name     Имя контента
   * @param <T>      Тип контента
   * @return DeferredHolder для указанного контента
   */
  public static <T extends IForgeRegistryEntry<T>> DeferredHolder<T> create(IForgeRegistry<T> registry, String modId, String name) {
    return create(registry, new ResourceLocation(modId, name));
  }

  /**
   * Регистрирует контент и возвращает DeferredHolder
   *
   * @param registrar Регистратор контента
   * @param registry  Реестр Forge
   * @param name      Имя контента
   * @param factory   Фабрика для создания экземпляра контента
   * @param <T>       Тип контента
   * @param <E>       Тип записи контента
   * @return DeferredHolder для зарегистрированного контента
   */
  public static <T extends IForgeRegistryEntry<T>, E extends IContentEntry<?, T>> DeferredHolder<T> register(
    IContentRegistrar registrar, IForgeRegistry<T> registry, String name, Function<ResourceLocation, E> factory) {

    ResourceLocation id = new ResourceLocation(registrar.getModule().getIdentifier().getNamespace(), name);
    E entry = factory.apply(id);
    registrar.addContent(name, entry);
    return create(registry, id);
  }

  /**
   * Создает DeferredHolder для уже существующего экземпляра
   *
   * @param registry Реестр Forge
   * @param entry    Существующий экземпляр
   * @param <T>      Тип контента
   * @return DeferredHolder для указанного экземпляра
   */
  public static <T extends IForgeRegistryEntry<T>> DeferredHolder<T> of(IForgeRegistry<T> registry, T entry) {
    return new ExistingDeferredHolder<>(registry, entry);
  }

  /**
   * Специальная реализация DeferredHolder для уже существующих экземпляров
   */
  private static class ExistingDeferredHolder<T extends IForgeRegistryEntry<T>> extends DeferredHolder<T> {

    private final T existingValue;

    private ExistingDeferredHolder(IForgeRegistry<T> registry, T existingValue) {
      super(registry, existingValue.getRegistryName());
      this.existingValue = existingValue;
      this.bound = true;
      this.value = existingValue;
    }

    @Nonnull
    @Override
    public T get() {
      return existingValue;
    }

    @Nonnull
    @Override
    public T getOrThrow() {
      return existingValue;
    }
  }
}
