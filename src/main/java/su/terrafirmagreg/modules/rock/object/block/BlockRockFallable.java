package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import java.util.Random;

public abstract class BlockRockFallable extends BlockRock {

  public BlockRockFallable(Settings settings, RockBlockVariant variant, RockType type) {
    super(settings, variant, type);

  }

  @SideOnly(Side.CLIENT)
  @Override
  public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    if (rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
      double d0 = (float) pos.getX() + rand.nextFloat();
      double d1 = (double) pos.getY() - 0.05D;
      double d2 = (float) pos.getZ() + rand.nextFloat();
      world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D,
              getStateId(state));
    }
  }
}
