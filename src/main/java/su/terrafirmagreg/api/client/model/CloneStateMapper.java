package su.terrafirmagreg.api.client.model;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;


import org.jetbrains.annotations.NotNull;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Clones another block's blockState file location.
 * <p>
 * The block using this must have the same blockState properties as the block it is cloning.
 */
public class CloneStateMapper extends StateMapperBase {

    private static final Map<Block, CloneStateMapper> CACHE = new IdentityHashMap<>();

    private final Block block;

    private CloneStateMapper(Block block) {

        this.block = block;
    }

    public static CloneStateMapper forBlock(Block block) {

        CloneStateMapper cloneStateMapper = CACHE.get(block);

        if (cloneStateMapper == null) {
            cloneStateMapper = new CloneStateMapper(block);
            CACHE.put(block, cloneStateMapper);
        }

        return cloneStateMapper;
    }

    @NotNull
    @Override
    protected ModelResourceLocation getModelResourceLocation(@NotNull IBlockState state) {

        return new ModelResourceLocation(
                Block.REGISTRY.getNameForObject(this.block),
                this.getPropertyString(state.getProperties())
        );
    }
}
