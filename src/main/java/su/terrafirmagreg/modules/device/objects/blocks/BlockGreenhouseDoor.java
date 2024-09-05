package su.terrafirmagreg.modules.device.objects.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import su.terrafirmagreg.api.base.block.BaseBlockDoor;

public class BlockGreenhouseDoor extends BaseBlockDoor {

  public BlockGreenhouseDoor() {
    super(Settings.of(Material.WOOD));

    getSettings()
        .registryKey("device/greenhouse/door")
        .oreDict("greenhouse")
        .sound(SoundType.METAL)
        .hardness(3F);
  }
}
