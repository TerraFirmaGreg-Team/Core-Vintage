package su.terrafirmagreg.modules.core.objects.block;

import su.terrafirmagreg.api.base.block.BaseBlock;

import net.minecraft.block.material.Material;

public class BlockDebug extends BaseBlock {

  public BlockDebug() {
    super(Settings.of(Material.SPONGE));

    getSettings()
        .registryKey("core/debug");
  }

}
