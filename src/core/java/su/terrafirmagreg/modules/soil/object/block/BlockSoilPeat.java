package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSoilPeat extends BaseBlock {

  public BlockSoilPeat() {
    super(Settings.of(Material.GROUND));

    getSettings()
      .registryKey("peat")
      .harvestLevel(ToolClasses.SHOVEL, 0)
      .sound(SoundType.GROUND)
      .hardness(0.6F);

    BlockUtils.addFireInfo(this, 5, 10);
    //DirtHelper.registerSoil(this.getDefaultState().get(), DirtHelper.GRAVELLIKE);
  }

}
