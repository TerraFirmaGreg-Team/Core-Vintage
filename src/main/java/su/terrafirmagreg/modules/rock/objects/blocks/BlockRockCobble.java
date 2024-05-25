package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;


import net.dries007.tfc.api.util.FallingBlockManager;

public class BlockRockCobble extends BlockRockFallable {

    public BlockRockCobble(RockBlockVariant variant, RockType type) {
        super(Settings.of(Material.ROCK), variant, type);

        getSettings()
                .renderLayer(BlockRenderLayer.CUTOUT)
                .addOreDict("cobblestone");

        FallingBlockManager.registerFallable(this, variant.getSpecification());
    }
}
