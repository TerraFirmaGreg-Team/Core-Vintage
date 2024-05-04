package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;

import net.minecraft.block.material.Material;

public class BlockCellarWall extends BaseBlock {

    public BlockCellarWall() {
        super(Settings.of()
                .material(Material.WOOD)
                .hardness(2F));
    }

    @Override
    public String getName() {
        return "device/cellar/wall";
    }
}
