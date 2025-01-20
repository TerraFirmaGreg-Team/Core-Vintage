package su.terrafirmagreg.modules.core.feature.ambiental.modifier;

import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalModifierStorage;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderBlock;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;

import java.util.Optional;

import static su.terrafirmagreg.modules.core.feature.ambiental.handler.ModifierHandlerBlock.BLOCK;

public class ModifierBlock extends ModifierBase {


  public boolean affectedByDistance = false;

  protected ModifierBlock(String name, float change, float potency) {
    super(name, change, potency);

  }

  protected ModifierBlock(String name, float change, float potency, boolean affectedByDistance) {
    super(name, change, potency);
    this.affectedByDistance = affectedByDistance;

  }

  public static Optional<ModifierBlock> defined(String name, float change, float potency) {
    return Optional.of(new ModifierBlock(name, change, potency));
  }

  public static Optional<ModifierTile> none() {
    return Optional.empty();
  }

  public static void computeModifiers(EntityPlayer player, AmbientalModifierStorage storage) {
    BlockPos p = player.getPosition();
    BlockPos pos1 = new BlockPos(p.getX() - 9, p.getY() - 3, p.getZ() - 9);
    BlockPos pos2 = new BlockPos(p.getX() + 9, p.getY() + 5, p.getZ() + 9);
    World world = player.world;
    Iterable<BlockPos.MutableBlockPos> allPositions = BlockPos.getAllInBoxMutable(pos1, pos2);
    IBlockState skipState = Blocks.AIR.getDefaultState();

    for (BlockPos.MutableBlockPos pos : allPositions) {
      IBlockState state = world.getBlockState(pos);
      Block block = state.getBlock();

      if (state == skipState) {
        continue;
      }
      if (state.getBlock() instanceof BlockRockVariant) {
        continue;
      }
//            if (block instanceof BlockRock || block instanceof BlockSoil) {
//                continue;
//            }

      double distance = Math.sqrt(player.getPosition().distanceSq(pos));
      float distanceMultiplier = (float) distance / 9f;

      distanceMultiplier = Math.min(1f, Math.max(0f, distanceMultiplier));
      distanceMultiplier = 1f - distanceMultiplier;

      boolean isInside = ModifierEnvironmental.getSkylight(player) < 14
                         && ModifierEnvironmental.getBlockLight(player) > 3;
      if (isInside) {
        distanceMultiplier *= 1.3f;
      }

      final float finalDistanceMultiplier = distanceMultiplier;
      if (block instanceof IAmbientalProviderBlock provider) {
        provider.getModifier(player, pos, state).ifPresent(mod -> {
          mod.setChange(mod.getChange() * finalDistanceMultiplier);
          mod.setPotency(mod.getPotency() * finalDistanceMultiplier);
          storage.add(mod);
        });
      } else {
        for (IAmbientalProviderBlock provider : BLOCK) {
          provider.getModifier(player, pos, state).ifPresent(mod -> {
            mod.setChange(mod.getChange() * finalDistanceMultiplier);
            mod.setPotency(mod.getPotency() * finalDistanceMultiplier);
            storage.add(mod);
          });
        }
      }
    }
  }

}
