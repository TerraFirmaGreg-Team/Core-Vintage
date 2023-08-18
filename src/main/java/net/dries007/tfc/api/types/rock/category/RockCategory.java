package net.dries007.tfc.api.types.rock.category;

import net.dries007.tfc.api.types.rock.type.RockType;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Класс, представляющий категорию породы.
 */
public class RockCategory {
    private static final Set<RockCategory> ROCK_CATEGORIES = new LinkedHashSet<>();

    private final String name;
    private final boolean layer1;
    private final boolean layer2;
    private final boolean layer3;
    private final float caveGenMod;
    private final float caveFreqMod;
    private final float hardnessModifier;

    @Nonnull
    private final TextFormatting textFormatting;
    private final boolean hasAnvil;


    /**
     * Создает экземпляр класса RockCategory с указанными параметрами.
     *
     * @param name             Название категории породы.
     * @param layer1           Флаг, указывающий, присутствует ли категория в первом слое пород.
     * @param layer2           Флаг, указывающий, присутствует ли категория во втором слое пород.
     * @param layer3           Флаг, указывающий, присутствует ли категория в третьем слое пород.
     * @param caveGenMod       Модификатор генерации породы в пещерах.
     * @param caveFreqMod      Модификатор частоты генерации породы в пещерах.
     * @param hardnessModifier Модификатор прочности породы.
     * @param textFormatting   Форматирование текста для отображения категории.
     * @param hasAnvil         Флаг, указывающий, имеет ли категория наковальню.
     */
    RockCategory(@Nonnull String name, boolean layer1, boolean layer2, boolean layer3, float caveGenMod, float caveFreqMod, float hardnessModifier, @Nonnull TextFormatting textFormatting, boolean hasAnvil) {
        this.name = name;
        this.layer1 = layer1;
        this.layer2 = layer2;
        this.layer3 = layer3;
        this.caveGenMod = caveGenMod;
        this.caveFreqMod = caveFreqMod;
        this.hardnessModifier = hardnessModifier;
        this.textFormatting = textFormatting;
        this.hasAnvil = hasAnvil;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("RockCategory name must contain any character: [%s]", name));
        }

        if (!ROCK_CATEGORIES.add(this)) {
            throw new RuntimeException(String.format("RockCategory: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех категорий пород.
     *
     * @return Набор всех категорий пород.
     */
    public static Set<RockCategory> getRockCategories() {
        return ROCK_CATEGORIES;
    }

    /**
     * Возвращает строковое представление категории породы.
     *
     * @return Строковое представление категории породы.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Возвращает модификатор генерации породы в пещерах.
     *
     * @return Модификатор генерации породы в пещерах.
     */
    public float getCaveGenMod() {
        return caveGenMod;
    }

    /**
     * Возвращает модификатор частоты генерации породы в пещерах.
     *
     * @return Модификатор частоты генерации породы в пещерах.
     */
    public float getCaveFreqMod() {
        return caveFreqMod;
    }

    /**
     * Возвращает модификатор прочности породы.
     *
     * @return Модификатор прочности породы.
     */
    public float getHardnessModifier() {
        return hardnessModifier;
    }

    /**
     * Возвращает форматирование текста для отображения категории.
     *
     * @return Форматирование текста для отображения категории.
     */
    @Nonnull
    public TextFormatting getTextFormatting() {
        return textFormatting;
    }

    /**
     * Проверяет, имеет ли категория наковальню.
     *
     * @return true, если категория имеет наковальню, в противном случае - false.
     */
    public boolean hasAnvil() {
        return hasAnvil;
    }

    /**
     * Возвращает локализованное имя категории породы.
     *
     * @return Локализованное имя категории породы.
     */
    public String getLocalizedName() {
        return textFormatting + new TextComponentTranslation(String.format("rockcategory.%s.name", this)).getFormattedText();
    }

    /**
     * Представляет слой породы и определяет, принадлежит ли порода к данному слою.
     */
    public enum Layer implements Predicate<RockType> {
        BOTTOM(3, x -> x.getCategory().layer3),
        MIDDLE(2, x -> x.getCategory().layer2),
        TOP(1, x -> x.getCategory().layer1);

        public final int layer;
        private final Predicate<RockType> filter;

        /**
         * Создает экземпляр слоя породы с указанными параметрами.
         *
         * @param layer  Номер слоя породы.
         * @param filter Фильтр, определяющий, принадлежит ли порода к данному слою.
         */
        Layer(int layer, Predicate<RockType> filter) {
            this.layer = layer;
            this.filter = filter;
        }

        /**
         * Проверяет, принадлежит ли указанная порода к данному слою.
         *
         * @param rockType Порода для проверки.
         * @return true, если порода принадлежит к данному слою, в противном случае - false.
         */
        @Override
        public boolean test(RockType rockType) {
            return filter.test(rockType);
        }
    }
}
