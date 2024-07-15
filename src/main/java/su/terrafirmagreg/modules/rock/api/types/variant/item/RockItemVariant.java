package su.terrafirmagreg.modules.rock.api.types.variant.item;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.spi.types.variant.Variant;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.init.ItemsRock;

import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;


import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

@Getter
public class RockItemVariant extends Variant<RockItemVariant> {

    @Getter
    private static final Set<RockItemVariant> itemVariants = new ObjectOpenHashSet<>();

    private final String name;
    private final BiFunction<RockItemVariant, RockType, ? extends Item> factory;

    private RockItemVariant(Builder builder) {
        super(builder.name);

        this.name = builder.name;
        this.factory = builder.factory;

        if (!itemVariants.add(this)) throw new RuntimeException(String.format("RockItemVariant: [%s] already exists!", name));

        createItem();
    }

    public Item get(RockType type) {
        var item = ItemsRock.ROCK_ITEMS.get(Pair.of(this, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item rock is null: %s, %s", this, type));
    }

    public String getLocalizedName() {
        return new TextComponentTranslation(String.format("rock.variant.%s.name", this)).getFormattedText();
    }

    private void createItem() {
        for (var type : RockType.getTypes()) {
            if (ItemsRock.ROCK_ITEMS.put(Pair.of(this, type), factory.apply(this, type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }
    }

    public static Builder builder(String name) {

        return new Builder(name);
    }

    public static class Builder {

        private final String name;
        private BiFunction<RockItemVariant, RockType, ? extends Item> factory;

        /**
         * Создает экземпляр Builder с указанным именем.
         *
         * @param name Имя породы.
         */
        public Builder(@NotNull String name) {

            this.name = name;
        }

        public Builder setFactory(BiFunction<RockItemVariant, RockType, ? extends Item> factory) {
            this.factory = factory;
            return this;
        }

        public RockItemVariant build() {

            return new RockItemVariant(this);
        }
    }
}
