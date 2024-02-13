package su.terrafirmagreg.modules.rock.init;

import net.minecraft.block.Block;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.api.util.Pair;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import java.util.Map;

public final class BlocksRock {

    public static final Map<Pair<RockBlockVariant, RockType>, Block> ROCK_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

    public static void onRegister(Registry registry) {

        for (var block : ROCK_BLOCKS.values()) registry.registerAuto(block);
    }


    @NotNull
    public static Block getBlock(@NotNull RockBlockVariant variant, @NotNull RockType type) {
        var block = (Block) ROCK_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block rock is null: %s, %s", variant, type));
    }

}
