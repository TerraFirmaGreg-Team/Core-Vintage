package su.terrafirmagreg.modules.wood.object.block;


import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockPressurePlate;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockWoodPressurePlate extends BaseBlockPressurePlate implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodPressurePlate(WoodType type) {
    super(Settings.of(Material.WOOD), Sensitivity.EVERYTHING);

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("pressure_plate"))
      .customResource(type.getResource("pressure_plate"))
      .sound(SoundType.WOOD)
      .hardness(0.5F)
      .fireInfo(5, 20)
      .addOreDict("pressure_plate")
      .addOreDict("pressure_plate", "wood")
      .addOreDict("pressure_plate", "wood", type);
  }
}
