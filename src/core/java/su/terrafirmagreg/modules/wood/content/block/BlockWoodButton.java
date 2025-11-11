package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockButton;
import su.terrafirmagreg.framework.manager.content.provider.IProviderBlockColor;
import su.terrafirmagreg.modules.wood.api.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.type.WoodType;

import net.minecraft.block.SoundType;

import lombok.Getter;

@Getter
public class BlockWoodButton extends BaseBlockButton implements IProviderBlockColor, IWoodEntry {


  protected final WoodType type;

  public BlockWoodButton(WoodType type) {
    super(true);

    this.type = type;

    getSettings()
      .customResource(type.getResource("button"))
      .hardness(0.5F)
      .sound(SoundType.WOOD)
      .fireInfo(5, 20)
      .addOreDict("button", "wood")
      .addOreDict("button", "wood", type);
  }


}
