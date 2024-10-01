package su.terrafirmagreg.modules.soil.object.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.data.ToolClasses;

public class BlockSoilPeat extends BaseBlock {

  public BlockSoilPeat() {
    super(Settings.of(Material.GROUND));

    getSettings()
      .registryKey("soil/peat")
      .harvestLevel(ToolClasses.SHOVEL, 0)
      .sound(SoundType.GROUND)
      .oreDict("peat")
      .hardness(0.6F);

    BlockUtils.setFireInfo(this, 5, 10);
    //DirtHelper.registerSoil(this.getDefaultState().get(), DirtHelper.GRAVELLIKE);
  }

}
