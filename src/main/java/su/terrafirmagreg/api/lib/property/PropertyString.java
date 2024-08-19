package su.terrafirmagreg.api.lib.property;

import net.minecraft.block.properties.PropertyHelper;


import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PropertyString extends PropertyHelper<String> {

    private final Collection<String> possibleValues;

    private final Map<String, Integer> valueMap = new HashMap<>();

    private PropertyString(String name, String... values) {

        super(name, String.class);
        this.possibleValues = new ArrayList<>(Arrays.asList(values));

        for (int i = 0; i < values.length; i++) {

            this.valueMap.put(values[i], i);
        }
    }

    public static PropertyString create(String name, String... values) {
        return new PropertyString(name, values);
    }

    @Override
    public Collection<String> getAllowedValues() {

        return this.possibleValues;
    }

    @Override
    public Optional<String> parseValue(String value) {

        return Optional.of(value);
    }

    @Override
    public String getName(String value) {

        return value;
    }

    public int getMetaData(String string) {

        return this.valueMap.get(string);
    }
}
