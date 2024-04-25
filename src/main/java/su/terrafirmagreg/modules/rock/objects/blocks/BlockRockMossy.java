package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.api.util.FallingBlockManager;

import org.jetbrains.annotations.NotNull;

/**
 * Пока это почти полная копия {@link BlockRock} Этот клас в будущем планируется использовать для механики распространения мха
 */
public class BlockRockMossy extends BlockRock {

    public BlockRockMossy(RockBlockVariant blockVariant, RockType type) {
        super(blockVariant, type);

        FallingBlockManager.registerFallable(this, blockVariant.getSpecification());
    }

    @NotNull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
