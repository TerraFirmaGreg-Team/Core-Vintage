package su.terrafirmagreg.modules.world.debug;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGeneratorDebug;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ChunkGenDebug extends ChunkGeneratorDebug {

    public ChunkGenDebug(World world, String modid) {
        super(world);

        // Build a list of block states for the provided modid.
        ALL_VALID_STATES.clear();
        for (final Block block : ModUtils.getSortedEntries(ForgeRegistries.BLOCKS).get(modid)) {
            ALL_VALID_STATES.addAll(block.getBlockState().getValidStates());
        }

    }
}
