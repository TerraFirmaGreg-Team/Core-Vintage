package su.terrafirmagreg.modules.rock.api.types.type;

import gregtech.api.unification.ore.StoneType;

import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.util.Helpers;
import su.terrafirmagreg.modules.rock.api.types.category.RockCategory;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import java.util.ArrayList;
import java.util.Set;

/**
 * Основной класс для типов камней.
 */

public class RockType {

    private static final Set<RockType> ROCK_TYPES = new ObjectLinkedOpenHashSet<>();

    private final String name;
    @Getter
    private final RockCategory rockCategory;
    @Getter
    private final OrePrefix orePrefix;
    @Getter
    private final Material material;
    @Getter
    private final boolean isFlux;


    private RockType(Builder builder) {
        this.name = builder.name;
        this.rockCategory = builder.rockCategory;
        this.orePrefix = builder.orePrefix;
        this.material = builder.material;
        this.isFlux = builder.isFlux;

        if (name.isEmpty())
            throw new RuntimeException(String.format("Rock name must contain any character: [%s]", name));

        if (!ROCK_TYPES.add(this)) throw new RuntimeException(String.format("Rock: [%s] already exists!", name));
    }

    /**
     * Возвращает список всех типов пород.
     *
     * @return Список всех типов пород.
     */
    public static Set<RockType> getTypes() {
        return ROCK_TYPES;
    }

    @Nullable
    public static RockType getByName(@NotNull String name) {
        return RockType.getTypes()
                .stream()
                .filter(s -> s.toString().equals(name))
                .findFirst()
                .orElse(null);
    }



    /**
     * Возвращает экземпляр породы по индексу.
     *
     * @param i Индекс породы.
     * @return Экземпляр породы.
     */
    public static RockType valueOf(int i) {
        var values = new RockType[ROCK_TYPES.size()];
        values = ROCK_TYPES.toArray(values);

        return i >= 0 && i < values.length ? values[i] : values[i % values.length];
    }

    /**
     * Возвращает индекс породы в списке.
     *
     * @param type Порода.
     * @return Индекс породы.
     */
    public static int indexOf(RockType type) {
        return new ArrayList<>(ROCK_TYPES).indexOf(type);
    }

    @Override
    public String toString() {
        return name;
    }


    /**
     * Возвращает ресурсное расположение текстуры породы.
     *
     * @return Ресурсное расположение текстуры породы.
     */
    @NotNull
    public ResourceLocation getTexture() {
        return Helpers.getID("textures/blocks/rock/raw/" + this + ".png");
    }

    public static class Builder {

        private final String name;
        private RockCategory rockCategory;
        private OrePrefix orePrefix;
        private Material material;
        private boolean isFlux = false;

        /**
         * Создает экземпляр Builder с указанным именем.
         *
         * @param name Имя породы.
         */
        public Builder(@NotNull String name) {
            this.name = name;
        }

        /**
         * Устанавливает орпрефикс для типа породы.
         *
         * @param orePrefix Орпрефикс для типа породы.
         * @return Builder.
         */
        public Builder setOrePrefix(@NotNull OrePrefix orePrefix) {
            this.orePrefix = orePrefix;
            return this;
        }

        /**
         * Устанавливает материал для типа породы.
         *
         * @param material Материал для типа породы.
         * @return Builder.
         */
        public Builder setMaterial(@NotNull Material material) {
            this.material = material;
            return this;
        }

        /**
         * Устанавливает категорию породы.
         *
         * @param rockCategory Категория породы.
         * @return Builder.
         */
        public Builder setRockCategory(@NotNull RockCategory rockCategory) {
            this.rockCategory = rockCategory;
            return this;
        }

        /**
         * Устанавливает флаг, указывающий, является ли порода флюсом.
         *
         * @return Builder.
         */
        public Builder setFlux() {
            this.isFlux = true;
            return this;
        }

        /**
         * Создает экземпляр RockType на основе Builder.
         *
         * @return Экземпляр RockType.
         */
        public RockType build() {
            return new RockType(this);
        }
    }
}
