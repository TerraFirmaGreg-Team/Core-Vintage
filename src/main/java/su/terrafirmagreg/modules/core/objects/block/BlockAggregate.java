package su.terrafirmagreg.modules.core.objects.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;

import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import lombok.Getter;

@Getter
public class BlockAggregate extends BlockGravel implements IBlockSettings {

    protected final Settings settings;

    public BlockAggregate() {
        this.settings = Settings.of(Material.SAND)
                .registryKey("core/aggregate")
                .addOreDict("aggregate")
                .soundType(SoundType.SAND)
                .hardness(0.4f);
    }
}
