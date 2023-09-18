package net.dries007.tfc.module.core.submodule.rock.common.blocks;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import net.dries007.tfc.module.core.submodule.rock.api.type.RockType;
import net.dries007.tfc.module.core.submodule.rock.api.variant.block.RockBlockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRockGravel extends BlockRockFallable {

    public BlockRockGravel(RockBlockVariant variant, RockType type) {
        super(Material.SAND, variant, type);

        setSoundType(SoundType.GROUND);

        FallingBlockManager.registerFallable(this, FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL);

        DirtHelper.registerSoil(this, DirtHelper.GRAVELLIKE);
    }

}
