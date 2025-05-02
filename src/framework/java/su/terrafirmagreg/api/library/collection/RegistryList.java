package su.terrafirmagreg.api.library.collection;

import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Адаптация {@link NonNullList}
 */
public class RegistryList<E extends IForgeRegistryEntry<E>> extends ObjectArrayList<E> {

  private final List<E> delegate;
  private final E defaultElement;


  protected RegistryList() {
    this(new ObjectArrayList<>(), null);
  }

  protected RegistryList(List<E> delegateIn, @Nullable E listType) {
    this.delegate = delegateIn;
    this.defaultElement = listType;
  }


  public static <E extends IForgeRegistryEntry<E>> RegistryList<E> create() {
    return new RegistryList<>();
  }

  public static <E extends IForgeRegistryEntry<E>> RegistryList<E> withSize(int size, E fill) {
    Validate.notNull(fill);
    return new RegistryList<>(Collections.nCopies(size, fill), fill);
  }

  @SafeVarargs
  public static <E extends IForgeRegistryEntry<E>> RegistryList<E> from(E defaultElementIn, E... elements) {
    return new RegistryList<>(Arrays.asList(elements), defaultElementIn);
  }

  public void register(IForgeRegistry<E> registry) {
    this.forEach(registry::register);
  }

  public void register(RegistryEvent.Register<E> registry) {
    this.forEach(registry.getRegistry()::register);
  }

  public void add(final int index, final E element) {
    Validate.notNull(element);
    this.delegate.add(index, element);
  }

  @Nonnull
  public E get(int index) {
    return this.delegate.get(index);
  }

  public E remove(int index) {
    return this.delegate.remove(index);
  }

  public E set(int index, E element) {
    Validate.notNull(element);
    return this.delegate.set(index, element);
  }

  public void clear() {
    if (this.defaultElement == null) {
      super.clear();
    } else {
      Collections.fill(this, this.defaultElement);
    }
  }

  public int size() {
    return this.delegate.size();
  }

}
