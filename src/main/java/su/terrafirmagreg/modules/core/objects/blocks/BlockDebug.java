package su.terrafirmagreg.modules.core.objects.blocks;

import su.terrafirmagreg.api.spi.block.BlockBase;

import net.minecraft.block.material.Material;

import org.jetbrains.annotations.NotNull;

public class BlockDebug extends BlockBase {

    public BlockDebug() {
        super(Material.SPONGE);
    }

    @Override
    public @NotNull String getName() {
        return "core/debug";
    }

}
