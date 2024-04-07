package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BlockBase;

import net.minecraft.block.material.Material;

import org.jetbrains.annotations.NotNull;

public class BlockCellarWall extends BlockBase {

    public BlockCellarWall() {
        super(Material.WOOD);

        setHardness(2F);
    }

    @Override
    public @NotNull String getName() {
        return "device/cellar/wall";
    }
}
