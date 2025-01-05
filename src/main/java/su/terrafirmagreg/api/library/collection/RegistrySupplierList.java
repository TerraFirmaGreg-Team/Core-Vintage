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
import java.util.function.Supplier;

/**
 * Адаптация {@link NonNullList}
 */
public class RegistrySupplierList<E extends IForgeRegistryEntry<E>> extends ObjectArrayList<Supplier<E>> {

  private final List<Supplier<E>> delegate;
  private final Supplier<E> defaultElement;

  protected RegistrySupplierList() {
    this(new ObjectArrayList<>(), null);
  }

  protected RegistrySupplierList(List<Supplier<E>> delegateIn, @Nullable Supplier<E> listType) {
    this.delegate = delegateIn;
    this.defaultElement = listType;
  }

  public static <E extends IForgeRegistryEntry<E>> RegistrySupplierList<E> create() {
    return new RegistrySupplierList<>();
  }

  public static <E extends IForgeRegistryEntry<E>> RegistrySupplierList<E> withSize(int size, Supplier<E> fill) {
    Validate.notNull(fill);
    return new RegistrySupplierList<>(Collections.nCopies(size, fill), fill);
  }

  @SafeVarargs
  public static <E extends IForgeRegistryEntry<E>> RegistrySupplierList<E> from(Supplier<E> defaultElementIn, Supplier<E>... elements) {
    return new RegistrySupplierList<>(Arrays.asList(elements), defaultElementIn);
  }

  public void register(IForgeRegistry<E> registry) {
    this.forEach(supplier -> registry.register(supplier.get()));
  }

  public void register(RegistryEvent.Register<E> register) {
    this.forEach(supplier -> register.getRegistry().register(supplier.get()));
  }

  public void add(final int index, final Supplier<E> element) {
    Validate.notNull(element);
    this.delegate.add(index, element);
  }

  @Override
  public boolean add(final Supplier<E> element) {
    Validate.notNull(element);
    return delegate.add(element);
  }

  @Nonnull
  public Supplier<E> get(int index) {
    return this.delegate.get(index);
  }

  public Supplier<E> remove(int index) {
    return this.delegate.remove(index);
  }

  public Supplier<E> set(int index, Supplier<E> element) {
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
