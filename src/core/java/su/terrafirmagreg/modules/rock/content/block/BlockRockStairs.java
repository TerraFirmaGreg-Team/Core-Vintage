package su.terrafirmagreg.modules.rock.content.block;

import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockStairs;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public class BlockRockStairs extends BaseBlockStairs implements IRockEntry {

  private final RockType type;

  public BlockRockStairs(Block model, RockType type) {
    super(BlockSettings.of(model)
      .registryKey(type.getRegistryKey("cobble/stairs"))
      .hardness(type.getHardness(6f))
      .sound(SoundType.STONE)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .addOreDict("stairs")
      .addOreDict("stairs", model)
    );

    this.type = type;

  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    // Prevents cobble stairs from falling
  }


  @Override
  public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
    // Prevents chiseled smooth stone stairs from collapsing
  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    // Prevents cobble stairs from falling
  }

}
