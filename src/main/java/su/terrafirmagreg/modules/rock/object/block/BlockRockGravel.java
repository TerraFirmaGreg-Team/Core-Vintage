package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.init.ItemsRock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockRockGravel extends BlockRockFallable {

  public BlockRockGravel(RockBlockVariant variant, RockType type) {
    super(Settings.of(Material.SAND), variant, type);

    getSettings()
      .sound(SoundType.GROUND);
    //DirtHelper.registerSoil(this, DirtHelper.GRAVELLIKE);
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
