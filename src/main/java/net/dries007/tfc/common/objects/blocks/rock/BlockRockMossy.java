package net.dries007.tfc.common.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Пока это почти полная копия {@link BlockRock}
 * Этот клас в будущем планируется использовать для механики распространения мха
 */
public class BlockRockMossy extends BlockRock {

    public BlockRockMossy(RockBlockVariant rockBlockVariant, RockType rockType) {
        super(rockBlockVariant, rockType);
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
