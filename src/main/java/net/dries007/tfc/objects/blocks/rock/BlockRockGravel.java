package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.block.type.RockBlockType;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariant;
import net.dries007.tfc.api.types.rock.type.Rock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRockGravel extends BlockRockFallable {

    public BlockRockGravel(RockBlockType rockBlockType, RockBlockVariant rockBlockVariant, Rock rock) {
        super(Material.SAND, rockBlockType, rockBlockVariant, rock);

        this.setSoundType(SoundType.GROUND);
    }

}
