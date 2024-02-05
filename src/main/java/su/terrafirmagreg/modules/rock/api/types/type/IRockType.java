package su.terrafirmagreg.modules.rock.api.types.type;

import net.minecraft.util.text.TextComponentTranslation;

import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.modules.rock.api.types.category.IRockCategory;
import su.terrafirmagreg.modules.rock.api.types.category.RockCategory;

public interface IRockType extends IRockCategory {

    /**
     * Возвращает тип породы.
     *
     * @return Тип породы.
     */
    @NotNull
    RockType getType();

    /**
     * Возвращает категорию породы.
     *
     * @return Категория породы.
     */
    @NotNull
    default RockCategory getCategory() {
        return getType().getRockCategory();
    }

    /**
     * Возвращает локализованное имя типа породы.
     *
     * @return Локализованное имя типа породы.
     */
    default String getLocalizedNameType() {
        return new TextComponentTranslation(String.format("rock.type.%s.name", getType())).getFormattedText();
    }
}
