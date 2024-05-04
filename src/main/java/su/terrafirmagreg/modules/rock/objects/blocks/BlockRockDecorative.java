package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public abstract class BlockRockDecorative extends BaseBlock {

    public BlockRockDecorative(MapColor blockMapColorIn) {
        super(Settings.of()
                .material(Material.ROCK)
                .mapColor(blockMapColorIn)
                .soundType(SoundType.STONE)
                .hardness(1.0F));
    }
}
