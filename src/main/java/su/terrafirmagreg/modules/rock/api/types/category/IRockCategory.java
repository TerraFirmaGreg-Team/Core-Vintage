package su.terrafirmagreg.modules.rock.api.types.category;

import net.minecraft.util.text.TextComponentTranslation;

import org.jetbrains.annotations.NotNull;

public interface IRockCategory {

    /**
     * Возвращает тип породы.
     *
     * @return Тип породы.
     */
    @NotNull
    RockCategory getCategory();

    /**
     * Возвращает локализованное имя типа породы.
     *
     * @return Локализованное имя типа породы.
     */
    default String getLocalizedNameCategory() {
        return new TextComponentTranslation(String.format("rock.category.%s.name", getCategory())).getFormattedText();
    }
}
