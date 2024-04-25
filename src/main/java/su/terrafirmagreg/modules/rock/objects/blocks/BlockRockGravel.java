package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import net.dries007.tfc.api.util.FallingBlockManager;

public class BlockRockGravel extends BlockRockFallable {

    public BlockRockGravel(RockBlockVariant blockVariant, RockType type) {
        super(Material.SAND, blockVariant, type);

        setSoundType(SoundType.GROUND);

        FallingBlockManager.registerFallable(this, blockVariant.getSpecification());
        //DirtHelper.registerSoil(this, DirtHelper.GRAVELLIKE);
    }

}
