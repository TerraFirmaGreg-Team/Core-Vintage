package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlockDoor;

import net.minecraft.block.material.Material;

public class BlockCellarDoor extends BaseBlockDoor {

    public BlockCellarDoor() {
        super(Settings.of()
                .material(Material.WOOD)
                .hardness(2F));
    }

    @Override
    public String getName() {
        return "device/cellar/door";
    }
}
