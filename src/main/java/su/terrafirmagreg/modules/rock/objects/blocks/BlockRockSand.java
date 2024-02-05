package su.terrafirmagreg.modules.rock.objects.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import net.dries007.tfc.api.util.FallingBlockManager;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

public class BlockRockSand extends BlockRockFallable {

    public BlockRockSand(RockBlockVariant variant, RockType type) {
        super(Material.SAND, variant, type);

        setSoundType(SoundType.SAND);

        FallingBlockManager.registerFallable(this, variant.getSpecification());
        //DirtHelper.registerSoil(this.getDefaultState().getBlock(), DirtHelper.SANDLIKE);
    }

}
