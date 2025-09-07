package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.block.BlockChest;
import net.minecraft.block.SoundType;

import lombok.Getter;

@Getter
public class BlockWoodChestTrap extends BlockWoodChest {

  public BlockWoodChestTrap(WoodType type) {
    super(Type.TRAP, type);

    getSettings()
      .registryKey(type.getRegistryKey("chest_trapped"))
      .customResource(type.getResource("chest_trapped"))
      .ignoresProperties(BlockChest.FACING)
      .sound(SoundType.WOOD)
      .hardness(2.5f)
      .capability(CapabilityProviderSize.of(Size.LARGE, Weight.MEDIUM))
      .fireInfo(5, 20)
      .addOreDict("chest", "trapped")
      .addOreDict("chest", "trapped", type)
      .addOreDict("chest", "wood");
  }
}
