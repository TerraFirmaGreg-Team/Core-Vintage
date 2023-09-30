package net.dries007.tfc.module.rock.api.types.variant.block;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.dries007.tfc.module.core.api.util.Pair;
import net.dries007.tfc.module.rock.api.types.type.RockType;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.BiFunction;

import static net.dries007.tfc.module.rock.StorageRock.ROCK_BLOCKS;

/**
 * Класс, представляющий тип блока породы.
 */
public class RockBlockVariant {
    private static final Set<RockBlockVariant> ROCK_BLOCK_VARIANTS = new ObjectLinkedOpenHashSet<>();

    @Nonnull
    private final String name;

    private final float baseHardness;

    @Nonnull
    private final BiFunction<RockBlockVariant, RockType, IRockBlock> factory;

    /**
     * Создает экземпляр класса RockType с указанными параметрами.
     *
     * @param name    Название типа блока породы.
     * @param factory Функция-фабрика для создания блока породы по умолчанию.
     */
    public RockBlockVariant(@Nonnull String name, float baseHardness, @Nonnull BiFunction<RockBlockVariant, RockType, IRockBlock> factory) {
        this.name = name;
        this.baseHardness = baseHardness;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("RockBlockVariant name must contain any character: [%s]", name));
        }

        if (!ROCK_BLOCK_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("RockBlockVariant: [%s] already exists!", name));
        }

        for (var type : RockType.getRockTypes()) {
            if (ROCK_BLOCKS.put(new Pair<>(this, type), this.create(type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }
    }

    /**
     * Возвращает набор всех типов блоков породы.
     *
     * @return Набор всех типов блоков породы.
     */
    public static Set<RockBlockVariant> getRockBlockVariants() {
        return ROCK_BLOCK_VARIANTS;
    }

    /**
     * Возвращает строковое представление типа блока породы.
     *
     * @return Строковое представление типа блока породы.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Возвращает базовую твердость блока породы.
     *
     * @return Базовая твердость блока породы.
     */
    public float getBaseHardness() {
        return baseHardness;
    }

    /**
     * Возвращает локализованное имя варианта породы.
     *
     * @return Локализованное имя варианта породы.
     */
    public String getLocalizedName() {
        return new TextComponentTranslation(String.format("rock.variant.%s.name", this)).getFormattedText();
    }

    /**
     * Применяет функцию-фабрику к типу блока породы и возвращает созданный блок породы.
     *
     * @param type Тип породы.
     * @return Созданный блок породы.
     */
    @Nonnull
    public IRockBlock create(RockType type) {
        return factory.apply(this, type);
    }
}
