package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.wood.api.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.block.BlockChest;
import net.minecraft.block.SoundType;

import lombok.Getter;

@Getter
public class BlockWoodChestTrap extends BlockWoodChest implements IWoodEntry {


  public BlockWoodChestTrap(WoodType type) {
    super(type, Type.TRAP);

    getSettings()
      .registryKey(type.getRegistryKey("chest_trapped"))
      .customResource(type.getResource("chest_trapped"))
      .ignoresProperties(BlockChest.FACING)
      .sound(SoundType.WOOD)
      .hardness(2.5f)
      .capability(CapabilityProviderSize.of(Size.LARGE, Weight.MEDIUM))
      .fireInfo(5, 20)
      .oreDict("chest")
      .oreDict("chest", "wood")
      .oreDict("chest", "wood", type);
  }
}
