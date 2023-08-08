package net.dries007.tfc.api.types.rock.type;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.rock.category.RockCategory;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Основной класс для типов камней.
 */
public class RockType {

    private static final Set<RockType> ROCK_TYPE = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final RockCategory rockCategory;
    private final boolean isFlux;

    /**
     * Создает экземпляр класса Rock с указанными параметрами.
     *
     * @param name         Название породы.
     * @param rockCategory Категория породы.
     * @param isFlux       Флаг, указывающий, является ли порода флюсом.
     */
    public RockType(@Nonnull String name, @Nonnull RockCategory rockCategory, boolean isFlux) {
        this.name = name;
        this.rockCategory = rockCategory;
        this.isFlux = isFlux;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("Rock name must contain any character: [%s]", name));
        }

        if (!ROCK_TYPE.add(this)) {
            throw new RuntimeException(String.format("Rock: [%s] already exists!", name));
        }
    }

    /**
     * Создает экземпляр класса Rock с указанными параметрами и флагом флюса, установленным в false.
     *
     * @param name         Название породы.
     * @param rockCategory Категория породы.
     */
    public RockType(@Nonnull String name, @Nonnull RockCategory rockCategory) {
        this(name, rockCategory, false);
    }

    /**
     * Возвращает строковое представление породы.
     *
     * @return Строковое представление породы.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Возвращает категорию породы.
     *
     * @return Категория породы.
     */
    @Nonnull
    public RockCategory getRockCategory() {
        return rockCategory;
    }

    /**
     * Проверяет, является ли порода флюсом.
     *
     * @return true, если порода является флюсом, в противном случае - false.
     */
    public boolean isFlux() {
        return isFlux;
    }

    /**
     * Возвращает ресурсное расположение текстуры породы.
     *
     * @return Ресурсное расположение текстуры породы.
     */
    @Nonnull
    public ResourceLocation getTexture() {
        return new ResourceLocation(TerraFirmaCraft.MOD_ID, "textures/blocks/rock/raw/" + this + ".png");
    }

    /**
     * Возвращает список всех типов пород.
     *
     * @return Список всех типов пород.
     */
    public static Set<RockType> getAllRockTypes() {
        return ROCK_TYPE;
    }

    /**
     * Возвращает экземпляр породы по индексу.
     *
     * @param i Индекс породы.
     * @return Экземпляр породы.
     */
    public static RockType valueOf(int i) {
        var values = new RockType[ROCK_TYPE.size()];
        values = ROCK_TYPE.toArray(values);

        return i >= 0 && i < values.length ? values[i] : values[i % values.length];
    }

    /**
     * Возвращает индекс породы в списке.
     *
     * @param rockType Порода.
     * @return Индекс породы.
     */
    public static int indexOf(RockType rockType) {
        return new ArrayList<>(ROCK_TYPE).indexOf(rockType);
    }
}
