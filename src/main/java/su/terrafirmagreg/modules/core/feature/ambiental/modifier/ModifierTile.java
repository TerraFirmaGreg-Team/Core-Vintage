package su.terrafirmagreg.modules.core.feature.ambiental.modifier;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalModifierStorage;
import su.terrafirmagreg.modules.core.feature.ambiental.handler.ModifierHandlerTile;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderTile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModifierTile extends ModifierBlock {

  public ModifierTile(String name) {
    super(name);

  }

  public ModifierTile(String name, float change, float potency) {
    super(name, change, potency);

  }

  public ModifierTile(String name, float change, float potency, boolean affectedByDistance) {
    super(name, change, potency, affectedByDistance);

  }

  public static boolean hasProtection(EntityPlayer player) {
    ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
    return !stack.isEmpty();
  }

  public static void computeModifiers(EntityPlayer player, AmbientalModifierStorage storage) {
    BlockPos p = player.getPosition();
    BlockPos pos1 = new BlockPos(p.getX() - 9, p.getY() - 3, p.getZ() - 9);
    BlockPos pos2 = new BlockPos(p.getX() + 9, p.getY() + 5, p.getZ() + 9);
    World world = player.world;
    IBlockState skipState = Blocks.AIR.getDefaultState();

    for (final BlockPos.MutableBlockPos pos : BlockPos.getAllInBoxMutable(pos1, pos2)) {
      IBlockState state = world.getBlockState(pos);
      Block block = state.getBlock();

      if (state == skipState) {
        continue;
      }

      if (block.hasTileEntity(state)) {

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

        TileUtils.getTile(world, pos).ifPresent(tile -> {
          if (tile instanceof IAmbientalProviderTile provider) {
            provider.getModifier(player, tile).ifPresent(mod -> {
              mod.setChange(mod.getChange() * finalDistanceMultiplier);
              mod.setPotency(mod.getPotency() * finalDistanceMultiplier);
              storage.add(mod);
            });
          } else {
            for (IAmbientalProviderTile provider : ModifierHandlerTile.TILE) {
              provider.getModifier(player, tile).ifPresent(mod -> {
                mod.setChange(mod.getChange() * finalDistanceMultiplier);
                mod.setPotency(mod.getPotency() * finalDistanceMultiplier);
                storage.add(mod);
              });
            }
          }
        });
      }
    }
  }

}
