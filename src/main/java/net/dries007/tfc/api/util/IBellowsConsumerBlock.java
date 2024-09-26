package net.dries007.tfc.api.util;

import su.terrafirmagreg.modules.device.object.tile.TileBellows;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

/**
 * Blocks(not TEs) must implement this interface in order to work with bellows and must provide an offset for them to check by calling
 * {@link TileBellows#addBellowsOffset(Vec3i)}
 */
public interface IBellowsConsumerBlock {

  /**
   * standard handlers should check if they have been accessed by belows from a legal offset
   *
   * @param offset that the bellows used to reach this block, NOT ROTATED accordingly!
   * @param facing direction the bellows output to
   * @return self-explanatory
   */
  boolean canIntakeFrom(@NotNull Vec3i offset, @NotNull EnumFacing facing);

  /**
   * @param airAmount the amount of air that the bellows give. For reference, TFC bellows always give 200.
   */
  void onAirIntake(@NotNull World world, @NotNull BlockPos pos, int airAmount);
}
