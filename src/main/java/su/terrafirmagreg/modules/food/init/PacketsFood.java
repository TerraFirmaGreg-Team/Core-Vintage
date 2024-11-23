package su.terrafirmagreg.modules.food.init;

import su.terrafirmagreg.api.network.IPacketRegistry;
import su.terrafirmagreg.modules.food.network.SCPacketFoodStatsReplace;
import su.terrafirmagreg.modules.food.network.SCPacketFoodStatsUpdate;

import net.minecraftforge.fml.relauncher.Side;

public final class PacketsFood {

  public static void onRegister(IPacketRegistry registry) {
    registry.register(Side.CLIENT, SCPacketFoodStatsReplace.class);
    registry.register(Side.CLIENT, SCPacketFoodStatsUpdate.class);
  }
}
