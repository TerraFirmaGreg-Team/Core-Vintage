package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRockSand extends BlockRockFallable {

    public BlockRockSand(RockBlockVariant variant, RockType type) {
        super(Settings.of(Material.SAND), variant, type);

        getSettings()
                .sound(SoundType.SAND);
        //DirtHelper.registerSoil(this.getDefaultState().get(), DirtHelper.SANDLIKE);
    }

}
