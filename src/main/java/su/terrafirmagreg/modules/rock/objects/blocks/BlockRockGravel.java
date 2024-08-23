package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import su.terrafirmagreg.modules.core.features.falling.FallingBlockManager;

public class BlockRockGravel extends BlockRockFallable {

    public BlockRockGravel(RockBlockVariant variant, RockType type) {
        super(Settings.of(Material.SAND), variant, type);

        getSettings()
                .soundType(SoundType.GROUND);

        FallingBlockManager.registerFallable(this, variant.getSpecification());
        //DirtHelper.registerSoil(this, DirtHelper.GRAVELLIKE);
    }

}
