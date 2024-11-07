package su.terrafirmagreg.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public final class GameUtils {

  /**
   * Checks if the game is running on the client or not.
   *
   * @return Whether or not the current thread is client sided.
   */
  public static boolean isClient() {
    return FMLCommonHandler.instance().getSide().isClient();
  }

  public static boolean isServer() {
    return FMLCommonHandler.instance().getSide().isServer();
  }

  /**
   * Gets the current client difficulty.
   *
   * @return The difficulty for the client.
   */
  public static EnumDifficulty getClientDifficulty() {
    return getGameSettings().difficulty;
  }

  /**
   * Static way to get the game settings.
   *
   * @return The current game settings.
   */
  @SideOnly(Side.CLIENT)
  public static GameSettings getGameSettings() {
    return Minecraft.getMinecraft().gameSettings;
  }

  public static boolean isModLoaded(String modName) {
    return Loader.isModLoaded(modName);
  }
}
