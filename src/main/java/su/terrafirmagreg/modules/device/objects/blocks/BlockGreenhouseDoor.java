package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlockDoor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockGreenhouseDoor extends BaseBlockDoor {

    public BlockGreenhouseDoor() {
        super(Settings.of(Material.WOOD));

        getSettings()
                .registryKey("device/greenhouse/door")
                .addOreDict("greenhouse")
                .soundType(SoundType.METAL)
                .hardness(3F);
    }
}
