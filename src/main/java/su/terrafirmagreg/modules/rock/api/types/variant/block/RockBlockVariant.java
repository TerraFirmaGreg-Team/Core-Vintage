package su.terrafirmagreg.modules.rock.api.types.variant.block;

import net.minecraft.block.SoundType;

import gregtech.api.unification.ore.StoneType;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.util.Pair;
import su.terrafirmagreg.modules.rock.StorageRock;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification;

/**
 * Класс, представляющий тип блока породы.
 */
public class RockBlockVariant {

    private static final Set<RockBlockVariant> ROCK_BLOCK_VARIANTS = new ObjectLinkedOpenHashSet<>();
    private static final AtomicInteger idCounter = new AtomicInteger(16);

    private final String name;
    @Getter
    private final float baseHardness;
    @Getter
    private final Specification specification;

    private RockBlockVariant(Builder builder) {
        this.name = builder.name;
        this.baseHardness = builder.baseHardness;
        this.specification = builder.specification;

        if (name.isEmpty())
            throw new RuntimeException(String.format("RockBlockVariant name must contain any character: [%s]", name));


        if (!ROCK_BLOCK_VARIANTS.add(this))
            throw new RuntimeException(String.format("RockBlockVariant: [%s] already exists!", name));


        for (var type : RockType.getTypes()) {
            if (StorageRock.ROCK_BLOCKS.put(new Pair<>(this, type), builder.factory.apply(this, type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));

            if (builder.hasStoneType) {
                new StoneType(idCounter.getAndIncrement(), "tfg_" + type + "_" + builder.name,
                        SoundType.STONE, type.getOrePrefix(), type.getMaterial(),
                        () -> StorageRock.getBlock(this, type).getDefaultState(),
                        state -> state.getBlock() == StorageRock.getBlock(this, type), false
                );
            }
        }
    }

    /**
     * Возвращает набор всех типов блоков породы.
     *
     * @return Набор всех типов блоков породы.
     */
    public static Set<RockBlockVariant> getBlockVariants() {
        return ROCK_BLOCK_VARIANTS;
    }

    @Override
    public String toString() {
        return name;
    }

    public static class Builder {

        private final String name;
        private float baseHardness;
        private BiFunction<RockBlockVariant, RockType, IRockBlock> factory;
        private Specification specification = null;
        private boolean hasStoneType = false;

        /**
         * Создает экземпляр Builder с указанным именем.
         *
         * @param name Имя породы.
         */
        public Builder(@NotNull String name) {
            this.name = name;
        }

        public Builder setBaseHardness(float baseHardness) {
            this.baseHardness = baseHardness;
            return this;
        }

        public Builder setFactory(BiFunction<RockBlockVariant, RockType, IRockBlock> factory) {
            this.factory = factory;
            return this;
        }

        public Builder setFallingSpecification(Specification specification) {
            this.specification = specification;
            return this;
        }

        public Builder setStoneType() {
            this.hasStoneType = true;
            return this;
        }

        public RockBlockVariant build() {
            return new RockBlockVariant(this);
        }
    }
}
