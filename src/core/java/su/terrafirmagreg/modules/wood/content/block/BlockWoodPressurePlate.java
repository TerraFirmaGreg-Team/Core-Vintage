package su.terrafirmagreg.modules.wood.content.block;


import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockPressurePlate;
import su.terrafirmagreg.modules.wood.api.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.type.WoodType;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockWoodPressurePlate extends BaseBlockPressurePlate implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodPressurePlate(WoodType type) {
    super(Sensitivity.EVERYTHING, BlockSettings.of()
      .material(Material.WOOD)
      .customResource(type.getResource("pressure_plate"))
      .sound(SoundType.WOOD)
      .hardness(0.5F)
      .fireInfo(5, 20)
      .addOreDict("pressure_plate")
      .addOreDict("pressure_plate", "wood")
      .addOreDict("pressure_plate", "wood", type)
    );

    this.type = type;

  }
}
