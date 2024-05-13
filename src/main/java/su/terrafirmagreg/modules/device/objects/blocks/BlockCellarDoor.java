package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlockDoor;

import net.minecraft.block.material.Material;

public class BlockCellarDoor extends BaseBlockDoor {

    public BlockCellarDoor() {
        super(Settings.of(Material.WOOD));

        getSettings()
                .registryKey("device/cellar/door")
                .hardness(2F);
    }
}
