package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockButton;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.block.SoundType;

import lombok.Getter;

@Getter
public class BlockWoodButton extends BaseBlockButton implements IProviderBlockColor, IWoodEntry {


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
      .addOreDict("button", "wood")
      .addOreDict("button", "wood", type);
  }


}
