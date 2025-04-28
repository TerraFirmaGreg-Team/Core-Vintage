package su.terrafirmagreg.api.util;

import su.terrafirmagreg.api.library.Location;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public final class SoundUtils {

  public static void playBlockPlaceSound(World world, EntityPlayer player, IBlockState state, Location location) {

    player.world.playSound(
      player, location.getBlockPos(),
      state.getBlock().getSoundType(state, world, location.getBlockPos(), player).getPlaceSound(),
      SoundCategory.NEUTRAL, 1.5F, 0.9F
    );
  }

  public static void playDing(World world, EntityPlayer player) {

    player.world.playSound(player, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 1, 1);
  }

  public static void playClick(World world, EntityPlayer player) {

    player.world.playSound(player, player.getPosition(), SoundEvents.BLOCK_LEVER_CLICK, player.getSoundCategory(), 1, 1);
  }

  public static void playClang(World world, EntityPlayer player) {

    player.world.playSound(player, player.getPosition(), SoundEvents.BLOCK_ANVIL_LAND, player.getSoundCategory(), 0.9F, 1.1F);
  }

  public static void playWarp(World world, EntityPlayer player) {

    player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 0.25F, 0.75F);
  }
}
