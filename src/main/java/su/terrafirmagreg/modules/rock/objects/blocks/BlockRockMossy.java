package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.util.BlockRenderLayer;

import su.terrafirmagreg.modules.core.features.falling.FallingBlockManager;

/**
 * Пока это почти полная копия {@link BlockRock} Этот клас в будущем планируется использовать для механики распространения мха
 */
public class BlockRockMossy extends BlockRock {

    public BlockRockMossy(RockBlockVariant variant, RockType type) {
        super(variant, type);

        getSettings()
                .renderLayer(BlockRenderLayer.CUTOUT);

        FallingBlockManager.registerFallable(this, variant.getSpecification());
    }
}
