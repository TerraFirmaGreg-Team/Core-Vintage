package net.dries007.tfc.module.rock.common.blocks;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import net.dries007.tfc.module.rock.api.type.RockType;
import net.dries007.tfc.module.rock.api.variant.block.RockBlockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRockSand extends BlockRockFallable {

    public BlockRockSand(RockBlockVariant variant, RockType type) {
        super(Material.SAND, variant, type);

        FallingBlockManager.registerFallable(this, FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL);

        setSoundType(SoundType.SAND);

        DirtHelper.registerSoil(this.getDefaultState().getBlock(), DirtHelper.SANDLIKE);
    }

}
