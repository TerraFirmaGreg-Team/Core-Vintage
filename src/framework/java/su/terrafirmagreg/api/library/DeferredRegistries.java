package su.terrafirmagreg.api.library;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class DeferredRegistries<T> {

  private final String modId;
  private final List<DeferredObject<T>> entries;


  public DeferredRegistries(String modId) {
    this.modId = modId;
    this.entries = new ArrayList<>();
  }


  public DeferredObject<T> register(String name, T entry) {
    if (this.getKey(entry) != null) {throw new IllegalArgumentException("Entry already exists: " + entry.toString());}
    DeferredObject<T> e = new DeferredObject<>(Identifier.of(this.modId, name), entry);
    this.entries.add(e);
    return e;
  }

  public DeferredObject<T> register(String name, Supplier<T> entry) {
    return this.register(name, entry.get());
  }

  public void register() {
    for (DeferredObject<T> entry : entries) {
      // TODO
    }
  }

  public Collection<DeferredObject<T>> getObjects() {
    return this.entries;
  }

  public Collection<T> getEntries() {
    List<T> entriesL = new ArrayList<>();
    for (DeferredObject<T> entry : this.entries) {
      entriesL.add(entry.get());
    }
    return entriesL;
  }

  @Nullable
  public Identifier getKey(T entry) {
    for (DeferredObject<T> object : this.entries) {
      if (object.get().equals(entry)) {return object.getIdentifier();}
    }
    return null;
  }

  @Nullable
  public T get(Identifier id) {
    for (DeferredObject<T> object : this.entries) {
      if (object.getIdentifier().equals(id)) {return object.get();}
    }
    return null;
  }

  @Nullable
  public Identifier get(T entry) {
    return this.getKey(entry);
  }
}
