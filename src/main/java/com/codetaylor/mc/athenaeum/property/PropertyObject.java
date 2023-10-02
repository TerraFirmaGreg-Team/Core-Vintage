package com.codetaylor.mc.athenaeum.property;

import net.minecraftforge.common.property.IUnlistedProperty;

public class PropertyObject<V>
        implements IUnlistedProperty<V> {

    private final String name;
    private final Class<V> type;

    public PropertyObject(String name, Class<V> type) {

        this.name = name;
        this.type = type;
    }

    public static <T> PropertyObject<T> create(String name, Class<T> type) {

        return new PropertyObject<>(name, type);
    }

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public boolean isValid(V value) {

        return true;
    }

    @Override
    public Class<V> getType() {

        return this.type;
    }

    @Override
    public String valueToString(V value) {

        return value.toString();
    }
}
