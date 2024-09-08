package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

public class BlockRockCobble extends BlockRockFallable {

  public BlockRockCobble(RockBlockVariant variant, RockType type) {
    super(Settings.of(Material.ROCK), variant, type);

    getSettings()
            .renderLayer(BlockRenderLayer.CUTOUT)
            .oreDict("cobblestone")
            .hardness(variant.getBaseHardness() + type.getCategory().getHardnessModifier());
  }
}
