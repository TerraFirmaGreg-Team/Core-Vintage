package su.terrafirmagreg.modules.worldgen.debug;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.worldgen.ModuleWorld;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGeneratorDebug;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import org.jetbrains.annotations.Nullable;

public class ChunkGenDebug extends ChunkGeneratorDebug {

  public ChunkGenDebug(World world, String modid, @Nullable String blockName) {
    super(world);

    ModuleWorld.LOGGER.debug("mod for ID {} {}", modid, blockName);

    // Build a list of block states for the provided modid.
    ALL_VALID_STATES.clear();
    ALL_VALID_STATES.add(Blocks.SPONGE.getDefaultState());
    for (final Block block : ModUtils.getSortedEntries(ForgeRegistries.BLOCKS).get(modid)) {
      if (blockName == null) {
        ALL_VALID_STATES.addAll(block.getBlockState().getValidStates());
      } else if (block.getRegistryName().getPath().startsWith(blockName)) {
        ALL_VALID_STATES.addAll(block.getBlockState().getValidStates());

      }
    }
  }

}
