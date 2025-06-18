package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockButton;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.feature.woodtype.spi.IWoodBlock;

import net.minecraft.block.SoundType;

import lombok.Getter;

@Getter
public class BlockWoodButton extends BaseBlockButton implements IWoodBlock {


  protected final WoodType type;

  public BlockWoodButton(WoodType type) {
    super(true);

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("button"))
      .customResource(type.getResource("button"))
      .hardness(0.5F)
      .sound(SoundType.WOOD)
      .fireInfo(5, 20)
      .oreDict("button", "wood")
      .oreDict("button", "wood", type);
  }

}
