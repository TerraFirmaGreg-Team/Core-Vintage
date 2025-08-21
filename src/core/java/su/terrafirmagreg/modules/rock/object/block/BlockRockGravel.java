package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockFalling;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager.Specification;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;
import su.terrafirmagreg.modules.rock.init.ItemsRock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import lombok.Getter;

import java.util.Random;

@Getter
public class BlockRockGravel extends BaseBlockFalling implements IRockEntry {

  public static final String NAME = "gravel";
  protected final RockType type;

  public BlockRockGravel(RockType type) {
    super(BlockSettings.of()
      .material(Material.SAND)
      .registryKey(type.getRegistryKey(NAME))
      .hardness(type.getHardness(6f))
      .sound(SoundType.GROUND)
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .addOreDict(NAME)
      .addOreDict(NAME, type)
    );

    this.type = type;

    //DirtHelper.registerSoil(this, DirtHelper.GRAVELLIKE);
    FallingBlockManager.registerFallable(this, Specification.VERTICAL_AND_HORIZONTAL);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    if (fortune > 3) {
      fortune = 3;
    }

    if (rand.nextInt(10 - fortune * 3) == 0) {
      return Items.FLINT;
    }

    return ItemsRock.GRAVEL_LAYER.get(type);
  }

}
