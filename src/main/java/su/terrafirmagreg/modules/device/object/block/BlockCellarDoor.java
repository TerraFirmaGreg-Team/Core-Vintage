package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockDoor;
import su.terrafirmagreg.api.util.BlockUtils;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCellarDoor extends BaseBlockDoor {

  public BlockCellarDoor() {
    super(Settings.of(Material.WOOD));

    getSettings()
            .registryKey("device/cellar/door")
            .sound(SoundType.WOOD)
            .hardness(2F);

    BlockUtils.setFireInfo(this, 5, 20);
  }
}
