package su.terrafirmagreg.api.model.property;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;

import net.minecraft.block.properties.PropertyHelper;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

public class PropertyRockType extends PropertyHelper<RockType> {

    private final ImmutableList<RockType> allowedValues;

    protected PropertyRockType(String name, Collection<? extends RockType> allowedValues) {
        super(name, RockType.class);
        this.allowedValues = ImmutableList.copyOf(allowedValues);
    }

    public static PropertyRockType create(String name, Collection<? extends RockType> allowedValues) {
        return new PropertyRockType(name, allowedValues);
    }

    public static PropertyRockType create(String name, RockType[] allowedValues) {
        return new PropertyRockType(name, Arrays.asList(allowedValues));
    }

    @NotNull
    @Override
    public ImmutableList<RockType> getAllowedValues() {
        return allowedValues;
    }

    @NotNull
    @Override
    public Optional<RockType> parseValue(@NotNull String value) {
        RockType rockType = RockType.getByName(value);
        if (rockType != null && this.allowedValues.contains(rockType)) {
            return Optional.of(rockType);
        }
        return Optional.absent();
    }

    @NotNull
    @Override
    public String getName(RockType rockType) {
        return rockType.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof PropertyRockType propertyRockType) {
            return this.allowedValues.equals(propertyRockType.allowedValues);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int i = super.hashCode();
        i = 31 * i + this.allowedValues.hashCode();
        return i;
    }
}
