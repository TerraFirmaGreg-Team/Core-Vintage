package su.terrafirmagreg.modules.core.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;

import net.minecraft.block.material.Material;

public class BlockDebug extends BaseBlock {

    public BlockDebug() {
        super(Settings.of().material(Material.SPONGE));
    }

    @Override
    public String getName() {
        return "core/debug";
    }

}
