package su.terrafirmagreg.modules.rock.data;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.block.Block;
import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import java.util.Map;

public final class BlocksRock {

	public static final Map<Pair<RockBlockVariant, RockType>, Block> ROCK_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

	public static void onRegister(RegistryManager registry) {
		for (var block : ROCK_BLOCKS.values()) registry.registerAuto(block);

	}


}
