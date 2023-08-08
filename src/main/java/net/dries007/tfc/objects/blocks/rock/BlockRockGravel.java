package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.block.type.RockType;
import net.dries007.tfc.api.types.rock.block.variant.RockVariant;
import net.dries007.tfc.api.types.rock.type.Rock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRockGravel extends BlockRockFallable {

    public BlockRockGravel(RockType rockType, RockVariant rockVariant, Rock rock) {
        super(Material.SAND, rockType, rockVariant, rock);

        this.setSoundType(SoundType.GROUND);
    }

}
