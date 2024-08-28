package su.terrafirmagreg.data.lib.collection;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Collection;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Modified from <a href="https://stackoverflow.com/questions/6409652/random-weighted-selection-in-java">...</a>
 *
 * @param <E> the type of the collection
 */
public class WeightedCollection<E> {

    private final NavigableMap<Double, E> backingMap = new TreeMap<>();
    @Getter
    private double totalWeight = 0;

    public WeightedCollection() {}

    public WeightedCollection(Map<? extends E, Double> values) {
        values.forEach((k, v) -> add(v, k));
    }

    public WeightedCollection<E> add(double weight, @NotNull E result) {
        if (weight > 0) {
            totalWeight += weight;
            backingMap.put(totalWeight, result);
        }
        return this;
    }

    @NotNull
    public E getRandomEntry(Random random) {
        double value = random.nextDouble() * totalWeight;
        return backingMap.higherEntry(value).getValue();
    }

    public Collection<E> values() {
        return backingMap.values();
    }

    public void clear() {
        backingMap.clear();
    }
}
