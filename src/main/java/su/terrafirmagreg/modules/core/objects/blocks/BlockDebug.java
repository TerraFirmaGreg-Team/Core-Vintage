package su.terrafirmagreg.modules.core.objects.blocks;

import net.minecraft.block.material.Material;

import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.block.BlockBase;

public class BlockDebug extends BlockBase {

    public BlockDebug() {
        super(Material.SPONGE);
    }

    @Override
    public @NotNull String getName() {
        return "debug";
    }
}
