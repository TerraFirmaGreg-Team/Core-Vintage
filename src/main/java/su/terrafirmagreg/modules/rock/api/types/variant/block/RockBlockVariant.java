package su.terrafirmagreg.modules.rock.api.types.variant.block;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.base.types.variant.Variant;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.util.text.TextComponentTranslation;


import gregtech.api.unification.ore.StoneType;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import static su.terrafirmagreg.modules.core.features.falling.FallingBlockManager.Specification;

@Getter
public class RockBlockVariant extends Variant<RockBlockVariant> {

    @Getter
    private static final Set<RockBlockVariant> blockVariants = new ObjectOpenHashSet<>();
    private static final AtomicInteger idCounter = new AtomicInteger(16);

    private float baseHardness;
    private Specification specification;
    private BiFunction<RockBlockVariant, RockType, ? extends Block> factory;
    private boolean hasStoneType;

    private RockBlockVariant(String name) {
        super(name);

        if (!blockVariants.add(this)) throw new RuntimeException(String.format("RockBlockVariant: [%s] already exists!", name));
    }

    public static RockBlockVariant builder(String name) {

        return new RockBlockVariant(name);
    }

    public Block get(RockType type) {
        var block = BlocksRock.ROCK_BLOCKS.get(Pair.of(this, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block rock is null: %s, %s", this, type));
    }

    public RockBlockVariant setBaseHardness(float baseHardness) {
        this.baseHardness = baseHardness;
        return this;
    }

    public RockBlockVariant setFactory(BiFunction<RockBlockVariant, RockType, ? extends Block> factory) {
        this.factory = factory;
        return this;
    }

    public RockBlockVariant setFallingSpecification(Specification specification) {
        this.specification = specification;
        return this;
    }

    public RockBlockVariant setStoneType() {
        this.hasStoneType = true;
        return this;
    }

    public RockBlockVariant build() {
        for (var type : RockType.getTypes()) {
            if (BlocksRock.ROCK_BLOCKS.put(Pair.of(this, type), factory.apply(this, type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));

            if (hasStoneType) createStoneType(idCounter.getAndIncrement(), type);
        }
        return this;
    }

    public boolean canFall() {
        return specification != null;
    }

    private void createStoneType(int id, RockType type) {
        new StoneType(id, type + "_" + this.getName(), SoundType.STONE,
                type.getOrePrefix(), type.getMaterial(),
                () -> this.get(type).getDefaultState(),
                state -> state.getBlock() == this.get(type), false
        );
    }

    public String getLocalizedName() {
        return new TextComponentTranslation(String.format("rock.variant.%s.name", this)).getFormattedText();
    }
}
