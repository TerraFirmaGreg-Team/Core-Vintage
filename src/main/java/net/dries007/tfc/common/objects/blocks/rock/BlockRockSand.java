package net.dries007.tfc.common.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRockSand extends BlockRockFallable {

    public BlockRockSand(RockBlockVariant variant, RockType type) {
        super(Material.SAND, variant, type);

        FallingBlockManager.registerFallable(this, FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL);

        setSoundType(SoundType.SAND);
    }

}
