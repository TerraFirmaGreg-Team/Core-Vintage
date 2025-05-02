package su.terrafirmagreg.api.library;

import java.util.concurrent.atomic.AtomicInteger;

public class IdSupplier {

  private final AtomicInteger nextId;

  public IdSupplier() {
    this.nextId = new AtomicInteger();
  }

  public int get() {
    return this.nextId.get();
  }

  public int getAndIncrement() {
    return this.nextId.getAndIncrement();
  }

  public void reset() {
    this.nextId.set(0);
  }

  public void set(int value) {
    this.nextId.set(value);
  }

  public int getAndSet(int newValue) {
    return this.nextId.getAndSet(newValue);
  }

}
