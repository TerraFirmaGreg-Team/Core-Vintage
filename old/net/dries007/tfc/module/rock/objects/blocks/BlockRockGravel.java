package net.dries007.tfc.module.rock.objects.blocks;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import net.dries007.tfc.module.core.api.util.FallingBlockManager;
import net.dries007.tfc.module.rock.api.types.type.RockType;
import net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariant;
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
