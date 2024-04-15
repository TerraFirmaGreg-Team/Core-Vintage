package su.terrafirmagreg.modules.worldgen.debugworld;

import su.terrafirmagreg.api.lib.Injector;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorDebug;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


import com.google.common.collect.Lists;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChunkGeneratorDebugMod extends ChunkGeneratorDebug {

    private final List<IBlockState> allModStates = Lists.newArrayList();
    private final int width;
    private final int length;
    private final World world;

    public ChunkGeneratorDebugMod(World world, String modid) {
        super(world);

        // Build a list of block states for the provided modid.
        for (final Block block : ModUtils.getSortedEntries(ForgeRegistries.BLOCKS).get(modid)) {
            allModStates.addAll(block.getBlockState().getValidStates());
        }

        // Calculate the width and height of the grid.
        width = MathHelper.ceil(MathHelper.sqrt(allModStates.size()));
        length = MathHelper.ceil((float) allModStates.size() / (float) width);

        // Set the fields, this is needed because vanilla hardcodes some references here.
        Injector.setFinalStaticFieldWithReflection(ChunkGeneratorDebug.class, "field_177464_a", allModStates);
        Injector.setFinalStaticFieldWithReflection(ChunkGeneratorDebug.class, "field_177462_b", width);
        Injector.setFinalStaticFieldWithReflection(ChunkGeneratorDebug.class, "field_181039_c", length);

        this.world = world;
    }

    @Override
    public @NotNull Chunk generateChunk(int x, int z) {

        final ChunkPrimer primer = new ChunkPrimer();

        for (int xOffset = 0; xOffset < 16; ++xOffset) {
            for (int zOffset = 0; zOffset < 16; ++zOffset) {

                final int xPos = x * 16 + xOffset;
                final int zPos = z * 16 + zOffset;
                primer.setBlockState(xOffset, 60, zOffset, BARRIER);
                final IBlockState iblockstate = this.getStateForPosition(xPos, zPos);

                if (iblockstate != null) {
                    primer.setBlockState(xOffset, 70, zOffset, iblockstate);
                }
            }
        }

        final Chunk chunk = new Chunk(this.world, primer, x, z);
        chunk.generateSkylightMap();
        final Biome[] abiome = this.world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
        final byte[] abyte = chunk.getBiomeArray();

        for (int i1 = 0; i1 < abyte.length; ++i1) {
            abyte[i1] = (byte) Biome.getIdForBiome(abiome[i1]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private IBlockState getStateForPosition(int xPos, int zPos) {

        IBlockState iblockstate = AIR;

        if (xPos > 0 && zPos > 0 && xPos % 2 != 0 && zPos % 2 != 0) {
            xPos = xPos / 2;
            zPos = zPos / 2;

            if (xPos <= this.width && zPos <= this.length) {
                final int i = MathHelper.abs(xPos * this.width + zPos);

                if (i < this.allModStates.size()) {
                    iblockstate = this.allModStates.get(i);
                }
            }
        }

        return iblockstate;
    }
}
