package su.terrafirmagreg.modules.core.objects.blocks;

import su.terrafirmagreg.api.spi.block.ISettingsBlock;

import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import lombok.Getter;

@Getter
public class BlockAggregate extends BlockGravel implements ISettingsBlock {

    protected final Settings settings;

    public BlockAggregate() {
        this.settings = Settings.of(Material.SAND)
                .registryKey("core/aggregate")
                .addOreDict("aggregate")
                .soundType(SoundType.SAND)
                .hardness(0.4f);
    }
}
