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
public class Rock {

    private static final Set<Rock> ROCKS = new LinkedHashSet<>();

    @Nonnull
    private final String rockTypeName;
    @Nonnull
    private final RockCategory rockCategory;
    private final boolean isFlux;

    /**
     * Создает экземпляр класса Rock с указанными параметрами.
     *
     * @param rockTypeName Название породы.
     * @param rockCategory Категория породы.
     * @param isFlux       Флаг, указывающий, является ли порода флюсом.
     */
    public Rock(@Nonnull String rockTypeName, @Nonnull RockCategory rockCategory, boolean isFlux) {
        this.rockTypeName = rockTypeName;
        this.rockCategory = rockCategory;
        this.isFlux = isFlux;

        if (rockTypeName.isEmpty()) {
            throw new RuntimeException(String.format("Rock name must contain any character: [%s]", rockTypeName));
        }

        if (!ROCKS.add(this)) {
            throw new RuntimeException(String.format("Rock: [%s] already exists!", rockTypeName));
        }
    }

    /**
     * Создает экземпляр класса Rock с указанными параметрами и флагом флюса, установленным в false.
     *
     * @param rockTypeName Название породы.
     * @param rockCategory Категория породы.
     */
    public Rock(@Nonnull String rockTypeName, @Nonnull RockCategory rockCategory) {
        this(rockTypeName, rockCategory, false);
    }

    /**
     * Возвращает строковое представление породы.
     *
     * @return Строковое представление породы.
     */
    @Override
    public String toString() {
        return rockTypeName;
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
    public static Set<Rock> getRockTypes() {
        return ROCKS;
    }

    /**
     * Возвращает экземпляр породы по индексу.
     *
     * @param i Индекс породы.
     * @return Экземпляр породы.
     */
    public static Rock valueOf(int i) {
        var values = new Rock[ROCKS.size()];
        values = ROCKS.toArray(values);

        return i >= 0 && i < values.length ? values[i] : values[i % values.length];
    }

    /**
     * Возвращает индекс породы в списке.
     *
     * @param rock Порода.
     * @return Индекс породы.
     */
    public static int indexOf(Rock rock) {
        return new ArrayList<>(ROCKS).indexOf(rock);
    }
}
