package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.block.type.RockBlockType;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariant;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRockGravel extends BlockRockFallable {

    public BlockRockGravel(RockBlockType rockBlockType, RockBlockVariant rockBlockVariant, RockType rockType) {
        super(Material.SAND, rockBlockType, rockBlockVariant, rockType);

        this.setSoundType(SoundType.GROUND);
    }

}
