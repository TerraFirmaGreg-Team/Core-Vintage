package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

import su.terrafirmagreg.modules.core.features.falling.FallingBlockManager;


import static su.terrafirmagreg.modules.core.features.falling.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL_ROCK;

public class BlockRockCobble extends BlockRockFallable {

    public BlockRockCobble(RockBlockVariant variant, RockType type) {
        super(Settings.of(Material.ROCK), variant, type);

        getSettings()
                .renderLayer(BlockRenderLayer.CUTOUT)
                .addOreDict("cobblestone")
                .hardness(variant.getBaseHardness() + type.getRockCategory().getHardnessModifier());

        FallingBlockManager.registerFallable(this, VERTICAL_AND_HORIZONTAL_ROCK);
    }
}
